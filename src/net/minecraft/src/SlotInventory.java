package net.minecraft.src;

public class SlotInventory extends Slot {
	private final GuiContainer guiContainer;
	public final int xDisplayPosition;
	public final int yDisplayPosition;

	public SlotInventory(GuiContainer guiContainer, IInventory inventory, int slotIndex, int x, int y) {
		super(inventory, slotIndex);
		this.guiContainer = guiContainer;
		this.xDisplayPosition = x;
		this.yDisplayPosition = y;
	}

	public boolean getIsMouseOverSlot(int x, int y) {
		int var3 = (this.guiContainer.width - this.guiContainer.xSize) / 2;
		int var4 = (this.guiContainer.height - this.guiContainer.ySize) / 2;
		x -= var3;
		y -= var4;
		return x >= this.xDisplayPosition - 1 && x < this.xDisplayPosition + 16 + 1 && y >= this.yDisplayPosition - 1 && y < this.yDisplayPosition + 16 + 1;
	}
}
