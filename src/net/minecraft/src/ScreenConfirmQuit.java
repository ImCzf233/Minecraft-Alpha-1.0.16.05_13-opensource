package net.minecraft.src;

public class ScreenConfirmQuit extends GuiScreen {
	public void initGui() {
		this.controlList.clear();
		this.controlList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 62, "Save and Quit"));
		this.controlList.add(new GuiButton(2, this.width / 2 - 100, this.height / 4 + 96, "Go back"));
	}

	protected void keyTyped(char var1, int var2) {
	}

	protected void actionPerformed(GuiButton var1) {
		if(var1.id == 0) {
		}

		if(var1.id == 1) {
			if(this.mc.isMultiplayerWorld()) {
				this.mc.theWorld.sendQuittingDisconnectingPacket();
			}

			this.mc.changeWorld1((World)null);
			this.mc.displayGuiScreen(new GuiMainMenu());
		}

		if(var1.id == 2) {
			this.mc.displayGuiScreen((GuiScreen)null);
			this.mc.setIngameFocus();
		}

	}

	public void drawScreen(int var1, int var2, float var3) {
		this.drawGradientRect(0, 0, this.width, this.height, -13619152, -14671840);
		this.drawCenteredString(this.fontRenderer, "Quitting the game will end the current boss fight. Are you sure?", this.width / 2, 30, 0xFFFFFF);
		super.drawScreen(var1, var2, var3);
	}

	public boolean doesGuiPauseGame() {
		return true;
	}
}
