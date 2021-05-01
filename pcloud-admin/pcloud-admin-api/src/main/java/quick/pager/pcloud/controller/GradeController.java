package quick.pager.pcloud.controller;

import java.util.List;
import java.util.Objects;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import quick.pager.pcloud.constants.ResponseStatus;
import quick.pager.pcloud.dto.GradeDTO;
import quick.pager.pcloud.response.ResponseResult;
import quick.pager.pcloud.request.GradeOtherRequest;
import quick.pager.pcloud.request.GradePageRequest;
import quick.pager.pcloud.request.GradeSaveRequest;
import quick.pager.pcloud.service.GradeService;
import quick.pager.pcloud.utils.Assert;

/**
 * 职级管理
 *
 * @author siguiyang
 */
@RestController
@RequestMapping("/admin")
public class GradeController {

    @Resource
    private GradeService gradeService;

    /**
     * 获取职级
     */
    @PostMapping("/grade/page")
    public ResponseResult<List<GradeDTO>> page(@RequestBody GradePageRequest request) {
        return gradeService.queryPage(request);
    }

    /**
     * 获取职级
     */
    @PostMapping("/grade/list")
    public ResponseResult<List<GradeDTO>> list(@RequestBody GradeOtherRequest request) {
        return gradeService.queryList(request);
    }


    /**
     * 新增职级
     */
    @PostMapping("/grade/create")
    public ResponseResult<Long> create(@RequestBody GradeSaveRequest request) {
        return gradeService.create(request);
    }

    /**
     * 修改职级
     */
    @PutMapping("/grade/modify")
    public ResponseResult<Long> modify(@RequestBody GradeSaveRequest request) {
        Assert.isTrue(Objects.nonNull(request.getId()), () -> ResponseStatus.PARAMS_EXCEPTION);
        return gradeService.modify(request);
    }

    /**
     * 删除职级
     *
     * @param id 角色主键
     */
    @DeleteMapping("/grade/{id}/delete")
    public ResponseResult<Long> delete(@PathVariable("id") Long id) {
        return gradeService.delete(id);
    }

}
