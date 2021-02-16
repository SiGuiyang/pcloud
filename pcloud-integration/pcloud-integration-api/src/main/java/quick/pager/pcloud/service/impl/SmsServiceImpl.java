package quick.pager.pcloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import quick.pager.pcloud.dto.SmsDTO;
import quick.pager.pcloud.mapper.SmsMapper;
import quick.pager.pcloud.model.SmsDO;
import quick.pager.pcloud.model.response.ResponseResult;
import quick.pager.pcloud.request.SmsPageRequest;
import quick.pager.pcloud.integration.request.SmsRequest;
import quick.pager.pcloud.request.SmsSaveRequest;
import quick.pager.pcloud.service.SmsService;
import quick.pager.pcloud.utils.Assert;

@Service
@Slf4j
public class SmsServiceImpl implements SmsService {

    @Resource
    private SmsMapper smsMapper;

    // region 数据转换

    private SmsDO convert(final SmsSaveRequest request) {
        return SmsDO.builder()
                .id(request.getId())
                .name(request.getName())
                .code(request.getCode())
                .description(request.getDescription())
                .gmtCreatedName(request.getGmtCreatedName())
                .gmtModifiedName(request.getGmtModifiedName())
                .gmtCreatedDate(request.getGmtCreatedDate())
                .gmtModifiedDate(request.getGmtModifiedDate())
                .deleteStatus(Boolean.FALSE)
                .build();
    }


    private SmsDTO convert(final SmsDO smsDO) {
        return SmsDTO.builder()
                .id(smsDO.getId())
                .name(smsDO.getName())
                .code(smsDO.getCode())
                .description(smsDO.getDescription())
                .gmtModifiedDate(smsDO.getGmtModifiedDate())
                .gmtModifiedName(smsDO.getGmtModifiedName())
                .build();
    }

    // endregion


    @Override
    public ResponseResult<String> send(final SmsRequest request) {
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<Long> create(final SmsSaveRequest request) {

        SmsDO smsDO = this.convert(request);

        Assert.isTrue(this.smsMapper.insert(smsDO) > 0, () -> "创建短信失败");

        return ResponseResult.toSuccess(smsDO.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<Long> modify(final SmsSaveRequest request) {
        SmsDO smsDO = this.convert(request);

        Assert.isTrue(this.smsMapper.updateById(smsDO) > 0, () -> "创建短信失败");

        return ResponseResult.toSuccess(smsDO.getId());
    }

    @Override
    public ResponseResult<List<SmsDTO>> page(final SmsPageRequest request) {

        LambdaQueryWrapper<SmsDO> wrapper = new LambdaQueryWrapper<SmsDO>().likeRight(StringUtils.isNotEmpty(request.getName()), SmsDO::getName, request.getName())
                .eq(StringUtils.isNotEmpty(request.getCode()), SmsDO::getCode, request.getCode());


        int total = this.smsMapper.selectCount(wrapper);

        List<SmsDTO> dtos = Collections.emptyList();

        if (total > 0) {
            List<SmsDO> smsDOS = this.smsMapper.selectPage(new Page<>(request.getPage(), request.getPageSize(), false), wrapper).getRecords();
            dtos = smsDOS.stream().map(this::convert).collect(Collectors.toList());
        }

        return ResponseResult.toSuccess(dtos, total);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<Long> delete(final Long id) {
        Assert.isTrue(this.smsMapper.deleteById(id) > 0, () -> "删除短信失败");
        return ResponseResult.toSuccess(id);
    }
}
