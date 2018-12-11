package me.djin.dcore.mq.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import me.djin.dcore.exception.ApplicationException;
import me.djin.dcore.mq.FutureCallback;
import me.djin.dcore.mq.IProducer;
import me.djin.dcore.mq.Message;
import me.djin.dcore.mq.Message.Status;

/**
 * @author djin Kafka消息生产者
 */
@Component
public class KafkaProducer implements IProducer {
	private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducer.class);
	@Autowired
	private KafkaTemplate<Integer, String> kafkaTemplate;

	/**
	 * (non-Javadoc)
	 * @see me.djin.dcore.mq.IProducer#send(java.lang.String, java.lang.String)
	 */
	@Override
	public void send(String topic, String message) {
		send(topic, message, new FutureCallback() {

			@Override
			public void onFailure(Message message, Throwable ex) {
				throw new ApplicationException(new Exception(ex), message);
			}

			@Override
			public void onSuccess(Message message) {
				LOGGER.debug(String.format("topic:%s; message:%s; 发送成功!", topic, message));
			}
		});
	}

	@Override
	public void send(String topic, String message, FutureCallback callback) {
		ListenableFuture<SendResult<Integer, String>> future = kafkaTemplate.send(topic, message);
		future.addCallback(new ListenableFutureCallback<SendResult<Integer, String>>() {
			@Override
			public void onSuccess(SendResult<Integer, String> result) {
				Message message = new Message();
				message.setTopic(result.getProducerRecord().topic());
				message.setMessage(result.getProducerRecord().value());
				message.setStatus(Status.SUCCESS);
				callback.onSuccess(message);
			}

			@Override
			public void onFailure(Throwable ex) {
				callback.onFailure(null, ex);
			}
		});
	}
}
