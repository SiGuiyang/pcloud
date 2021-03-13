package quick.pager.pcloud.service;

import java.util.List;
import quick.pager.pcloud.dto.PostDTO;
import quick.pager.pcloud.model.response.ResponseResult;
import quick.pager.pcloud.model.request.PostOtherRequest;
import quick.pager.pcloud.model.request.PostPageRequest;
import quick.pager.pcloud.model.request.PostSaveRequest;

/**
 * 岗位服务
 *
 * @author siguiyang
 */
public interface PostService {

    /**
     * 查询列表
     */
    ResponseResult<List<PostDTO>> queryPage(final PostPageRequest request);

    /**
     * 查询列表，无分页
     */
    ResponseResult<List<PostDTO>> queryList(final PostOtherRequest request);

    /**
     * 树结构
     */
    ResponseResult<List<PostDTO>> queryTree(final PostOtherRequest request);

    /**
     * 新增
     */
    ResponseResult<Long> create(final PostSaveRequest request);

    /**
     * 修改
     */
    ResponseResult<Long> modify(final PostSaveRequest request);

    /**
     * 删除
     *
     * @param id 主键
     */
    ResponseResult<Long> delete(final Long id);
}
