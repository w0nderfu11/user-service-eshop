package io.github.vitalii.userservice.config;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

@SpringBootTest(classes = RedisConfig.class)
@TestPropertySource(properties = {
        "spring.data.redis.host=localhost",
        "spring.data.redis.port=6379",
        "spring.data.redis.password=pass"
})
public class RedisConfigTest {

    @Autowired
    private LettuceConnectionFactory lettuceConnectionFactory;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    void redisConnectionFactoryIsCreated() {
        assertThat(lettuceConnectionFactory).isNotNull();
        assertThat(lettuceConnectionFactory.getHostName()).isEqualTo("localhost");
        assertThat(lettuceConnectionFactory.getPort()).isEqualTo(6379);
        assertThat(lettuceConnectionFactory.getPassword()).isEqualTo("pass");
    }

    @Test
    void redisTemplateIsConfiguredProperly() {
        assertThat(redisTemplate).isNotNull();

        assertThat(redisTemplate.getKeySerializer()).isInstanceOf(StringRedisSerializer.class);
        assertThat(redisTemplate.getValueSerializer()).isInstanceOf(GenericJackson2JsonRedisSerializer.class);
        assertThat(redisTemplate.getHashKeySerializer()).isInstanceOf(StringRedisSerializer.class);
        assertThat(redisTemplate.getHashValueSerializer()).isInstanceOf(GenericJackson2JsonRedisSerializer.class);
    }
}

