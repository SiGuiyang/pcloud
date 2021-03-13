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
import quick.pager.pcloud.dto.MenuDTO;
import quick.pager.pcloud.model.request.MenuOtherRequest;
import quick.pager.pcloud.model.request.MenuSaveRequest;
import quick.pager.pcloud.model.response.ResponseResult;
import quick.pager.pcloud.service.MenuService;
import quick.pager.pcloud.utils.Assert;

/**
 * 菜单管理
 *
 * @author siguiyang
 * @version 3.0
 */
@RestController
@RequestMapping("/admin")
public class MenuController {

    @Resource
    private MenuService menuService;

    /**
     * 系统菜单列表
     */
    @PostMapping("/menu/page")
    public ResponseResult<List<MenuDTO>> list(@RequestBody MenuOtherRequest request) {
        return menuService.queryList(request);
    }

    /**
     * 新增
     */
    @PostMapping("/menu/create")
    public ResponseResult<Long> create(@RequestBody MenuSaveRequest request) {
        return menuService.create(request);
    }

    /**
     * 修改
     */
    @PutMapping("/menu/modify")
    public ResponseResult<Long> modify(@RequestBody MenuSaveRequest request) {
        Assert.isTrue(Objects.nonNull(request.getId()), () -> ResponseStatus.PARAMS_EXCEPTION);
        return menuService.modify(request);
    }

    /**
     * 删除
     */
    @DeleteMapping("/menu/{id}/delete")
    public ResponseResult<Long> delete(@PathVariable("id") Long id) {
        return menuService.delete(id);
    }

}
