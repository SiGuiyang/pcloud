package quick.pager.pcloud.service;

import java.util.List;
import quick.pager.pcloud.dto.OpenAccountDTO;
import quick.pager.pcloud.model.response.ResponseResult;
import quick.pager.pcloud.open.request.OpenLoginRequest;
import quick.pager.pcloud.request.OpenAccountPageRequest;
import quick.pager.pcloud.request.OpenAccountSaveRequest;

/**
 * 开放账户服务
 *
 * @author siguiyang
 */
public interface OpenAccountService {
    /**
     * 分页列表
     *
     * @param request 请求参数
     */
    ResponseResult<List<OpenAccountDTO>> queryPage(final OpenAccountPageRequest request);

    /**
     * 开通账户
     *
     * @param request 请求参数
     */
    ResponseResult<Long> create(final OpenAccountSaveRequest request);

    /**
     * 更新状态
     *
     * @param request 请求参数
     */
    ResponseResult<Long> status(final OpenAccountSaveRequest request);

    /**
     * 查看密钥
     *
     * @param id 主键
     */
    ResponseResult<String> view(final Long id);

    /**
     * 重置secureKey
     *
     * @param id 主键
     */
    ResponseResult<String> reset(final Long id);

    /**
     * 删除
     *
     * @param id 主键
     */
    ResponseResult<Long> delete(final Long id);

}
