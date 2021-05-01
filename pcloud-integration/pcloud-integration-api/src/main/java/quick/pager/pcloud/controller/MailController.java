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
import quick.pager.pcloud.dto.MailDTO;
import quick.pager.pcloud.response.ResponseResult;
import quick.pager.pcloud.request.MailPageRequest;
import quick.pager.pcloud.request.MailSaveRequest;
import quick.pager.pcloud.service.MailService;
import quick.pager.pcloud.utils.Assert;
import quick.pager.pcloud.utils.WebUtils;

/**
 * 站内信
 *
 * @author siguiyang
 */
@RestController
@RequestMapping("/integration")
public class MailController {

    @Resource
    private MailService mailService;

    /**
     * 保存
     *
     * @param request 请求参数
     */
    @PostMapping("/admin/mail/create")
    public ResponseResult<Long> create(@RequestBody final MailSaveRequest request) {
        return mailService.create(request);
    }

    /**
     * 修改
     *
     * @param request 请求参数
     */
    @PutMapping("/admin/mail/modify")
    public ResponseResult<Long> modify(@RequestBody final MailSaveRequest request) {
        Assert.isTrue(Objects.nonNull(request.getId()), () -> ResponseStatus.PARAMS_EXCEPTION);
        return mailService.modify(request);
    }

    /**
     * 修改
     *
     * @param request 请求参数
     */
    @PostMapping("/admin/mail/page")
    public ResponseResult<List<MailDTO>> page(@RequestBody final MailPageRequest request) {
        return mailService.page(request);
    }


    @GetMapping("/admin/mail/quantity")
    public ResponseResult<Integer> quantity() {
        return mailService.quantity(WebUtils.getPhone());
    }

    /**
     * 站内信详情
     *
     * @param id 请求参数
     */
    @PostMapping("/admin/mail/{id}/detail")
    public ResponseResult<MailDTO> read(@PathVariable("id") final Long id) {
        return mailService.detail(id);
    }

    /**
     * 删除
     *
     * @param id 主键
     */
    @DeleteMapping("/admin/mail/{id}/delete")
    public ResponseResult<Long> delete(@PathVariable("id") final Long id) {
        return mailService.delete(id);
    }
}
