package net.minecraft.src;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

public class GuiMainMenu extends GuiScreen {
	private static final Random rand = new Random();
	String[] logoBlockLayers = new String[]{" *   * * *   * *** *** *** *** *** ***", " ** ** * **  * *   *   * * * * *    * ", " * * * * * * * **  *   **  *** **   * ", " *   * * *  ** *   *   * * * * *    * ", " *   * * *   * *** *** * * * * *    * "};
	private LogoEffectRandomizer[][] logoEffects;
	private float updateCounter = 0.0F;
	private String splashString = "missingno";
	private String[] splashes = new String[]{"MINECRAFT DEMO", "(Stay tuned for more)"};
	public static boolean shw = false;

	public GuiMainMenu() {
		try {
			this.splashString = this.splashes[rand.nextInt(this.splashes.length)];
		} catch (Exception var2) {
		}

	}

	public void updateScreen() {
		++this.updateCounter;
		if(this.logoEffects != null) {
			for(int var1 = 0; var1 < this.logoEffects.length; ++var1) {
				for(int var2 = 0; var2 < this.logoEffects[var1].length; ++var2) {
					this.logoEffects[var1][var2].updateLogoEffects();
				}
			}
		}

	}

	protected void keyTyped(char var1, int var2) {
	}

	public void initGui() {
		Calendar var1 = Calendar.getInstance();
		var1.setTime(new Date());
		if(var1.get(2) + 1 == 11 && var1.get(5) == 9) {
			this.splashString = "Happy birthday, ez!";
		} else if(var1.get(2) + 1 == 6 && var1.get(5) == 1) {
			this.splashString = "Happy birthday, Notch!";
		} else if(var1.get(2) + 1 == 12 && var1.get(5) == 24) {
			this.splashString = "Merry X-mas!";
		} else if(var1.get(2) + 1 == 1 && var1.get(5) == 1) {
			this.splashString = "Happy new year!";
		}

		this.controlList.clear();
		this.controlList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 48, "Singleplayer"));
		this.controlList.add(new GuiButton(2, this.width / 2 - 100, this.height / 4 + 72, "Multiplayer"));
		this.controlList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 120 + 12, "Options..."));
		if(this.mc.session == null) {
			((GuiButton)this.controlList.get(1)).enabled = false;
		}

	}

	protected void actionPerformed(GuiButton var1) {
		if(var1.id == 0) {
			this.mc.displayGuiScreen(new GuiOptions(this, this.mc.options));
		}

		if(var1.id == 1) {
			this.mc.displayGuiScreen(new GuiSelectWorld(this));
		}

		if(var1.id == 2) {
			this.mc.displayGuiScreen(new GuiMultiplayer(this));
		}

	}

	public void drawScreen(int var1, int var2, float var3) {
		this.drawDefaultBackground();
		Tessellator var4 = Tessellator.instance;
		this.drawLogo(var3);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTexture("/gui/logo.png"));
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		var4.setColorOpaque_I(0xFFFFFF);
		GL11.glPushMatrix();
		GL11.glTranslatef((float)(this.width / 2 + 90), 70.0F, 0.0F);
		GL11.glRotatef(-20.0F, 0.0F, 0.0F, 1.0F);
		float var5 = (1.8F - MathHelper.abs(MathHelper.sin((float)(System.currentTimeMillis() % 1000L) / 1000.0F * (float)Math.PI * 2.0F) * 0.1F)) * 100.0F / (float)(this.fontRenderer.getStringWidth(this.splashString) + 32);
		GL11.glScalef(var5, var5, var5);
		this.drawCenteredString(this.fontRenderer, this.splashString, 0, -8, 16776960);
		GL11.glPopMatrix();
		this.drawString(this.fontRenderer, "Copyright Mojang. Distribution strictly prohibited.", this.width - this.fontRenderer.getStringWidth("Copyright Mojang. Distribution strictly prohibited.") - 2, this.height - 10, 0xFFFFFF);
		long var7 = Runtime.getRuntime().maxMemory();
		long var9 = Runtime.getRuntime().totalMemory();
		String var11 = "Free memory: " + (var7 - Runtime.getRuntime().freeMemory()) * 100L / var7 + "% of " + var7 / 1024L / 1024L + "MB";
		this.drawString(this.fontRenderer, var11, this.width - this.fontRenderer.getStringWidth(var11) - 2, 2, 8421504);
		String var12 = "Allocated memory: " + var9 * 100L / var7 + "% (" + var9 / 1024L / 1024L + "MB)";
		this.drawString(this.fontRenderer, var12, this.width - this.fontRenderer.getStringWidth(var12) - 2, 12, 8421504);
		super.drawScreen(var1, var2, var3);
		if(!shw) {
			System.out.println("Showing screen");
			this.mc.displayGuiScreen(new ScreenInputPass(this.mc));
		} else {
			this.drawString(this.fontRenderer, "QA Build: " + ScreenInputPass.name, 2, 12, 2105376);
		}
	}

	private void drawLogo(float renderPartialTick) {
		int var2;
		int var3;
		if(this.logoEffects == null) {
			this.logoEffects = new LogoEffectRandomizer[this.logoBlockLayers[0].length()][this.logoBlockLayers.length];

			for(var2 = 0; var2 < this.logoEffects.length; ++var2) {
				for(var3 = 0; var3 < this.logoEffects[var2].length; ++var3) {
					this.logoEffects[var2][var3] = new LogoEffectRandomizer(this, var2, var3);
				}
			}
		}

		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glPushMatrix();
		GL11.glLoadIdentity();
		var2 = 120 * (new ScaledResolution(this.mc.displayWidth, this.mc.displayHeight)).scaleFactor;
		GLU.gluPerspective(70.0F, (float)this.mc.displayWidth / (float)var2, 0.05F, 100.0F);
		GL11.glViewport(0, this.mc.displayHeight - var2, this.mc.displayWidth, var2);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glPushMatrix();
		GL11.glLoadIdentity();
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
		GL11.glDepthMask(true);

		for(var3 = 0; var3 < 3; ++var3) {
			GL11.glPushMatrix();
			GL11.glTranslatef(0.4F, 0.6F, -12.0F);
			if(var3 == 0) {
				GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
				GL11.glTranslatef(0.0F, -0.4F, 0.0F);
				GL11.glScalef(0.98F, 1.0F, 1.0F);
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			}

			if(var3 == 1) {
				GL11.glDisable(GL11.GL_BLEND);
				GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
			}

			if(var3 == 2) {
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
			}

			GL11.glScalef(1.0F, -1.0F, 1.0F);
			GL11.glRotatef(15.0F, 1.0F, 0.0F, 0.0F);
			GL11.glScalef(0.89F, 1.0F, 0.4F);
			GL11.glTranslatef((float)(-this.logoBlockLayers[0].length()) * 0.5F, (float)(-this.logoBlockLayers.length) * 0.5F, 0.0F);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTexture("/terrain.png"));
			if(var3 == 0) {
				GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTexture("/title/black.png"));
			}

			RenderBlocks var4 = new RenderBlocks();

			for(int var5 = 0; var5 < this.logoBlockLayers.length; ++var5) {
				for(int var6 = 0; var6 < this.logoBlockLayers[var5].length(); ++var6) {
					if(this.logoBlockLayers[var5].charAt(var6) != 32) {
						GL11.glPushMatrix();
						LogoEffectRandomizer var7 = this.logoEffects[var6][var5];
						float var8 = (float)(var7.prevHeight + (var7.height - var7.prevHeight) * (double)renderPartialTick);
						float var9 = 1.0F;
						float var10 = 1.0F;
						if(var3 == 0) {
							var9 = var8 * 0.04F + 1.0F;
							var10 = 1.0F / var9;
							var8 = 0.0F;
						}

						GL11.glTranslatef((float)var6, (float)var5, var8);
						GL11.glScalef(var9, var9, var9);
						GL11.glRotatef(0.0F, 0.0F, 1.0F, 0.0F);
						var4.renderBlockAsItem(Block.stone, var10);
						GL11.glPopMatrix();
					}
				}
			}

			GL11.glPopMatrix();
		}

		GL11.glDisable(GL11.GL_BLEND);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glPopMatrix();
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glPopMatrix();
		GL11.glViewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
		GL11.glEnable(GL11.GL_CULL_FACE);
	}

	public static Random getRandom() {
		return rand;
	}
}
