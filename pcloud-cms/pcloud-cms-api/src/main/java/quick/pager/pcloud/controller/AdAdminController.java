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
import quick.pager.pcloud.request.AdAdminPageRequest;
import quick.pager.pcloud.request.AdAdminSaveRequest;
import quick.pager.pcloud.response.ResponseResult;
import quick.pager.pcloud.service.AdAdminService;
import quick.pager.pcloud.vo.AdAdminVO;

/**
 * 广告位
 *
 * @author siguiyang
 */
@RestController
@RequestMapping("/cms/admin")
public class AdAdminController {

    @Resource
    private AdAdminService adAdminService;

    /**
     * 广告管理
     *
     * @param request 请求参数
     */
    @PostMapping("/ad/page")
    public ResponseResult<List<AdAdminVO>> page(@RequestBody final AdAdminPageRequest request) {

        return adAdminService.page(request);
    }

    /**
     * 创建
     *
     * @param request 请求参数
     */
    @PostMapping("/ad/create")
    public ResponseResult<Long> create(@RequestBody final AdAdminSaveRequest request) {

        return adAdminService.create(request);
    }

    /**
     * 编辑
     *
     * @param request 请求参数
     */
    @PutMapping("/ad/modify")
    public ResponseResult<Long> modify(@RequestBody final AdAdminSaveRequest request) {

        return adAdminService.modify(request);
    }

    /**
     * 删除
     *
     * @param id 主键
     */
    @DeleteMapping("/ad/{id}/delete")
    public ResponseResult<Long> delete(@PathVariable("id") final Long id) {

        return adAdminService.delete(id);
    }
}
