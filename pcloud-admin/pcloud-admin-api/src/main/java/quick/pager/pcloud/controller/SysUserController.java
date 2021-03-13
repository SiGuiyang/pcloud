package quick.pager.pcloud.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import java.net.URLEncoder;
import java.util.List;
import java.util.Objects;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import quick.pager.pcloud.constants.ResponseStatus;
import quick.pager.pcloud.dto.SysUserDTO;
import quick.pager.pcloud.dto.SysUserDownloadDTO;
import quick.pager.pcloud.request.SysUserDownloadRequest;
import quick.pager.pcloud.request.SysUserPageRequest;
import quick.pager.pcloud.request.SysUserSaveRequest;
import quick.pager.pcloud.model.response.ResponseResult;
import quick.pager.pcloud.service.SysUserService;
import quick.pager.pcloud.utils.Assert;
import quick.pager.pcloud.utils.WebUtils;

/**
 * 系统管理
 *
 * @author siguiyang
 * @version 3.0
 */
@RestController
@RequestMapping("/admin")
public class SysUserController {

    @Resource
    private SysUserService sysUserService;


    /**
     * 获取系统用户
     *
     * @param phone 手机号码
     */
//    @PostMapping("/permit/login")
    public ResponseResult<SysUserDTO> getSysUser(@RequestParam("phone") String phone) {
        return sysUserService.querySysUserByPhone(phone);
    }

    /**
     * 登陆获取用户的角色编码
     *
     * @param sysUserId 登陆的用户主键
     */
    @PostMapping("/permit/permission/{sysUserId}")
    public ResponseResult<List<String>> getRolesBySysUserId(@PathVariable("sysUserId") Long sysUserId) {
        return sysUserService.getRolesBySysUserId(sysUserId);
    }


    /**
     * 系统登陆用户吧信息
     */
    @PostMapping("/system/adminInfo")
    public ResponseResult sysUserInfo() {
        return sysUserService.adminInfo(WebUtils.getPhone());
    }

    /**
     * 系统用户列表
     */
    @PostMapping("/system/user/page")
    public ResponseResult<List<SysUserDTO>> page(@RequestBody SysUserPageRequest request) {
        return sysUserService.queryPage(request);
    }

    /**
     * 系统用户强踢下线
     */
    @GetMapping("/system/user/{id}/offline")
    public ResponseResult<String> offline(@PathVariable("id") Long id) {
        return sysUserService.offline(id);
    }

    /**
     * 新增系统用户
     */
    @PostMapping("/system/user/create")
    public ResponseResult<Long> create(@RequestBody SysUserSaveRequest request) {

        return sysUserService.create(request);
    }

    /**
     * 修改系统用户
     */
    @PutMapping("/system/user/modify")
    public ResponseResult<Long> modify(@RequestBody SysUserSaveRequest request) {

        Assert.isTrue(Objects.nonNull(request.getId()), () -> ResponseStatus.PARAMS_EXCEPTION);

        return sysUserService.modify(request);
    }

    /**
     * 导出
     *
     * @param request 请求参数
     */
    @PostMapping("/system/user/download")
    public void download(@RequestBody SysUserDownloadRequest request, HttpServletResponse response) throws Exception {

        Assert.isTrue(CollectionUtils.isNotEmpty(request.getIds()), () -> ResponseStatus.PARAMS_EXCEPTION);

        List<SysUserDownloadDTO> responseList = sysUserService.queryDownload(request.getIds());

        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(request.getFileName(), "UTF-8"));

        EasyExcel.write(response.getOutputStream(), SysUserDownloadDTO.class).registerWriteHandler(new LongestMatchColumnWidthStyleStrategy()).sheet("用户信息").doWrite(responseList);
    }
}
