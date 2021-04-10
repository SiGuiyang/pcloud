package quick.pager.pcloud.service;

import java.util.List;
import quick.pager.pcloud.dto.LogViewDTO;
import quick.pager.pcloud.dto.LogDTO;
import quick.pager.pcloud.model.response.ResponseResult;
import quick.pager.pcloud.model.request.LogPageRequest;
import quick.pager.pcloud.model.request.LogSaveRequest;

/**
 * 请求日志服务
 *
 * @author siguiyang
 */
public interface LogService {

    /**
     * 访问量
     */
    ResponseResult<LogViewDTO> statistics();

    /**
     * 查询列表
     */
    ResponseResult<List<LogDTO>> queryPage(final LogPageRequest request);

    /**
     * 新增
     */
    ResponseResult<String> create(final LogSaveRequest request);

}
