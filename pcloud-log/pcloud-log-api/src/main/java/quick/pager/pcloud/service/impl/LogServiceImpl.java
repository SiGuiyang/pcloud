package quick.pager.pcloud.service.impl;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.index.IndexOperations;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import quick.pager.pcloud.dto.LogViewDTO;
import quick.pager.pcloud.log.dto.LogDTO;
import quick.pager.pcloud.model.LogDO;
import quick.pager.pcloud.model.response.ResponseResult;
import quick.pager.pcloud.log.request.LogPageRequest;
import quick.pager.pcloud.log.request.LogSaveRequest;
import quick.pager.pcloud.service.LogService;
import quick.pager.pcloud.utils.DateUtils;

@Service
public class LogServiceImpl implements LogService {

    @Resource
    @Qualifier("logMongoTemplate")
    private MongoTemplate logMongoTemplate;

    // region 数据转换

    /**
     * request -> DO
     */
    private LogDO convert(final LogSaveRequest request) {
        return LogDO.builder()
                ._id(request.get_id())
                .browser(request.getBrowser())
                .clientIp(request.getClientIp())
                .userId(request.getUserId())
                .name(request.getName())
                .os(request.getOs())
                .params(request.getParams())
                .path(request.getPath())
                .build();
    }

    /**
     * DO -> DTO
     */
    private LogDTO convert(final LogDO logDO) {
        return LogDTO.builder()
                ._id(logDO.get_id())
                .browser(logDO.getBrowser())
                .clientIp(logDO.getClientIp())
                .userId(logDO.getUserId())
                .name(logDO.getName())
                .os(logDO.getOs())
                .params(logDO.getParams())
                .path(logDO.getPath())
                .gmtModifiedDate(logDO.getGmtCreatedDate())
                .gmtModifiedName(logDO.getGmtModifiedName())
                .build();
    }

    // endregion


    @Override
    public ResponseResult<LogViewDTO> statistics() {

        String collectionName = DateUtils.dateTime().format(DateTimeFormatter.ofPattern(DateUtils.NORM_DATE_PATTERN, Locale.getDefault()));

        Query query = new Query(Criteria.where("deleteStatus").is(Boolean.FALSE));

        long total = logMongoTemplate.count(query, LogDO.class, collectionName);

        AggregationResults<String> aggregate = logMongoTemplate.aggregate(Aggregation.newAggregation(Aggregation.group("userId")), collectionName, String.class);

        return ResponseResult.toSuccess(LogViewDTO.builder()
                .dailyVisits((long) aggregate.getMappedResults().size())
                .dailyInterfaceVisits(total)
                .build());
    }

    @Override
    public ResponseResult<List<LogDTO>> queryPage(final LogPageRequest request) {

        // 得到表名
        String collectionName = Optional.ofNullable(request.getVisitDate())
                .orElseGet(LocalDate::now)
                .format(DateTimeFormatter.ofPattern(DateUtils.NORM_DATE_PATTERN, Locale.getDefault()));


        Query query = new Query(Criteria.where("deleteStatus").is(Boolean.FALSE));

        if (StringUtils.hasText(request.getName())) {
            query.addCriteria(Criteria.where("name").is(request.getName()));

        }

        query.with(Sort.by(Sort.Order.desc("gmtCreatedDate")));

        long total = logMongoTemplate.count(query, LogDO.class, collectionName);

        List<LogDTO> dtos = Collections.emptyList();
        if (total > 0) {
            //设置起始数
            query.skip((request.getPage() - 1) * request.getPageSize());
            //设置查询条数
            query.limit(request.getPageSize());
            List<LogDO> logDOS = logMongoTemplate.find(query, LogDO.class, collectionName);

            dtos = logDOS.stream().map(this::convert).collect(Collectors.toList());
        }

        return ResponseResult.toSuccess(dtos, total);
    }

    @Override
    public ResponseResult<String> create(final LogSaveRequest request) {
        LogDO logDO = this.convert(request);
        LocalDateTime dateTime = DateUtils.dateTime();
        logDO.setGmtCreatedDate(dateTime);
        logDO.setGmtModifiedDate(dateTime);
        logDO.setGmtCreatedName(request.getName());
        logDO.setGmtModifiedName(request.getName());
        logDO.setDeleteStatus(Boolean.FALSE);
        String collectionName = DateUtils.format(dateTime, DateUtils.NORM_DATE_PATTERN);
        logMongoTemplate.insert(logDO, collectionName);

        IndexOperations indexOperations = logMongoTemplate.indexOps(collectionName);
        Arrays.stream(LogDO.class.getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(Indexed.class))
                .map(Field::getName)
                .filter(StringUtils::hasText)
                .forEach(indexField -> indexOperations.ensureIndex(new Index(indexField, Sort.Direction.ASC)));
        return ResponseResult.toSuccess(logDO.get_id());
    }
}
