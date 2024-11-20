package hbv501g.recipes.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisConnectionTest implements CommandLineRunner {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Override
    public void run(String... args) throws Exception {
        try {
            // Test connection
            redisTemplate.opsForValue().set("test-key", "test-value");
            String value = redisTemplate.opsForValue().get("test-key");
            System.out.println("Successfully connected to Redis! Value: " + value);

            // Log Redis details
            System.out.println("Redis Connection Factory: " + redisConnectionFactory.getConnection().toString());
        } catch (Exception e) {
            System.err.println("Failed to connect to Redis: " + e.getMessage());
        }
    }
}
