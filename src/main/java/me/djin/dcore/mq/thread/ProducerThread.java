/**
 * 
 */
package me.djin.dcore.mq.thread;

import org.springframework.util.concurrent.ListenableFutureCallback;

import me.djin.dcore.mq.IProducer;

/**
 * @author djin
 * 消息生产者线程，主要用于单机应用
 */
public class ProducerThread implements IProducer {

	@Override
	public void send(String topic, String message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void send(String topic, String message, ListenableFutureCallback<Object> callback) {
		// TODO Auto-generated method stub
		
	}

}
