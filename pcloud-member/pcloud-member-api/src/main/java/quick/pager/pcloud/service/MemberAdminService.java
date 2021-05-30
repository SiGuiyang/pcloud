package quick.pager.pcloud.service;

import java.util.List;
import quick.pager.pcloud.request.MemberAdminPageRequest;
import quick.pager.pcloud.response.ResponseResult;
import quick.pager.pcloud.vo.MemberAdminVO;

/**
 * 管理平台 会员服务
 *
 * @author siguiyang
 */
public interface MemberAdminService {

    /**
     * 会员管理
     *
     * @param request 请求参数
     */
    ResponseResult<List<MemberAdminVO>> page(final MemberAdminPageRequest request);
}
