package quick.pager.pcloud.convert;

import quick.pager.pcloud.model.Member;
import quick.pager.pcloud.member.dto.MemberDTO;

/**
 * 数据转换
 *
 * @author siguiyang
 */
public class MemberConvert {

    /**
     * 会员数据转换
     *
     * @param member 会员
     */
    public static MemberDTO convert(final Member member) {
        return MemberDTO.builder()
                .id(member.getId())
                .birthday(member.getBirthday())
                .familyName(member.getFamilyName())
                .gender(member.getGender())
                .gmtModifiedDate(member.getGmtModifiedDate())
                .gmtModifiedName(member.getGmtModifiedName())
                .lastLoginTime(member.getLastLoginTime())
                .loginFlag(member.getLoginFlag())
                .loginTime(member.getLoginTime())
                .name(member.getName())
                .phone(member.getPhone())
                .registerChannel(member.getRegisterChannel())
                .build();
    }
}
