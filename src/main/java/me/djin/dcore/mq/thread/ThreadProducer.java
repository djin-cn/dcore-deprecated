package me.djin.dcore.mq.thread;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import me.djin.dcore.mq.FutureCallback;
import me.djin.dcore.mq.IConsumer;
import me.djin.dcore.mq.IProducer;
import me.djin.dcore.mq.Message;
import me.djin.dcore.util.CommonFactory;

@Component
public class ThreadProducer implements IProducer {
	@Override
	public void send(String topic, String message) {
		send(topic, message, new FutureCallback() {

			@Override
			public void onFailure(Message message, Throwable ex) {
			}

			@Override
			public void onSuccess(Message message) {
			}
		});
	}

	@Override
	public void send(String topic, String message, FutureCallback callback) {
		String beanId = StringUtils.uncapitalize(topic + "Consumer");
		IConsumer consumer = (IConsumer) CommonFactory.getBean(beanId);
		if(consumer == null) {
			throw new NullPointerException("undefined consumer for topic:"+topic);
		}
		if(callback == null) {
			throw new NullPointerException("undefined callback for topic:"+topic);
		}
		ThreadMessagePool.getInstance().submit(topic, message, callback, consumer);
	}
}
