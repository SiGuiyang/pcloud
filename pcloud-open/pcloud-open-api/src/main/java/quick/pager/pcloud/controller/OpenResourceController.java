package quick.pager.pcloud.controller;

import java.util.List;
import java.util.Objects;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import quick.pager.pcloud.constants.ResponseStatus;
import quick.pager.pcloud.dto.OpenPermissionDTO;
import quick.pager.pcloud.dto.OpenResourceDTO;
import quick.pager.pcloud.model.response.ResponseResult;
import quick.pager.pcloud.request.OpenAccountResourceRequest;
import quick.pager.pcloud.request.OpenPermissionPageRequest;
import quick.pager.pcloud.request.OpenResourceOtherRequest;
import quick.pager.pcloud.request.OpenResourcePageRequest;
import quick.pager.pcloud.request.OpenResourceSaveRequest;
import quick.pager.pcloud.service.OpenResourceService;
import quick.pager.pcloud.utils.Assert;

/**
 * 开放平台管理
 *
 * @author siguiyang
 */
@RestController
@RequestMapping("/open/admin")
public class OpenResourceController {

    @Resource
    private OpenResourceService openResourceService;

    /**
     * 获取系统资源
     */
    @PostMapping("/resource/page")
    public ResponseResult<List<OpenResourceDTO>> page(@RequestBody OpenResourcePageRequest request) {
        return openResourceService.queryPage(request);
    }

    /**
     * 获取系统资源
     */
    @PostMapping("/resource/list")
    public ResponseResult<List<OpenResourceDTO>> list(@RequestBody OpenResourceOtherRequest request) {
        return openResourceService.queryList(request);
    }


    /**
     * 新增系统资源
     */
    @PostMapping("/resource/create")
    public ResponseResult<Long> create(@RequestBody OpenResourceSaveRequest request) {
        return openResourceService.create(request);
    }

    /**
     * 修改系统资源
     */
    @PutMapping("/resource/modify")
    public ResponseResult<Long> modify(@RequestBody OpenResourceSaveRequest request) {
        Assert.isTrue(Objects.nonNull(request.getId()), () -> ResponseStatus.PARAMS_EXCEPTION);
        return openResourceService.modify(request);
    }

    /**
     * 授权
     *
     * @param request 请求参数
     */
    @PostMapping("/resource/permission")
    public ResponseResult<List<OpenPermissionDTO>> permission(@RequestBody OpenPermissionPageRequest request) {
        return openResourceService.permission(request);
    }

    /**
     * 授权
     *
     * @param request 请求参数
     */
    @PostMapping("/resource/grant")
    public ResponseResult<Long> grant(@RequestBody OpenAccountResourceRequest request) {
        Assert.isTrue(Objects.nonNull(request.getAccountId()), () -> ResponseStatus.PARAMS_EXCEPTION);
        return openResourceService.grant(request);
    }

    /**
     * 刷新缓存
     *
     */
    @GetMapping("/resource/{id}/refresh")
    public ResponseResult<String> refresh(@PathVariable("id") Long id) {
        return openResourceService.refresh(id);
    }

    /**
     * 删除资源
     *
     * @param id 资源主键
     */
    @DeleteMapping("/resource/{id}/delete")
    public ResponseResult<Long> delete(@PathVariable("id") Long id) {
        return openResourceService.delete(id);
    }

}
