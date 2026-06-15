package io.github.carlinhoshk.iot_data_consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class IotDataConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(IotDataConsumerApplication.class, args);
	}

}
