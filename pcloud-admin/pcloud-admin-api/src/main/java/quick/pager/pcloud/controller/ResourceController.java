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
import quick.pager.pcloud.dto.ResourceDTO;
import quick.pager.pcloud.model.response.ResponseResult;
import quick.pager.pcloud.request.ResourceOtherRequest;
import quick.pager.pcloud.request.ResourcePageRequest;
import quick.pager.pcloud.request.ResourceSaveRequest;
import quick.pager.pcloud.service.ResourceService;
import quick.pager.pcloud.utils.Assert;

/**
 * 资源管理
 *
 * @author siguiyang
 * @version 3.0
 */
@RestController
@RequestMapping("/admin")
public class ResourceController {

    @Resource
    private ResourceService resourceService;

    /**
     * 获取系统资源
     */
    @PostMapping("resource/page")
    public ResponseResult<List<ResourceDTO>> page(@RequestBody ResourcePageRequest request) {
        return resourceService.queryPage(request);
    }

    /**
     * 获取系统资源
     */
    @PostMapping("/resource/list")
    public ResponseResult<List<ResourceDTO>> list(@RequestBody ResourceOtherRequest request) {
        return resourceService.queryList(request);
    }

    /**
     * 获取系统资源
     */
    @PostMapping("/resource/tree")
    public ResponseResult<List<ResourceDTO>> tree(@RequestBody ResourceOtherRequest request) {
        return resourceService.queryTree(request);
    }

    /**
     * 新增系统资源
     */
    @PostMapping("resource/create")
    public ResponseResult<Long> create(@RequestBody ResourceSaveRequest request) {
        return resourceService.create(request);
    }

    /**
     * 修改系统资源
     */
    @PutMapping("resource/modify")
    public ResponseResult<Long> modify(@RequestBody ResourceSaveRequest request) {
        Assert.isTrue(Objects.nonNull(request.getId()), () -> ResponseStatus.PARAMS_EXCEPTION);
        return resourceService.modify(request);
    }

    /**
     * 删除资源
     *
     * @param id 资源主键
     */
    @DeleteMapping("resource/{id}/delete")
    public ResponseResult<Long> delete(@PathVariable("id") Long id) {
        return resourceService.delete(id);
    }
}
