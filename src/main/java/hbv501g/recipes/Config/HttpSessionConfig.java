package hbv501g.recipes.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@EnableRedisHttpSession
public class HttpSessionConfig {
    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        // Specify the Redis hostname and port explicitly
        return new LettuceConnectionFactory("redis-19791.c261.us-east-1-4.ec2.redns.redis-cloud.com", 19791);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory());
        return template;
    }
}
