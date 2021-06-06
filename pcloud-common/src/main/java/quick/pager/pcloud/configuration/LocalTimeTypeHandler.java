package quick.pager.pcloud.configuration;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.springframework.stereotype.Component;
import quick.pager.pcloud.utils.DateUtils;

@Component
//定义转换器支持的JAVA类型
@MappedTypes(LocalTime.class)
//定义转换器支持的数据库类型
@MappedJdbcTypes(value = JdbcType.DATE, includeNullJdbcType = true)
public class LocalTimeTypeHandler extends BaseTypeHandler<LocalTime> {

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DateUtils.NORM_TIME_PATTERN);

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, LocalTime parameter, JdbcType jdbcType)
            throws SQLException {
        if (parameter != null) {
            ps.setString(i, dateTimeFormatter.format(parameter));
        }
    }

    @Override
    public LocalTime getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String target = rs.getString(columnName);
        if (StringUtils.isBlank(target)) {
            return null;
        }
        return LocalTime.parse(target, dateTimeFormatter);
    }

    @Override
    public LocalTime getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String target = rs.getString(columnIndex);
        if (StringUtils.isBlank(target)) {
            return null;
        }
        return LocalTime.parse(target, dateTimeFormatter);
    }

    @Override
    public LocalTime getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String target = cs.getString(columnIndex);
        if (StringUtils.isBlank(target)) {
            return null;
        }
        return LocalTime.parse(target, dateTimeFormatter);
    }

}
