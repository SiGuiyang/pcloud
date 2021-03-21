package quick.pager.pcloud.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import quick.pager.pcloud.dto.OpenAccountDTO;
import quick.pager.pcloud.enums.OpenAccountStatusEnums;
import quick.pager.pcloud.mapper.OpenAccountMapper;
import quick.pager.pcloud.model.OpenAccountDO;
import quick.pager.pcloud.model.response.ResponseResult;
import quick.pager.pcloud.request.OpenAccountPageRequest;
import quick.pager.pcloud.request.OpenAccountSaveRequest;
import quick.pager.pcloud.service.OpenAccountService;
import quick.pager.pcloud.utils.Assert;
import quick.pager.pcloud.utils.WebUtils;

@Service
public class OpenAccountServiceImpl implements OpenAccountService {

    @Resource
    private OpenAccountMapper openAccountMapper;


    // region 数据转换

    private OpenAccountDTO convert(final OpenAccountDO openAccountDO) {

        return OpenAccountDTO.builder()
                .id(openAccountDO.getId())
                .name(openAccountDO.getName())
                .status(openAccountDO.getStatus())
                .secureId(openAccountDO.getSecureId())
                .gmtModifiedName(openAccountDO.getGmtModifiedName())
                .gmtModifiedDate(openAccountDO.getGmtModifiedDate())
                .build();
    }


    private OpenAccountDO convert(final OpenAccountSaveRequest request) {
        return OpenAccountDO.builder()
                .id(request.getId())
                .name(request.getName())
                .status(request.getStatus())
                .gmtCreatedName(request.getGmtCreatedName())
                .gmtModifiedName(request.getGmtModifiedName())
                .deleteStatus(Boolean.FALSE)
                .build();
    }

    // endregion


    @Override
    public ResponseResult<List<OpenAccountDTO>> queryPage(final OpenAccountPageRequest request) {

        LambdaQueryWrapper<OpenAccountDO> wrapper = new LambdaQueryWrapper<OpenAccountDO>()
                .like(StringUtils.isNotEmpty(request.getName()), OpenAccountDO::getName, request.getName())
                .eq(Objects.nonNull(request.getStatus()), OpenAccountDO::getStatus, request.getStatus());


        int total = this.openAccountMapper.selectCount(wrapper);

        List<OpenAccountDTO> dtos = Collections.emptyList();
        if (total > 0) {
            List<OpenAccountDO> dos = this.openAccountMapper.selectPage(new Page<>(request.getPage(), request.getPageSize(), false), wrapper).getRecords();

            dtos = dos.stream().map(this::convert).collect(Collectors.toList());
        }

        return ResponseResult.toSuccess(dtos, total);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<Long> create(final OpenAccountSaveRequest request) {
        OpenAccountDO openAccountDO = this.convert(request);
        openAccountDO.setStatus(OpenAccountStatusEnums.NORMAL.getCode());

        String secureId = RandomUtil.randomString(32);

        String secureKey = RandomUtil.randomString(32);

        int total = this.openAccountMapper.selectCount(new LambdaQueryWrapper<OpenAccountDO>()
                .eq(OpenAccountDO::getSecureId, secureId)
                .eq(OpenAccountDO::getSecureKey, secureKey));

        while (total > 0) {
            secureId = RandomUtil.randomString(32);
            secureKey = RandomUtil.randomString(32);
            total = this.openAccountMapper.selectCount(new LambdaQueryWrapper<OpenAccountDO>()
                    .eq(OpenAccountDO::getSecureId, secureId)
                    .eq(OpenAccountDO::getSecureKey, secureKey));

            if (total == 0) {
                break;
            }
        }

        openAccountDO.setSecureId(secureId);
        openAccountDO.setSecureKey(secureKey);

        Assert.isTrue(this.openAccountMapper.insert(openAccountDO) > 0, () -> "创建开放账户失败");
        return ResponseResult.toSuccess(openAccountDO.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<Long> status(final OpenAccountSaveRequest request) {

        OpenAccountDO openAccountDO = this.openAccountMapper.selectById(request.getId());

        Assert.isTrue(Objects.nonNull(openAccountDO), () -> "开放账户不存在");

        OpenAccountDO updateOpenAccountDO = OpenAccountDO.builder()
                .id(openAccountDO.getId())
                .status(request.getStatus())
                .gmtModifiedName(WebUtils.getName())
                .build();

        Assert.isTrue(this.openAccountMapper.updateById(updateOpenAccountDO) > 0, () -> "更新状态失败");

        return ResponseResult.toSuccess(openAccountDO.getId());
    }

    @Override
    public ResponseResult<String> view(final Long id) {
        OpenAccountDO openAccountDO = this.openAccountMapper.selectById(id);
        Assert.isTrue(Objects.nonNull(openAccountDO), () -> "开放账户不存在");
        return ResponseResult.toSuccess(openAccountDO.getSecureKey());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<String> reset(final Long id) {

        OpenAccountDO openAccountDO = this.openAccountMapper.selectById(id);

        String secureId = openAccountDO.getSecureId();

        String secureKey = RandomUtil.randomString(32);

        int total = this.openAccountMapper.selectCount(new LambdaQueryWrapper<OpenAccountDO>()
                .eq(OpenAccountDO::getSecureId, secureId)
                .eq(OpenAccountDO::getSecureKey, secureKey));

        while (total > 0) {
            secureKey = RandomUtil.randomString(32);
            total = this.openAccountMapper.selectCount(new LambdaQueryWrapper<OpenAccountDO>()
                    .eq(OpenAccountDO::getSecureId, secureId)
                    .eq(OpenAccountDO::getSecureKey, secureKey));
            if (total == 0) {
                break;
            }
        }

        openAccountDO.setSecureId(secureId);
        openAccountDO.setSecureKey(secureKey);

        Assert.isTrue(this.openAccountMapper.updateById(openAccountDO) > 0, () -> "重置开放账户SecureKey失败");
        return ResponseResult.toSuccess(secureKey);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<Long> delete(final Long id) {
        Assert.isTrue(this.openAccountMapper.deleteById(id) > 0, () -> "删除开放账户失败");
        return ResponseResult.toSuccess(id);
    }
}
