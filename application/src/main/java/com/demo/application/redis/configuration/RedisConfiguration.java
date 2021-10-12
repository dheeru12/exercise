package com.demo.application.redis.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.demo.application.model.AccountEvent;
import com.demo.application.model.AccountTransaction;

import org.springframework.data.redis.serializer.RedisSerializationContext.RedisSerializationContextBuilder;

@Configuration
public class RedisConfiguration {

	@Autowired
	private ReactiveRedisConnectionFactory reactiveRedisConnectionFactory;

	@Bean
	public <V> RedisSerializationContext<String, V> serializationContext(Class<V> class2) {
		StringRedisSerializer keySerialzer = new StringRedisSerializer();
		Jackson2JsonRedisSerializer<V> valueSerializer = new Jackson2JsonRedisSerializer<>(class2);

		RedisSerializationContextBuilder<String, V> builder = RedisSerializationContext
				.newSerializationContext(keySerialzer);
		return builder.value(valueSerializer).hashKey(keySerialzer).hashValue(valueSerializer).build();
	}

	@Bean(name = "reactiveRedisTemplateAccountTransaction")
	public ReactiveRedisTemplate<String, AccountTransaction> reactiveRedisTemplateAccountTransaction() {
		return new ReactiveRedisTemplate<String, AccountTransaction>(reactiveRedisConnectionFactory,
				serializationContext(AccountTransaction.class));
	}


	@Bean
	public ReactiveRedisTemplate<String, AccountEvent> reactiveRedisTemplateAccountEvent() {
		return new ReactiveRedisTemplate<String, AccountEvent>(reactiveRedisConnectionFactory,
				serializationContext(AccountEvent.class));
	}

}
