package quick.pager.pcloud.service;

import java.util.List;
import quick.pager.pcloud.request.ClassificationAdminPageRequest;
import quick.pager.pcloud.request.ClassificationAdminSaveRequest;
import quick.pager.pcloud.response.ResponseResult;
import quick.pager.pcloud.vo.ClassificationAdminVO;

/**
 * 分类
 *
 * @author siguiyang
 */
public interface ClassificationAdminService {

    /**
     * 列表
     *
     * @param request 请求参数
     */
    ResponseResult<List<ClassificationAdminVO>> page(final ClassificationAdminPageRequest request);

    /**
     * 新增
     *
     * @param request 请求参数
     */
    ResponseResult<Long> create(final ClassificationAdminSaveRequest request);

    /**
     * 编辑
     *
     * @param request 请求参数
     */
    ResponseResult<Long> modify(final ClassificationAdminSaveRequest request);

    /**
     * 修改状态
     *
     * @param id 主键
     */
    ResponseResult<Long> status(final Long id);

    /**
     * 删除
     *
     * @param id 主键
     */
    ResponseResult<Long> delete(final Long id);
}
