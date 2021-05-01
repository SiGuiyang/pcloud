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
import quick.pager.pcloud.dto.SmsDTO;
import quick.pager.pcloud.response.ResponseResult;
import quick.pager.pcloud.request.SmsPageRequest;
import quick.pager.pcloud.integration.request.SmsRequest;
import quick.pager.pcloud.request.SmsSaveRequest;
import quick.pager.pcloud.service.SmsService;
import quick.pager.pcloud.utils.Assert;

/**
 * 短信
 *
 * @author siguiyang
 */
@RestController
@RequestMapping("/integration")
public class SmsController {

    @Resource
    private SmsService smsService;

    /**
     * 发送短信
     *
     * @param request 请求参数
     */
    @PostMapping("/sms/send")
    public ResponseResult<String> send(@RequestBody final SmsRequest request) {
        Assert.isTrue(Objects.nonNull(request.getSmsSourceEnums()), () -> "短信事件源不能为空");
        return smsService.send(request);
    }


    /**
     * 保存
     *
     * @param request 请求参数
     */
    @PostMapping("/admin/sms/create")
    public ResponseResult<Long> create(@RequestBody final SmsSaveRequest request) {
        return smsService.create(request);
    }

    /**
     * 修改
     *
     * @param request 请求参数
     */
    @PutMapping("/admin/sms/modify")
    public ResponseResult<Long> modify(@RequestBody final SmsSaveRequest request) {
        Assert.isTrue(Objects.nonNull(request.getId()), () -> ResponseStatus.PARAMS_EXCEPTION);
        return smsService.modify(request);
    }

    /**
     * 修改
     *
     * @param request 请求参数
     */
    @PostMapping("/admin/sms/page")
    public ResponseResult<List<SmsDTO>> page(@RequestBody final SmsPageRequest request) {
        return smsService.page(request);
    }

    /**
     * 删除
     *
     * @param id 主键
     */
    @DeleteMapping("/admin/sms/{id}/delete")
    public ResponseResult<Long> delete(@PathVariable("id") final Long id) {
        return smsService.delete(id);
    }
}
