package quick.pager.pcloud.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDbFactory;

/**
 * mongoDB配置
 *
 * @author siguiyang
 */
@Configuration
public class MongoConfiguration {

    @Value("${spring.data.mongodb.log.uri}")
    private String logUrl;

    @Bean("logMongoTemplate")
    @Primary
    public MongoTemplate logMongoTemplate() {
        return new MongoTemplate(new SimpleMongoClientDbFactory(logUrl));
    }

}
