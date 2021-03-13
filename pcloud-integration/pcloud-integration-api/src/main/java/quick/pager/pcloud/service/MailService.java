package quick.pager.pcloud.service;

import java.util.List;
import quick.pager.pcloud.dto.MailDTO;
import quick.pager.pcloud.model.response.ResponseResult;
import quick.pager.pcloud.model.request.MailPageRequest;
import quick.pager.pcloud.model.request.MailSaveRequest;

/**
 * 站内信服务
 *
 * @author siguiyang
 */
public interface MailService {

    /**
     * 保存
     *
     * @param request 请求参数
     */
    ResponseResult<Long> create(final MailSaveRequest request);

    /**
     * 修改
     *
     * @param request 请求参数
     */
    ResponseResult<Long> modify(final MailSaveRequest request);


    /**
     * 修改
     *
     * @param request 请求参数
     */
    ResponseResult<List<MailDTO>> page(final MailPageRequest request);

    /**
     * 消息已读
     *
     * @param id 请求参数
     */
    ResponseResult<MailDTO> detail(final Long id);

    /**
     * 消息未读数量
     *
     * @param phone 当前人登录账号
     */
    ResponseResult<Integer> quantity(final String phone);

    /**
     * 删除
     *
     * @param id 主键
     */
    ResponseResult<Long> delete(final Long id);
}
