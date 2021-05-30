package quick.pager.pcloud.controller;

import com.alibaba.fastjson.JSON;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import quick.pager.pcloud.request.MemberSmsRequest;
import quick.pager.pcloud.response.ResponseResult;
import quick.pager.pcloud.service.MemberSmsService;
import quick.pager.pcloud.utils.Assert;

@RestController
@RequestMapping("/member")
@Slf4j
public class SmsController {

    @Resource
    private MemberSmsService memberSmsService;

    /**
     * 发送短信
     *
     * @param request 请求参数
     * @return 响应消息
     */
    @PostMapping("/sms/send")
    public ResponseResult<String> sms(@RequestBody final MemberSmsRequest request) {
        log.info("短信发送请求 request = {}", JSON.toJSONString(request));
        Assert.isTrue(StringUtils.isNotBlank(request.getMobile()), "手机号不能为空");
        Assert.isTrue(StringUtils.isNotBlank(request.getSource()), "发送短信事件源不能为空");
        return memberSmsService.sms(request);
    }
}
