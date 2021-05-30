package quick.pager.pcloud.service.impl;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import quick.pager.pcloud.request.OrderAdminPageRequest;
import quick.pager.pcloud.response.ResponseResult;
import quick.pager.pcloud.service.OrderAdminService;
import quick.pager.pcloud.vo.OrderAdminVO;

@Service
@Slf4j
public class OrderAdminServiceImpl implements OrderAdminService {

    @Override
    public ResponseResult<List<OrderAdminVO>> page(final OrderAdminPageRequest request) {

        return ResponseResult.toSuccess();
    }
}
