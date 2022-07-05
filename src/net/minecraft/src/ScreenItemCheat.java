package net.minecraft.src;

import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

public class ScreenItemCheat extends GuiScreen {
	public Minecraft b;
	private RenderBlocks blockRendererBlurryTroll = new RenderBlocks();
	private boolean createButtons = true;
	private int lastCols = -1;
	private int lastRows = -1;
	private int lastNOfPages = -1;
	private long rotateTimer = -1L;
	private boolean selectingBlocks = true;
	private boolean resetButtons = false;
	private int currentPage = 0;

	public ScreenItemCheat(Minecraft var1) {
		this.b = var1;
	}

	public void DottyIfYouDontKnowWhatsGoingOnHere_NeitherDoI(int var1, int var2, int var3, int var4, int var5, int var6) {
		Tessellator var10 = Tessellator.instance;
		var10.startDrawingQuads();
		var10.addVertexWithUV((double)(var1 + 0), (double)(var2 + var6), 0.0D, (double)((float)(var3 + 0) * 0.00390625F), (double)((float)(var4 + var6) * 0.00390625F));
		var10.addVertexWithUV((double)(var1 + var5), (double)(var2 + var6), 0.0D, (double)((float)(var3 + var5) * 0.00390625F), (double)((float)(var4 + var6) * 0.00390625F));
		var10.addVertexWithUV((double)(var1 + var5), (double)(var2 + 0), 0.0D, (double)((float)(var3 + var5) * 0.00390625F), (double)((float)(var4 + 0) * 0.00390625F));
		var10.addVertexWithUV((double)(var1 + 0), (double)(var2 + 0), 0.0D, (double)((float)(var3 + 0) * 0.00390625F), (double)((float)(var4 + 0) * 0.00390625F));
		var10.setNormal(0.0F, 1.0F, 0.0F);
		var10.draw();
	}

	public void RenderTheFunny(Block var1) {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.b.renderEngine.getTexture("/terrain.png"));
		int var2 = var1.blockIndexInTexture;
		this.DottyIfYouDontKnowWhatsGoingOnHere_NeitherDoI(0, 0, var2 % 16 * 16, var2 / 16 * 16, 16, 16);
	}

	public void RenderTheItem(Item var1) {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.b.renderEngine.getTexture("/gui/items.png"));
		int var2 = var1.iconIndex;
		this.DottyIfYouDontKnowWhatsGoingOnHere_NeitherDoI(0, 0, var2 % 16 * 16, var2 / 16 * 16, 16, 16);
	}

	public int nthExistingBlock(int var1) {
		int var2 = 0;

		for(int var3 = 1; var3 != 256; ++var3) {
			if(Block.blocksList[var3] != null && var3 != 120) {
				++var2;
			}

			if(var2 == var1 + 1) {
				return var3;
			}
		}

		return -1;
	}

	public int nOfExistingBlocks() {
		int var1 = 0;

		for(int var2 = 1; var2 != 256; ++var2) {
			if(Block.blocksList[var2] != null && var2 != 120) {
				++var1;
			}
		}

		return var1;
	}

	public int nthExistingItem(int var1) {
		int var2 = 0;

		for(int var3 = 256; var3 != Item.itemsList.length; ++var3) {
			if(Item.itemsList[var3] != null) {
				++var2;
			}

			if(var2 == var1 + 1) {
				return var3;
			}
		}

		return -1;
	}

	public int nOfExistingItems() {
		int var1 = 0;

		for(int var2 = 256; var2 != Item.itemsList.length; ++var2) {
			if(Item.itemsList[var2] != null) {
				++var1;
			}
		}

		return var1;
	}

	public boolean doesGuiPauseGame() {
		return false;
	}

	protected void actionPerformed(GuiButton var1) {
		if(var1.enabled) {
			if(var1.id == 2) {
				this.resetButtons = true;
				this.selectingBlocks = true;
			} else if(var1.id == 3) {
				this.resetButtons = true;
				this.selectingBlocks = false;
			} else if(var1.id == 4) {
				if(this.currentPage == 0) {
					this.currentPage = this.lastNOfPages - 1;
				} else {
					--this.currentPage;
				}

				this.resetButtons = true;
			} else if(var1.id == 5) {
				++this.currentPage;
				this.currentPage %= this.lastNOfPages;
				this.resetButtons = true;
			}

			if(var1.id >= 4096) {
				this.b.thePlayer.dropPlayerItemWithRandomChoice(new ItemStack(Item.itemsList[var1.id - 4096], 1), true);
			} else if(var1.id >= 2048) {
				this.b.thePlayer.dropPlayerItemWithRandomChoice(new ItemStack(Block.blocksList[var1.id - 2048], 64), true);
			}

		}
	}

	public void drawScreen(int var1, int var2, float var3) {
		this.drawDefaultBackground();
		if(this.rotateTimer == -1L) {
			this.rotateTimer = System.currentTimeMillis();
		}

		float var4 = Math.min((float)(System.currentTimeMillis() - this.rotateTimer) / 100.0F, 1.0F);
		this.drawGradientRect(0, (int)((float)this.height * (1.0F - var4)), this.width, this.height, 1614823488, 1612718112);
		this.drawCenteredString(this.fontRenderer, "Palette", this.width / 2, 15, 0xFFFFFF);
		this.drawCenteredString(this.fontRenderer, "" + this.currentPage, 208, 35, 0xFFFFFF);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.b.renderEngine.getTexture("/terrain.png"));
		int var5 = (this.width - 18) / 32;
		int var6 = (this.height - 50) / 23;
		if(var5 != 0 && var6 != 0) {
			int var7 = (this.selectingBlocks ? this.nOfExistingBlocks() : this.nOfExistingItems()) / (var5 * var6) + 1;
			if(this.lastCols != var5 || this.lastRows != var6 || this.resetButtons) {
				this.lastNOfPages = var7;
				this.lastCols = var5;

				for(this.lastRows = var6; var7 <= this.currentPage; --this.currentPage) {
				}

				this.controlList.clear();
				this.createButtons = true;
				this.resetButtons = false;
			}

			if(this.createButtons) {
				this.controlList.add(new SelButton(2, 18, 30, "Blocks"));
				this.controlList.add(new SelButton(3, 68, 30, "Items"));
				this.controlList.add(new SelButton(4, 148, 30, "<<"));
				this.controlList.add(new SelButton(5, 218, 30, ">>"));
				((GuiButton)this.controlList.get(this.selectingBlocks ? 0 : 1)).enabled = false;
				((GuiButton)this.controlList.get(2)).enabled = ((GuiButton)this.controlList.get(3)).enabled = var7 != 1;
			}

			int var8 = this.currentPage * var5 * var6;

			for(int var9 = 0; var9 != var5 * var6; ++var9) {
				int var10;
				float var13;
				if(this.selectingBlocks) {
					var10 = this.nthExistingBlock(var8 + var9);
					if(var10 == -1) {
						break;
					}

					Block var11 = Block.blocksList[var10];
					boolean var12 = RenderBlocks.renderItemIn3d(var11.getRenderType()) || var11.getRenderType() == 1 || var11.getRenderType() == 2;
					var13 = 18.0F + 32.0F * (float)(var9 % var5);
					float var14 = 50.0F + 23.0F * (float)(var9 / var5);
					if(this.createButtons) {
						this.controlList.add(new BlockButton(2048 + var10, (int)var13, (int)var14, "", var10, this));
					}

					GL11.glPushMatrix();
					if(var12) {
						GL11.glTranslatef(var13 + 14.0F, var14 + 8.0F, 16.0F);
						GL11.glScalef(16.0F, 16.0F, 16.0F);
						GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
						GL11.glRotatef(30.0F, -1.0F, -1.0F, 0.0F);
						float var15 = (float)(System.currentTimeMillis() - this.rotateTimer) / 13000.0F * 360.0F;
						GL11.glRotatef(var15, 0.0F, -1.0F, 0.0F);
						this.blockRendererBlurryTroll.renderBlockOnInventory(var11);
					} else {
						GL11.glTranslatef(var13 + 5.0F, var14, 13.0F);
						this.RenderTheFunny(var11);
					}

					GL11.glPopMatrix();
				} else {
					var10 = this.nthExistingItem(var8 + var9);
					if(var10 == -1) {
						break;
					}

					Item var16 = Item.itemsList[var10];
					float var17 = 18.0F + 32.0F * (float)(var9 % var5);
					var13 = 50.0F + 23.0F * (float)(var9 / var5);
					if(this.createButtons) {
						this.controlList.add(new BlockButton(4096 + var10, (int)var17, (int)var13, "", var10, this));
					}

					GL11.glPushMatrix();
					GL11.glTranslatef(var17 + 5.0F, var13, 13.0F);
					this.RenderTheItem(var16);
					GL11.glPopMatrix();
				}
			}

			this.createButtons = false;
			super.drawScreen(var1, var2, var3);
		}
	}
}
