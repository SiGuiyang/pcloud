package quick.pager.pcloud.service;

import java.util.List;
import java.util.Map;
import quick.pager.pcloud.dto.OpenPermissionDTO;
import quick.pager.pcloud.dto.OpenResourceDTO;
import quick.pager.pcloud.response.ResponseResult;
import quick.pager.pcloud.request.OpenAccountResourceRequest;
import quick.pager.pcloud.request.OpenPermissionPageRequest;
import quick.pager.pcloud.request.OpenResourceOtherRequest;
import quick.pager.pcloud.request.OpenResourcePageRequest;
import quick.pager.pcloud.request.OpenResourceSaveRequest;

/**
 * 资源服务
 *
 * @author siguiyang
 * @version 3.0
 */
public interface OpenResourceService {

    /**
     * 查询列表
     */
    ResponseResult<List<OpenResourceDTO>> queryPage(final OpenResourcePageRequest request);

    /**
     * 查询列表无分页
     */
    ResponseResult<List<OpenResourceDTO>> queryList(final OpenResourceOtherRequest request);

    /**
     * 新增
     */
    ResponseResult<Long> create(final OpenResourceSaveRequest request);

    /**
     * 修改
     */
    ResponseResult<Long> modify(final OpenResourceSaveRequest request);

    /**
     * 开放账户权限
     *
     * @param request 请求参数
     */
    ResponseResult<List<OpenPermissionDTO>> permission(final OpenPermissionPageRequest request);

    /**
     * 开放账户授权
     *
     * @param request 请求参数
     */
    ResponseResult<Long> grant(final OpenAccountResourceRequest request);

    /**
     * 手动刷新缓存
     */
    ResponseResult<String> refresh(final Long id);

    /**
     * 删除
     *
     * @param id 主键
     */
    ResponseResult<Long> delete(final Long id);

    ResponseResult<Map<String,List<String>>> permissions();

}
