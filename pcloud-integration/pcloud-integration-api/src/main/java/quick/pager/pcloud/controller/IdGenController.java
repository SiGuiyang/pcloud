package quick.pager.pcloud.controller;

import javax.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import quick.pager.pcloud.response.ResponseResult;
import quick.pager.pcloud.service.IDGenService;
import quick.pager.pcloud.gen.common.Result;
import quick.pager.pcloud.gen.common.Status;
import quick.pager.pcloud.gen.exception.LeafServerException;
import quick.pager.pcloud.gen.exception.NoKeyException;

/**
 * 全局id生成器服务
 *
 * @author siguiyang
 */
@RestController
@RequestMapping("/integration")
public class IdGenController {

    @Resource
    private IDGenService idGenService;


    /**
     * 获取自增主键号段
     *
     * @param key 号段业务类型
     * @return 自增主键号段
     */
    @GetMapping(value = "/segment/get/{key}")
    public ResponseResult<String> getSegmentId(@PathVariable("key") String key) {
        if (key == null || key.isEmpty()) {
            throw new NoKeyException();
        }
        Result result = idGenService.get(key);
        if (result.getStatus().equals(Status.EXCEPTION)) {
            throw new LeafServerException(result.toString());
        }
        return ResponseResult.toSuccess(String.valueOf(result.getId()));
    }

}
