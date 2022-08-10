package com.aws.sqs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
public class SQSController {

    @Autowired
    SQSService sqsService;

    @GetMapping("/")
    public void sendMessage() throws Exception {
        String message = "Hello from IntelliJ! " + new Random().nextInt(100);
        sqsService.publishMessageToQueue(message, "100");
    }
}
