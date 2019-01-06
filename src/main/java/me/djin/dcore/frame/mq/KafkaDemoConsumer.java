package me.djin.dcore.frame.mq;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import me.djin.dcore.mq.Consumer;

/**
 * 演示kafka消费者
 * 
 * @author djin
 *
 */
@Component
public class KafkaDemoConsumer implements Consumer {

	@KafkaListener(topics = { "demo" })
	@Override
	public void process(String message) {
		System.out.println(message);
	}
}