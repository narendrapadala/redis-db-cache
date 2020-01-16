package cisco.redis.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;


/**
 * Sample cache example
 *
 */
@SpringBootApplication
@ComponentScan({ "cisco.redis.cache" })
@EnableCaching
public class App 
{
	
	private static final Logger LOG = LoggerFactory.getLogger(App.class);
	
    public static void main( String[] args )
    {
    	ApplicationContext ctx = SpringApplication.run(App.class, args);

		LOG.info("Redis cache test application is running..!");

		System.out.println("Redis cache test application is running..!!");
    }
}
