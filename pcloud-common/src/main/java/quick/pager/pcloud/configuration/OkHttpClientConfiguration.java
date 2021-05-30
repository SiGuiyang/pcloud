package quick.pager.pcloud.configuration;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.ConnectionPool;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OkHttpClient 配置
 *
 * @author siguiyang
 */
@Configuration
public class OkHttpClientConfiguration {

    @Value("${okHttp.connectTimeout:10}")
    private Long connectTimeout;
    @Value("${okHttp.readTimeout:10}")
    private Long readTimeout;
    @Value("${okHttp.maxIdleConnections:10}")
    private Integer maxIdleConnections;
    @Value("${okHttp.keepAliveDuration:10}")
    private Long keepAliveDuration;
    @Value("${okHttp.maxRetryCount:10}")
    private Integer maxRetryCount;


    @Bean
    public OkHttpClient okHttpClient() {

        return new OkHttpClient.Builder()
                .retryOnConnectionFailure(false)
                .connectTimeout(connectTimeout, TimeUnit.SECONDS)
                .readTimeout(readTimeout, TimeUnit.SECONDS)
                .hostnameVerifier((hostname, session) -> true)
                .connectionPool(new ConnectionPool(maxIdleConnections, keepAliveDuration, TimeUnit.SECONDS))
                .addInterceptor(new RetryInterceptor(maxRetryCount))
                .build();
    }


    class RetryInterceptor implements Interceptor {
        private int maxRetryCount;
        private int count = 0;

        public RetryInterceptor(int maxRetryCount) {
            this.maxRetryCount = maxRetryCount;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {

            return retry(chain);
        }

        private Response retry(Chain chain) {
            Response response = null;
            Request request = chain.request();
            try {
                response = chain.proceed(request);
                while (!response.isSuccessful() && count < maxRetryCount) {
                    count++;
                    response = retry(chain);
                }
            } catch (Exception e) {
                while (count < maxRetryCount) {
                    count++;
                    response = retry(chain);
                }
            }
            return response;
        }
    }
}
