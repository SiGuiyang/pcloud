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
import quick.pager.pcloud.mapper.AdMapper;
import quick.pager.pcloud.model.AdDO;
import quick.pager.pcloud.request.AdAdminPageRequest;
import quick.pager.pcloud.request.AdAdminSaveRequest;
import quick.pager.pcloud.response.ResponseResult;
import quick.pager.pcloud.service.AdAdminService;
import quick.pager.pcloud.utils.Assert;
import quick.pager.pcloud.utils.DateUtils;
import quick.pager.pcloud.vo.AdAdminVO;

@Service
@Slf4j
public class AdAdminServiceImpl implements AdAdminService {

    @Resource
    private AdMapper adMapper;

    // region 数据转换

    private AdAdminVO convert(final AdDO adDO) {
        return AdAdminVO.builder()
                .id(adDO.getId())
                .title(adDO.getTitle())
                .adType(adDO.getAdType())
                .state(adDO.getState())
                .adUrl(adDO.getAdUrl())
                .clickUrl(adDO.getClickUrl())
                .startDate(adDO.getStartDate())
                .endDate(adDO.getEndDate())
                .gmtModifiedDate(adDO.getGmtModifiedDate())
                .gmtModifiedName(adDO.getGmtModifiedName())
                .build();
    }

    private AdDO convert(final AdAdminSaveRequest request) {
        return AdDO.builder()
                .id(request.getId())
                .title(request.getTitle())
                .adUrl(request.getAdUrl())
                .clickUrl(request.getClickUrl())
                .state(request.getState())
                .adType(request.getAdType())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .gmtCreatedName(request.getGmtCreatedName())
                .gmtModifiedName(request.getGmtModifiedName())
                .gmtCreatedDate(request.getGmtCreatedDate())
                .gmtModifiedDate(request.getGmtModifiedDate())
                .deleteStatus(Boolean.FALSE)
                .build();
    }

    // endregion

    @Override
    public ResponseResult<List<AdAdminVO>> page(final AdAdminPageRequest request) {

        LambdaQueryWrapper<AdDO> wrapper = new LambdaQueryWrapper<>();

        if (StringUtils.isNotBlank(request.getTitle())) {
            wrapper.likeRight(AdDO::getTitle, request.getTitle());
        }
        if (Objects.nonNull(request.getState())) {
            wrapper.eq(AdDO::getState, request.getState());
        }
        if (Objects.nonNull(request.getAdType())) {
            wrapper.eq(AdDO::getAdType, request.getAdType());
        }
        if (Objects.nonNull(request.getStartDate()) && Objects.nonNull(request.getEndDate())) {
            wrapper.ge(AdDO::getStartDate, request.getStartDate());
            wrapper.le(AdDO::getEndDate, request.getEndDate());
        }

        List<AdAdminVO> dtos = Collections.emptyList();
        Integer total = adMapper.selectCount(wrapper);
        if (total > 0) {
            Page<AdDO> page = adMapper.selectPage(new Page<>(request.getPage(), request.getPageSize(), false), wrapper);

            dtos = page.getRecords().stream().map(this::convert).collect(Collectors.toList());
        }
        return ResponseResult.toSuccess(dtos, total);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<Long> create(final AdAdminSaveRequest request) {
        AdDO adDO = this.convert(request);
        adDO.setGmtModifiedDate(DateUtils.dateTime());
        adDO.setGmtCreatedDate(DateUtils.dateTime());
        Assert.isTrue(this.adMapper.insert(adDO) > 0, () -> "创建广告失败");
        return ResponseResult.toSuccess(adDO.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<Long> modify(final AdAdminSaveRequest request) {
        AdDO adDO = this.convert(request);
        adDO.setGmtModifiedDate(DateUtils.dateTime());
        Assert.isTrue(this.adMapper.updateById(adDO) > 0, () -> "编辑广告失败");
        return ResponseResult.toSuccess(adDO.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<Long> delete(final Long id) {
        Assert.isTrue(this.adMapper.deleteById(id) > 0, () -> "删除广告失败");
        return ResponseResult.toSuccess(id);
    }
}
