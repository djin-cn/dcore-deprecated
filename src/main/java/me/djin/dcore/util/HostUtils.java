/**
 * 
 */
package me.djin.dcore.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

/**
 * @author djin
 * 主机工具类，获取主机信息
 */
public class HostUtils {
	/**
	 * 获取局域网IP，如果局域网IP获取不到将采用InetAddress.getLocalHost()获取的IP
	 * @return
	 */
	public static InetAddress getLanInetAddress() {
		InetAddress addr = null;
		InetAddress tmpAddr = null;
		Enumeration<NetworkInterface> networkList = null;
		
		try {
			tmpAddr = InetAddress.getLocalHost();
			if(!tmpAddr.isLoopbackAddress() && tmpAddr.isSiteLocalAddress()) {
				return tmpAddr;
			}
			networkList = NetworkInterface.getNetworkInterfaces();
		} catch (UnknownHostException e) {
			tmpAddr = null;
			try {
				networkList = NetworkInterface.getNetworkInterfaces();
			} catch (SocketException e1) {
				throw new RuntimeException("can not get ip", e1);
			}
		} catch (SocketException e) {
			return tmpAddr;
		}
		while (networkList.hasMoreElements()) {
			NetworkInterface networkInterface = networkList.nextElement();
			Enumeration<InetAddress> addressList = networkInterface.getInetAddresses();
			while (addressList.hasMoreElements()) {
				InetAddress inetAddress = addressList.nextElement();
				//loopback类型的地址，一般为本机地址127.0.0.1
				if(inetAddress.isLoopbackAddress()) {
					continue;
				}
				//非siteLocal类型地址，
				if(!inetAddress.isSiteLocalAddress()) {
					continue;
				}
				addr = inetAddress;
				break;
			}
			if(addr != null) {
				break;
			}
		}
		if(addr != null) {
			return addr;
		}
		return tmpAddr;
	}
}
