/**
 * 
 */
package me.djin.dcore.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author djin 主机工具类，获取主机信息
 */
public class HostUtils {
	private static final Logger LOGGER = LoggerFactory.getLogger(HostUtils.class);
	
	/**
	 * 获取局域网IP，如果局域网IP获取不到将采用InetAddress.getLocalHost()获取的IP
	 * 
	 * 局域网网段一般为192.168.*、172.16.*-172.31.*、10.*
	 * 
	 * 优先192.168网段，其次172网段，最后10网段
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
				LOGGER.info(String.format("IP:%s; isLoopbackAddress:%s; isSiteLocalAddress:%s; IsLinkLocal:%s; isAnyLocal:%s; isMCGlobal:%s; isMCLinkLocal:%s; isMCNodeLocal:%s; isMCOrgLocal:%s; isMCSiteLocal:%s; isMulticast:%s;"
						, inetAddress.getHostAddress()
						, inetAddress.isLoopbackAddress()
						, inetAddress.isSiteLocalAddress()
						, inetAddress.isLinkLocalAddress()
						, inetAddress.isAnyLocalAddress()
						, inetAddress.isMCGlobal()
						, inetAddress.isMCLinkLocal()
						, inetAddress.isMCNodeLocal()
						, inetAddress.isMCOrgLocal()
						, inetAddress.isMCSiteLocal()
						, inetAddress.isMulticastAddress()
						));
				
				//loopback类型的地址，一般为本机地址127.0.0.1
				if(inetAddress.isLoopbackAddress()) {
					System.out.println("Loopback IP:"+inetAddress.getHostAddress());
					continue;
				}
				//非siteLocal类型地址，
				if(!inetAddress.isSiteLocalAddress()) {
					continue;
				}
				if(addr == null || inetAddress.getHostAddress().compareTo(addr.getHostAddress()) > 0) {
					addr = inetAddress;					
				}
			}
		}
		if(addr != null) {
			return addr;
		}
		return tmpAddr;
	}
}
