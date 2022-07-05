package net.minecraft.src;

import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

public class BlockButton extends GuiButton {
	public int bID;
	public GuiScreen caller;

	public BlockButton(int var1, int var2, int var3, String var4, int var5, GuiScreen var6) {
		super(var1, var2, var3, 25, 20, var4);
		this.bID = var5;
		this.caller = var6;
	}

	public void drawButton(Minecraft minecraft, int mouseX, int mouseY) {
		if(this.visible) {
			FontRenderer var4 = minecraft.fontRenderer;
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, minecraft.renderEngine.getTexture("/gui/GUICREA.png"));
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			byte var5 = 1;
			boolean var6 = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
			if(!this.enabled) {
				var5 = 0;
			} else if(var6) {
				var5 = 2;
			}

			this.drawTexturedModalRect(this.xPosition, this.yPosition, 0, 46 + var5 * 20, this.width / 2, this.height);
			this.drawTexturedModalRect(this.xPosition + this.width / 2, this.yPosition, 200 - this.width / 2, 46 + var5 * 20, this.width / 2, this.height);
			if(!this.enabled) {
				this.drawCenteredString(var4, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, -6250336);
			} else if(var6) {
				this.drawCenteredString(var4, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, 16777120);
			} else {
				this.drawCenteredString(var4, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, 14737632);
			}

		}
	}
}
