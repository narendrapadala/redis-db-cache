package cisco.redis.cache.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import java.time.Duration;
import io.lettuce.core.ClientOptions;
import io.lettuce.core.SocketOptions;

@Configuration
public class RedisCacheConfig {

	@Value("${redis.hostname:localhost}")
	private String redisHostName;

	@Value("${redis.port:6379}")
	private int redisPort;

	@Value("${redis.ttl.hours:1}")
	private int redisDataTTL;

	@Value("${redis.password:}")
	private String redisPassword;

	@Value("${redis.timeout.secs:1}")
	private int redisTimeoutInSecs;

	@Value("${redis.socket.timeout.secs:1}")
	private int redisSocketTimeoutInSecs;

	@Bean
	public LettuceConnectionFactory redisConnectionFactory() {

		final SocketOptions socketOptions = SocketOptions.builder()
				.connectTimeout(Duration.ofSeconds(redisSocketTimeoutInSecs)).build();

		final ClientOptions clientOptions = ClientOptions.builder().socketOptions(socketOptions).build();

		LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
				.commandTimeout(Duration.ofSeconds(redisTimeoutInSecs)).clientOptions(clientOptions).build();

		RedisStandaloneConfiguration redisConf = new RedisStandaloneConfiguration();
		redisConf.setHostName(redisHostName);
		redisConf.setPort(redisPort);
		redisConf.setPassword(RedisPassword.of(redisPassword));
		final LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(redisConf);
		lettuceConnectionFactory.setValidateConnection(true);

		// return
		return lettuceConnectionFactory;
	}

	@Bean
	public RedisTemplate<Object, Object> redisTemplate() {
		RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<Object, Object>();
		redisTemplate.setConnectionFactory(redisConnectionFactory());
		return redisTemplate;
	}

	@Bean
	public RedisCacheConfiguration cacheConfiguration() {
		RedisCacheConfiguration cacheConfig = RedisCacheConfiguration.defaultCacheConfig()
				.entryTtl(Duration.ofSeconds(600)).disableCachingNullValues();
		return cacheConfig;
	}

	@Bean
	public RedisCacheManager cacheManager() {
		RedisCacheManager rcm = RedisCacheManager.builder(redisConnectionFactory()).cacheDefaults(cacheConfiguration())
				.transactionAware().build();
		return rcm;
	}

	public CacheErrorHandler errorHandler() {
		return new RedisCacheErrorHandler();
	}
}
