package net.minecraft.src;

public class GuiYesNo extends GuiScreen {
	private GuiScreen parentScreen;
	private String message1;
	private String message2;
	private int worldNumber;

	public GuiYesNo(GuiScreen var1, String var2, String var3, int var4) {
		this.parentScreen = var1;
		this.message1 = var2;
		this.message2 = var3;
		this.worldNumber = var4;
	}

	public void initGui() {
		this.controlList.add(new GuiSmallButton(0, this.width / 2 - 155 + 0, this.height / 6 + 96, "Yes"));
		this.controlList.add(new GuiSmallButton(1, this.width / 2 - 155 + 160, this.height / 6 + 96, "No"));
	}

	protected void actionPerformed(GuiButton var1) {
		this.parentScreen.deleteWorld(var1.id == 0, this.worldNumber);
	}

	public void drawScreen(int var1, int var2, float var3) {
		this.drawDefaultBackground();
		this.drawCenteredString(this.fontRenderer, this.message1, this.width / 2, 70, 0xFFFFFF);
		this.drawCenteredString(this.fontRenderer, this.message2, this.width / 2, 90, 0xFFFFFF);
		super.drawScreen(var1, var2, var3);
	}
}
