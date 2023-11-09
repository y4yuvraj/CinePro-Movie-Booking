package com.yuv.movieBooking.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaProducerConfig {

	@Bean
	public NewTopic topic() {
		return TopicBuilder.name("movie")
				.build();
	}
	
}
