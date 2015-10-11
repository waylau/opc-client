package com.waylau.opc.utgard.client;

import java.net.UnknownHostException;
import java.util.concurrent.Executors;

import org.jinterop.dcom.common.JIException;
import org.openscada.opc.lib.common.ConnectionInformation;
import org.openscada.opc.lib.common.NotConnectedException;
import org.openscada.opc.lib.da.AccessBase;
import org.openscada.opc.lib.da.AddFailedException;
import org.openscada.opc.lib.da.AutoReconnectController;
import org.openscada.opc.lib.da.DataCallback;
import org.openscada.opc.lib.da.DuplicateGroupException;
import org.openscada.opc.lib.da.Item;
import org.openscada.opc.lib.da.ItemState;
import org.openscada.opc.lib.da.Server;
import org.openscada.opc.lib.da.SyncAccess;

import com.waylau.opc.utgard.BaseConfiguration;

/**
 * 说明：
 *
 * @author <a href="http://www.waylau.com">waylau.com</a>  2015年10月11日
 */
public class Test {

	private static final long SLEEP = 1000;
	private static final int PERIOD = 10000;

	/**
	 * 
	 */
	public Test() {
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
	public static void main(String[] args) throws IllegalArgumentException, UnknownHostException, NotConnectedException, JIException, DuplicateGroupException, AddFailedException, InterruptedException {
		// create connection information
		// if ProgId is not working, try it using the Clsid instead
		ConnectionInformation ci =  BaseConfiguration.getCLSIDConnectionInfomation();
		
		// create a new server
		Server server = new Server(ci,
				Executors.newSingleThreadScheduledExecutor());

		AutoReconnectController controller = new AutoReconnectController(server);
		
		// aoto reconnect to server
		controller.connect();

		// add sync access, poll every PERIOD ms
		AccessBase access = new SyncAccess(server, PERIOD);

		access.addItem("Random.Real5", new DataCallback() {
			private int i;

			@Override
			public void changed(Item item, ItemState itemstate) {
				System.out.println((++i) + ",ItemName:"+ item.getId()
						+ ",value:" + itemstate.getValue());
			}
		});

		// start reading
		access.bind();
		
		// wait a little bit
		Thread.sleep(SLEEP);
		
		// stop reading
		access.unbind();
		
		// disconnect from server
		controller.disconnect();
	}

}
