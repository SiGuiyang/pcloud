package quick.pager.pcloud.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import quick.pager.pcloud.request.MemberSmsRequest;
import quick.pager.pcloud.response.ResponseResult;
import quick.pager.pcloud.service.MemberSmsService;

@Service
@Slf4j
public class MemberSmsServiceImpl implements MemberSmsService {

    @Override
    public ResponseResult<String> sms(final MemberSmsRequest request) {
        return null;
    }
}
