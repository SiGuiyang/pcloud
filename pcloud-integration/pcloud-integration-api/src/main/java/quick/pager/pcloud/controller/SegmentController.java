package quick.pager.pcloud.controller;

import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import quick.pager.pcloud.dto.LeafDTO;
import quick.pager.pcloud.model.response.ResponseResult;
import quick.pager.pcloud.request.IdGenPageRequest;
import quick.pager.pcloud.request.IdGenSaveRequest;
import quick.pager.pcloud.service.SegmentService;
import quick.pager.pcloud.utils.Assert;

/**
 * 全局id生成器管理
 *
 * @author siguiyang
 */
@RestController
@RequestMapping("/integration")
public class SegmentController {

    @Resource
    private SegmentService segmentService;

    /**
     * 新增
     *
     * @param request 请求参数
     */
    @PostMapping("/admin/segment/create")
    public ResponseResult<String> create(@RequestBody IdGenSaveRequest request) {
        return segmentService.create(request);
    }

    /**
     * 编辑
     *
     * @param request 请求参数
     */
    @PutMapping("/admin/segment/modify")
    public ResponseResult<String> modify(@RequestBody IdGenSaveRequest request) {
        Assert.isTrue(StringUtils.isNotEmpty(request.getBizTag()), () -> "号段业务类型不能为空");

        return segmentService.modify(request);
    }

    /**
     * 列表分页
     *
     * @param request 请求参数
     */
    @PostMapping("/admin/segment/page")
    public ResponseResult<List<LeafDTO>> page(@RequestBody IdGenPageRequest request) {
        return segmentService.page(request);
    }
}
