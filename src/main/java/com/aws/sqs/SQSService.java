package com.aws.sqs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class SQSService {

    @Autowired
    private QueueMessagingTemplate queueMessagingTemplate;

    @Value("${cloud.aws.endpoint.url}")
    private String endPoint;

    public void publishMessageToQueue(String message, String studentId) throws Exception {
        try {
            log.info("event=publishMessageToQueue, status=publishStart, studentId={}", studentId);
            Map<String, Object> headersMap = new HashMap<String, Object>();
            headersMap.put("HeaderKey", "HeaderValue");
            //queueMessagingTemplate.send(endPoint, MessageBuilder.withPayload(message).build());
            queueMessagingTemplate.convertAndSend(endPoint, MessageBuilder.withPayload(message).build(), headersMap);
            log.info("event=publishMessageToQueue, status=publishEnd, studentId={}", studentId);
        } catch (Exception e) {
            log.info("event=publishMessageToQueue, status=publishFailed, studentId={}", studentId);
        }
    }

}
