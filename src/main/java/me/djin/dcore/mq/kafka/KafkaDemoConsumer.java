package me.djin.dcore.mq.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import me.djin.dcore.mq.IConsumer;

/**
 * 演示kafka消费者
 * 
 * @author djin
 *
 */
@Component
public class KafkaDemoConsumer implements IConsumer {

	@KafkaListener(topics = { "demo" })
	@Override
	public void process(String message) {
		System.out.println(message);
	}
}