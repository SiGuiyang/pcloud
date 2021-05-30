package quick.pager.pcloud.controller;

import java.util.List;
import java.util.Objects;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import quick.pager.pcloud.request.ClassificationAdminPageRequest;
import quick.pager.pcloud.request.ClassificationAdminSaveRequest;
import quick.pager.pcloud.response.ResponseResult;
import quick.pager.pcloud.service.ClassificationAdminService;
import quick.pager.pcloud.utils.Assert;
import quick.pager.pcloud.vo.ClassificationAdminVO;

/**
 * 商品分类
 *
 * @author siguiyang
 */
@RestController
@RequestMapping("/oms/admin")
public class ClassificationAdminController {

    @Resource
    private ClassificationAdminService classificationAdminService;

    /**
     * 列表
     *
     * @param request 请求参数
     */
    @PostMapping("/classification/page")
    public ResponseResult<List<ClassificationAdminVO>> page(@RequestBody ClassificationAdminPageRequest request) {

        return classificationAdminService.page(request);
    }

    /**
     * 新增
     *
     * @param request 请求参数
     */
    @PostMapping("/classification/create")
    public ResponseResult<Long> create(@RequestBody ClassificationAdminSaveRequest request) {

        return classificationAdminService.create(request);
    }

    /**
     * 编辑
     *
     * @param request 请求参数
     */
    @PutMapping("/classification/modify")
    public ResponseResult<Long> modify(@RequestBody ClassificationAdminSaveRequest request) {

        Assert.isTrue(Objects.nonNull(request.getId()), "分类主键不能为空");

        return classificationAdminService.modify(request);
    }

    /**
     * 编辑
     *
     * @param id 主键
     */
    @GetMapping("/classification/{id}/status")
    public ResponseResult<Long> status(@PathVariable("id") Long id) {

        return classificationAdminService.status(id);
    }

    /**
     * 编辑
     *
     * @param id 主键
     */
    @DeleteMapping("/classification/{id}/delete")
    public ResponseResult<Long> delete(@PathVariable("id") Long id) {

        return classificationAdminService.delete(id);
    }

}
