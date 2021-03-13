package quick.pager.pcloud.service;

import java.util.List;
import quick.pager.pcloud.dto.ResourceDTO;
import quick.pager.pcloud.model.response.ResponseResult;
import quick.pager.pcloud.model.request.ResourceOtherRequest;
import quick.pager.pcloud.model.request.ResourcePageRequest;
import quick.pager.pcloud.model.request.ResourceSaveRequest;

/**
 * 资源服务
 *
 * @author siguiyang
 * @version 3.0
 */
public interface ResourceService {

    /**
     * 查询列表
     */
    ResponseResult<List<ResourceDTO>> queryPage(final ResourcePageRequest request);

    /**
     * 查询列表无分页
     */
    ResponseResult<List<ResourceDTO>> queryList(final ResourceOtherRequest request);

    /**
     * 查询列表无分页
     * 树结构
     */
    ResponseResult<List<ResourceDTO>> queryTree(final ResourceOtherRequest request);

    /**
     * 新增
     */
    ResponseResult<Long> create(final ResourceSaveRequest request);

    /**
     * 修改
     */
    ResponseResult<Long> modify(final ResourceSaveRequest request);

    /**
     * 删除
     *
     * @param id 主键
     */
    ResponseResult<Long> delete(final Long id);
}
