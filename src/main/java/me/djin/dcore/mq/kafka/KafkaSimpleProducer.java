/**
 * 
 */
package me.djin.dcore.mq.kafka;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import me.djin.dcore.exception.ApplicationException;
import me.djin.dcore.mq.IProducer;

/**
 * @author djin Kafka消息生产者
 */
@Component
public class KafkaSimpleProducer implements IProducer {
	private static final Logger LOGGER = LoggerFactory.getLogger(KafkaSimpleProducer.class);
	@Autowired
	private KafkaTemplate<Integer, String> kafkaTemplate;

	/**
	 * (non-Javadoc)
	 * @see me.djin.dcore.mq.IProducer#send(java.lang.String, java.lang.String)
	 */
	@Override
	public void send(String topic, String message) {
		send(topic, message, new ListenableFutureCallback<Object>() {
			@Override
			public void onSuccess(Object result) {
				LOGGER.debug(String.format("topic:%s; message:%s; 发送成功!", topic, message));
			}

			@Override
			public void onFailure(Throwable ex) {
				HashMap<String, String> data = new HashMap<String, String>(2);
				data.put("topic", topic);
				data.put("message", message);
				throw new ApplicationException(new Exception(ex), data);
			}
		});
	}

	@Override
	public void send(String topic, String message, ListenableFutureCallback<Object> callback) {
		ListenableFuture<SendResult<Integer, String>> future = kafkaTemplate.send(topic, message);
		future.addCallback(callback);
	}
}