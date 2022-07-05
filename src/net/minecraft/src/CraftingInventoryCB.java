package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;

public class CraftingInventoryCB {
	protected List list = new ArrayList();

	public void onCraftGuiClosed(EntityPlayer entityPlayer) {
		InventoryPlayer var2 = entityPlayer.inventory;
		if(var2.draggedItemStack != null) {
			entityPlayer.dropPlayerItem(var2.draggedItemStack);
		}

	}

	public void onCraftMatrixChanged(IInventory inventory) {
	}
}
