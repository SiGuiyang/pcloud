package quick.pager.pcloud.controller;

import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import quick.pager.pcloud.response.ResponseResult;
import quick.pager.pcloud.vo.ClassificationVO;


@RestController
@RequestMapping("/oms")
public class ProductController {

    /**
     * 商品分类
     */
    @PostMapping("/product/classification")
    public ResponseResult<List<ClassificationVO>> classification() {

        return ResponseResult.toSuccess();
    }

    /**
     * 分类下商品列表
     */
    @PostMapping("/product/list")
    public ResponseResult<List<ClassificationVO>> list() {

        return ResponseResult.toSuccess();
    }


}
