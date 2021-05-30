package quick.pager.pcloud.controller;


import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import quick.pager.pcloud.enums.MemberEnums;
import quick.pager.pcloud.exception.PCloudException;
import quick.pager.pcloud.member.dto.MemberDTO;
import quick.pager.pcloud.response.ResponseResult;
import quick.pager.pcloud.service.MemberService;

/**
 * 会员中心
 *
 * @author siguiyang
 */
@RestController
@RequestMapping("/member")
@Slf4j
public class MemberController {


    @Resource
    private MemberService memberService;

    /**
     * 根据手机号码查询会员信息
     *
     * @param phone 手机号码
     */
    @GetMapping("/feign/profile/{phone}/info")
    public ResponseResult<MemberDTO> profileInfo(@PathVariable("phone") final String phone) {
        return memberService.profileInfo(phone);
    }
}
