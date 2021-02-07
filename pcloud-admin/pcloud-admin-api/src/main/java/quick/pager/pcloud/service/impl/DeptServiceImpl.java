package quick.pager.pcloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import quick.pager.pcloud.constants.IConsts;
import quick.pager.pcloud.constants.LConsts;
import quick.pager.pcloud.dto.DeptDTO;
import quick.pager.pcloud.mapper.DeptMapper;
import quick.pager.pcloud.mapper.SysUserMapper;
import quick.pager.pcloud.model.DeptDO;
import quick.pager.pcloud.model.SysUserDO;
import quick.pager.pcloud.model.response.ResponseResult;
import quick.pager.pcloud.request.DeptOtherRequest;
import quick.pager.pcloud.request.DeptPageRequest;
import quick.pager.pcloud.request.DeptSaveRequest;
import quick.pager.pcloud.service.DeptService;
import quick.pager.pcloud.utils.Assert;
import quick.pager.pcloud.utils.DateUtils;

@Service
public class DeptServiceImpl implements DeptService {

    @Resource
    private DeptMapper deptMapper;
    @Resource
    private SysUserMapper sysUserMapper;

    // region 数据转换

    /**
     * request -> DO
     *
     * @param request 请求参数
     * @return DeptDO
     */
    private DeptDO convert(final DeptSaveRequest request) {

        return DeptDO.builder()
                .id(request.getId())
                .parentId(request.getParentId())
                .name(request.getName())
                .managerId(request.getManagerId())
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
     * @param deptDO DeptDO
     * @return DeptDTO
     */
    private DeptDTO convert(final DeptDO deptDO) {
        DeptDTO deptDTO = DeptDTO.builder()
                .id(deptDO.getId())
                .parentId(deptDO.getParentId())
                .name(deptDO.getName())
                .label(deptDO.getName())
                .gmtModifiedName(deptDO.getGmtModifiedName())
                .gmtModifiedDate(deptDO.getGmtModifiedDate())
                .build();

        if (Objects.nonNull(deptDO.getParentId())
                && IConsts.ZERO != LConsts.ZERO.compareTo(deptDO.getParentId())) {
            DeptDO dept = this.deptMapper.selectById(deptDO.getParentId());
            deptDTO.setParentName(dept.getName());
        }

        if (Objects.nonNull(deptDO.getManagerId())
                && IConsts.ZERO != LConsts.ZERO.compareTo(deptDO.getManagerId())) {
            SysUserDO sysUserDO = this.sysUserMapper.selectById(deptDO.getManagerId());
            deptDTO.setManagerName(sysUserDO.getName());
        }

        // 为了下拉子节点列表，强制设置parentId未空
        if (IConsts.ZERO == LConsts.ZERO.compareTo(deptDO.getParentId())) {
            deptDTO.setParentId(null);
        }

        return deptDTO;
    }

    // endregion

    @Override
    public ResponseResult<List<DeptDTO>> queryPage(final DeptPageRequest request) {
        LambdaQueryWrapper<DeptDO> wrapper = new LambdaQueryWrapper<DeptDO>()
                .likeRight(StringUtils.isNotBlank(request.getName()), DeptDO::getName, request.getName());
        wrapper.orderByDesc(DeptDO::getGmtCreatedDate);

        Integer count = this.deptMapper.selectCount(wrapper);

        List<DeptDTO> dtos = Collections.emptyList();
        if (count > 0) {
            List<DeptDO> records = this.deptMapper.selectPage(new Page<>(request.getPage(), request.getPageSize()), wrapper).getRecords();

            dtos = records.stream().map(this::convert).collect(Collectors.toList());
        }

        return ResponseResult.toSuccess(dtos, count);
    }

    @Override
    public ResponseResult<List<DeptDTO>> queryList(final DeptOtherRequest request) {
        LambdaQueryWrapper<DeptDO> wrapper = new LambdaQueryWrapper<DeptDO>()
                .in(CollectionUtils.isNotEmpty(request.getIds()), DeptDO::getId, request.getIds())
                .likeRight(StringUtils.isNotBlank(request.getName()), DeptDO::getName, request.getName());

        List<DeptDO> DeptDOS = this.deptMapper.selectList(wrapper);

        return ResponseResult.toSuccess(DeptDOS.stream().map(this::convert).collect(Collectors.toList()));
    }

    @Override
    public List<DeptDTO> queryTree() {
        List<DeptDO> deptDOS = this.deptMapper.selectList(Wrappers.emptyWrapper());

        List<DeptDTO> parentDTOS = Optional.ofNullable(deptDOS).orElse(Collections.emptyList()).stream()
                .filter(item -> IConsts.ZERO == LConsts.ZERO.compareTo(item.getParentId()))
                .map(this::convert)
                .collect(Collectors.toList());
        Map<Long, List<DeptDTO>> childrenMap = Optional.ofNullable(deptDOS).orElse(Collections.emptyList()).stream()
                .filter(item -> IConsts.ZERO != LConsts.ZERO.compareTo(item.getParentId()))
                .map(this::convert)
                .collect(Collectors.groupingBy(DeptDTO::getParentId, Collectors.toList()));

        // 数据转换
        toTree(parentDTOS, childrenMap);
        return parentDTOS;
    }

    @Override
    public ResponseResult<Long> create(final DeptSaveRequest request) {
        DeptDO deptDO = this.convert(request);
        deptDO.setEstablishedTime(DateUtils.dateTime());
        Assert.isTrue(this.deptMapper.insert(deptDO) > 0, () -> "创建部门失败");
        return ResponseResult.toSuccess(deptDO.getId());
    }

    @Override
    public ResponseResult<Long> modify(final DeptSaveRequest request) {
        DeptDO deptDO = this.convert(request);
        Assert.isTrue(this.deptMapper.updateById(deptDO) > 0, () -> "更新部门失败");
        return ResponseResult.toSuccess(deptDO.getId());
    }

    @Override
    public ResponseResult<Long> delete(final Long id) {
        Assert.isTrue(this.deptMapper.deleteById(id) > 0, () -> "删除部门失败");
        return ResponseResult.toSuccess(id);
    }

    // region 私有方法

    /**
     * 树形结构转换
     *
     * @param parentResp  顶级菜单
     * @param childrenMap 孩子节点
     */
    private void toTree(final List<DeptDTO> parentResp, final Map<Long, List<DeptDTO>> childrenMap) {
        parentResp.forEach(item -> {
            List<DeptDTO> list = childrenMap.get(item.getId());
            toTree(Optional.ofNullable(list).orElse(Collections.emptyList()), childrenMap);
            item.setChildren(list);
        });
    }

    // endregion
}
