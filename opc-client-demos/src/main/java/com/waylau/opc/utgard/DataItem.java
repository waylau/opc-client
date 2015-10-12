/**
 * 
 */
package com.waylau.opc.utgard;

import org.openscada.opc.lib.da.DataCallback;
import org.openscada.opc.lib.da.Item;
import org.openscada.opc.lib.da.ItemState;

/**
 * 说明：
 *
 * @author <a href="http://www.waylau.com">waylau.com</a> 2015年10月12日 
 */
public class DataItem implements DataCallback {

	private Item item;
	private ItemState itemstate;
 
	/* (non-Javadoc)
	 * @see org.openscada.opc.lib.da.DataCallback#changed(org.openscada.opc.lib.da.Item, org.openscada.opc.lib.da.ItemState)
	 */
	@Override
	public void changed(Item item, ItemState itemstate) {
		this.item = item;
		this.itemstate = itemstate;
	}
	
	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public ItemState getItemstate() {
		return itemstate;
	}

	public void setItemstate(ItemState itemstate) {
		this.itemstate = itemstate;
	}
}
