package quick.pager.pcloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import quick.pager.pcloud.constants.IConsts;
import quick.pager.pcloud.constants.LConsts;
import quick.pager.pcloud.mapper.MenuMapper;
import quick.pager.pcloud.model.MenuDO;
import quick.pager.pcloud.dto.MenuDTO;
import quick.pager.pcloud.request.MenuOtherRequest;
import quick.pager.pcloud.request.MenuSaveRequest;
import quick.pager.pcloud.model.response.ResponseResult;
import quick.pager.pcloud.service.MenuService;
import quick.pager.pcloud.utils.Assert;

/**
 * MenuServiceImpl
 *
 * @author siguiyang
 */
@Service
public class MenuServiceImpl implements MenuService {

    @Resource
    private MenuMapper menuMapper;

    // region 数据转换

    /**
     * request -> DO
     *
     * @param request 请求参数
     * @return MenuDO
     */
    private MenuDO convert(final MenuSaveRequest request) {

        return MenuDO.builder()
                .id(request.getId())
                .component(request.getComponent())
                .hidden(request.getHidden())
                .icon(request.getIcon())
                .name(request.getName())
                .parentId(Objects.isNull(request.getParentId()) ? LConsts.ZERO : request.getParentId())
                .path(request.getPath())
                .redirect(request.getRedirect())
                .router(request.getRouter())
                .sequence(request.getSequence())
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
     * @param menuDO menuDO
     * @return MenuDTO
     */
    private MenuDTO convert(final MenuDO menuDO) {

        return MenuDTO.builder()
                .id(menuDO.getId())
                .parentId(menuDO.getParentId())
                .label(menuDO.getName())
                .component(menuDO.getComponent())
                .hidden(menuDO.getHidden())
                .icon(menuDO.getIcon())
                .name(menuDO.getName())
                .parentId(menuDO.getParentId())
                .path(menuDO.getPath())
                .redirect(menuDO.getRedirect())
                .router(menuDO.getRouter())
                .sequence(menuDO.getSequence())
                .gmtModifiedDate(menuDO.getGmtModifiedDate())
                .gmtModifiedName(menuDO.getGmtModifiedName())
                .build();
    }

    // endregion

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<Long> create(final MenuSaveRequest request) {
        MenuDO menuDO = this.convert(request);
        Assert.isTrue(this.menuMapper.insert(menuDO) > 0, () -> "创建菜单失败");

        return ResponseResult.toSuccess(menuDO.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<Long> modify(final MenuSaveRequest request) {
        MenuDO menuDO = this.convert(request);
        this.menuMapper.updateById(menuDO);

        return ResponseResult.toSuccess(menuDO.getId());
    }

    @Override
    public ResponseResult<List<MenuDTO>> queryList(final MenuOtherRequest request) {

        LambdaQueryWrapper<MenuDO> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(request.getName())) {
            wrapper.likeRight(MenuDO::getName, request.getName());
        }
        wrapper.orderByAsc(MenuDO::getSequence);
        List<MenuDO> menuDOS = this.menuMapper.selectList(wrapper);
        List<MenuDTO> parentDTOS = Optional.ofNullable(menuDOS).orElse(Collections.emptyList()).stream()
                .filter(item -> IConsts.ZERO == LConsts.ZERO.compareTo(item.getParentId()))
                .map(this::convert)
                .collect(Collectors.toList());
        Map<Long, List<MenuDTO>> childrenMap = Optional.ofNullable(menuDOS).orElse(Collections.emptyList()).stream()
                .filter(item -> IConsts.ZERO != LConsts.ZERO.compareTo(item.getParentId()))
                .map(this::convert)
                .collect(Collectors.groupingBy(MenuDTO::getParentId, Collectors.toList()));

        // 数据转换
        toTree(parentDTOS, childrenMap);
        parentDTOS.forEach(menuDTO -> menuDTO.setParentId(null));

        return ResponseResult.toSuccess(parentDTOS, 0L);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<Long> delete(final Long id) {
        this.menuMapper.deleteById(id);
        return ResponseResult.toSuccess(id);
    }

    // region 私有方法

    /**
     * 树形结构转换
     *
     * @param parentResp  顶级菜单
     * @param childrenMap 孩子节点
     */
    private void toTree(final List<MenuDTO> parentResp, final Map<Long, List<MenuDTO>> childrenMap) {
        parentResp.forEach(item -> {
            List<MenuDTO> list = childrenMap.get(item.getId());
            toTree(Optional.ofNullable(list).orElse(Collections.emptyList()), childrenMap);
            item.setChildren(list);
        });
    }

    // endregion
}
