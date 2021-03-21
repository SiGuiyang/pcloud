package quick.pager.pcloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import quick.pager.pcloud.enums.OpenAccountStatusEnums;
import quick.pager.pcloud.mapper.OpenAccountMapper;
import quick.pager.pcloud.mapper.OpenAccountResourceMapper;
import quick.pager.pcloud.mapper.OpenResourceMapper;
import quick.pager.pcloud.model.OpenAccountDO;
import quick.pager.pcloud.model.OpenAccountResourceDO;
import quick.pager.pcloud.model.OpenResourceDO;
import quick.pager.pcloud.model.response.ResponseResult;
import quick.pager.pcloud.open.dto.OpenAccountDTO;
import quick.pager.pcloud.open.request.OpenLoginRequest;
import quick.pager.pcloud.service.OpenLoginService;
import quick.pager.pcloud.utils.Assert;

@Service
@Slf4j
public class OpenLoginServiceImpl implements OpenLoginService {

    @Resource
    private OpenAccountMapper openAccountMapper;
    @Resource
    private OpenResourceMapper openResourceMapper;
    @Resource
    private OpenAccountResourceMapper openAccountResourceMapper;

    @Override
    public ResponseResult<OpenAccountDTO> login(final OpenLoginRequest request) {

        OpenAccountDO openAccountDO = this.openAccountMapper.selectOne(new LambdaQueryWrapper<OpenAccountDO>()
                .eq(OpenAccountDO::getSecureId, request.getSecureId()));

        Assert.isTrue(Objects.nonNull(openAccountDO), () -> "账户不存在");

        Assert.isTrue(OpenAccountStatusEnums.FROZEN.getCode().equals(openAccountDO.getStatus()), () -> "账户已被冻结");
        Assert.isTrue(OpenAccountStatusEnums.FORBIDDEN.getCode().equals(openAccountDO.getStatus()), () -> "账户已被禁用");
        Assert.isTrue(openAccountDO.getSecureKey().equals(request.getSecureKey()), () -> "账户密码错误");
        log.info("登录开放账户正常 secureId = {}", request.getSecureId());

        List<OpenAccountResourceDO> openAccountResourceDOS = this.openAccountResourceMapper.selectList(new LambdaQueryWrapper<OpenAccountResourceDO>()
                .eq(OpenAccountResourceDO::getAccountId, openAccountDO.getId()));

        List<String> authorities = Collections.emptyList();
        if (CollectionUtils.isNotEmpty(openAccountResourceDOS)) {
            List<Long> resourceIds = openAccountResourceDOS.stream()
                    .map(OpenAccountResourceDO::getResourceId)
                    .collect(Collectors.toList());

            List<OpenResourceDO> openResourceDOS = this.openResourceMapper.selectBatchIds(resourceIds);
            authorities = openResourceDOS.stream().map(OpenResourceDO::getResourceUrl).collect(Collectors.toList());

        }

        return ResponseResult.toSuccess(OpenAccountDTO.builder()
                .id(openAccountDO.getId())
                .secureId(openAccountDO.getSecureId())
                .secureKey(openAccountDO.getSecureKey())
                .authorities(authorities)
                .build());
    }
}
