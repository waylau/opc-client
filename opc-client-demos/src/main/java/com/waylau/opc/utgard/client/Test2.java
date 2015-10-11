package com.waylau.opc.utgard.client;

import java.net.UnknownHostException;
import java.util.concurrent.Executors;

import org.jinterop.dcom.common.JIException;
import org.openscada.opc.lib.common.AlreadyConnectedException;
import org.openscada.opc.lib.common.ConnectionInformation;
import org.openscada.opc.lib.common.NotConnectedException;
import org.openscada.opc.lib.da.AddFailedException;
import org.openscada.opc.lib.da.DuplicateGroupException;
import org.openscada.opc.lib.da.Server;
import org.openscada.opc.lib.da.browser.Branch;
import org.openscada.opc.lib.da.browser.FlatBrowser;
import org.openscada.opc.lib.da.browser.Leaf;

import com.waylau.opc.utgard.BaseConfiguration;

/**
 * 说明：
 *
 * @author <a href="http://www.waylau.com">waylau.com</a>  2015年10月11日
 */
public class Test2 {

	/**
	 * 
	 */
	public Test2() {
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
	 * @throws AlreadyConnectedException 
	 */
	public static void main(String[] args) throws IllegalArgumentException, UnknownHostException, NotConnectedException, JIException, DuplicateGroupException, AddFailedException, InterruptedException, AlreadyConnectedException {
		// TODO Auto-generated method stub
		ConnectionInformation ci =  BaseConfiguration.getCLSIDConnectionInfomation();
		Server server = new Server(ci,
				Executors.newSingleThreadScheduledExecutor());

		server.connect();

		dumpTree(server.getTreeBrowser().browse(), 0);
		dumpFlat(server.getFlatBrowser());

		server.disconnect();
	}

	private static void dumpFlat(final FlatBrowser browser)
			throws IllegalArgumentException, UnknownHostException, JIException {
		for (String name : browser.browse()) {
			System.out.println(name);
		}
	}

	private static void dumpTree(final Branch branch, final int level) {

		for (final Leaf leaf : branch.getLeaves()) {
			dumpLeaf(leaf, level);
		}
		for (final Branch subBranch : branch.getBranches()) {
			dumpBranch(subBranch, level);
			dumpTree(subBranch, level + 1);
		}
	}

	private static String printTab(int level) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < level; i++) {
			sb.append("\t");
		}
		return sb.toString();
	}

	private static void dumpLeaf(final Leaf leaf, final int level) {
		System.out.println(printTab(level) + "Leaf: " + leaf.getName() + ":"
				+ leaf.getItemId());
	}

	private static void dumpBranch(final Branch branch, final int level) {
		System.out.println(printTab(level) + "Branch: " + branch.getName());
	}

}
