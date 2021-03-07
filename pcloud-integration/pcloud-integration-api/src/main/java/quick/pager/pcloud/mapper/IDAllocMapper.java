package quick.pager.pcloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import quick.pager.pcloud.model.LeafAllocDO;
import quick.pager.pcloud.model.LeafDO;

@Mapper
public interface IDAllocMapper extends BaseMapper<LeafDO> {

    @Select("SELECT biz_tag, max_id, step, update_time FROM t_leaf_alloc")
    @Results(value = {
            @Result(column = "biz_tag", property = "key"),
            @Result(column = "max_id", property = "maxId"),
            @Result(column = "step", property = "step"),
            @Result(column = "update_time", property = "updateTime")
    })
    List<LeafAllocDO> getAllLeafAllocs();

    @Select("SELECT biz_tag, max_id, step FROM t_leaf_alloc WHERE biz_tag = #{tag}")
    @Results(value = {
            @Result(column = "biz_tag", property = "key"),
            @Result(column = "max_id", property = "maxId"),
            @Result(column = "step", property = "step")
    })
    LeafAllocDO getLeafAlloc(@Param("tag") String tag);

    @Update("UPDATE t_leaf_alloc SET max_id = max_id + step WHERE biz_tag = #{tag}")
    void updateMaxId(@Param("tag") String tag);

    @Update("UPDATE t_leaf_alloc SET max_id = max_id + #{step} WHERE biz_tag = #{key}")
    void updateMaxIdByCustomStep(@Param("leafAllocDO") LeafAllocDO leafAllocDO);

    @Select("SELECT biz_tag FROM t_leaf_alloc")
    List<String> getAllTags();
}
