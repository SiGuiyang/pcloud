package quick.pager.pcloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import java.util.Objects;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import quick.pager.pcloud.convert.MemberConvert;
import quick.pager.pcloud.mapper.MemberMapper;
import quick.pager.pcloud.member.dto.MemberDTO;
import quick.pager.pcloud.model.Member;
import quick.pager.pcloud.response.ResponseResult;
import quick.pager.pcloud.service.MemberService;
import quick.pager.pcloud.utils.Assert;

@Service
@Slf4j
public class MemberServiceImpl implements MemberService {

    @Resource
    private MemberMapper memberMapper;

    @Override
    public ResponseResult<MemberDTO> profileInfo(final String phone) {

        Member member = memberMapper.selectOne(new LambdaQueryWrapper<Member>().eq(Member::getPhone, phone));
        Assert.isTrue(Objects.nonNull(member), () -> "会员不存在");
        return ResponseResult.toSuccess(MemberConvert.convert(member));
    }
}
