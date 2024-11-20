package hbv501g.recipes.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisVerificationController {

    @Autowired
    private LettuceConnectionFactory lettuceConnectionFactory;

    @GetMapping("/verify-redis")
    public String verifyRedisConnection() {
        String host = lettuceConnectionFactory.getHostName();
        int port = lettuceConnectionFactory.getPort();
        return "Connected to Redis on host: " + host + ", port: " + port;
    }
}
