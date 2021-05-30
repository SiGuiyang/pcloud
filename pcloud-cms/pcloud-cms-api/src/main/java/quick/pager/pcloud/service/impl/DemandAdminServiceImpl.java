package quick.pager.pcloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import quick.pager.pcloud.mapper.DemandMapper;
import quick.pager.pcloud.member.client.MemberClient;
import quick.pager.pcloud.member.dto.MemberDTO;
import quick.pager.pcloud.model.DemandDO;
import quick.pager.pcloud.oss.client.OSSClient;
import quick.pager.pcloud.oss.request.OSSUploadRequest;
import quick.pager.pcloud.request.DemandAdminPageRequest;
import quick.pager.pcloud.request.DemandAdminSaveRequest;
import quick.pager.pcloud.response.ResponseResult;
import quick.pager.pcloud.service.DemandAdminService;
import quick.pager.pcloud.utils.Assert;
import quick.pager.pcloud.utils.DateUtils;
import quick.pager.pcloud.vo.DemandAdminVO;

@Service
@Slf4j
public class DemandAdminServiceImpl implements DemandAdminService {

    @Resource
    private DemandMapper demandMapper;

    @Resource
    private MemberClient memberClient;
    @Resource
    private OSSClient ossClient;

    // region 数据转换

    private DemandAdminVO convert(final DemandDO demandDO) {
        return DemandAdminVO.builder()
                .id(demandDO.getId())
                .name(demandDO.getName())
                .publishCode(demandDO.getPublishCode())
                .memberIdRel(demandDO.getMemberIdRel())
                .amount(demandDO.getAmount())
                .content(demandDO.getContent())
                .jumpUrl(demandDO.getJumpUrl())
                .startDate(demandDO.getStartDate())
                .endDate(demandDO.getEndDate())
                .status(demandDO.getStatus())
                .gmtModifiedDate(demandDO.getGmtModifiedDate())
                .gmtModifiedName(demandDO.getGmtModifiedName())
                .build();
    }

    private DemandDO convert(final DemandAdminSaveRequest request) {
        return DemandDO.builder()
                .id(request.getId())
                .memberIdRel(request.getMemberIdRel())
                .amount(request.getAmount())
                .content(request.getContent())
                .jumpUrl(request.getJumpUrl())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .name(request.getName())
                .publishCode(request.getPublishCode())
                .gmtCreatedName(request.getGmtCreatedName())
                .gmtModifiedName(request.getGmtModifiedName())
                .gmtCreatedDate(request.getGmtCreatedDate())
                .gmtModifiedDate(request.getGmtModifiedDate())
                .deleteStatus(Boolean.FALSE)
                .build();
    }
    // endregion

    @Override
    public ResponseResult<List<DemandAdminVO>> page(final DemandAdminPageRequest request) {

        LambdaQueryWrapper<DemandDO> wrapper = new LambdaQueryWrapper<>();

        if (StringUtils.isNotBlank(request.getPhone())) {
            ResponseResult<MemberDTO> result = memberClient.profileInfo(request.getPhone());
            if (result.check()) {
                wrapper.eq(DemandDO::getMemberIdRel, result.getData().getId());
            } else {
                log.error("获取会员服务异常 phone = {}", request.getPhone());
                return ResponseResult.toError(result.getCode(), result.getMsg());
            }
        }

        if (StringUtils.isNotBlank(request.getName())) {
            wrapper.likeRight(DemandDO::getName, request.getName());
        }
        if (Objects.nonNull(request.getStatus())) {
            wrapper.eq(DemandDO::getStatus, request.getStatus());
        }
        if (Objects.nonNull(request.getPublishCode())) {
            wrapper.eq(DemandDO::getPublishCode, request.getPublishCode());
        }
        if (Objects.nonNull(request.getStartDate()) && Objects.nonNull(request.getEndDate())) {
            wrapper.ge(DemandDO::getStartDate, request.getStartDate());
            wrapper.le(DemandDO::getEndDate, request.getEndDate());
        }

        List<DemandAdminVO> dtos = Collections.emptyList();
        Integer total = this.demandMapper.selectCount(wrapper);
        if (total > 0) {
            Page<DemandDO> page = this.demandMapper.selectPage(new Page<>(request.getPage(), request.getPageSize(), false), wrapper);

            dtos = page.getRecords().stream().map(this::convert).collect(Collectors.toList());
        }
        return ResponseResult.toSuccess(dtos, total);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<Long> create(final DemandAdminSaveRequest request) {
        DemandDO demandDO = this.convert(request);
        demandDO.setJumpUrl(this.getUrl(request.getContent()));
        demandDO.setGmtCreatedDate(DateUtils.dateTime());
        Assert.isTrue(this.demandMapper.insert(demandDO) > 0, () -> "创建需求失败");
        return ResponseResult.toSuccess(demandDO.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<Long> modify(final DemandAdminSaveRequest request) {
        DemandDO demandDO = this.convert(request);
        demandDO.setJumpUrl(this.getUrl(request.getContent()));
        demandDO.setGmtModifiedDate(DateUtils.dateTime());
        Assert.isTrue(this.demandMapper.updateById(demandDO) > 0, () -> "编辑需求失败");
        return ResponseResult.toSuccess(demandDO.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<Long> delete(final Long id) {
        Assert.isTrue(this.demandMapper.deleteById(id) > 0, "需求删除失败");
        return ResponseResult.toSuccess(id);
    }

    // region 私有方法

    /**
     * 获取访问链接
     *
     * @param content 上传内容
     */
    private String getUrl(final String content) {
        if (StringUtils.isNotBlank(content)) {

            ResponseResult<Map<String, String>> result = ossClient.upload(OSSUploadRequest.builder()
                    .filename(System.currentTimeMillis() + ".html")
                    .content(content)
                    .build());

            if (result.check()) {
                return result.getData().getOrDefault("url", "");
            }
        }
        return "";
    }

    // endregion
}
