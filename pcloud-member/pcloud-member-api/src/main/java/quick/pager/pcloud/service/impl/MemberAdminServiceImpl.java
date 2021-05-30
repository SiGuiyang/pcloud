package quick.pager.pcloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import quick.pager.pcloud.mapper.MemberMapper;
import quick.pager.pcloud.mapper.MemberPartnerMapper;
import quick.pager.pcloud.model.Member;
import quick.pager.pcloud.request.MemberAdminPageRequest;
import quick.pager.pcloud.response.ResponseResult;
import quick.pager.pcloud.service.MemberAdminService;
import quick.pager.pcloud.vo.MemberAdminVO;

@Service
@Slf4j
public class MemberAdminServiceImpl implements MemberAdminService {

    @Resource
    private MemberMapper memberMapper;
    @Resource
    private MemberPartnerMapper memberPartnerMapper;


    // region 数据转换

    private MemberAdminVO convert(final Member member) {
        return MemberAdminVO.builder()
                .id(member.getId())
                .birthday(member.getBirthday())
                .familyName(member.getFamilyName())
                .name(member.getName())
                .gender(member.getGender())
                .phone(member.getPhone())
                .loginFlag(member.getLoginFlag())
                .registerChannel(member.getRegisterChannel())
                .loginTime(member.getLoginTime())
                .lastLoginTime(member.getLastLoginTime())
                .gmtCreatedDate(member.getGmtCreatedDate())
                .build();
    }


    // endregion


    @Override
    public ResponseResult<List<MemberAdminVO>> page(final MemberAdminPageRequest request) {

        LambdaQueryWrapper<Member> wrapper = new LambdaQueryWrapper<>();

        if (StringUtils.isNotBlank(request.getPhone())) {
            wrapper.eq(Member::getPhone, request.getPhone());
        }

        if (Objects.nonNull(request.getStartTime()) && Objects.nonNull(request.getEndTime())) {
            wrapper.ge(Member::getGmtCreatedDate, request.getStartTime());
            wrapper.le(Member::getGmtCreatedDate, request.getEndTime());
        }

        List<MemberAdminVO> dtos = Collections.emptyList();

        Integer total = memberMapper.selectCount(wrapper);

        if (total > 0) {
            Page<Member> page = memberMapper.selectPage(new Page<>(request.getPage(), request.getPageSize(), false), wrapper);

            dtos = page.getRecords().stream().map(this::convert).collect(Collectors.toList());
        }

        return ResponseResult.toSuccess(dtos, total);
    }
}
