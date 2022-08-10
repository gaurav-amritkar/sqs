package com.aws.sqs;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SqsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SqsApplication.class, args);
	}

}
