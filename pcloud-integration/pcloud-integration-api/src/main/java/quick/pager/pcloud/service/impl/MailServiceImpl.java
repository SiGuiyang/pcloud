package quick.pager.pcloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import quick.pager.pcloud.dto.MailDTO;
import quick.pager.pcloud.mapper.MailMapper;
import quick.pager.pcloud.model.MailDO;
import quick.pager.pcloud.response.ResponseResult;
import quick.pager.pcloud.request.MailPageRequest;
import quick.pager.pcloud.request.MailSaveRequest;
import quick.pager.pcloud.service.MailService;
import quick.pager.pcloud.utils.Assert;

@Service
@Slf4j
public class MailServiceImpl implements MailService {

    @Resource
    private MailMapper mailMapper;
    // region 数据转换

    private MailDO convert(final MailSaveRequest request) {
        return MailDO.builder()
                .id(request.getId())
                .name(request.getName())
                .phone(request.getPhone())
                .state(Boolean.FALSE)
                .description(request.getDescription())
                .gmtCreatedName(request.getGmtCreatedName())
                .gmtModifiedName(request.getGmtModifiedName())
                .gmtCreatedDate(request.getGmtCreatedDate())
                .gmtModifiedDate(request.getGmtModifiedDate())
                .deleteStatus(Boolean.FALSE)
                .build();
    }

    private MailDTO convert(final MailDO mailDO) {
        return MailDTO.builder()
                .id(mailDO.getId())
                .name(mailDO.getName())
                .username(mailDO.getUsername())
                .phone(mailDO.getPhone())
                .avatar(mailDO.getAvatar())
                .sendUsername(mailDO.getSendUsername())
                .sendPhone(mailDO.getSendPhone())
                .sendAvatar(mailDO.getSendAvatar())
                .state(mailDO.getState())
                .description(mailDO.getDescription())
                .gmtModifiedDate(mailDO.getGmtModifiedDate())
                .gmtModifiedName(mailDO.getGmtModifiedName())
                .build();
    }

    // endregion


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<Long> create(final MailSaveRequest request) {

        MailDO MailDO = this.convert(request);

        Assert.isTrue(this.mailMapper.insert(MailDO) > 0, () -> "创建站内信失败");

        return ResponseResult.toSuccess(MailDO.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<Long> modify(final MailSaveRequest request) {
        MailDO MailDO = this.convert(request);

        Assert.isTrue(this.mailMapper.updateById(MailDO) > 0, () -> "创建站内信失败");

        return ResponseResult.toSuccess(MailDO.getId());
    }

    @Override
    public ResponseResult<List<MailDTO>> page(final MailPageRequest request) {

        LambdaQueryWrapper<MailDO> wrapper = new LambdaQueryWrapper<MailDO>()
                .likeRight(StringUtils.isNotEmpty(request.getName()), MailDO::getName, request.getName())
                .eq(StringUtils.isNotEmpty(request.getPhone()), MailDO::getPhone, request.getPhone())
                .eq(Objects.nonNull(request.getState()), MailDO::getState, request.getState());


        int total = this.mailMapper.selectCount(wrapper);

        List<MailDTO> dtos = Collections.emptyList();

        if (total > 0) {
            List<MailDO> MailDOS = this.mailMapper.selectPage(new Page<>(request.getPage(), request.getPageSize(), false), wrapper).getRecords();
            dtos = MailDOS.stream().map(this::convert).collect(Collectors.toList());
        }

        return ResponseResult.toSuccess(dtos, total);
    }

    @Override
    public ResponseResult<MailDTO> detail(final Long id) {

        MailDO mailDO = this.mailMapper.selectById(id);
        Assert.isTrue(Objects.nonNull(mailDO), () -> "站内信不存在");

        MailDO updateMailDO = MailDO.builder()
                .id(mailDO.getId())
                .state(Boolean.TRUE)
                .build();
        Assert.isTrue(this.mailMapper.updateById(updateMailDO) > 0, () -> "站内信已读更新失败");

        return ResponseResult.toSuccess(this.convert(mailDO));
    }

    @Override
    public ResponseResult<Integer> quantity(final String phone) {

        Integer count = this.mailMapper.selectCount(new LambdaQueryWrapper<MailDO>()
                .eq(MailDO::getPhone, phone)
                .eq(MailDO::getState, Boolean.FALSE));

        return ResponseResult.toSuccess(count);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<Long> delete(final Long id) {
        Assert.isTrue(this.mailMapper.deleteById(id) > 0, () -> "删除站内信失败");
        return ResponseResult.toSuccess(id);
    }
}
