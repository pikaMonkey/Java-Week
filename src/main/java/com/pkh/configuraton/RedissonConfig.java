package com.pkh.configuraton;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
//        config.useClusterServers()
//                .addNodeAddress("redis://127.0.0.1:7001")
//                .addNodeAddress("redis://127.0.0.1:7002")
//                .addNodeAddress("redis://127.0.0.1:7003");
        config.useSingleServer()
                .setAddress("redis://127.0.0.1:6379")
                .setDatabase(1);
        return Redisson.create(config);
    }
}
