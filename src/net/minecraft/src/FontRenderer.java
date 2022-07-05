package net.minecraft.src;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.IntBuffer;
import javax.imageio.ImageIO;
import org.lwjgl.opengl.GL11;

public class FontRenderer {
	private int[] charWidth = new int[256];
	public int fontTextureName = 0;
	private int fontDisplayLists;
	private IntBuffer buffer = GLAllocation.createDirectIntBuffer(1024);

	public FontRenderer(GameSettings var1, String var2, RenderEngine var3) {
		BufferedImage var4;
		try {
			var4 = ImageIO.read(RenderEngine.class.getResourceAsStream(var2));
		} catch (IOException var18) {
			throw new RuntimeException(var18);
		}

		int var5 = var4.getWidth();
		int var6 = var4.getHeight();
		int[] var7 = new int[var5 * var6];
		var4.getRGB(0, 0, var5, var6, var7, 0, var5);
		int var8 = 0;

		int var9;
		int var10;
		int var11;
		int var12;
		while(var8 < 256) {
			var9 = var8 % 16;
			var10 = var8 / 16;
			var11 = 7;

			while(true) {
				if(var11 >= 0) {
					var12 = var9 * 8 + var11;
					boolean var13 = true;

					for(int var14 = 0; var14 < 8 && var13; ++var14) {
						if((var7[var12 + (var10 * 8 + var14) * var5] & 255) > 0) {
							var13 = false;
						}
					}

					if(var13) {
						--var11;
						continue;
					}
				}

				if(var8 == 32) {
					var11 = 2;
				}

				this.charWidth[var8] = var11 + 2;
				++var8;
				break;
			}
		}

		this.fontTextureName = var3.allocateAndSetupTexture(var4);
		this.fontDisplayLists = GLAllocation.generateDisplayLists(288);
		Tessellator var19 = Tessellator.instance;

		for(var9 = 0; var9 < 256; ++var9) {
			GL11.glNewList(this.fontDisplayLists + var9, GL11.GL_COMPILE);
			var19.startDrawingQuads();
			var10 = var9 % 16 * 8;
			var11 = var9 / 16 * 8;
			var19.addVertexWithUV(0.0D, 7.989999771118164D, 0.0D, (double)((float)var10 / 128.0F + 0.0F), (double)(((float)var11 + 7.99F) / 128.0F + 0.0F));
			var19.addVertexWithUV(7.989999771118164D, 7.989999771118164D, 0.0D, (double)(((float)var10 + 7.99F) / 128.0F + 0.0F), (double)(((float)var11 + 7.99F) / 128.0F + 0.0F));
			var19.addVertexWithUV(7.989999771118164D, 0.0D, 0.0D, (double)(((float)var10 + 7.99F) / 128.0F + 0.0F), (double)((float)var11 / 128.0F + 0.0F));
			var19.addVertexWithUV(0.0D, 0.0D, 0.0D, (double)((float)var10 / 128.0F + 0.0F), (double)((float)var11 / 128.0F + 0.0F));
			var19.draw();
			GL11.glTranslatef((float)this.charWidth[var9], 0.0F, 0.0F);
			GL11.glEndList();
		}

		for(var9 = 0; var9 < 32; ++var9) {
			var10 = (var9 >> 3 & 1) * 85;
			var11 = (var9 >> 2 & 1) * 170 + var10;
			var12 = (var9 >> 1 & 1) * 170 + var10;
			int var20 = (var9 >> 0 & 1) * 170 + var10;
			if(var9 == 6) {
				var11 += 85;
			}

			boolean var21 = var9 >= 16;
			if(var1.anaglyph) {
				int var15 = (var11 * 30 + var12 * 59 + var20 * 11) / 100;
				int var16 = (var11 * 30 + var12 * 70) / 100;
				int var17 = (var11 * 30 + var20 * 70) / 100;
				var11 = var15;
				var12 = var16;
				var20 = var17;
			}

			if(var21) {
				var11 /= 4;
				var12 /= 4;
				var20 /= 4;
			}

			GL11.glNewList(this.fontDisplayLists + 256 + var9, GL11.GL_COMPILE);
			GL11.glColor3f((float)var11 / 255.0F, (float)var12 / 255.0F, (float)var20 / 255.0F);
			GL11.glEndList();
		}

	}

	public void drawStringWithShadow(String var1, int var2, int var3, int var4) {
		this.renderString(var1, var2 + 1, var3 + 1, var4, true);
		this.drawString(var1, var2, var3, var4);
	}

	public void drawString(String var1, int var2, int var3, int var4) {
		this.renderString(var1, var2, var3, var4, false);
	}

	public void renderString(String var1, int var2, int var3, int var4, boolean var5) {
		if(var1 != null) {
			if(var5) {
				int var6 = var4 >> 24 & 255;
				int var7 = var4 & 0xFF000000;
				var4 = (var4 & 16579836) >> 2;
				var4 += var7;
				if(var6 != 255 && var6 != 0) {
					var4 |= var6 / 2 << 24;
				} else {
					var4 |= 0xFF000000;
				}
			}

			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.fontTextureName);
			float var12 = (float)(var4 >> 16 & 255) / 255.0F;
			float var13 = (float)(var4 >> 8 & 255) / 255.0F;
			float var8 = (float)(var4 & 255) / 255.0F;
			float var9 = (float)(var4 >> 24 & 255) / 255.0F;
			if(var9 == 0.0F) {
				var9 = 1.0F;
			}

			GL11.glColor4f(var12, var13, var8, var9);
			this.buffer.clear();
			GL11.glPushMatrix();
			GL11.glTranslatef((float)var2, (float)var3, 0.0F);

			for(int var10 = 0; var10 < var1.length(); ++var10) {
				int var11;
				for(; var1.charAt(var10) == 167 && var1.length() > var10 + 1; var10 += 2) {
					var11 = "0123456789abcdef".indexOf(var1.toLowerCase().charAt(var10 + 1));
					if(var11 < 0 || var11 > 15) {
						var11 = 15;
					}

					this.buffer.put(this.fontDisplayLists + 256 + var11 + (var5 ? 16 : 0));
					if(this.buffer.remaining() == 0) {
						this.buffer.flip();
						GL11.glCallLists(this.buffer);
						this.buffer.clear();
					}
				}

				var11 = " !\"#$%&\'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_\'abcdefghijklmnopqrstuvwxyz{|}~\u2302\u00c7\u00fc\u00e9\u00e2\u00e4\u00e0\u00e5\u00e7\u00ea\u00eb\u00e8\u00ef\u00ee\u00ec\u00c4\u00c5\u00c9\u00e6\u00c6\u00f4\u00f6\u00f2\u00fb\u00f9\u00ff\u00d6\u00dc\u00f8?\u00d8\u00d7\u0192\u00e1\u00ed\u00f3\u00fa\u00f1\u00d1???\u00ae\u00ac???\u00ab\u00bb".indexOf(var1.charAt(var10));
				if(var11 >= 0) {
					this.buffer.put(this.fontDisplayLists + var11 + 32);
				}

				if(this.buffer.remaining() == 0) {
					this.buffer.flip();
					GL11.glCallLists(this.buffer);
					this.buffer.clear();
				}
			}

			this.buffer.flip();
			GL11.glCallLists(this.buffer);
			GL11.glPopMatrix();
			GL11.glDisable(GL11.GL_BLEND);
		}
	}

	public int getStringWidth(String var1) {
		if(var1 == null) {
			return 0;
		} else {
			int var2 = 0;

			for(int var3 = 0; var3 < var1.length(); ++var3) {
				if(var1.charAt(var3) == 167) {
					++var3;
				} else {
					int var4 = " !\"#$%&\'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_\'abcdefghijklmnopqrstuvwxyz{|}~\u2302\u00c7\u00fc\u00e9\u00e2\u00e4\u00e0\u00e5\u00e7\u00ea\u00eb\u00e8\u00ef\u00ee\u00ec\u00c4\u00c5\u00c9\u00e6\u00c6\u00f4\u00f6\u00f2\u00fb\u00f9\u00ff\u00d6\u00dc\u00f8?\u00d8\u00d7\u0192\u00e1\u00ed\u00f3\u00fa\u00f1\u00d1???\u00ae\u00ac???\u00ab\u00bb".indexOf(var1.charAt(var3));
					if(var4 >= 0) {
						var2 += this.charWidth[var4 + 32];
					}
				}
			}

			return var2;
		}
	}
}
