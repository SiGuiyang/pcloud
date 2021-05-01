package quick.pager.pcloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import quick.pager.pcloud.constants.LConsts;
import quick.pager.pcloud.dto.PostDTO;
import quick.pager.pcloud.mapper.PostMapper;
import quick.pager.pcloud.model.PostDO;
import quick.pager.pcloud.response.ResponseResult;
import quick.pager.pcloud.request.PostOtherRequest;
import quick.pager.pcloud.request.PostPageRequest;
import quick.pager.pcloud.request.PostSaveRequest;
import quick.pager.pcloud.service.PostService;
import quick.pager.pcloud.utils.Assert;

@Service
public class PostServiceImpl implements PostService {

    @Resource
    private PostMapper postMapper;

    // region 数据转换

    /**
     * request -> DO
     *
     * @param request 请求参数
     * @return MenuDO
     */
    private PostDO convert(final PostSaveRequest request) {

        return PostDO.builder()
                .id(request.getId())
                .parentId(request.getParentId())
                .name(request.getName())
                .gmtCreatedName(request.getGmtCreatedName())
                .gmtModifiedName(request.getGmtModifiedName())
                .gmtCreatedDate(request.getGmtCreatedDate())
                .gmtModifiedDate(request.getGmtModifiedDate())
                .deleteStatus(Boolean.FALSE)
                .build();
    }

    /**
     * DO -> DTO
     *
     * @param postDO PostDO
     * @return PostDTO
     */
    private PostDTO convert(final PostDO postDO) {
        PostDTO postDTO = PostDTO.builder()
                .id(postDO.getId())
                .parentId(Objects.nonNull(postDO.getParentId()) ? postDO.getParentId() : null)
                .label(postDO.getName())
                .name(postDO.getName())
                .gmtModifiedName(postDO.getGmtModifiedName())
                .gmtModifiedDate(postDO.getGmtModifiedDate())
                .build();

        if (Objects.nonNull(postDO.getParentId()) && LConsts.ZERO.compareTo(postDO.getParentId()) != 0) {
            postDTO.setParentName(this.postMapper.selectById(postDO.getParentId()).getName());
        }
        return postDTO;
    }

    // endregion

    @Override
    public ResponseResult<List<PostDTO>> queryPage(final PostPageRequest request) {
        LambdaQueryWrapper<PostDO> wrapper = new LambdaQueryWrapper<PostDO>()
                .likeRight(StringUtils.isNotBlank(request.getName()), PostDO::getName, request.getName());

        wrapper.orderByDesc(PostDO::getGmtCreatedDate);

        Integer count = this.postMapper.selectCount(wrapper);

        List<PostDTO> dtos = Collections.emptyList();
        if (count > 0) {
            List<PostDO> records = this.postMapper.selectPage(new Page<>(request.getPage(), request.getPageSize(), false), wrapper).getRecords();

            dtos = records.stream().map(this::convert).collect(Collectors.toList());
        }

        return ResponseResult.toSuccess(dtos, count);
    }

    @Override
    public ResponseResult<List<PostDTO>> queryList(final PostOtherRequest request) {
        LambdaQueryWrapper<PostDO> wrapper = new LambdaQueryWrapper<PostDO>()
                .in(CollectionUtils.isNotEmpty(request.getIds()), PostDO::getId, request.getIds())
                .eq(PostDO::getParentId, request.getParentId())
                .likeRight(StringUtils.isNotBlank(request.getName()), PostDO::getName, request.getName());

        List<PostDO> PostDOS = this.postMapper.selectList(wrapper);

        return ResponseResult.toSuccess(PostDOS.stream().map(this::convert).collect(Collectors.toList()));
    }

    @Override
    public ResponseResult<List<PostDTO>> queryTree(final PostOtherRequest request) {
        LambdaQueryWrapper<PostDO> wrapper = new LambdaQueryWrapper<PostDO>()
                .in(CollectionUtils.isNotEmpty(request.getIds()), PostDO::getId, request.getIds())
                .eq(Objects.nonNull(request.getParentId()), PostDO::getParentId, request.getParentId())
                .likeRight(StringUtils.isNotBlank(request.getName()), PostDO::getName, request.getName());

        List<PostDO> PostDOS = this.postMapper.selectList(wrapper);

        return ResponseResult.toSuccess(PostDOS.stream().map(this::convert).collect(Collectors.toList()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<Long> create(final PostSaveRequest request) {
        PostDO PostDO = this.convert(request);
        Assert.isTrue(this.postMapper.insert(PostDO) > 0, () -> "创建职级失败");
        return ResponseResult.toSuccess(PostDO.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<Long> modify(final PostSaveRequest request) {
        PostDO PostDO = this.convert(request);
        Assert.isTrue(this.postMapper.updateById(PostDO) > 0, () -> "更新职级失败");
        return ResponseResult.toSuccess(PostDO.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<Long> delete(final Long id) {
        Assert.isTrue(this.postMapper.deleteById(id) > 0, () -> "删除职级失败");
        return ResponseResult.toSuccess(id);
    }
}
