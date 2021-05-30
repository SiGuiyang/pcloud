package quick.pager.pcloud.service;

import quick.pager.pcloud.member.dto.MemberDTO;
import quick.pager.pcloud.response.ResponseResult;

/**
 * 会员服务
 *
 * @author siguiyang
 */
public interface MemberService {
    /**
     * 根据手机号码查询会员信息
     *
     * @param phone 手机号码
     */
    ResponseResult<MemberDTO> profileInfo(final String phone);
}
