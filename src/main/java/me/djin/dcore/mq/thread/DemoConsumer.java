package me.djin.dcore.mq.thread;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.djin.dcore.mq.IConsumer;
import me.djin.dcore.util.PathUtil;

public class DemoConsumer implements IConsumer {
	private static final String PATH = PathUtil.getJarPath()+"demoConsumer.log";
	private static volatile AtomicLong COUNT = new AtomicLong(0);
	private static final Logger LOG = LoggerFactory.getLogger(DemoConsumer.class);

	@Override
	public void process(String message) {
		PathUtil.initPath(PATH);
		File file = new File(PATH);
		COUNT.addAndGet(1);
		try {
			FileUtils.write(file, String.valueOf(COUNT), Charset.defaultCharset());
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
		}
		LOG.debug("DemoConsumer process:" + message);
	}

}
