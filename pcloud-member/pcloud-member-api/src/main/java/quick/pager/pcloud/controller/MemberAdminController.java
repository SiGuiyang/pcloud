package quick.pager.pcloud.controller;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import quick.pager.pcloud.request.MemberAdminPageRequest;
import quick.pager.pcloud.response.ResponseResult;
import quick.pager.pcloud.service.MemberAdminService;
import quick.pager.pcloud.vo.MemberAdminVO;

/**
 * 会员中心
 *
 * @author siguiyang
 */
@RestController
@RequestMapping("/member/admin")
public class MemberAdminController {

    @Resource
    private MemberAdminService memberAdminService;

    /**
     * 会员管理
     *
     * @param request 请求参数
     */
    @PostMapping("page")
    public ResponseResult<List<MemberAdminVO>> page(@RequestBody final MemberAdminPageRequest request) {

        return memberAdminService.page(request);
    }
}
