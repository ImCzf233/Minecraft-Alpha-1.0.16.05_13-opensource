package net.minecraft.src;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import javax.imageio.ImageIO;

public class TerrainTextureManager {
	private float[] texCols = new float[768];
	private int[] pixels = new int[5120];
	private int[] zBuf = new int[5120];
	private int[] waterBuf = new int[5120];
	private int[] waterBr = new int[5120];
	private int[] yBuf = new int[34];
	private int[] textures = new int[768];

	public TerrainTextureManager() {
		try {
			BufferedImage var1 = ImageIO.read(TerrainTextureManager.class.getResource("/terrain.png"));
			int[] var2 = new int[65536];
			var1.getRGB(0, 0, 256, 256, var2, 0, 256);

			for(int var3 = 0; var3 < 256; ++var3) {
				int var4 = 0;
				int var5 = 0;
				int var6 = 0;
				int var7 = var3 % 16 * 16;
				int var8 = var3 / 16 * 16;
				int var9 = 0;

				for(int var10 = 0; var10 < 16; ++var10) {
					for(int var11 = 0; var11 < 16; ++var11) {
						int var12 = var2[var11 + var7 + (var10 + var8) * 256];
						int var13 = var12 >> 24 & 255;
						if(var13 > 128) {
							var4 += var12 >> 16 & 255;
							var5 += var12 >> 8 & 255;
							var6 += var12 & 255;
							++var9;
						}
					}

					if(var9 == 0) {
						++var9;
					}

					this.texCols[var3 * 3 + 0] = (float)(var4 / var9);
					this.texCols[var3 * 3 + 1] = (float)(var5 / var9);
					this.texCols[var3 * 3 + 2] = (float)(var6 / var9);
				}
			}
		} catch (IOException var14) {
			var14.printStackTrace();
		}

		for(int var15 = 0; var15 < 256; ++var15) {
			if(Block.blocksList[var15] != null) {
				this.textures[var15 * 3 + 0] = Block.blocksList[var15].getBlockTextureFromSide(1);
				this.textures[var15 * 3 + 1] = Block.blocksList[var15].getBlockTextureFromSide(2);
				this.textures[var15 * 3 + 2] = Block.blocksList[var15].getBlockTextureFromSide(3);
			}
		}

	}

	public void render(IsoImageBuffer var1) {
		World var2 = var1.level;
		if(var2 == null) {
			var1.noContent = true;
			var1.rendered = true;
		} else {
			int var3 = var1.x * 16;
			int var4 = var1.y * 16;
			int var5 = var3 + 16;
			int var6 = var4 + 16;
			Chunk var7 = var2.getChunkFromChunkCoords(var1.x, var1.y);
			if(var7.isChunkRendered) {
				var1.noContent = true;
				var1.rendered = true;
			} else {
				var1.noContent = false;
				Arrays.fill(this.zBuf, 0);
				Arrays.fill(this.waterBuf, 0);
				Arrays.fill(this.yBuf, 160);

				for(int var8 = var6 - 1; var8 >= var4; --var8) {
					for(int var9 = var5 - 1; var9 >= var3; --var9) {
						int var10 = var9 - var3;
						int var11 = var8 - var4;
						int var12 = var10 + var11;
						boolean var13 = true;

						for(int var14 = 0; var14 < 128; ++var14) {
							int var15 = var11 - var10 - var14 + 160 - 16;
							if(var15 < this.yBuf[var12] || var15 < this.yBuf[var12 + 1]) {
								Block var16 = Block.blocksList[var2.getBlockId(var9, var14, var8)];
								if(var16 == null) {
									var13 = false;
								} else if(var16.material == Material.water) {
									int var17 = var2.getBlockId(var9, var14 + 1, var8);
									if(var17 == 0 || Block.blocksList[var17].material != Material.water) {
										float var18 = (float)var14 / 127.0F * 0.6F + 0.4F;
										float var19 = var2.getBrightness(var9, var14 + 1, var8) * var18;
										if(var15 >= 0 && var15 < 160) {
											int var20 = var12 + var15 * 32;
											if(var12 >= 0 && var12 <= 32 && this.waterBuf[var20] <= var14) {
												this.waterBuf[var20] = var14;
												this.waterBr[var20] = (int)(var19 * 127.0F);
											}

											if(var12 >= -1 && var12 <= 31 && this.waterBuf[var20 + 1] <= var14) {
												this.waterBuf[var20 + 1] = var14;
												this.waterBr[var20 + 1] = (int)(var19 * 127.0F);
											}

											var13 = false;
										}
									}
								} else {
									if(var13) {
										if(var15 < this.yBuf[var12]) {
											this.yBuf[var12] = var15;
										}

										if(var15 < this.yBuf[var12 + 1]) {
											this.yBuf[var12 + 1] = var15;
										}
									}

									float var24 = (float)var14 / 127.0F * 0.6F + 0.4F;
									float var22;
									int var25;
									int var26;
									float var27;
									if(var15 >= 0 && var15 < 160) {
										var25 = var12 + var15 * 32;
										var26 = this.textures[var16.blockID * 3 + 0];
										var27 = (var2.getBrightness(var9, var14 + 1, var8) * 0.8F + 0.2F) * var24;
										if(var12 >= 0 && this.zBuf[var25] <= var14) {
											this.zBuf[var25] = var14;
											this.pixels[var25] = 0xFF000000 | (int)(this.texCols[var26 * 3 + 0] * var27) << 16 | (int)(this.texCols[var26 * 3 + 1] * var27) << 8 | (int)(this.texCols[var26 * 3 + 2] * var27);
										}

										if(var12 < 31) {
											var22 = var27 * 0.9F;
											if(this.zBuf[var25 + 1] <= var14) {
												this.zBuf[var25 + 1] = var14;
												this.pixels[var25 + 1] = 0xFF000000 | (int)(this.texCols[var26 * 3 + 0] * var22) << 16 | (int)(this.texCols[var26 * 3 + 1] * var22) << 8 | (int)(this.texCols[var26 * 3 + 2] * var22);
											}
										}
									}

									if(var15 >= -1 && var15 < 159) {
										var25 = var12 + (var15 + 1) * 32;
										var26 = this.textures[var16.blockID * 3 + 1];
										var27 = var2.getBrightness(var9 - 1, var14, var8) * 0.8F + 0.2F;
										int var21 = this.textures[var16.blockID * 3 + 2];
										var22 = var2.getBrightness(var9, var14, var8 + 1) * 0.8F + 0.2F;
										float var23;
										if(var12 >= 0) {
											var23 = var27 * var24 * 0.6F;
											if(this.zBuf[var25] <= var14 - 1) {
												this.zBuf[var25] = var14 - 1;
												this.pixels[var25] = 0xFF000000 | (int)(this.texCols[var26 * 3 + 0] * var23) << 16 | (int)(this.texCols[var26 * 3 + 1] * var23) << 8 | (int)(this.texCols[var26 * 3 + 2] * var23);
											}
										}

										if(var12 < 31) {
											var23 = var22 * 0.9F * var24 * 0.4F;
											if(this.zBuf[var25 + 1] <= var14 - 1) {
												this.zBuf[var25 + 1] = var14 - 1;
												this.pixels[var25 + 1] = 0xFF000000 | (int)(this.texCols[var21 * 3 + 0] * var23) << 16 | (int)(this.texCols[var21 * 3 + 1] * var23) << 8 | (int)(this.texCols[var21 * 3 + 2] * var23);
											}
										}
									}
								}
							}
						}
					}
				}

				this.postProcess();
				if(var1.image == null) {
					var1.image = new BufferedImage(32, 160, 2);
				}

				var1.image.setRGB(0, 0, 32, 160, this.pixels, 0, 32);
				var1.rendered = true;
			}
		}
	}

	private void postProcess() {
		for(int var1 = 0; var1 < 32; ++var1) {
			for(int var2 = 0; var2 < 160; ++var2) {
				int var3 = var1 + var2 * 32;
				if(this.zBuf[var3] == 0) {
					this.pixels[var3] = 0;
				}

				if(this.waterBuf[var3] > this.zBuf[var3]) {
					int var4 = this.pixels[var3] >> 24 & 255;
					this.pixels[var3] = ((this.pixels[var3] & 16711422) >> 1) + this.waterBr[var3];
					if(var4 < 128) {
						this.pixels[var3] = Integer.MIN_VALUE + this.waterBr[var3] * 2;
					} else {
						this.pixels[var3] |= 0xFF000000;
					}
				}
			}
		}

	}
}
