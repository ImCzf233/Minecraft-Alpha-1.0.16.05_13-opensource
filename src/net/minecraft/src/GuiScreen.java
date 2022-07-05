package net.minecraft.src;

import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class GuiScreen extends Gui {
	protected Minecraft mc;
	public int width;
	public int height;
	protected List controlList = new ArrayList();
	public boolean allowUserInput = false;
	protected FontRenderer fontRenderer;
	public static String currentID = "";
	private boolean hasInputHandler = false;

	private boolean InputHandlerLoaded() {
		try {
			Class.forName("net.minecraft.src.InputHandler");
			return true;
		} catch (ClassNotFoundException var2) {
			return false;
		}
	}

	public GuiScreen() {
		this.hasInputHandler = this.InputHandlerLoaded();
	}

	public void drawScreen(int mouseX, int mouseY, float renderPartialTick) {
		for(int var4 = 0; var4 < this.controlList.size(); ++var4) {
			((GuiButton)this.controlList.get(var4)).drawButton(this.mc, mouseX, mouseY);
		}

	}

	protected void keyTyped(char character, int key) {
		if(key == 1) {
			this.mc.displayGuiScreen((GuiScreen)null);
			this.mc.setIngameFocus();
		}

	}

	public static String getClipboardString() {
		try {
			Transferable var0 = Toolkit.getDefaultToolkit().getSystemClipboard().getContents((Object)null);
			if(var0 != null && var0.isDataFlavorSupported(DataFlavor.stringFlavor)) {
				return (String)var0.getTransferData(DataFlavor.stringFlavor);
			}
		} catch (Exception var1) {
		}

		return null;
	}

	protected void mouseClicked(int var1, int var2, int var3) {
		if(var3 == 0) {
			for(int var4 = 0; var4 < this.controlList.size(); ++var4) {
				GuiButton var5 = (GuiButton)this.controlList.get(var4);
				if(var5.mousePressed(var1, var2)) {
					this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
					this.actionPerformed(var5);
				}
			}
		}

	}

	protected void mouseMovedOrUp(int var1, int var2, int var3) {
	}

	protected void actionPerformed(GuiButton button) {
	}

	public void setWorldAndResolution(Minecraft var1, int var2, int var3) {
		this.mc = var1;
		if(this.hasInputHandler) {
			InputHandler.mc = var1;
		}

		this.fontRenderer = var1.fontRenderer;
		this.width = var2;
		this.height = var3;
		this.initGui();
	}

	public void initGui() {
	}

	public void handleInput() {
		while(Mouse.next()) {
			this.handleMouseInput();
		}

		if(this.hasInputHandler) {
			while(InputHandler.NextKBEvent()) {
				this.handleKeyboardInput();
			}
		} else {
			while(Keyboard.next()) {
				this.handleKeyboardInput();
			}
		}

	}

	public void handleMouseInput() {
		if(Mouse.getEventButtonState()) {
			this.mouseClicked(Mouse.getEventX() * this.width / this.mc.displayWidth, this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1, Mouse.getEventButton());
		} else {
			this.mouseMovedOrUp(Mouse.getEventX() * this.width / this.mc.displayWidth, this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1, Mouse.getEventButton());
		}

	}

	public void handleKeyboardInput() {
		if(Keyboard.getEventKeyState()) {
			if(this.hasInputHandler && InputHandler.cheatsEnabled) {
				if(Keyboard.getEventKey() == Keyboard.KEY_NUMPAD1) {
					currentID = currentID + '1';
					System.out.println("Current entered ID: " + currentID);
				} else if(Keyboard.getEventKey() == Keyboard.KEY_NUMPAD2) {
					currentID = currentID + '2';
					System.out.println("Current entered ID: " + currentID);
				} else if(Keyboard.getEventKey() == Keyboard.KEY_NUMPAD3) {
					currentID = currentID + '3';
					System.out.println("Current entered ID: " + currentID);
				} else if(Keyboard.getEventKey() == Keyboard.KEY_NUMPAD4) {
					currentID = currentID + '4';
					System.out.println("Current entered ID: " + currentID);
				} else if(Keyboard.getEventKey() == Keyboard.KEY_NUMPAD5) {
					currentID = currentID + '5';
					System.out.println("Current entered ID: " + currentID);
				} else if(Keyboard.getEventKey() == Keyboard.KEY_NUMPAD6) {
					currentID = currentID + '6';
					System.out.println("Current entered ID: " + currentID);
				} else if(Keyboard.getEventKey() == Keyboard.KEY_NUMPAD7) {
					currentID = currentID + '7';
					System.out.println("Current entered ID: " + currentID);
				} else if(Keyboard.getEventKey() == Keyboard.KEY_NUMPAD8) {
					currentID = currentID + '8';
					System.out.println("Current entered ID: " + currentID);
				} else if(Keyboard.getEventKey() == Keyboard.KEY_NUMPAD9) {
					currentID = currentID + '9';
					System.out.println("Current entered ID: " + currentID);
				} else if(Keyboard.getEventKey() == Keyboard.KEY_NUMPAD0) {
					currentID = currentID + '0';
					System.out.println("Current entered ID: " + currentID);
				} else if(Keyboard.getEventKey() == Keyboard.KEY_SUBTRACT) {
					currentID = "";
				} else if(Keyboard.getEventKey() == Keyboard.KEY_ADD) {
					try {
						if(Block.blocksList.length > Integer.parseInt(currentID) && Block.blocksList[Integer.parseInt(currentID)] != null) {
							this.mc.thePlayer.dropPlayerItemWithRandomChoice(new ItemStack(Block.blocksList[Integer.parseInt(currentID)], 64), true);
							System.out.println("Given block to the player");
						} else if(Item.itemsList[Integer.parseInt(currentID)] != null) {
							this.mc.thePlayer.dropPlayerItemWithRandomChoice(new ItemStack(Item.itemsList[Integer.parseInt(currentID)], 1), true);
							System.out.println("Given item to the player");
						} else {
							System.out.println("No block or item with ID " + currentID);
						}
					} catch (Exception var2) {
						var2.printStackTrace();
					}
				}
			}

			if(Keyboard.getEventKey() == Keyboard.KEY_F11) {
				this.mc.toggleFullscreen();
				return;
			}

			this.keyTyped(Keyboard.getEventCharacter(), Keyboard.getEventKey());
		}

	}

	public void updateScreen() {
	}

	public void onGuiClosed() {
	}

	public void drawDefaultBackground() {
		this.drawWorldBackground(0);
	}

	public void drawWorldBackground(int var1) {
		if(this.mc.theWorld != null) {
			this.drawGradientRect(0, 0, this.width, this.height, -1072689136, -804253680);
		} else {
			this.drawBackground(var1);
		}

	}

	public void drawBackground(int var1) {
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_FOG);
		Tessellator var2 = Tessellator.instance;
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTexture("/dirt.png"));
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		var2.startDrawingQuads();
		var2.setColorOpaque_I(4210752);
		var2.addVertexWithUV(0.0D, (double)this.height, 0.0D, 0.0D, (double)((float)this.height / 32.0F + (float)var1));
		var2.addVertexWithUV((double)this.width, (double)this.height, 0.0D, (double)((float)this.width / 32.0F), (double)((float)this.height / 32.0F + (float)var1));
		var2.addVertexWithUV((double)this.width, 0.0D, 0.0D, (double)((float)this.width / 32.0F), (double)(0 + var1));
		var2.addVertexWithUV(0.0D, 0.0D, 0.0D, 0.0D, (double)(0 + var1));
		var2.draw();
	}

	public boolean doesGuiPauseGame() {
		return true;
	}

	public void deleteWorld(boolean var1, int var2) {
	}

	static {
		currentID = "";
	}
}
