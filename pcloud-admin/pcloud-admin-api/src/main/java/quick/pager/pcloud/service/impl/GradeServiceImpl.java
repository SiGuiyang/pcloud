package quick.pager.pcloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import quick.pager.pcloud.dto.GradeDTO;
import quick.pager.pcloud.mapper.GradeMapper;
import quick.pager.pcloud.model.GradeDO;
import quick.pager.pcloud.model.response.ResponseResult;
import quick.pager.pcloud.request.GradeOtherRequest;
import quick.pager.pcloud.request.GradePageRequest;
import quick.pager.pcloud.request.GradeSaveRequest;
import quick.pager.pcloud.service.GradeService;
import quick.pager.pcloud.utils.Assert;

@Service
public class GradeServiceImpl implements GradeService {

    @Resource
    private GradeMapper gradeMapper;

    // region 数据转换

    /**
     * request -> DO
     *
     * @param request 请求参数
     * @return MenuDO
     */
    private GradeDO convert(final GradeSaveRequest request) {

        return GradeDO.builder()
                .id(request.getId())
                .name(request.getName())
                .gmtCreatedName(request.getGmtCreatedName())
                .gmtModifiedName(request.getGmtModifiedName())
                .gmtCreatedDate(request.getGmtCreatedDate())
                .gmtModifiedDate(request.getGmtModifiedDate())
                .deleteStatus(Boolean.FALSE)
                .build();
    }

    /**
     * DO -> DTO
     *
     * @param gradeDO GradeDO
     * @return GradeDTO
     */
    private GradeDTO convert(final GradeDO gradeDO) {
        return GradeDTO.builder()
                .id(gradeDO.getId())
                .name(gradeDO.getName())
                .gmtModifiedDate(gradeDO.getGmtModifiedDate())
                .gmtModifiedName(gradeDO.getGmtModifiedName())
                .build();
    }

    // endregion

    @Override
    public ResponseResult<List<GradeDTO>> queryPage(final GradePageRequest request) {
        LambdaQueryWrapper<GradeDO> wrapper = new LambdaQueryWrapper<GradeDO>()
                .likeRight(StringUtils.isNotBlank(request.getName()), GradeDO::getName, request.getName());
        wrapper.orderByDesc(GradeDO::getGmtCreatedDate);

        Integer count = this.gradeMapper.selectCount(wrapper);

        List<GradeDTO> dtos = Collections.emptyList();
        if (count > 0) {
            List<GradeDO> records = this.gradeMapper.selectPage(new Page<>(request.getPage(), request.getPageSize()), wrapper).getRecords();

            dtos = records.stream().map(this::convert).collect(Collectors.toList());
        }

        return ResponseResult.toSuccess(dtos, count);
    }

    @Override
    public ResponseResult<List<GradeDTO>> queryList(final GradeOtherRequest request) {
        LambdaQueryWrapper<GradeDO> wrapper = new LambdaQueryWrapper<GradeDO>()
                .in(CollectionUtils.isNotEmpty(request.getIds()), GradeDO::getId, request.getIds())
                .likeRight(StringUtils.isNotBlank(request.getName()), GradeDO::getName, request.getName());

        List<GradeDO> gradeDOS = this.gradeMapper.selectList(wrapper);

        return ResponseResult.toSuccess(gradeDOS.stream().map(this::convert).collect(Collectors.toList()));
    }

    @Override
    public ResponseResult<Long> create(final GradeSaveRequest request) {
        GradeDO gradeDO = this.convert(request);
        Assert.isTrue(this.gradeMapper.insert(gradeDO) > 0, () -> "创建职级失败");
        return ResponseResult.toSuccess(gradeDO.getId());
    }

    @Override
    public ResponseResult<Long> modify(final GradeSaveRequest request) {
        GradeDO gradeDO = this.convert(request);
        Assert.isTrue(this.gradeMapper.updateById(gradeDO) > 0, () -> "更新职级失败");
        return ResponseResult.toSuccess(gradeDO.getId());
    }

    @Override
    public ResponseResult<Long> delete(final Long id) {
        Assert.isTrue(this.gradeMapper.deleteById(id) > 0, () -> "删除职级失败");
        return ResponseResult.toSuccess(id);
    }
}
