package net.minecraft.src;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

public class GuiIngame extends Gui {
	private static RenderItem itemRenderer = new RenderItem();
	private List chatMessageList = new ArrayList();
	private Random rand = new Random();
	private Minecraft mc;
	public String testMessage = null;
	private int updateCounter = 0;
	private String recordPlaying = "";
	private int recordPlayingUpFor = 0;
	public float damageGuiPartialTime;
	float prevVignetteBrightness = 1.0F;
	public boolean renderQAName = true;
	public static String uqKey = "";
	private boolean initedArea = false;
	private long lastSeed;
	private int lgroupX;
	private int lgroupY;
	public String currentArea = "";
	public long areaTimer = 0L;
	private static final String[] syllab = new String[]{"SIE", "LOH", "KII", "HUR", "MIS", "RUU", "VY", "KA", "TAV", "OLE", "PAH", "MUI", "MAT", "JA", "SAU", "NIN", "UD", "MU", "NGI", "BAR", "LUG", "MAH", "GIR", "AK", "USU", "ESE", "IRU", "UUN", "AMTU", "AGAS", "HI", "TOOI", "YORU", "NEN", "PON", "ONNA", "TSU", "YA", "AO", "ONI", "AN", "KO", "SHI", "YUME", "YARI", "TEST"};

	public void RenderHungerBar() {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTexture("/gui/icons.png"));
		this.drawTexturedModalRect(0, 0, 0, 27, 32, 16);
		int var1 = this.mc.thePlayer.hunger < 300 ? 0 : (this.mc.thePlayer.hunger < 600 ? 1 : (this.mc.thePlayer.hunger < 900 ? 2 : 3));
		var1 = 3 - var1;
		this.drawTexturedModalRect(0, 0, var1 * 32, 43, 32, 16);
	}

	public void renderSomethingIdk(int var1, int var2, int var3, int var4, int var5, int var6) {
		Tessellator var10 = Tessellator.instance;
		var10.startDrawingQuads();
		var10.addVertexWithUV((double)(var1 + 0), (double)(var2 + var6), 0.0D, (double)((float)(var3 + 0) * 0.00390625F), (double)((float)(var4 + var6) * 0.00390625F));
		var10.addVertexWithUV((double)(var1 + var5), (double)(var2 + var6), 0.0D, (double)((float)(var3 + var5) * 0.00390625F), (double)((float)(var4 + var6) * 0.00390625F));
		var10.addVertexWithUV((double)(var1 + var5), (double)(var2 + 0), 0.0D, (double)((float)(var3 + var5) * 0.00390625F), (double)((float)(var4 + 0) * 0.00390625F));
		var10.addVertexWithUV((double)(var1 + 0), (double)(var2 + 0), 0.0D, (double)((float)(var3 + 0) * 0.00390625F), (double)((float)(var4 + 0) * 0.00390625F));
		var10.setNormal(0.0F, 1.0F, 0.0F);
		var10.draw();
	}

	public static String Namegen2(long var0, int var2, int var3) {
		long var4 = (long)(var2 + 392214);
		long var6 = (long)(var3 + 392214);
		long var8 = var6 * 784428L + var4;
		long var10000 = var0 + var8;
		int var12 = (int)Math.sqrt((double)(var2 * var2 + var3 * var3));
		int var13 = 0;
		int var14 = 3;

		for(int var15 = var12; var15 / var14 > 0; var14 *= 4) {
			++var13;
		}

		String var17 = "";
		if(var13 > 0) {
			for(int var16 = 0; var16 != var13; ++var16) {
				var17 = var17 + syllab[(int)(((long)(var12 + var13 + var14) + var8 * (long)(2 + var16)) % 45L)];
			}

			var17 = var17 + "-";
		}

		var17 = var17 + syllab[(int)((var8 * 2L + (long)var12) % 45L)];
		var17 = var17 + syllab[(int)((var8 * 3L + (long)var12) % 45L)];
		var17 = var17 + syllab[(int)((var8 * 4L + (long)var12) % 45L)];
		return var17;
	}

	public void ResetNamegen() {
		this.lgroupX = (int)(this.mc.thePlayer.posX / 32.0D);
		this.lgroupY = (int)(this.mc.thePlayer.posZ / 32.0D);
		this.lastSeed = this.mc.theWorld.randomSeed;
		this.initedArea = true;
		this.currentArea = Namegen2(this.lastSeed, this.lgroupX, this.lgroupY);
		System.out.println("Name at " + this.mc.thePlayer.posX + " " + this.mc.thePlayer.posZ + ": " + this.currentArea);
		this.areaTimer = System.currentTimeMillis();
	}

	public boolean NamegenNeedsReset() {
		int var1 = (int)(this.mc.thePlayer.posX / 32.0D);
		int var2 = (int)(this.mc.thePlayer.posZ / 32.0D);
		return !this.initedArea || this.lastSeed != this.mc.theWorld.randomSeed || var1 != this.lgroupX || var2 != this.lgroupY;
	}

	public GuiIngame(Minecraft minecraft) {
		this.mc = minecraft;
	}

	public void renderGameOverlay(float renderPartialTick, boolean hasScreen, int width, int height) {
		ScaledResolution var5 = new ScaledResolution(this.mc.displayWidth, this.mc.displayHeight);
		int var6 = var5.getScaledWidth();
		int var7 = var5.getScaledHeight();
		FontRenderer var8 = this.mc.fontRenderer;
		this.mc.entityRenderer.setupOverlayRendering();
		GL11.glEnable(GL11.GL_BLEND);
		if(this.mc.options.fancyGraphics) {
			this.renderVignette(this.mc.thePlayer.getBrightness(renderPartialTick), var6, var7);
		}

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTexture("/gui/gui.png"));
		InventoryPlayer var9 = this.mc.thePlayer.inventory;
		this.zLevel = -90.0F;
		this.drawTexturedModalRect(var6 / 2 - 91, var7 - 22, 0, 0, 182, 22);
		this.drawTexturedModalRect(var6 / 2 - 91 - 1 + var9.currentItem * 20, var7 - 22 - 1, 0, 22, 24, 22);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTexture("/gui/icons.png"));
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_ONE_MINUS_DST_COLOR, GL11.GL_ONE_MINUS_SRC_COLOR);
		this.drawTexturedModalRect(var6 / 2 - 7, var7 / 2 - 7, 0, 0, 16, 16);
		GL11.glDisable(GL11.GL_BLEND);
		boolean var10 = this.mc.thePlayer.heartsLife / 3 % 2 == 1;
		if(this.mc.thePlayer.heartsLife < 10) {
			var10 = false;
		}

		int var11 = this.mc.thePlayer.health;
		int var12 = this.mc.thePlayer.prevHealth;
		this.rand.setSeed((long)(this.updateCounter * 312871));
		int var13;
		int var14;
		int var16;
		int var20;
		int var30;
		if(this.mc.playerController.shouldDrawHUD()) {
			var13 = this.mc.thePlayer.getPlayerArmorValue();
			var14 = var6 / 2 + 19;
			int var15 = var7 - (this.mc.options.difficulty == 4 ? 32 : 41);

			int var19;
			for(var16 = 0; var16 != 4; ++var16) {
				ItemStack var17 = this.mc.thePlayer.inventory.armorItemInSlot(var16);
				if(var17 != null && var17.getItem() instanceof ItemArmor) {
					Item var18 = var17.getItem();
					var19 = var17.getMaxDamage();
					var20 = var19 - var17.itemDmg;
					float var21 = (float)var20 / (float)var19;
					this.drawTexturedModalRect(var14 + 18 * var16, var15, 52, 9, 9, 9);
					this.drawTexturedModalRect(var14 + 9 + 18 * var16, var15, 52, 9, 9, 9);
					if(var21 >= 0.75F) {
						this.drawTexturedModalRect(var14 + 18 * var16, var15, 88, 9, 9, 9);
						this.drawTexturedModalRect(var14 + 9 + 18 * var16, var15, 88, 9, 9, 9);
					} else if(var21 >= 0.5F) {
						this.drawTexturedModalRect(var14 + 18 * var16, var15, 97, 9, 9, 9);
						this.drawTexturedModalRect(var14 + 9 + 18 * var16, var15, 88, 9, 9, 9);
					} else if(var21 >= 0.25F) {
						this.drawTexturedModalRect(var14 + 9 + 18 * var16, var15, 88, 9, 9, 9);
					} else {
						this.drawTexturedModalRect(var14 + 9 + 18 * var16, var15, 97, 9, 9, 9);
					}
				}
			}

			if(this.mc.thePlayer.dashTimer != 0) {
				this.drawGradientRect(var6 / 2 - 50 - 1, var7 - 90 - 1, var6 / 2 - 50 + 100 + 1, var7 - 90 + 5 + 1, -14671840, 0xFF000000);
				this.drawGradientRect(var6 / 2 - 50, var7 - 90, var6 / 2 - 50 + (int)(100.0F * (1.0F - (float)this.mc.thePlayer.dashTimer / 30.0F)), var7 - 90 + 5, -3584, -13893888);
			}

			if(this.mc.theWorld.bossfightInProgress) {
				this.drawGradientRect(var6 / 2 - 150 - 1, 59, var6 / 2 - 150 + 301, 66, -14671840, 0xFF000000);
				this.drawGradientRect(var6 / 2 - 150, 60, var6 / 2 - 150 + (int)(300.0F * ((float)this.mc.theWorld.bossRef.health / (float)this.mc.theWorld.bossRef.maxHP)), 65, -3070185, -786683);
				var8.drawStringWithShadow(this.mc.theWorld.bossname, var6 / 2 - 150 - 1, 65, 0xFFFFFF);
				GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTexture("/gui/icons.png"));
			}

			int var31;
			if(this.mc.options.difficulty == 4) {
				if(this.mc.thePlayer.health != 0) {
					var16 = var6 / 2 - 91;
					var30 = var7 - 32;
					this.drawTexturedModalRect(var16, var30, 0, 16, 9, 9);
				}
			} else {
				for(var16 = 0; var16 < 10; ++var16) {
					var30 = var7 - 32;
					if(var13 > 0) {
						var31 = var6 / 2 + 91 - var16 * 8 - 9;
						if(var16 * 2 + 1 < var13) {
							this.drawTexturedModalRect(var31, var30, 34, 9, 9, 9);
						}

						if(var16 * 2 + 1 == var13) {
							this.drawTexturedModalRect(var31, var30, 25, 9, 9, 9);
						}

						if(var16 * 2 + 1 > var13) {
							this.drawTexturedModalRect(var31, var30, 16, 9, 9, 9);
						}
					}

					byte var32 = 0;
					if(var10) {
						var32 = 1;
					}

					var19 = var6 / 2 - 91 + var16 * 8;
					if(var11 <= 4) {
						var30 += this.rand.nextInt(2);
					}

					this.drawTexturedModalRect(var19, var30, 16 + var32 * 9, 0, 9, 9);
					if(var10) {
						if(var16 * 2 + 1 < var12) {
							this.drawTexturedModalRect(var19, var30, 70, 0, 9, 9);
						}

						if(var16 * 2 + 1 == var12) {
							this.drawTexturedModalRect(var19, var30, 79, 0, 9, 9);
						}
					}

					if(var16 * 2 + 1 < var11) {
						this.drawTexturedModalRect(var19, var30, 52, 0, 9, 9);
					}

					if(var16 * 2 + 1 == var11) {
						this.drawTexturedModalRect(var19, var30, 61, 0, 9, 9);
					}
				}
			}

			if(this.mc.thePlayer.isInsideOfMaterial(Material.water)) {
				var16 = (int)Math.ceil((double)(this.mc.thePlayer.air - 2) * 10.0D / 300.0D);
				var30 = (int)Math.ceil((double)this.mc.thePlayer.air * 10.0D / 300.0D) - var16;

				for(var31 = 0; var31 < var16 + var30; ++var31) {
					if(var31 < var16) {
						this.drawTexturedModalRect(var6 / 2 - 91 + var31 * 8, var7 - 32 - 9, 16, 18, 9, 9);
					} else {
						this.drawTexturedModalRect(var6 / 2 - 91 + var31 * 8, var7 - 32 - 9, 25, 18, 9, 9);
					}
				}
			}
		}

		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable('\u803a');
		GL11.glPushMatrix();
		GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
		GLStatics.b();
		GL11.glPopMatrix();

		for(var13 = 0; var13 < 9; ++var13) {
			this.renderInventorySlot(var13, var6 / 2 - 90 + var13 * 20 + 2, var7 - 16 - 3, renderPartialTick);
		}

		GLStatics.a();
		GL11.glDisable('\u803a');
		if(this.mc.options.d) {
			var8.drawStringWithShadow("Minecraft Alpha 1.0.16.05_13 Lilypad", 2, 2, 0xFFFFFF);
			var8.drawStringWithShadow(this.mc.debug, 2, 12, 0xFFFFFF);
			var8.drawStringWithShadow(this.mc.debugInfoRenders(), 2, 22, 0xFFFFFF);
			var8.drawStringWithShadow(this.mc.getEntityDebug(), 2, 32, 0xFFFFFF);
			var8.drawStringWithShadow(this.mc.debugInfoEntities(), 2, 42, 0xFFFFFF);
			if(this.mc.theWorld != null) {
				var8.drawStringWithShadow(Long.toString(this.mc.theWorld.randomSeed), 2, 52, 0xFFFFFF);
			}

			if(uqKey != "") {
				var8.drawStringWithShadow(uqKey, 2, 52, 0xFFFFFF);
			}

			long var25 = Runtime.getRuntime().maxMemory();
			long var27 = Runtime.getRuntime().totalMemory();
			long var33 = var27 - Runtime.getRuntime().freeMemory();
			String var35 = "Used memory: " + var33 * 100L / var25 + "% (" + var33 / 1024L / 1024L + "MB) of " + var25 / 1024L / 1024L + "MB";
			this.drawString(var8, var35, var6 - var8.getStringWidth(var35) - 2, 2, 14737632);
			String var36 = "Allocated memory: " + var27 * 100L / var25 + "% (" + var27 / 1024L / 1024L + "MB)";
			this.drawString(var8, var36, var6 - var8.getStringWidth(var36) - 2, 12, 14737632);
		} else {
			var8.drawStringWithShadow("Minecraft Alpha 1.0.16.05_13 Lilypad", 2, 2, 0xFFFFFF);
			if(uqKey != "") {
				var8.drawStringWithShadow(uqKey, 2, 12, 0xFFFFFF);
			}
		}

		GL11.glPushMatrix();
		if(this.NamegenNeedsReset()) {
			this.ResetNamegen();
		}

		float var26 = (float)(1.0D * Math.pow(0.5D, (double)(this.currentArea.length() / 10)));
		GL11.glScalef(1.0F + var26, 1.0F + var26, 1.0F + var26);
		var14 = (int)(255.0F * Math.max(1.0F - (float)Math.min(System.currentTimeMillis() - this.areaTimer, 5000L) / 5000.0F, 0.3F));
		if(var14 != 0) {
			var8.drawStringWithShadow(this.currentArea, var6 / 2 - 80, 20, 0xFFFFFF + var14 * 16777216);
		}

		GL11.glPopMatrix();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTexture("/gui/icons.png"));
		if(this.recordPlayingUpFor > 0) {
			float var28 = (float)this.recordPlayingUpFor - renderPartialTick;
			var16 = (int)(var28 * 256.0F / 20.0F);
			if(var16 > 255) {
				var16 = 255;
			}

			if(var16 > 0) {
				GL11.glPushMatrix();
				GL11.glTranslatef((float)(var6 / 2), (float)(var7 - 48), 0.0F);
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
				var8.drawString(this.recordPlaying, -var8.getStringWidth(this.recordPlaying) / 2, -4, (Color.HSBtoRGB(var28 / 50.0F, 0.7F, 0.6F) & 0xFFFFFF) + (var16 << 24));
				GL11.glDisable(GL11.GL_BLEND);
				GL11.glPopMatrix();
			}
		}

		byte var29 = 10;
		boolean var34 = false;
		if(this.mc.currentScreen instanceof GuiChat) {
			var29 = 20;
			var34 = true;
		}

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glPushMatrix();
		GL11.glTranslatef(0.0F, (float)(var7 - 48), 0.0F);
		if(GuiScreen.currentID != "") {
			var8.drawStringWithShadow("[Use numpad to enter, - to clear, + to give]", 2, 10, 16449390);
			var8.drawStringWithShadow("Give item: " + GuiScreen.currentID, 2, 20, 16449390);

			try {
				var30 = Integer.parseInt(GuiScreen.currentID);
				if((Block.blocksList.length <= var30 || Block.blocksList[var30] == null) && (Item.itemsList.length <= var30 || Item.itemsList[var30] == null)) {
					var8.drawStringWithShadow("(INVALID)", 2, 30, 16711680);
				}
			} catch (Exception var24) {
				var8.drawStringWithShadow("(ERROR)", 2, 30, 16711680);
			}
		}

		for(var30 = 0; var30 < this.chatMessageList.size() && var30 < var29; ++var30) {
			if(((ChatLine)this.chatMessageList.get(var30)).updateCounter < 200 || var34) {
				double var37 = (1.0D - (double)((ChatLine)this.chatMessageList.get(var30)).updateCounter / 200.0D) * 10.0D;
				if(var37 < 0.0D) {
					var37 = 0.0D;
				}

				if(var37 > 1.0D) {
					var37 = 1.0D;
				}

				var20 = (int)(255.0D * var37 * var37);
				if(var34) {
					var20 = 255;
				}

				if(var20 > 0) {
					int var22 = -var30 * 9;
					String var23 = ((ChatLine)this.chatMessageList.get(var30)).message;
					this.drawRect(2, var22 - 1, 322, var22 + 8, var20 / 2 << 24);
					GL11.glEnable(GL11.GL_BLEND);
					var8.drawStringWithShadow(var23, 2, var22, 0xFFFFFF + (var20 << 24));
				}
			}
		}

		if(this.renderQAName) {
			GL11.glScalef(2.0F, 2.0F, 2.0F);
			var8.drawStringWithShadow("QA BUILD: " + ScreenInputPass.name, 5, 0, 553320302);
		}

		GL11.glPopMatrix();
		if(this.mc.options.difficulty != 4) {
			GL11.glPushMatrix();
			GL11.glTranslatef((float)var6 / 2.0F - 16.0F, (float)var7 - 50.0F, -1.0F);
			GL11.glEnable(GL11.GL_BLEND);
			this.RenderHungerBar();
			GL11.glPopMatrix();
		}

		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glDisable(GL11.GL_BLEND);
	}

	private void renderVignette(float brightness, int width, int height) {
		brightness = 1.0F - brightness;
		if(brightness < 0.0F) {
			brightness = 0.0F;
		}

		if(brightness > 1.0F) {
			brightness = 1.0F;
		}

		this.prevVignetteBrightness += (float)((double)(brightness - this.prevVignetteBrightness) * 0.01D);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		GL11.glBlendFunc(GL11.GL_ZERO, GL11.GL_ONE_MINUS_SRC_COLOR);
		GL11.glColor4f(this.prevVignetteBrightness, this.prevVignetteBrightness, this.prevVignetteBrightness, 1.0F);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTexture("/misc/vignette.png"));
		Tessellator var4 = Tessellator.instance;
		var4.startDrawingQuads();
		var4.addVertexWithUV(0.0D, (double)height, -90.0D, 0.0D, 1.0D);
		var4.addVertexWithUV((double)width, (double)height, -90.0D, 1.0D, 1.0D);
		var4.addVertexWithUV((double)width, 0.0D, -90.0D, 1.0D, 0.0D);
		var4.addVertexWithUV(0.0D, 0.0D, -90.0D, 0.0D, 0.0D);
		var4.draw();
		GL11.glDepthMask(true);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	}

	private void renderInventorySlot(int var1, int var2, int var3, float renderPartialTick) {
		ItemStack var5 = this.mc.thePlayer.inventory.mainInventory[var1];
		if(var5 != null) {
			float var6 = (float)var5.animationsToGo - renderPartialTick;
			if(var6 > 0.0F) {
				GL11.glPushMatrix();
				float var7 = 1.0F + var6 / 5.0F;
				GL11.glTranslatef((float)(var2 + 8), (float)(var3 + 12), 0.0F);
				GL11.glScalef(1.0F / var7, (var7 + 1.0F) / 2.0F, 1.0F);
				GL11.glTranslatef((float)(-(var2 + 8)), (float)(-(var3 + 12)), 0.0F);
			}

			itemRenderer.renderItemIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, var5, var2, var3);
			if(var6 > 0.0F) {
				GL11.glPopMatrix();
			}

			itemRenderer.renderItemOverlayIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, var5, var2, var3);
		}
	}

	public void updateTick() {
		if(this.recordPlayingUpFor > 0) {
			--this.recordPlayingUpFor;
		}

		++this.updateCounter;

		for(int var1 = 0; var1 < this.chatMessageList.size(); ++var1) {
			ChatLine var2 = (ChatLine)this.chatMessageList.get(var1);
			++var2.updateCounter;
		}

	}

	public void addChatMessage(String message) {
		while(this.mc.fontRenderer.getStringWidth(message) > 320) {
			int var2;
			for(var2 = 1; var2 < message.length() && this.mc.fontRenderer.getStringWidth(message.substring(0, var2 + 1)) <= 320; ++var2) {
			}

			this.addChatMessage(message.substring(0, var2));
			message = message.substring(var2);
		}

		this.chatMessageList.add(0, new ChatLine(message));

		while(this.chatMessageList.size() > 50) {
			this.chatMessageList.remove(this.chatMessageList.size() - 1);
		}

	}

	public void setRecordPlayingMessage(String record) {
		this.recordPlaying = "Now playing: " + record;
		this.recordPlayingUpFor = 60;
	}
}
