package net.minecraft.src;

public class InventoryCraftResult implements IInventory {
	private ItemStack[] stackResult = new ItemStack[1];

	public int getSizeInventory() {
		return 1;
	}

	public ItemStack getStackInSlot(int var1) {
		return this.stackResult[var1];
	}

	public String getInvName() {
		return "Result";
	}

	public ItemStack decrStackSize(int slot, int stackSize) {
		if(this.stackResult[slot] != null) {
			ItemStack var3 = this.stackResult[slot];
			this.stackResult[slot] = null;
			return var3;
		} else {
			return null;
		}
	}

	public void setInventorySlotContents(int var1, ItemStack var2) {
		this.stackResult[var1] = var2;
	}

	public int getInventoryStackLimit() {
		return 64;
	}

	public void onInventoryChanged() {
	}
}
