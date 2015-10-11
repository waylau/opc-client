/**
 * 
 */
package com.waylau.opc.client;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 说明：
 *
 * @author <a href="http://www.waylau.com">waylau.com</a> 2015年9月8日 
 */
public class App {
	private static final Logger logger = LogManager.getLogger();
	
	/**
	 * 
	 */
	public App() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 * @throws UnknownHostException 
	 */
	public static void main(String[] args) throws UnknownHostException {
		logger.info(InetAddress.getLocalHost());
		logger.info(Inet4Address.getLocalHost());
		logger.info(Inet6Address.getLocalHost());
		logger.info(InetAddress.getLocalHost().getHostAddress());
		
		String host = "192.168.11.103";
		InetAddress[] addrs = InetAddress.getAllByName(host);
		
		for(InetAddress addr : addrs){
			logger.info(addr.getHostAddress());
			logger.info(addr.getHostName());
			logger.info(addr.getCanonicalHostName());
		}
 
	}

}
