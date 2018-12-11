package me.djin.dcore.frame.common;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import me.djin.dcore.mq.IProducer;
import me.djin.dcore.mq.thread.ThreadMessagePool;
import me.djin.dcore.util.CommonFactory;

/**
 * 创建MQ实例工厂，通过配置文件的dcore.mq指定MQ提供者。
 * @author djin
 *
 */
@Configuration
public class MqFactory {
	/**
	 * 通过配置文件的dcore.mq指定MQ提供者。
	 * 线程MQ只支持单机方式通过多线程创建消息，不支持集群，如需集群支持可采用其他厂商提供的产品，如:kafka, activemq, rabbitmq等。
	 * 如果未指定，默认使用ThreadMQ，如需手动指定为ThreadMQ，则必须配置为thread。其他厂商的可自定义，符合命名规则即可。
	 * 通过此方法创建的MQ实例，必须遵从以下命名规则：dcore.mq配置项+Producer，如配置项指定为active，则Producer必须命名为ActiveProducer。
	 * 
	 * 框架默认提供了两种MQ：ThreadMQ、Kafka。Kafka需要安装服务以提供MQ服务；ThreadMQ不需要，可直接使用。
	 * @return
	 */
	@Bean
	public IProducer getBean() {
		String factoryName = "thread";
		String beanId = StringUtils.uncapitalize(factoryName) + "Producer";
		IProducer producer = (IProducer)CommonFactory.getBean(beanId);
		ThreadMessagePool.getInstance();
		return producer;
	}
}