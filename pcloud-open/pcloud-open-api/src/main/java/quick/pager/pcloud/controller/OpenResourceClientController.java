package quick.pager.pcloud.controller;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import quick.pager.pcloud.model.response.ResponseResult;
import quick.pager.pcloud.service.OpenResourceService;

/**
 * 开放平台管理
 *
 * @author siguiyang
 */
@RestController
@RequestMapping("/open")
public class OpenResourceClientController {

    @Resource
    private OpenResourceService openResourceService;

    /**
     * 访问权限
     */
    @GetMapping("/resource/permissions")
    public ResponseResult<Map<String, List<String>>> permissions() {
        return openResourceService.permissions();
    }

}
