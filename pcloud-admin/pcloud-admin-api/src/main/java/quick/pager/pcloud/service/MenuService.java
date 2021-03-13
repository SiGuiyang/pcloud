package quick.pager.pcloud.service;

import java.util.List;
import quick.pager.pcloud.dto.MenuDTO;
import quick.pager.pcloud.model.request.MenuOtherRequest;
import quick.pager.pcloud.model.request.MenuSaveRequest;
import quick.pager.pcloud.model.response.ResponseResult;

/**
 * 菜单服务
 *
 * @author siguiyang
 */
public interface MenuService {

    /**
     * 查询列表无分页
     */
    ResponseResult<List<MenuDTO>> queryList(final MenuOtherRequest request);

    /**
     * 新增
     */
    ResponseResult<Long> create(final MenuSaveRequest request);

    /**
     * 修改
     */
    ResponseResult<Long> modify(final MenuSaveRequest request);

    /**
     * 删除
     *
     * @param id 主键
     */
    ResponseResult<Long> delete(final Long id);
}
