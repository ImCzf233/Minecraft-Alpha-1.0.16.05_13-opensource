package net.minecraft.src;

public class SlotCrafting extends SlotInventory {
	private final IInventory craftMatrix;

	public SlotCrafting(GuiContainer guiContainer, IInventory inventory, IInventory resultInventory, int slotIndex, int x, int y) {
		super(guiContainer, resultInventory, slotIndex, x, y);
		this.craftMatrix = inventory;
	}

	public boolean isItemValid(ItemStack itemStack) {
		return false;
	}

	public void onPickupFromSlot() {
		for(int var1 = 0; var1 < this.craftMatrix.getSizeInventory(); ++var1) {
			if(this.craftMatrix.getStackInSlot(var1) != null) {
				this.craftMatrix.decrStackSize(var1, 1);
			}
		}

	}
}
