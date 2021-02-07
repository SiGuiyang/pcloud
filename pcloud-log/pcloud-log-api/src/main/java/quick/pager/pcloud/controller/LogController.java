package quick.pager.pcloud.controller;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import quick.pager.pcloud.dto.LogViewDTO;
import quick.pager.pcloud.model.response.ResponseResult;
import quick.pager.pcloud.log.dto.LogDTO;
import quick.pager.pcloud.log.request.LogPageRequest;
import quick.pager.pcloud.log.request.LogSaveRequest;
import quick.pager.pcloud.service.LogService;

/**
 * 请求日志
 *
 * @author siguiyang
 */
@RestController
@RequestMapping("/log")
public class LogController {

    @Resource
    private LogService logService;

    /**
     * 首页访问量
     */
    @PostMapping("/admin/action/statistics")
    public ResponseResult<LogViewDTO> statistics() {
        return logService.statistics();
    }

    /**
     * 日志列表
     * 管理后台查询接口
     */
    @PostMapping("/admin/action/page")
    public ResponseResult<List<LogDTO>> page(@RequestBody LogPageRequest request) {
        return logService.queryPage(request);
    }

    /**
     * 新增日志
     */
    @PostMapping("/action/create")
    public ResponseResult<String> create(@RequestBody LogSaveRequest request) {
        return logService.create(request);
    }

}
