package me.djin.dcore.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Date;
import java.util.Random;

/**
 * ID生成工具，调用方式IdUtil.nextId()
 * @author djin
 *
 */
public class IdUtil {
	private static int sequence = 0;
	private static long lastTimestamp = -1L;

	private static int sequenceBit = 20;
	private static int workerIdBit = 10;
	/**
	 * 最大序号
	 */
	private static int sequenceMask = -1 ^ (-1 << sequenceBit);
	/**
	 * 当前服务器机器码
	 */
	private static long workerId = -1L;
	/**
	 * 最大主机数
	 */
	private static final int MAX_WORKER_ID = -1 ^ (-1 << workerIdBit);
	/**
	 * 最小时间戳2018-01-01 00:00:00
	 */
	private static final long MIN_TIMESTAMP = 1514764800L;

	private static long tilNextMillis(long lastTimestamp) {
		long timestamp = -1L;
		do {
			timestamp = (new Date()).getTime()/1000;
		} while (timestamp <= lastTimestamp);
		return timestamp;
	}

	/**
	 * 获取当前服务器的机器码
	 * @return
	 */
	private static final long getWorkerId() {
		if(workerId != -1L) {
			return workerId;
		}
		InetAddress ia;
		byte[] mac = null;
		try {
			// 获取本地IP对象
			ia = InetAddress.getLocalHost();
			// 获得网络接口对象（即网卡），并得到mac地址，mac地址存在于一个byte数组中。
			mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
		} catch (Exception e) {
			throw new RuntimeException("mac address is incorrect，ID is not generated correctly!", e);
		}
		long seed = 0;
		for (int i = 0; i < 6; i++) {
			seed <<= 8;
			seed |= (mac[i] & 0xff);
		}
		
		Random random = new Random(seed);
		workerId = (long)(MAX_WORKER_ID * random.nextDouble());
		return workerId;
	}

	/**
	 * 生成ID
	 * @return
	 */
	public static final synchronized long nextId() {
		long currentTime = (new Date()).getTime() / 1000;
		if (MIN_TIMESTAMP >= currentTime) {
			throw new RuntimeException("服务器时间不正确，请更新服务器时间");
		}
		if (currentTime == lastTimestamp) {
			sequence = (sequence + 1) & sequenceMask;
			if (sequence == 0) {
				currentTime = tilNextMillis(lastTimestamp);
			}
		} else {
			sequence = 0;
		}
		lastTimestamp = currentTime;

		long workerId = getWorkerId();
		return (currentTime-MIN_TIMESTAMP) << (sequenceBit + workerIdBit) | sequence << workerIdBit | workerId;

	}

	/*public static void main(String[] args) {
		HashSet<Long> ids = new HashSet<Long>();
		long startMill = (new Date()).getTime();
		for(long i = 100000L; i > 0; i--) {
			long id = IdUtil.nextId();
			ids.add(id);
			System.out.println(id);
		}
		long endMill = (new Date()).getTime();
		
		System.out.println("时长："+(endMill - startMill));
		System.out.println("ID数量："+ids.size());
	}*/
}
