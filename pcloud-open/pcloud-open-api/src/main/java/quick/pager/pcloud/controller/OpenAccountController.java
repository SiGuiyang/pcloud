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
import quick.pager.pcloud.dto.OpenAccountDTO;
import quick.pager.pcloud.response.ResponseResult;
import quick.pager.pcloud.request.OpenAccountPageRequest;
import quick.pager.pcloud.request.OpenAccountSaveRequest;
import quick.pager.pcloud.service.OpenAccountService;
import quick.pager.pcloud.utils.Assert;

/**
 * 开放平台管理
 *
 * @author siguiyang
 */
@RestController
@RequestMapping("/open/admin")
public class OpenAccountController {

    @Resource
    private OpenAccountService openAccountService;

    /**
     * 获取分页
     */
    @PostMapping("/account/page")
    public ResponseResult<List<OpenAccountDTO>> page(@RequestBody OpenAccountPageRequest request) {
        return openAccountService.queryPage(request);
    }

    /**
     * 新增
     */
    @PostMapping("/account/create")
    public ResponseResult<Long> create(@RequestBody OpenAccountSaveRequest request) {
        return openAccountService.create(request);
    }

    /**
     * 修改状态
     */
    @PutMapping("/account/status")
    public ResponseResult<Long> status(@RequestBody OpenAccountSaveRequest request) {
        Assert.isTrue(Objects.nonNull(request.getId()), () -> ResponseStatus.PARAMS_EXCEPTION);
        Assert.isTrue(Objects.nonNull(request.getStatus()), () -> ResponseStatus.PARAMS_EXCEPTION);
        return openAccountService.status(request);
    }

    /**
     * 查看密钥
     *
     * @param id 主键
     */
    @GetMapping("/account/{id}/view")
    public ResponseResult<String> view(@PathVariable("id") Long id) {
        return openAccountService.view(id);
    }

    /**
     * 重置密钥
     *
     * @param id 主键
     */
    @PutMapping("/account/{id}/reset")
    public ResponseResult<String> reset(@PathVariable("id") Long id) {
        return openAccountService.reset(id);
    }

    /**
     * 删除
     *
     * @param id 主键
     */
    @DeleteMapping("/account/{id}/delete")
    public ResponseResult<Long> delete(@PathVariable("id") Long id) {
        return openAccountService.delete(id);
    }
}
