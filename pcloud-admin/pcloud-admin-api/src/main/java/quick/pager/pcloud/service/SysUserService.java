package quick.pager.pcloud.service;

import java.util.List;
import quick.pager.pcloud.dto.SysUserDTO;
import quick.pager.pcloud.dto.SysUserDownloadDTO;
import quick.pager.pcloud.model.request.SysUserOtherRequest;
import quick.pager.pcloud.model.request.SysUserPageRequest;
import quick.pager.pcloud.model.request.SysUserSaveRequest;
import quick.pager.pcloud.model.response.ResponseResult;

/**
 * 系统用户服务
 *
 * @author siguiyang
 * @version 3.0
 */
public interface SysUserService {

    /**
     * 查询系统用户列表
     */
    ResponseResult<List<SysUserDTO>> queryPage(final SysUserPageRequest request);

    /**
     * 查询系统用户列表
     *
     * @param request 查询参数
     * @return 数据列表
     */
    ResponseResult<List<SysUserDTO>> queryList(final SysUserOtherRequest request);

    /**
     * 系统用户强踢下线
     *
     * @param id 用户主键
     */
    ResponseResult<String> offline(final Long id);

    /**
     * 获取导出文件的数据集
     *
     * @param ids 用户主键集
     */
    List<SysUserDownloadDTO> queryDownload(final List<Long> ids);

    /**
     * 创建用户
     */
    ResponseResult<Long> create(SysUserSaveRequest request);

    /**
     * 修改用户
     */
    ResponseResult<Long> modify(SysUserSaveRequest request);

    /**
     * 用户登陆权限信息
     *
     * @param phone 手机号码
     */
    ResponseResult adminInfo(String phone);

    ResponseResult<List<String>> getRolesBySysUserId(final Long sysUserId);

    ResponseResult<SysUserDTO> querySysUserByPhone(final String phone);
}
