package quick.pager.pcloud.member.fallback;

import org.springframework.stereotype.Component;
import quick.pager.pcloud.constants.ResponseStatus;
import quick.pager.pcloud.member.client.MemberClient;
import quick.pager.pcloud.member.dto.MemberDTO;
import quick.pager.pcloud.response.ResponseResult;

@Component
public class MemberClientFallback implements MemberClient {
    @Override
    public ResponseResult<MemberDTO> profileInfo(final String phone) {
        return ResponseResult.toError(ResponseStatus.FALLBACK_CODE, ResponseStatus.FALLBACK_MSG);
    }
}
