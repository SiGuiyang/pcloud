package quick.pager.pcloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import quick.pager.pcloud.enums.StateEnums;
import quick.pager.pcloud.mapper.ClassificationMapper;
import quick.pager.pcloud.model.ClassificationDO;
import quick.pager.pcloud.request.ClassificationAdminPageRequest;
import quick.pager.pcloud.request.ClassificationAdminSaveRequest;
import quick.pager.pcloud.response.ResponseResult;
import quick.pager.pcloud.service.ClassificationAdminService;
import quick.pager.pcloud.utils.Assert;
import quick.pager.pcloud.vo.ClassificationAdminVO;

@Service
public class ClassificationAdminServiceImpl implements ClassificationAdminService {

    @Resource
    private ClassificationMapper classificationMapper;


    // region 数据转换

    private ClassificationDO convert(final ClassificationAdminSaveRequest request) {
        return ClassificationDO.builder()
                .id(request.getId())
                .name(request.getName())
                .state(request.getState())
                .sequence(request.getSequence())
                .gmtCreatedName(request.getGmtCreatedName())
                .gmtModifiedName(request.getGmtModifiedName())
                .gmtCreatedDate(request.getGmtCreatedDate())
                .gmtModifiedDate(request.getGmtModifiedDate())
                .deleteStatus(Boolean.FALSE)
                .build();
    }

    private ClassificationAdminVO convert(final ClassificationDO classificationDO) {

        return ClassificationAdminVO.builder()
                .id(classificationDO.getId())
                .name(classificationDO.getName())
                .state(classificationDO.getState())
                .sequence(classificationDO.getSequence())
                .gmtModifiedDate(classificationDO.getGmtModifiedDate())
                .gmtModifiedName(classificationDO.getGmtModifiedName())
                .build();
    }
    // endregion

    @Override
    public ResponseResult<List<ClassificationAdminVO>> page(final ClassificationAdminPageRequest request) {

        LambdaQueryWrapper<ClassificationDO> wrapper = new LambdaQueryWrapper<ClassificationDO>()
                .eq(StringUtils.isNotBlank(request.getName()), ClassificationDO::getName, request.getName())
                .eq(Objects.nonNull(request.getState()), ClassificationDO::getState, request.getState())
                .orderByDesc(ClassificationDO::getSequence);

        Integer total = this.classificationMapper.selectCount(wrapper);

        List<ClassificationAdminVO> vos = Collections.emptyList();
        if (total > 0) {
            Page<ClassificationDO> page = this.classificationMapper.selectPage(new Page<>(request.getPage(), request.getPageSize(), false), wrapper);

            vos = page.getRecords().stream().map(this::convert).collect(Collectors.toList());
        }

        return ResponseResult.toSuccess(vos, total);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<Long> create(final ClassificationAdminSaveRequest request) {
        ClassificationDO classificationDO = this.convert(request);
        Assert.isTrue(this.classificationMapper.insert(classificationDO) > 0, () -> "新增分类失败");
        return ResponseResult.toSuccess(classificationDO.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<Long> modify(final ClassificationAdminSaveRequest request) {
        ClassificationDO classificationDO = this.convert(request);
        Assert.isTrue(this.classificationMapper.updateById(classificationDO) > 0, () -> "编辑分类失败");
        return ResponseResult.toSuccess(classificationDO.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<Long> status(final Long id) {
        ClassificationDO classificationDO = this.classificationMapper.selectById(id);

        Assert.isTrue(Objects.nonNull(classificationDO), () -> "分类不存在");

        Integer state = classificationDO.getState();
        StateEnums stateEnums = StateEnums.parse(state);
        if (stateEnums.equals(StateEnums.CLOSE)) {
            state = StateEnums.OPEN.getCode();
        } else {
            state = StateEnums.CLOSE.getCode();
        }

        Assert.isTrue(this.classificationMapper.updateById(ClassificationDO.builder().id(id).state(state).build()) > 0, () -> "更新状态失败");

        return ResponseResult.toSuccess(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<Long> delete(final Long id) {
        ClassificationDO classificationDO = this.classificationMapper.selectById(id);

        Assert.isTrue(Objects.nonNull(classificationDO), () -> "分类不存在");
        Assert.isTrue(this.classificationMapper.deleteById(id) > 0, () -> "删除分类失败");

        return ResponseResult.toSuccess(id);
    }
}
