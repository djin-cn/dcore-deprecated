/**
 * 
 */
package me.djin.dcore.mq;

import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * @author djin
 * 消息生产者
 */
public interface IProducer {
	/**
	 * 发送主题消息
	 * @param topic 主题
	 * @param message 消息
	 */
	void send(String topic, String message);
	
	/**
	 * 发送主题消息
	 * @param topic
	 * @param message
	 * @param callback
	 */
	void send(String topic, String message, ListenableFutureCallback<SendResult<Integer, String>> callback);
}
