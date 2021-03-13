package quick.pager.pcloud.controller;

import java.util.List;
import java.util.Objects;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import quick.pager.pcloud.constants.ResponseStatus;
import quick.pager.pcloud.dto.PostDTO;
import quick.pager.pcloud.model.response.ResponseResult;
import quick.pager.pcloud.model.request.PostOtherRequest;
import quick.pager.pcloud.model.request.PostPageRequest;
import quick.pager.pcloud.model.request.PostSaveRequest;
import quick.pager.pcloud.service.PostService;
import quick.pager.pcloud.utils.Assert;

/**
 * 岗位管理
 *
 * @author siguiyang
 */
@RestController
@RequestMapping("/admin")
public class PostController {

    @Resource
    private PostService postService;

    /**
     * 获取岗位
     */
    @PostMapping("/post/page")
    public ResponseResult<List<PostDTO>> page(@RequestBody PostPageRequest request) {
        return postService.queryPage(request);
    }

    /**
     * 获取岗位
     */
    @PostMapping("/post/list")
    public ResponseResult<List<PostDTO>> list(@RequestBody PostOtherRequest request) {
        return postService.queryList(request);
    }

    /**
     * 获取岗位
     */
    @PostMapping("/post/tree")
    public ResponseResult<List<PostDTO>> tree(@RequestBody PostOtherRequest request) {
        return postService.queryTree(request);
    }

    /**
     * 新增岗位
     */
    @PostMapping("/post/create")
    public ResponseResult<Long> create(@RequestBody PostSaveRequest request) {
        return postService.create(request);
    }

    /**
     * 修改岗位
     */
    @PutMapping("/post/modify")
    public ResponseResult<Long> modify(@RequestBody PostSaveRequest request) {
        Assert.isTrue(Objects.nonNull(request.getId()), () -> ResponseStatus.PARAMS_EXCEPTION);
        return postService.modify(request);
    }

    /**
     * 删除岗位
     *
     * @param id 角色主键
     */
    @DeleteMapping("/post/{id}/delete")
    public ResponseResult<Long> delete(@PathVariable("id") Long id) {
        return postService.delete(id);
    }
}
