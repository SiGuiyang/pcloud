package quick.pager.pcloud.member.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import quick.pager.pcloud.member.dto.MemberDTO;
import quick.pager.pcloud.member.fallback.MemberClientFallback;
import quick.pager.pcloud.response.ResponseResult;

@FeignClient(value = "pcloud-member-api", path = "/member", fallback = MemberClientFallback.class)
public interface MemberClient {

    /**
     * 根据手机号码查询会员信息
     *
     * @param phone 手机号码
     */
    @GetMapping("/feign/profile/{phone}/info")
    ResponseResult<MemberDTO> profileInfo(@PathVariable("phone") final String phone);
}
