package quick.pager.pcloud.service;

import quick.pager.pcloud.request.MemberSmsRequest;
import quick.pager.pcloud.response.ResponseResult;

/**
 * 短信发送
 *
 * @author siguiyang
 */
public interface MemberSmsService {

    /**
     * 发送短信
     *
     * @param request 请求参数
     * @return 响应
     */
    ResponseResult<String> sms(final MemberSmsRequest request);
}
