package quick.pager.pcloud.controller;

import java.util.List;
import java.util.Objects;
import javax.annotation.Resource;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import quick.pager.pcloud.constants.IConsts;
import quick.pager.pcloud.constants.ResponseStatus;
import quick.pager.pcloud.dto.DeptDTO;
import quick.pager.pcloud.response.ResponseResult;
import quick.pager.pcloud.request.DeptOtherRequest;
import quick.pager.pcloud.request.DeptPageRequest;
import quick.pager.pcloud.request.DeptSaveRequest;
import quick.pager.pcloud.service.DeptService;
import quick.pager.pcloud.utils.Assert;

/**
 * 部门管理
 *
 * @author siguiyang
 */
@RestController
@RequestMapping("/admin")
public class DeptController {

    @Resource
    private DeptService deptService;

    /**
     * 获取部门
     */
    @PostMapping("/dept/page")
    public ResponseResult<List<DeptDTO>> page(@RequestBody DeptPageRequest request) {
        return deptService.queryPage(request);
    }

    /**
     * 获取部门
     */
    @PostMapping("/dept/list")
    public ResponseResult<List<DeptDTO>> list(@RequestBody DeptOtherRequest request) {
        return deptService.queryList(request);
    }

    /**
     * 获取部门
     */
    @PostMapping("/dept/tree")
    public ResponseResult<List<DeptDTO>> tree() {
        return ResponseResult.toSuccess(deptService.queryTree());
    }

    /**
     * 获取部门
     */
    @GetMapping("/dept/org")
    public ResponseResult<DeptDTO> org() {
        List<DeptDTO> deptDTOS = deptService.queryTree();
        return CollectionUtils.isNotEmpty(deptDTOS) ? ResponseResult.toSuccess(deptDTOS.get(IConsts.ZERO)) : ResponseResult.toSuccess();
    }

    /**
     * 新增部门
     */
    @PostMapping("/dept/create")
    public ResponseResult<Long> create(@RequestBody DeptSaveRequest request) {
        return deptService.create(request);
    }

    /**
     * 修改部门
     */
    @PutMapping("/dept/modify")
    public ResponseResult<Long> modify(@RequestBody DeptSaveRequest request) {
        Assert.isTrue(Objects.nonNull(request.getId()), () -> ResponseStatus.PARAMS_EXCEPTION);
        return deptService.modify(request);
    }

    /**
     * 删除部门
     *
     * @param id 角色主键
     */
    @DeleteMapping("/dept/{id}/delete")
    public ResponseResult<Long> delete(@PathVariable("id") Long id) {
        return deptService.delete(id);
    }
}
