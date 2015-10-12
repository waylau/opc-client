package com.waylau.opc.utgard.client;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Executors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jinterop.dcom.common.JIException;
import org.openscada.opc.lib.common.ConnectionInformation;
import org.openscada.opc.lib.common.NotConnectedException;
import org.openscada.opc.lib.da.AccessBase;
import org.openscada.opc.lib.da.AddFailedException;
import org.openscada.opc.lib.da.AutoReconnectController;
import org.openscada.opc.lib.da.DuplicateGroupException;
import org.openscada.opc.lib.da.Server;
import org.openscada.opc.lib.da.SyncAccess;

import com.waylau.opc.utgard.BaseConfiguration;
import com.waylau.opc.utgard.DataItem;
/**
 * 说明：
 *
 * @author <a href="http://www.waylau.com">waylau.com</a>  2015年10月11日
 */
public class DateItemfTest {
	private static final Logger logger = LogManager.getLogger();
	
	private static final long SLEEP = 1000;
	private static final int PERIOD = 10000;

	/**
	 * 
	 */
	public DateItemfTest() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 * @throws DuplicateGroupException 
	 * @throws JIException 
	 * @throws NotConnectedException 
	 * @throws UnknownHostException 
	 * @throws IllegalArgumentException 
	 * @throws AddFailedException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws IllegalArgumentException, 
	UnknownHostException, NotConnectedException, JIException, DuplicateGroupException, 
	AddFailedException, InterruptedException {
		// create connection information
		// if ProgId is not working, try it using the Clsid instead
		ConnectionInformation ci =  BaseConfiguration.getCLSIDConnectionInfomation();
		
		// create a new server
		Server server = new Server(ci,
				Executors.newSingleThreadScheduledExecutor());

		AutoReconnectController controller = new AutoReconnectController(server);
		
		// auto reconnect to server
		controller.connect();

		// add sync access, poll every PERIOD ms
		AccessBase access = new SyncAccess(server, PERIOD);

		String[] items = BaseConfiguration.getItems();
		Map<String,DataItem> itemsMap = new HashMap<String,DataItem>();
		
		for (String item : items) {
			DataItem dataItem = new DataItem();
			access.addItem(item, dataItem);
			itemsMap.put(item, dataItem);
		}


		// start reading
		access.bind();

		// wait a little bit
		Thread.sleep(SLEEP);
		 
		for (Entry<String, DataItem> entry : itemsMap.entrySet()) {
			String key = entry.getKey();
			DataItem dataitem = itemsMap.get(key);
			
			logger.info("item:"+ key + ";itemId:" + dataitem.getItem().getId() + 
					";itemValue:" + dataitem.getItemstate().getValue());
		}

		// stop reading
		access.unbind();
		
		// disconnect from server
		controller.disconnect();
	}

}
