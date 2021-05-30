package quick.pager.pcloud.controller;


import java.util.List;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import quick.pager.pcloud.request.DemandAdminPageRequest;
import quick.pager.pcloud.request.DemandAdminSaveRequest;
import quick.pager.pcloud.response.ResponseResult;
import quick.pager.pcloud.service.DemandAdminService;
import quick.pager.pcloud.vo.DemandAdminVO;

/**
 * 发布需求
 *
 * @author siguiyang
 */
@RestController
@RequestMapping("/cms/admin")
public class DemandAdminController {

    @Resource
    private DemandAdminService demandAdminService;

    /**
     * 列表
     *
     * @param request 请求参数
     */
    @PostMapping("/demand/page")
    public ResponseResult<List<DemandAdminVO>> page(@RequestBody final DemandAdminPageRequest request) {

        return demandAdminService.page(request);
    }

    /**
     * 创建
     *
     * @param request 请求参数
     */
    @PostMapping("/demand/create")
    public ResponseResult<Long> create(@RequestBody final DemandAdminSaveRequest request) {

        return demandAdminService.create(request);
    }

    /**
     * 编辑
     *
     * @param request 请求参数
     */
    @PutMapping("/demand/modify")
    public ResponseResult<Long> modify(@RequestBody final DemandAdminSaveRequest request) {

        return demandAdminService.modify(request);
    }

    /**
     * 删除
     *
     * @param id 主键
     */
    @DeleteMapping("/demand/{id}/delete")
    public ResponseResult<Long> delete(@PathVariable("id") final Long id) {

        return demandAdminService.delete(id);
    }

}
