package net.minecraft.src;

public class Slot {
	public final int slotIndex;
	public final IInventory inventory;

	public Slot(IInventory inventory, int slotIndex) {
		this.inventory = inventory;
		this.slotIndex = slotIndex;
	}

	public void onPickupFromSlot() {
		this.onSlotChanged();
	}

	public boolean isItemValid(ItemStack itemStack) {
		return true;
	}

	public ItemStack getStack() {
		return this.inventory.getStackInSlot(this.slotIndex);
	}

	public void putStack(ItemStack itemStack) {
		this.inventory.setInventorySlotContents(this.slotIndex, itemStack);
		this.onSlotChanged();
	}

	public int getBackgroundIconIndex() {
		return -1;
	}

	public void onSlotChanged() {
		this.inventory.onInventoryChanged();
	}
}
