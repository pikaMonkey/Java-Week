package com.pkh.configuraton;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig extends CachingConfigurerSupport {
    /**
     * @param redisConnectionFactory redis连接工厂
     * @return
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        // 设置Redis连接工厂
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        // 设置key的序列化策略
        redisTemplate.setKeySerializer(new StringRedisSerializer());

        // 设置value的序列化策略
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));

        return redisTemplate;
    }

    @Bean
    public MessageListenerAdapter biliLikeListenerAdapter() {
        return new MessageListenerAdapter(new BiliLikeListener());
    }

    @Bean
    public MessageListenerAdapter orderListenerAdapter() {
        return new MessageListenerAdapter(new OrderListener());
    }

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory redisConnectionFactory,
                                                                       @Qualifier("biliLikeListenerAdapter") MessageListenerAdapter biliLikeListenerAdapter,
                                                                       @Qualifier("orderListenerAdapter") MessageListenerAdapter orderListenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);
        container.addMessageListener(biliLikeListenerAdapter, new ChannelTopic("__bili_like__"));
        container.addMessageListener(orderListenerAdapter, new ChannelTopic("__order__"));
        return container;
    }
}
