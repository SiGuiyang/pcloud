package quick.pager.pcloud.service;

import java.util.List;
import quick.pager.pcloud.dto.SmsDTO;
import quick.pager.pcloud.model.response.ResponseResult;
import quick.pager.pcloud.request.SmsPageRequest;
import quick.pager.pcloud.integration.request.SmsRequest;
import quick.pager.pcloud.request.SmsSaveRequest;

/**
 * 短信服务
 *
 * @author siguiyang
 */
public interface SmsService {

    /**
     * 发送短信
     *
     * @param request 请求参数
     */
    ResponseResult<String> send(final SmsRequest request);

    /**
     * 保存
     *
     * @param request 请求参数
     */
    ResponseResult<Long> create(final SmsSaveRequest request);

    /**
     * 修改
     *
     * @param request 请求参数
     */
    ResponseResult<Long> modify(final SmsSaveRequest request);


    /**
     * 修改
     *
     * @param request 请求参数
     */
    ResponseResult<List<SmsDTO>> page(final SmsPageRequest request);

    /**
     * 删除
     *
     * @param id 主键
     */
    ResponseResult<Long> delete(final Long id);

}
