package quick.pager.pcloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import quick.pager.pcloud.dto.LeafDTO;
import quick.pager.pcloud.mapper.IDAllocMapper;
import quick.pager.pcloud.model.LeafDO;
import quick.pager.pcloud.model.response.ResponseResult;
import quick.pager.pcloud.request.IdGenPageRequest;
import quick.pager.pcloud.request.IdGenSaveRequest;
import quick.pager.pcloud.service.SegmentService;
import quick.pager.pcloud.utils.Assert;

@Service
public class SegmentServiceImpl implements SegmentService {

    @Resource
    private IDAllocMapper idAllocMapper;


    private LeafDO convert(final IdGenSaveRequest request) {
        return LeafDO.builder()
                .bizTag(request.getBizTag())
                .maxId(request.getMaxId())
                .step(request.getStep())
                .updateTime(LocalDateTime.now())
                .description(request.getDescription())
                .author(request.getAuthor())
                .build();
    }


    private LeafDTO convert(final LeafDO leafDO) {
        return LeafDTO.builder()
                .bizTag(leafDO.getBizTag())
                .maxId(leafDO.getMaxId())
                .step(leafDO.getStep())
                .description(leafDO.getDescription())
                .updateTime(leafDO.getUpdateTime())
                .author(leafDO.getAuthor())
                .build();
    }

    @Override
    public ResponseResult<String> create(final IdGenSaveRequest request) {

        LeafDO leafDO = this.convert(request);
        Assert.isTrue(idAllocMapper.insert(leafDO) > 0, () -> "新增号段失败");
        return ResponseResult.toSuccess(request.getBizTag());
    }

    @Override
    public ResponseResult<String> modify(final IdGenSaveRequest request) {

        LeafDO leafDO = this.convert(request);

        Assert.isTrue(idAllocMapper.update(leafDO, new LambdaQueryWrapper<LeafDO>().eq(LeafDO::getBizTag, request.getBizTag())) > 0,
                () -> "号段更新失败");
        return ResponseResult.toSuccess(request.getBizTag());
    }

    @Override
    public ResponseResult<List<LeafDTO>> page(final IdGenPageRequest request) {

        LambdaQueryWrapper<LeafDO> wrapper = new LambdaQueryWrapper<LeafDO>()
                .like(StringUtils.isNotEmpty(request.getDescription()), LeafDO::getDescription, request.getDescription());

        int total = idAllocMapper.selectCount(wrapper);

        List<LeafDTO> dtos = Collections.emptyList();
        if (total > 0) {

            List<LeafDO> leafDOS = idAllocMapper.selectPage(new Page<>(request.getPage(), request.getPageSize(), false), wrapper).getRecords();

            dtos = leafDOS.stream().map(this::convert).collect(Collectors.toList());

        }

        return ResponseResult.toSuccess(dtos, total);
    }
}
