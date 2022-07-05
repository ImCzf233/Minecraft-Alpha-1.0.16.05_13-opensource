package net.minecraft.src;

import org.lwjgl.opengl.GL11;

public class RenderBlocks {
	private IBlockAccess blockAccess;
	private int overrideBlockTexture = -1;
	private boolean flipTexture = false;
	private boolean renderAllFaces = false;

	public RenderBlocks(IBlockAccess blockAccess) {
		this.blockAccess = blockAccess;
	}

	public RenderBlocks() {
	}

	public void renderBlockUsingTexture(Block block, int var2, int var3, int var4, int var5) {
		this.overrideBlockTexture = var5;
		this.renderBlockByRenderType(block, var2, var3, var4);
		this.overrideBlockTexture = -1;
	}

	public boolean renderBlockByRenderType(Block block, int x, int y, int z) {
		int var5 = block.getRenderType();
		block.setBlockBoundsBasedOnState(this.blockAccess, x, y, z);
		return var5 == 0 ? this.renderStandardBlock(block, x, y, z) : (var5 == 4 ? this.renderBlockFluids(block, x, y, z) : (var5 == 13 ? this.renderBlockCactus(block, x, y, z) : (var5 == 1 ? this.renderBlockReed(block, x, y, z) : (var5 == 6 ? this.renderBlockCrops(block, x, y, z) : (var5 == 2 ? this.renderBlockTorch(block, x, y, z) : (var5 == 3 ? this.renderBlockFire(block, x, y, z) : (var5 == 5 ? this.renderBlockRedstoneWire(block, x, y, z) : (var5 == 8 ? this.renderBlockLadder(block, x, y, z) : (var5 == 7 ? this.renderBlockDoor(block, x, y, z) : (var5 == 9 ? this.renderBlockMinecartTrack(block, x, y, z) : (var5 == 10 ? this.renderBlockStairs(block, x, y, z) : (var5 == 11 ? this.renderBlockFence(block, x, y, z) : (var5 == 12 ? this.renderBlockLever(block, x, y, z) : false)))))))))))));
	}

	public boolean renderBlockTorch(Block block, int x, int y, int z) {
		int var5 = this.blockAccess.getBlockMetadata(x, y, z);
		Tessellator var6 = Tessellator.instance;
		float var7 = block.getBlockBrightness(this.blockAccess, x, y, z);
		if(Block.lightValue[block.blockID] > 0) {
			var7 = 1.0F;
		}

		var6.setColorOpaque_F(var7, var7, var7);
		double var8 = (double)0.4F;
		double var10 = 0.5D - var8;
		double var12 = (double)0.2F;
		if(var5 == 1) {
			this.renderTorchAtAngle(block, (double)x - var10, (double)y + var12, (double)z, -var8, 0.0D);
		} else if(var5 == 2) {
			this.renderTorchAtAngle(block, (double)x + var10, (double)y + var12, (double)z, var8, 0.0D);
		} else if(var5 == 3) {
			this.renderTorchAtAngle(block, (double)x, (double)y + var12, (double)z - var10, 0.0D, -var8);
		} else if(var5 == 4) {
			this.renderTorchAtAngle(block, (double)x, (double)y + var12, (double)z + var10, 0.0D, var8);
		} else {
			this.renderTorchAtAngle(block, (double)x, (double)y, (double)z, 0.0D, 0.0D);
		}

		return true;
	}

	public boolean renderBlockLever(Block block, int x, int y, int z) {
		int var5 = this.blockAccess.getBlockMetadata(x, y, z);
		int var6 = var5 & 7;
		boolean var7 = (var5 & 8) > 0;
		Tessellator var8 = Tessellator.instance;
		boolean var9 = this.overrideBlockTexture >= 0;
		if(!var9) {
			this.overrideBlockTexture = Block.cobblestone.blockIndexInTexture;
		}

		float var10 = 0.25F;
		float var11 = 0.1875F;
		float var12 = 0.1875F;
		if(var6 == 5) {
			block.setBlockBounds(0.5F - var11, 0.0F, 0.5F - var10, 0.5F + var11, var12, 0.5F + var10);
		} else if(var6 == 6) {
			block.setBlockBounds(0.5F - var10, 0.0F, 0.5F - var11, 0.5F + var10, var12, 0.5F + var11);
		} else if(var6 == 4) {
			block.setBlockBounds(0.5F - var11, 0.5F - var10, 1.0F - var12, 0.5F + var11, 0.5F + var10, 1.0F);
		} else if(var6 == 3) {
			block.setBlockBounds(0.5F - var11, 0.5F - var10, 0.0F, 0.5F + var11, 0.5F + var10, var12);
		} else if(var6 == 2) {
			block.setBlockBounds(1.0F - var12, 0.5F - var10, 0.5F - var11, 1.0F, 0.5F + var10, 0.5F + var11);
		} else if(var6 == 1) {
			block.setBlockBounds(0.0F, 0.5F - var10, 0.5F - var11, var12, 0.5F + var10, 0.5F + var11);
		}

		this.renderStandardBlock(block, x, y, z);
		if(!var9) {
			this.overrideBlockTexture = -1;
		}

		float var13 = block.getBlockBrightness(this.blockAccess, x, y, z);
		if(Block.lightValue[block.blockID] > 0) {
			var13 = 1.0F;
		}

		var8.setColorOpaque_F(var13, var13, var13);
		int var14 = block.getBlockTextureFromSide(0);
		if(this.overrideBlockTexture >= 0) {
			var14 = this.overrideBlockTexture;
		}

		int var15 = (var14 & 15) << 4;
		int var16 = var14 & 240;
		float var17 = (float)var15 / 256.0F;
		float var18 = ((float)var15 + 15.99F) / 256.0F;
		float var19 = (float)var16 / 256.0F;
		float var20 = ((float)var16 + 15.99F) / 256.0F;
		Vec3D[] var21 = new Vec3D[8];
		float var22 = 0.0625F;
		float var23 = 0.0625F;
		float var24 = 0.625F;
		var21[0] = Vec3D.createVector((double)(-var22), 0.0D, (double)(-var23));
		var21[1] = Vec3D.createVector((double)var22, 0.0D, (double)(-var23));
		var21[2] = Vec3D.createVector((double)var22, 0.0D, (double)var23);
		var21[3] = Vec3D.createVector((double)(-var22), 0.0D, (double)var23);
		var21[4] = Vec3D.createVector((double)(-var22), (double)var24, (double)(-var23));
		var21[5] = Vec3D.createVector((double)var22, (double)var24, (double)(-var23));
		var21[6] = Vec3D.createVector((double)var22, (double)var24, (double)var23);
		var21[7] = Vec3D.createVector((double)(-var22), (double)var24, (double)var23);

		for(int var25 = 0; var25 < 8; ++var25) {
			if(var7) {
				var21[var25].zCoord -= 0.0625D;
				var21[var25].rotateAroundX((float)Math.PI / 4.5F);
			} else {
				var21[var25].zCoord += 0.0625D;
				var21[var25].rotateAroundX(-0.69813174F);
			}

			if(var6 == 6) {
				var21[var25].rotateAroundY((float)Math.PI / 2F);
			}

			if(var6 < 5) {
				var21[var25].yCoord -= 0.375D;
				var21[var25].rotateAroundX((float)Math.PI / 2F);
				if(var6 == 4) {
					var21[var25].rotateAroundY(0.0F);
				}

				if(var6 == 3) {
					var21[var25].rotateAroundY((float)Math.PI);
				}

				if(var6 == 2) {
					var21[var25].rotateAroundY((float)Math.PI / 2F);
				}

				if(var6 == 1) {
					var21[var25].rotateAroundY(-1.5707964F);
				}

				var21[var25].xCoord += (double)x + 0.5D;
				var21[var25].yCoord += (double)((float)y + 0.5F);
				var21[var25].zCoord += (double)z + 0.5D;
			} else {
				var21[var25].xCoord += (double)x + 0.5D;
				var21[var25].yCoord += (double)((float)y + 0.125F);
				var21[var25].zCoord += (double)z + 0.5D;
			}
		}

		Vec3D var30 = null;
		Vec3D var26 = null;
		Vec3D var27 = null;
		Vec3D var28 = null;

		for(int var29 = 0; var29 < 6; ++var29) {
			if(var29 == 0) {
				var17 = (float)(var15 + 7) / 256.0F;
				var18 = ((float)(var15 + 9) - 0.01F) / 256.0F;
				var19 = (float)(var16 + 6) / 256.0F;
				var20 = ((float)(var16 + 8) - 0.01F) / 256.0F;
			} else if(var29 == 2) {
				var17 = (float)(var15 + 7) / 256.0F;
				var18 = ((float)(var15 + 9) - 0.01F) / 256.0F;
				var19 = (float)(var16 + 6) / 256.0F;
				var20 = ((float)(var16 + 16) - 0.01F) / 256.0F;
			}

			if(var29 == 0) {
				var30 = var21[0];
				var26 = var21[1];
				var27 = var21[2];
				var28 = var21[3];
			} else if(var29 == 1) {
				var30 = var21[7];
				var26 = var21[6];
				var27 = var21[5];
				var28 = var21[4];
			} else if(var29 == 2) {
				var30 = var21[1];
				var26 = var21[0];
				var27 = var21[4];
				var28 = var21[5];
			} else if(var29 == 3) {
				var30 = var21[2];
				var26 = var21[1];
				var27 = var21[5];
				var28 = var21[6];
			} else if(var29 == 4) {
				var30 = var21[3];
				var26 = var21[2];
				var27 = var21[6];
				var28 = var21[7];
			} else if(var29 == 5) {
				var30 = var21[0];
				var26 = var21[3];
				var27 = var21[7];
				var28 = var21[4];
			}

			var8.addVertexWithUV(var30.xCoord, var30.yCoord, var30.zCoord, (double)var17, (double)var20);
			var8.addVertexWithUV(var26.xCoord, var26.yCoord, var26.zCoord, (double)var18, (double)var20);
			var8.addVertexWithUV(var27.xCoord, var27.yCoord, var27.zCoord, (double)var18, (double)var19);
			var8.addVertexWithUV(var28.xCoord, var28.yCoord, var28.zCoord, (double)var17, (double)var19);
		}

		return true;
	}

	public boolean renderBlockFire(Block block, int x, int y, int z) {
		Tessellator var5 = Tessellator.instance;
		int var6 = block.getBlockTextureFromSide(0);
		if(this.overrideBlockTexture >= 0) {
			var6 = this.overrideBlockTexture;
		}

		float var7 = block.getBlockBrightness(this.blockAccess, x, y, z);
		var5.setColorOpaque_F(var7, var7, var7);
		int var8 = (var6 & 15) << 4;
		int var9 = var6 & 240;
		double var10 = (double)((float)var8 / 256.0F);
		double var12 = (double)(((float)var8 + 15.99F) / 256.0F);
		double var14 = (double)((float)var9 / 256.0F);
		double var16 = (double)(((float)var9 + 15.99F) / 256.0F);
		float var18 = 1.4F;
		double var21;
		double var23;
		double var25;
		double var27;
		double var29;
		double var31;
		double var33;
		if(!this.blockAccess.isBlockNormalCube(x, y - 1, z) && !Block.fire.canBlockCatchFire(this.blockAccess, x, y - 1, z)) {
			float var37 = 0.2F;
			float var20 = 0.0625F;
			if((x + y + z & 1) == 1) {
				var10 = (double)((float)var8 / 256.0F);
				var12 = (double)(((float)var8 + 15.99F) / 256.0F);
				var14 = (double)((float)(var9 + 16) / 256.0F);
				var16 = (double)(((float)var9 + 15.99F + 16.0F) / 256.0F);
			}

			if((x / 2 + y / 2 + z / 2 & 1) == 1) {
				var21 = var12;
				var12 = var10;
				var10 = var21;
			}

			if(Block.fire.canBlockCatchFire(this.blockAccess, x - 1, y, z)) {
				var5.addVertexWithUV((double)((float)x + var37), (double)((float)y + var18 + var20), (double)(z + 1), var12, var14);
				var5.addVertexWithUV((double)(x + 0), (double)((float)(y + 0) + var20), (double)(z + 1), var12, var16);
				var5.addVertexWithUV((double)(x + 0), (double)((float)(y + 0) + var20), (double)(z + 0), var10, var16);
				var5.addVertexWithUV((double)((float)x + var37), (double)((float)y + var18 + var20), (double)(z + 0), var10, var14);
				var5.addVertexWithUV((double)((float)x + var37), (double)((float)y + var18 + var20), (double)(z + 0), var10, var14);
				var5.addVertexWithUV((double)(x + 0), (double)((float)(y + 0) + var20), (double)(z + 0), var10, var16);
				var5.addVertexWithUV((double)(x + 0), (double)((float)(y + 0) + var20), (double)(z + 1), var12, var16);
				var5.addVertexWithUV((double)((float)x + var37), (double)((float)y + var18 + var20), (double)(z + 1), var12, var14);
			}

			if(Block.fire.canBlockCatchFire(this.blockAccess, x + 1, y, z)) {
				var5.addVertexWithUV((double)((float)(x + 1) - var37), (double)((float)y + var18 + var20), (double)(z + 0), var10, var14);
				var5.addVertexWithUV((double)(x + 1 - 0), (double)((float)(y + 0) + var20), (double)(z + 0), var10, var16);
				var5.addVertexWithUV((double)(x + 1 - 0), (double)((float)(y + 0) + var20), (double)(z + 1), var12, var16);
				var5.addVertexWithUV((double)((float)(x + 1) - var37), (double)((float)y + var18 + var20), (double)(z + 1), var12, var14);
				var5.addVertexWithUV((double)((float)(x + 1) - var37), (double)((float)y + var18 + var20), (double)(z + 1), var12, var14);
				var5.addVertexWithUV((double)(x + 1 - 0), (double)((float)(y + 0) + var20), (double)(z + 1), var12, var16);
				var5.addVertexWithUV((double)(x + 1 - 0), (double)((float)(y + 0) + var20), (double)(z + 0), var10, var16);
				var5.addVertexWithUV((double)((float)(x + 1) - var37), (double)((float)y + var18 + var20), (double)(z + 0), var10, var14);
			}

			if(Block.fire.canBlockCatchFire(this.blockAccess, x, y, z - 1)) {
				var5.addVertexWithUV((double)(x + 0), (double)((float)y + var18 + var20), (double)((float)z + var37), var12, var14);
				var5.addVertexWithUV((double)(x + 0), (double)((float)(y + 0) + var20), (double)(z + 0), var12, var16);
				var5.addVertexWithUV((double)(x + 1), (double)((float)(y + 0) + var20), (double)(z + 0), var10, var16);
				var5.addVertexWithUV((double)(x + 1), (double)((float)y + var18 + var20), (double)((float)z + var37), var10, var14);
				var5.addVertexWithUV((double)(x + 1), (double)((float)y + var18 + var20), (double)((float)z + var37), var10, var14);
				var5.addVertexWithUV((double)(x + 1), (double)((float)(y + 0) + var20), (double)(z + 0), var10, var16);
				var5.addVertexWithUV((double)(x + 0), (double)((float)(y + 0) + var20), (double)(z + 0), var12, var16);
				var5.addVertexWithUV((double)(x + 0), (double)((float)y + var18 + var20), (double)((float)z + var37), var12, var14);
			}

			if(Block.fire.canBlockCatchFire(this.blockAccess, x, y, z + 1)) {
				var5.addVertexWithUV((double)(x + 1), (double)((float)y + var18 + var20), (double)((float)(z + 1) - var37), var10, var14);
				var5.addVertexWithUV((double)(x + 1), (double)((float)(y + 0) + var20), (double)(z + 1 - 0), var10, var16);
				var5.addVertexWithUV((double)(x + 0), (double)((float)(y + 0) + var20), (double)(z + 1 - 0), var12, var16);
				var5.addVertexWithUV((double)(x + 0), (double)((float)y + var18 + var20), (double)((float)(z + 1) - var37), var12, var14);
				var5.addVertexWithUV((double)(x + 0), (double)((float)y + var18 + var20), (double)((float)(z + 1) - var37), var12, var14);
				var5.addVertexWithUV((double)(x + 0), (double)((float)(y + 0) + var20), (double)(z + 1 - 0), var12, var16);
				var5.addVertexWithUV((double)(x + 1), (double)((float)(y + 0) + var20), (double)(z + 1 - 0), var10, var16);
				var5.addVertexWithUV((double)(x + 1), (double)((float)y + var18 + var20), (double)((float)(z + 1) - var37), var10, var14);
			}

			if(Block.fire.canBlockCatchFire(this.blockAccess, x, y + 1, z)) {
				var21 = (double)x + 0.5D + 0.5D;
				var23 = (double)x + 0.5D - 0.5D;
				var25 = (double)z + 0.5D + 0.5D;
				var27 = (double)z + 0.5D - 0.5D;
				var29 = (double)x + 0.5D - 0.5D;
				var31 = (double)x + 0.5D + 0.5D;
				var33 = (double)z + 0.5D - 0.5D;
				double var35 = (double)z + 0.5D + 0.5D;
				var10 = (double)((float)var8 / 256.0F);
				var12 = (double)(((float)var8 + 15.99F) / 256.0F);
				var14 = (double)((float)var9 / 256.0F);
				var16 = (double)(((float)var9 + 15.99F) / 256.0F);
				++y;
				var18 = -0.2F;
				if((x + y + z & 1) == 0) {
					var5.addVertexWithUV(var29, (double)((float)y + var18), (double)(z + 0), var12, var14);
					var5.addVertexWithUV(var21, (double)(y + 0), (double)(z + 0), var12, var16);
					var5.addVertexWithUV(var21, (double)(y + 0), (double)(z + 1), var10, var16);
					var5.addVertexWithUV(var29, (double)((float)y + var18), (double)(z + 1), var10, var14);
					var10 = (double)((float)var8 / 256.0F);
					var12 = (double)(((float)var8 + 15.99F) / 256.0F);
					var14 = (double)((float)(var9 + 16) / 256.0F);
					var16 = (double)(((float)var9 + 15.99F + 16.0F) / 256.0F);
					var5.addVertexWithUV(var31, (double)((float)y + var18), (double)(z + 1), var12, var14);
					var5.addVertexWithUV(var23, (double)(y + 0), (double)(z + 1), var12, var16);
					var5.addVertexWithUV(var23, (double)(y + 0), (double)(z + 0), var10, var16);
					var5.addVertexWithUV(var31, (double)((float)y + var18), (double)(z + 0), var10, var14);
				} else {
					var5.addVertexWithUV((double)(x + 0), (double)((float)y + var18), var35, var12, var14);
					var5.addVertexWithUV((double)(x + 0), (double)(y + 0), var27, var12, var16);
					var5.addVertexWithUV((double)(x + 1), (double)(y + 0), var27, var10, var16);
					var5.addVertexWithUV((double)(x + 1), (double)((float)y + var18), var35, var10, var14);
					var10 = (double)((float)var8 / 256.0F);
					var12 = (double)(((float)var8 + 15.99F) / 256.0F);
					var14 = (double)((float)(var9 + 16) / 256.0F);
					var16 = (double)(((float)var9 + 15.99F + 16.0F) / 256.0F);
					var5.addVertexWithUV((double)(x + 1), (double)((float)y + var18), var33, var12, var14);
					var5.addVertexWithUV((double)(x + 1), (double)(y + 0), var25, var12, var16);
					var5.addVertexWithUV((double)(x + 0), (double)(y + 0), var25, var10, var16);
					var5.addVertexWithUV((double)(x + 0), (double)((float)y + var18), var33, var10, var14);
				}
			}
		} else {
			double var19 = (double)x + 0.5D + 0.2D;
			var21 = (double)x + 0.5D - 0.2D;
			var23 = (double)z + 0.5D + 0.2D;
			var25 = (double)z + 0.5D - 0.2D;
			var27 = (double)x + 0.5D - 0.3D;
			var29 = (double)x + 0.5D + 0.3D;
			var31 = (double)z + 0.5D - 0.3D;
			var33 = (double)z + 0.5D + 0.3D;
			var5.addVertexWithUV(var27, (double)((float)y + var18), (double)(z + 1), var12, var14);
			var5.addVertexWithUV(var19, (double)(y + 0), (double)(z + 1), var12, var16);
			var5.addVertexWithUV(var19, (double)(y + 0), (double)(z + 0), var10, var16);
			var5.addVertexWithUV(var27, (double)((float)y + var18), (double)(z + 0), var10, var14);
			var5.addVertexWithUV(var29, (double)((float)y + var18), (double)(z + 0), var12, var14);
			var5.addVertexWithUV(var21, (double)(y + 0), (double)(z + 0), var12, var16);
			var5.addVertexWithUV(var21, (double)(y + 0), (double)(z + 1), var10, var16);
			var5.addVertexWithUV(var29, (double)((float)y + var18), (double)(z + 1), var10, var14);
			var10 = (double)((float)var8 / 256.0F);
			var12 = (double)(((float)var8 + 15.99F) / 256.0F);
			var14 = (double)((float)(var9 + 16) / 256.0F);
			var16 = (double)(((float)var9 + 15.99F + 16.0F) / 256.0F);
			var5.addVertexWithUV((double)(x + 1), (double)((float)y + var18), var33, var12, var14);
			var5.addVertexWithUV((double)(x + 1), (double)(y + 0), var25, var12, var16);
			var5.addVertexWithUV((double)(x + 0), (double)(y + 0), var25, var10, var16);
			var5.addVertexWithUV((double)(x + 0), (double)((float)y + var18), var33, var10, var14);
			var5.addVertexWithUV((double)(x + 0), (double)((float)y + var18), var31, var12, var14);
			var5.addVertexWithUV((double)(x + 0), (double)(y + 0), var23, var12, var16);
			var5.addVertexWithUV((double)(x + 1), (double)(y + 0), var23, var10, var16);
			var5.addVertexWithUV((double)(x + 1), (double)((float)y + var18), var31, var10, var14);
			var19 = (double)x + 0.5D - 0.5D;
			var21 = (double)x + 0.5D + 0.5D;
			var23 = (double)z + 0.5D - 0.5D;
			var25 = (double)z + 0.5D + 0.5D;
			var27 = (double)x + 0.5D - 0.4D;
			var29 = (double)x + 0.5D + 0.4D;
			var31 = (double)z + 0.5D - 0.4D;
			var33 = (double)z + 0.5D + 0.4D;
			var5.addVertexWithUV(var27, (double)((float)y + var18), (double)(z + 0), var10, var14);
			var5.addVertexWithUV(var19, (double)(y + 0), (double)(z + 0), var10, var16);
			var5.addVertexWithUV(var19, (double)(y + 0), (double)(z + 1), var12, var16);
			var5.addVertexWithUV(var27, (double)((float)y + var18), (double)(z + 1), var12, var14);
			var5.addVertexWithUV(var29, (double)((float)y + var18), (double)(z + 1), var10, var14);
			var5.addVertexWithUV(var21, (double)(y + 0), (double)(z + 1), var10, var16);
			var5.addVertexWithUV(var21, (double)(y + 0), (double)(z + 0), var12, var16);
			var5.addVertexWithUV(var29, (double)((float)y + var18), (double)(z + 0), var12, var14);
			var10 = (double)((float)var8 / 256.0F);
			var12 = (double)(((float)var8 + 15.99F) / 256.0F);
			var14 = (double)((float)var9 / 256.0F);
			var16 = (double)(((float)var9 + 15.99F) / 256.0F);
			var5.addVertexWithUV((double)(x + 0), (double)((float)y + var18), var33, var10, var14);
			var5.addVertexWithUV((double)(x + 0), (double)(y + 0), var25, var10, var16);
			var5.addVertexWithUV((double)(x + 1), (double)(y + 0), var25, var12, var16);
			var5.addVertexWithUV((double)(x + 1), (double)((float)y + var18), var33, var12, var14);
			var5.addVertexWithUV((double)(x + 1), (double)((float)y + var18), var31, var10, var14);
			var5.addVertexWithUV((double)(x + 1), (double)(y + 0), var23, var10, var16);
			var5.addVertexWithUV((double)(x + 0), (double)(y + 0), var23, var12, var16);
			var5.addVertexWithUV((double)(x + 0), (double)((float)y + var18), var31, var12, var14);
		}

		return true;
	}

	public boolean renderBlockRedstoneWire(Block block, int x, int y, int z) {
		Tessellator var5 = Tessellator.instance;
		int var6 = block.getBlockTextureFromSideAndMetadata(1, this.blockAccess.getBlockMetadata(x, y, z));
		if(this.overrideBlockTexture >= 0) {
			var6 = this.overrideBlockTexture;
		}

		float var7 = block.getBlockBrightness(this.blockAccess, x, y, z);
		var5.setColorOpaque_F(var7, var7, var7);
		int var8 = (var6 & 15) << 4;
		int var9 = var6 & 240;
		double var10 = (double)((float)var8 / 256.0F);
		double var12 = (double)(((float)var8 + 15.99F) / 256.0F);
		double var14 = (double)((float)var9 / 256.0F);
		double var16 = (double)(((float)var9 + 15.99F) / 256.0F);
		float var18 = 0.0F;
		float var19 = 0.03125F;
		boolean var20 = BlockRedstoneWire.isPowerProviderOrWire(this.blockAccess, x - 1, y, z) || !this.blockAccess.isBlockNormalCube(x - 1, y, z) && BlockRedstoneWire.isPowerProviderOrWire(this.blockAccess, x - 1, y - 1, z);
		boolean var21 = BlockRedstoneWire.isPowerProviderOrWire(this.blockAccess, x + 1, y, z) || !this.blockAccess.isBlockNormalCube(x + 1, y, z) && BlockRedstoneWire.isPowerProviderOrWire(this.blockAccess, x + 1, y - 1, z);
		boolean var22 = BlockRedstoneWire.isPowerProviderOrWire(this.blockAccess, x, y, z - 1) || !this.blockAccess.isBlockNormalCube(x, y, z - 1) && BlockRedstoneWire.isPowerProviderOrWire(this.blockAccess, x, y - 1, z - 1);
		boolean var23 = BlockRedstoneWire.isPowerProviderOrWire(this.blockAccess, x, y, z + 1) || !this.blockAccess.isBlockNormalCube(x, y, z + 1) && BlockRedstoneWire.isPowerProviderOrWire(this.blockAccess, x, y - 1, z + 1);
		if(!this.blockAccess.isBlockNormalCube(x, y + 1, z)) {
			if(this.blockAccess.isBlockNormalCube(x - 1, y, z) && BlockRedstoneWire.isPowerProviderOrWire(this.blockAccess, x - 1, y + 1, z)) {
				var20 = true;
			}

			if(this.blockAccess.isBlockNormalCube(x + 1, y, z) && BlockRedstoneWire.isPowerProviderOrWire(this.blockAccess, x + 1, y + 1, z)) {
				var21 = true;
			}

			if(this.blockAccess.isBlockNormalCube(x, y, z - 1) && BlockRedstoneWire.isPowerProviderOrWire(this.blockAccess, x, y + 1, z - 1)) {
				var22 = true;
			}

			if(this.blockAccess.isBlockNormalCube(x, y, z + 1) && BlockRedstoneWire.isPowerProviderOrWire(this.blockAccess, x, y + 1, z + 1)) {
				var23 = true;
			}
		}

		float var24 = 0.3125F;
		float var25 = (float)(x + 0);
		float var26 = (float)(x + 1);
		float var27 = (float)(z + 0);
		float var28 = (float)(z + 1);
		byte var29 = 0;
		if((var20 || var21) && !var22 && !var23) {
			var29 = 1;
		}

		if((var22 || var23) && !var21 && !var20) {
			var29 = 2;
		}

		if(var29 != 0) {
			var10 = (double)((float)(var8 + 16) / 256.0F);
			var12 = (double)(((float)(var8 + 16) + 15.99F) / 256.0F);
			var14 = (double)((float)var9 / 256.0F);
			var16 = (double)(((float)var9 + 15.99F) / 256.0F);
		}

		if(var29 == 0) {
			if(var21 || var22 || var23 || var20) {
				if(!var20) {
					var25 += var24;
				}

				if(!var20) {
					var10 += (double)(var24 / 16.0F);
				}

				if(!var21) {
					var26 -= var24;
				}

				if(!var21) {
					var12 -= (double)(var24 / 16.0F);
				}

				if(!var22) {
					var27 += var24;
				}

				if(!var22) {
					var14 += (double)(var24 / 16.0F);
				}

				if(!var23) {
					var28 -= var24;
				}

				if(!var23) {
					var16 -= (double)(var24 / 16.0F);
				}
			}

			var5.addVertexWithUV((double)(var26 + var18), (double)((float)y + var19), (double)(var28 + var18), var12, var16);
			var5.addVertexWithUV((double)(var26 + var18), (double)((float)y + var19), (double)(var27 - var18), var12, var14);
			var5.addVertexWithUV((double)(var25 - var18), (double)((float)y + var19), (double)(var27 - var18), var10, var14);
			var5.addVertexWithUV((double)(var25 - var18), (double)((float)y + var19), (double)(var28 + var18), var10, var16);
		}

		if(var29 == 1) {
			var5.addVertexWithUV((double)(var26 + var18), (double)((float)y + var19), (double)(var28 + var18), var12, var16);
			var5.addVertexWithUV((double)(var26 + var18), (double)((float)y + var19), (double)(var27 - var18), var12, var14);
			var5.addVertexWithUV((double)(var25 - var18), (double)((float)y + var19), (double)(var27 - var18), var10, var14);
			var5.addVertexWithUV((double)(var25 - var18), (double)((float)y + var19), (double)(var28 + var18), var10, var16);
		}

		if(var29 == 2) {
			var5.addVertexWithUV((double)(var26 + var18), (double)((float)y + var19), (double)(var28 + var18), var12, var16);
			var5.addVertexWithUV((double)(var26 + var18), (double)((float)y + var19), (double)(var27 - var18), var10, var16);
			var5.addVertexWithUV((double)(var25 - var18), (double)((float)y + var19), (double)(var27 - var18), var10, var14);
			var5.addVertexWithUV((double)(var25 - var18), (double)((float)y + var19), (double)(var28 + var18), var12, var14);
		}

		var10 = (double)((float)(var8 + 16) / 256.0F);
		var12 = (double)(((float)(var8 + 16) + 15.99F) / 256.0F);
		var14 = (double)((float)var9 / 256.0F);
		var16 = (double)(((float)var9 + 15.99F) / 256.0F);
		if(!this.blockAccess.isBlockNormalCube(x, y + 1, z)) {
			if(this.blockAccess.isBlockNormalCube(x - 1, y, z) && this.blockAccess.getBlockId(x - 1, y + 1, z) == Block.redstoneWire.blockID) {
				var5.addVertexWithUV((double)((float)x + var19), (double)((float)(y + 1) + var18), (double)((float)(z + 1) + var18), var12, var14);
				var5.addVertexWithUV((double)((float)x + var19), (double)((float)(y + 0) - var18), (double)((float)(z + 1) + var18), var10, var14);
				var5.addVertexWithUV((double)((float)x + var19), (double)((float)(y + 0) - var18), (double)((float)(z + 0) - var18), var10, var16);
				var5.addVertexWithUV((double)((float)x + var19), (double)((float)(y + 1) + var18), (double)((float)(z + 0) - var18), var12, var16);
			}

			if(this.blockAccess.isBlockNormalCube(x + 1, y, z) && this.blockAccess.getBlockId(x + 1, y + 1, z) == Block.redstoneWire.blockID) {
				var5.addVertexWithUV((double)((float)(x + 1) - var19), (double)((float)(y + 0) - var18), (double)((float)(z + 1) + var18), var10, var16);
				var5.addVertexWithUV((double)((float)(x + 1) - var19), (double)((float)(y + 1) + var18), (double)((float)(z + 1) + var18), var12, var16);
				var5.addVertexWithUV((double)((float)(x + 1) - var19), (double)((float)(y + 1) + var18), (double)((float)(z + 0) - var18), var12, var14);
				var5.addVertexWithUV((double)((float)(x + 1) - var19), (double)((float)(y + 0) - var18), (double)((float)(z + 0) - var18), var10, var14);
			}

			if(this.blockAccess.isBlockNormalCube(x, y, z - 1) && this.blockAccess.getBlockId(x, y + 1, z - 1) == Block.redstoneWire.blockID) {
				var5.addVertexWithUV((double)((float)(x + 1) + var18), (double)((float)(y + 0) - var18), (double)((float)z + var19), var10, var16);
				var5.addVertexWithUV((double)((float)(x + 1) + var18), (double)((float)(y + 1) + var18), (double)((float)z + var19), var12, var16);
				var5.addVertexWithUV((double)((float)(x + 0) - var18), (double)((float)(y + 1) + var18), (double)((float)z + var19), var12, var14);
				var5.addVertexWithUV((double)((float)(x + 0) - var18), (double)((float)(y + 0) - var18), (double)((float)z + var19), var10, var14);
			}

			if(this.blockAccess.isBlockNormalCube(x, y, z + 1) && this.blockAccess.getBlockId(x, y + 1, z + 1) == Block.redstoneWire.blockID) {
				var5.addVertexWithUV((double)((float)(x + 1) + var18), (double)((float)(y + 1) + var18), (double)((float)(z + 1) - var19), var12, var14);
				var5.addVertexWithUV((double)((float)(x + 1) + var18), (double)((float)(y + 0) - var18), (double)((float)(z + 1) - var19), var10, var14);
				var5.addVertexWithUV((double)((float)(x + 0) - var18), (double)((float)(y + 0) - var18), (double)((float)(z + 1) - var19), var10, var16);
				var5.addVertexWithUV((double)((float)(x + 0) - var18), (double)((float)(y + 1) + var18), (double)((float)(z + 1) - var19), var12, var16);
			}
		}

		return true;
	}

	public boolean renderBlockMinecartTrack(Block block, int x, int y, int z) {
		Tessellator var5 = Tessellator.instance;
		int var6 = this.blockAccess.getBlockMetadata(x, y, z);
		int var7 = block.getBlockTextureFromSideAndMetadata(0, var6);
		if(this.overrideBlockTexture >= 0) {
			var7 = this.overrideBlockTexture;
		}

		float var8 = block.getBlockBrightness(this.blockAccess, x, y, z);
		var5.setColorOpaque_F(var8, var8, var8);
		int var9 = (var7 & 15) << 4;
		int var10 = var7 & 240;
		double var11 = (double)((float)var9 / 256.0F);
		double var13 = (double)(((float)var9 + 15.99F) / 256.0F);
		double var15 = (double)((float)var10 / 256.0F);
		double var17 = (double)(((float)var10 + 15.99F) / 256.0F);
		float var19 = 0.0625F;
		float var20 = (float)(x + 1);
		float var21 = (float)(x + 1);
		float var22 = (float)(x + 0);
		float var23 = (float)(x + 0);
		float var24 = (float)(z + 0);
		float var25 = (float)(z + 1);
		float var26 = (float)(z + 1);
		float var27 = (float)(z + 0);
		float var28 = (float)y + var19;
		float var29 = (float)y + var19;
		float var30 = (float)y + var19;
		float var31 = (float)y + var19;
		if(var6 != 1 && var6 != 2 && var6 != 3 && var6 != 7) {
			if(var6 == 8) {
				var20 = var21 = (float)(x + 0);
				var22 = var23 = (float)(x + 1);
				var24 = var27 = (float)(z + 1);
				var25 = var26 = (float)(z + 0);
			} else if(var6 == 9) {
				var20 = var23 = (float)(x + 0);
				var21 = var22 = (float)(x + 1);
				var24 = var25 = (float)(z + 0);
				var26 = var27 = (float)(z + 1);
			}
		} else {
			var20 = var23 = (float)(x + 1);
			var21 = var22 = (float)(x + 0);
			var24 = var25 = (float)(z + 1);
			var26 = var27 = (float)(z + 0);
		}

		if(var6 != 2 && var6 != 4) {
			if(var6 == 3 || var6 == 5) {
				++var29;
				++var30;
			}
		} else {
			++var28;
			++var31;
		}

		var5.addVertexWithUV((double)var20, (double)var28, (double)var24, var13, var15);
		var5.addVertexWithUV((double)var21, (double)var29, (double)var25, var13, var17);
		var5.addVertexWithUV((double)var22, (double)var30, (double)var26, var11, var17);
		var5.addVertexWithUV((double)var23, (double)var31, (double)var27, var11, var15);
		var5.addVertexWithUV((double)var23, (double)var31, (double)var27, var11, var15);
		var5.addVertexWithUV((double)var22, (double)var30, (double)var26, var11, var17);
		var5.addVertexWithUV((double)var21, (double)var29, (double)var25, var13, var17);
		var5.addVertexWithUV((double)var20, (double)var28, (double)var24, var13, var15);
		return true;
	}

	public boolean renderBlockLadder(Block block, int x, int y, int z) {
		Tessellator var5 = Tessellator.instance;
		int var6 = block.getBlockTextureFromSide(0);
		if(this.overrideBlockTexture >= 0) {
			var6 = this.overrideBlockTexture;
		}

		float var7 = block.getBlockBrightness(this.blockAccess, x, y, z);
		var5.setColorOpaque_F(var7, var7, var7);
		int var8 = (var6 & 15) << 4;
		int var9 = var6 & 240;
		double var10 = (double)((float)var8 / 256.0F);
		double var12 = (double)(((float)var8 + 15.99F) / 256.0F);
		double var14 = (double)((float)var9 / 256.0F);
		double var16 = (double)(((float)var9 + 15.99F) / 256.0F);
		int var18 = this.blockAccess.getBlockMetadata(x, y, z);
		float var19 = 0.0F;
		float var20 = 0.05F;
		if(var18 == 5) {
			var5.addVertexWithUV((double)((float)x + var20), (double)((float)(y + 1) + var19), (double)((float)(z + 1) + var19), var10, var14);
			var5.addVertexWithUV((double)((float)x + var20), (double)((float)(y + 0) - var19), (double)((float)(z + 1) + var19), var10, var16);
			var5.addVertexWithUV((double)((float)x + var20), (double)((float)(y + 0) - var19), (double)((float)(z + 0) - var19), var12, var16);
			var5.addVertexWithUV((double)((float)x + var20), (double)((float)(y + 1) + var19), (double)((float)(z + 0) - var19), var12, var14);
		}

		if(var18 == 4) {
			var5.addVertexWithUV((double)((float)(x + 1) - var20), (double)((float)(y + 0) - var19), (double)((float)(z + 1) + var19), var12, var16);
			var5.addVertexWithUV((double)((float)(x + 1) - var20), (double)((float)(y + 1) + var19), (double)((float)(z + 1) + var19), var12, var14);
			var5.addVertexWithUV((double)((float)(x + 1) - var20), (double)((float)(y + 1) + var19), (double)((float)(z + 0) - var19), var10, var14);
			var5.addVertexWithUV((double)((float)(x + 1) - var20), (double)((float)(y + 0) - var19), (double)((float)(z + 0) - var19), var10, var16);
		}

		if(var18 == 3) {
			var5.addVertexWithUV((double)((float)(x + 1) + var19), (double)((float)(y + 0) - var19), (double)((float)z + var20), var12, var16);
			var5.addVertexWithUV((double)((float)(x + 1) + var19), (double)((float)(y + 1) + var19), (double)((float)z + var20), var12, var14);
			var5.addVertexWithUV((double)((float)(x + 0) - var19), (double)((float)(y + 1) + var19), (double)((float)z + var20), var10, var14);
			var5.addVertexWithUV((double)((float)(x + 0) - var19), (double)((float)(y + 0) - var19), (double)((float)z + var20), var10, var16);
		}

		if(var18 == 2) {
			var5.addVertexWithUV((double)((float)(x + 1) + var19), (double)((float)(y + 1) + var19), (double)((float)(z + 1) - var20), var10, var14);
			var5.addVertexWithUV((double)((float)(x + 1) + var19), (double)((float)(y + 0) - var19), (double)((float)(z + 1) - var20), var10, var16);
			var5.addVertexWithUV((double)((float)(x + 0) - var19), (double)((float)(y + 0) - var19), (double)((float)(z + 1) - var20), var12, var16);
			var5.addVertexWithUV((double)((float)(x + 0) - var19), (double)((float)(y + 1) + var19), (double)((float)(z + 1) - var20), var12, var14);
		}

		return true;
	}

	public boolean renderBlockReed(Block block, int x, int y, int z) {
		Tessellator var5 = Tessellator.instance;
		float var6 = block.getBlockBrightness(this.blockAccess, x, y, z);
		var5.setColorOpaque_F(var6, var6, var6);
		this.renderCrossedSquares(block, this.blockAccess.getBlockMetadata(x, y, z), (double)x, (double)y, (double)z);
		return true;
	}

	public boolean renderBlockCrops(Block block, int x, int y, int z) {
		Tessellator var5 = Tessellator.instance;
		float var6 = block.getBlockBrightness(this.blockAccess, x, y, z);
		var5.setColorOpaque_F(var6, var6, var6);
		this.renderBlockCropsImpl(block, this.blockAccess.getBlockMetadata(x, y, z), (double)x, (double)((float)y - 0.0625F), (double)z);
		return true;
	}

	public void renderTorchAtAngle(Block block, double var2, double var4, double var6, double var8, double var10) {
		Tessellator var12 = Tessellator.instance;
		int var13 = block.getBlockTextureFromSide(0);
		if(this.overrideBlockTexture >= 0) {
			var13 = this.overrideBlockTexture;
		}

		int var14 = (var13 & 15) << 4;
		int var15 = var13 & 240;
		float var16 = (float)var14 / 256.0F;
		float var17 = ((float)var14 + 15.99F) / 256.0F;
		float var18 = (float)var15 / 256.0F;
		float var19 = ((float)var15 + 15.99F) / 256.0F;
		double var20 = (double)var16 + 7.0D / 256D;
		double var22 = (double)var18 + 6.0D / 256D;
		double var24 = (double)var16 + 9.0D / 256D;
		double var26 = (double)var18 + 8.0D / 256D;
		var2 += 0.5D;
		var6 += 0.5D;
		double var28 = var2 - 0.5D;
		double var30 = var2 + 0.5D;
		double var32 = var6 - 0.5D;
		double var34 = var6 + 0.5D;
		double var36 = 0.0625D;
		double var38 = 0.625D;
		var12.addVertexWithUV(var2 + var8 * (1.0D - var38) - var36, var4 + var38, var6 + var10 * (1.0D - var38) - var36, var20, var22);
		var12.addVertexWithUV(var2 + var8 * (1.0D - var38) - var36, var4 + var38, var6 + var10 * (1.0D - var38) + var36, var20, var26);
		var12.addVertexWithUV(var2 + var8 * (1.0D - var38) + var36, var4 + var38, var6 + var10 * (1.0D - var38) + var36, var24, var26);
		var12.addVertexWithUV(var2 + var8 * (1.0D - var38) + var36, var4 + var38, var6 + var10 * (1.0D - var38) - var36, var24, var22);
		var12.addVertexWithUV(var2 - var36, var4 + 1.0D, var32, (double)var16, (double)var18);
		var12.addVertexWithUV(var2 - var36 + var8, var4 + 0.0D, var32 + var10, (double)var16, (double)var19);
		var12.addVertexWithUV(var2 - var36 + var8, var4 + 0.0D, var34 + var10, (double)var17, (double)var19);
		var12.addVertexWithUV(var2 - var36, var4 + 1.0D, var34, (double)var17, (double)var18);
		var12.addVertexWithUV(var2 + var36, var4 + 1.0D, var34, (double)var16, (double)var18);
		var12.addVertexWithUV(var2 + var8 + var36, var4 + 0.0D, var34 + var10, (double)var16, (double)var19);
		var12.addVertexWithUV(var2 + var8 + var36, var4 + 0.0D, var32 + var10, (double)var17, (double)var19);
		var12.addVertexWithUV(var2 + var36, var4 + 1.0D, var32, (double)var17, (double)var18);
		var12.addVertexWithUV(var28, var4 + 1.0D, var6 + var36, (double)var16, (double)var18);
		var12.addVertexWithUV(var28 + var8, var4 + 0.0D, var6 + var36 + var10, (double)var16, (double)var19);
		var12.addVertexWithUV(var30 + var8, var4 + 0.0D, var6 + var36 + var10, (double)var17, (double)var19);
		var12.addVertexWithUV(var30, var4 + 1.0D, var6 + var36, (double)var17, (double)var18);
		var12.addVertexWithUV(var30, var4 + 1.0D, var6 - var36, (double)var16, (double)var18);
		var12.addVertexWithUV(var30 + var8, var4 + 0.0D, var6 - var36 + var10, (double)var16, (double)var19);
		var12.addVertexWithUV(var28 + var8, var4 + 0.0D, var6 - var36 + var10, (double)var17, (double)var19);
		var12.addVertexWithUV(var28, var4 + 1.0D, var6 - var36, (double)var17, (double)var18);
	}

	public void renderCrossedSquares(Block block, int metadata, double x, double y, double z) {
		Tessellator var9 = Tessellator.instance;
		int var10 = block.getBlockTextureFromSideAndMetadata(0, metadata);
		if(this.overrideBlockTexture >= 0) {
			var10 = this.overrideBlockTexture;
		}

		int var11 = (var10 & 15) << 4;
		int var12 = var10 & 240;
		double var13 = (double)((float)var11 / 256.0F);
		double var15 = (double)(((float)var11 + 15.99F) / 256.0F);
		double var17 = (double)((float)var12 / 256.0F);
		double var19 = (double)(((float)var12 + 15.99F) / 256.0F);
		double var21 = x + 0.5D - (double)0.45F;
		double var23 = x + 0.5D + (double)0.45F;
		double var25 = z + 0.5D - (double)0.45F;
		double var27 = z + 0.5D + (double)0.45F;
		var9.addVertexWithUV(var21, y + 1.0D, var25, var13, var17);
		var9.addVertexWithUV(var21, y + 0.0D, var25, var13, var19);
		var9.addVertexWithUV(var23, y + 0.0D, var27, var15, var19);
		var9.addVertexWithUV(var23, y + 1.0D, var27, var15, var17);
		var9.addVertexWithUV(var23, y + 1.0D, var27, var13, var17);
		var9.addVertexWithUV(var23, y + 0.0D, var27, var13, var19);
		var9.addVertexWithUV(var21, y + 0.0D, var25, var15, var19);
		var9.addVertexWithUV(var21, y + 1.0D, var25, var15, var17);
		var9.addVertexWithUV(var21, y + 1.0D, var27, var13, var17);
		var9.addVertexWithUV(var21, y + 0.0D, var27, var13, var19);
		var9.addVertexWithUV(var23, y + 0.0D, var25, var15, var19);
		var9.addVertexWithUV(var23, y + 1.0D, var25, var15, var17);
		var9.addVertexWithUV(var23, y + 1.0D, var25, var13, var17);
		var9.addVertexWithUV(var23, y + 0.0D, var25, var13, var19);
		var9.addVertexWithUV(var21, y + 0.0D, var27, var15, var19);
		var9.addVertexWithUV(var21, y + 1.0D, var27, var15, var17);
	}

	public void renderBlockCropsImpl(Block block, int metadata, double x, double y, double z) {
		Tessellator var9 = Tessellator.instance;
		int var10 = block.getBlockTextureFromSideAndMetadata(0, metadata);
		if(this.overrideBlockTexture >= 0) {
			var10 = this.overrideBlockTexture;
		}

		int var11 = (var10 & 15) << 4;
		int var12 = var10 & 240;
		double var13 = (double)((float)var11 / 256.0F);
		double var15 = (double)(((float)var11 + 15.99F) / 256.0F);
		double var17 = (double)((float)var12 / 256.0F);
		double var19 = (double)(((float)var12 + 15.99F) / 256.0F);
		double var21 = x + 0.5D - 0.25D;
		double var23 = x + 0.5D + 0.25D;
		double var25 = z + 0.5D - 0.5D;
		double var27 = z + 0.5D + 0.5D;
		var9.addVertexWithUV(var21, y + 1.0D, var25, var13, var17);
		var9.addVertexWithUV(var21, y + 0.0D, var25, var13, var19);
		var9.addVertexWithUV(var21, y + 0.0D, var27, var15, var19);
		var9.addVertexWithUV(var21, y + 1.0D, var27, var15, var17);
		var9.addVertexWithUV(var21, y + 1.0D, var27, var13, var17);
		var9.addVertexWithUV(var21, y + 0.0D, var27, var13, var19);
		var9.addVertexWithUV(var21, y + 0.0D, var25, var15, var19);
		var9.addVertexWithUV(var21, y + 1.0D, var25, var15, var17);
		var9.addVertexWithUV(var23, y + 1.0D, var27, var13, var17);
		var9.addVertexWithUV(var23, y + 0.0D, var27, var13, var19);
		var9.addVertexWithUV(var23, y + 0.0D, var25, var15, var19);
		var9.addVertexWithUV(var23, y + 1.0D, var25, var15, var17);
		var9.addVertexWithUV(var23, y + 1.0D, var25, var13, var17);
		var9.addVertexWithUV(var23, y + 0.0D, var25, var13, var19);
		var9.addVertexWithUV(var23, y + 0.0D, var27, var15, var19);
		var9.addVertexWithUV(var23, y + 1.0D, var27, var15, var17);
		var21 = x + 0.5D - 0.5D;
		var23 = x + 0.5D + 0.5D;
		var25 = z + 0.5D - 0.25D;
		var27 = z + 0.5D + 0.25D;
		var9.addVertexWithUV(var21, y + 1.0D, var25, var13, var17);
		var9.addVertexWithUV(var21, y + 0.0D, var25, var13, var19);
		var9.addVertexWithUV(var23, y + 0.0D, var25, var15, var19);
		var9.addVertexWithUV(var23, y + 1.0D, var25, var15, var17);
		var9.addVertexWithUV(var23, y + 1.0D, var25, var13, var17);
		var9.addVertexWithUV(var23, y + 0.0D, var25, var13, var19);
		var9.addVertexWithUV(var21, y + 0.0D, var25, var15, var19);
		var9.addVertexWithUV(var21, y + 1.0D, var25, var15, var17);
		var9.addVertexWithUV(var23, y + 1.0D, var27, var13, var17);
		var9.addVertexWithUV(var23, y + 0.0D, var27, var13, var19);
		var9.addVertexWithUV(var21, y + 0.0D, var27, var15, var19);
		var9.addVertexWithUV(var21, y + 1.0D, var27, var15, var17);
		var9.addVertexWithUV(var21, y + 1.0D, var27, var13, var17);
		var9.addVertexWithUV(var21, y + 0.0D, var27, var13, var19);
		var9.addVertexWithUV(var23, y + 0.0D, var27, var15, var19);
		var9.addVertexWithUV(var23, y + 1.0D, var27, var15, var17);
	}

	public boolean renderBlockFluids(Block block, int x, int y, int z) {
		Tessellator var5 = Tessellator.instance;
		boolean var6 = block.shouldSideBeRendered(this.blockAccess, x, y + 1, z, 1);
		boolean var7 = block.shouldSideBeRendered(this.blockAccess, x, y - 1, z, 0);
		boolean[] var8 = new boolean[]{block.shouldSideBeRendered(this.blockAccess, x, y, z - 1, 2), block.shouldSideBeRendered(this.blockAccess, x, y, z + 1, 3), block.shouldSideBeRendered(this.blockAccess, x - 1, y, z, 4), block.shouldSideBeRendered(this.blockAccess, x + 1, y, z, 5)};
		if(!var6 && !var7 && !var8[0] && !var8[1] && !var8[2] && !var8[3]) {
			return false;
		} else {
			boolean var9 = false;
			float var10 = 0.5F;
			float var11 = 1.0F;
			float var12 = 0.8F;
			float var13 = 0.6F;
			double var14 = 0.0D;
			double var16 = 1.0D;
			Material var18 = block.material;
			int var19 = this.blockAccess.getBlockMetadata(x, y, z);
			float var20 = this.getFluidHeight(x, y, z, var18);
			float var21 = this.getFluidHeight(x, y, z + 1, var18);
			float var22 = this.getFluidHeight(x + 1, y, z + 1, var18);
			float var23 = this.getFluidHeight(x + 1, y, z, var18);
			int var24;
			int var27;
			float var32;
			float var33;
			float var34;
			if(this.renderAllFaces || var6) {
				var9 = true;
				var24 = block.getBlockTextureFromSideAndMetadata(1, var19);
				float var25 = (float)BlockFluid.getFlowDirection(this.blockAccess, x, y, z, var18);
				if(var25 > -999.0F) {
					var24 = block.getBlockTextureFromSideAndMetadata(2, var19);
				}

				int var26 = (var24 & 15) << 4;
				var27 = var24 & 240;
				double var28 = ((double)var26 + 8.0D) / 256.0D;
				double var30 = ((double)var27 + 8.0D) / 256.0D;
				if(var25 < -999.0F) {
					var25 = 0.0F;
				} else {
					var28 = (double)((float)(var26 + 16) / 256.0F);
					var30 = (double)((float)(var27 + 16) / 256.0F);
				}

				var32 = MathHelper.sin(var25) * 8.0F / 256.0F;
				var33 = MathHelper.cos(var25) * 8.0F / 256.0F;
				var34 = block.getBlockBrightness(this.blockAccess, x, y, z);
				var5.setColorOpaque_F(var11 * var34, var11 * var34, var11 * var34);
				var5.addVertexWithUV((double)(x + 0), (double)((float)y + var20), (double)(z + 0), var28 - (double)var33 - (double)var32, var30 - (double)var33 + (double)var32);
				var5.addVertexWithUV((double)(x + 0), (double)((float)y + var21), (double)(z + 1), var28 - (double)var33 + (double)var32, var30 + (double)var33 + (double)var32);
				var5.addVertexWithUV((double)(x + 1), (double)((float)y + var22), (double)(z + 1), var28 + (double)var33 + (double)var32, var30 + (double)var33 - (double)var32);
				var5.addVertexWithUV((double)(x + 1), (double)((float)y + var23), (double)(z + 0), var28 + (double)var33 - (double)var32, var30 - (double)var33 - (double)var32);
			}

			if(this.renderAllFaces || var7) {
				float var48 = block.getBlockBrightness(this.blockAccess, x, y - 1, z);
				var5.setColorOpaque_F(var10 * var48, var10 * var48, var10 * var48);
				this.renderBottomFace(block, (double)x, (double)y, (double)z, block.getBlockTextureFromSide(0));
				var9 = true;
			}

			for(var24 = 0; var24 < 4; ++var24) {
				int var49 = x;
				var27 = z;
				if(var24 == 0) {
					var27 = z - 1;
				}

				if(var24 == 1) {
					++var27;
				}

				if(var24 == 2) {
					var49 = x - 1;
				}

				if(var24 == 3) {
					++var49;
				}

				int var50 = block.getBlockTextureFromSideAndMetadata(var24 + 2, var19);
				int var29 = (var50 & 15) << 4;
				int var51 = var50 & 240;
				if(this.renderAllFaces || var8[var24]) {
					float var31;
					float var35;
					float var36;
					if(var24 == 0) {
						var31 = var20;
						var32 = var23;
						var33 = (float)x;
						var35 = (float)(x + 1);
						var34 = (float)z;
						var36 = (float)z;
					} else if(var24 == 1) {
						var31 = var22;
						var32 = var21;
						var33 = (float)(x + 1);
						var35 = (float)x;
						var34 = (float)(z + 1);
						var36 = (float)(z + 1);
					} else if(var24 == 2) {
						var31 = var21;
						var32 = var20;
						var33 = (float)x;
						var35 = (float)x;
						var34 = (float)(z + 1);
						var36 = (float)z;
					} else {
						var31 = var23;
						var32 = var22;
						var33 = (float)(x + 1);
						var35 = (float)(x + 1);
						var34 = (float)z;
						var36 = (float)(z + 1);
					}

					var9 = true;
					double var37 = (double)((float)(var29 + 0) / 256.0F);
					double var39 = ((double)(var29 + 16) - 0.01D) / 256.0D;
					double var41 = (double)(((float)var51 + (1.0F - var31) * 16.0F) / 256.0F);
					double var43 = (double)(((float)var51 + (1.0F - var32) * 16.0F) / 256.0F);
					double var45 = ((double)(var51 + 16) - 0.01D) / 256.0D;
					float var47 = block.getBlockBrightness(this.blockAccess, var49, y, var27);
					if(var24 < 2) {
						var47 *= var12;
					} else {
						var47 *= var13;
					}

					var5.setColorOpaque_F(var11 * var47, var11 * var47, var11 * var47);
					var5.addVertexWithUV((double)var33, (double)((float)y + var31), (double)var34, var37, var41);
					var5.addVertexWithUV((double)var35, (double)((float)y + var32), (double)var36, var39, var43);
					var5.addVertexWithUV((double)var35, (double)(y + 0), (double)var36, var39, var45);
					var5.addVertexWithUV((double)var33, (double)(y + 0), (double)var34, var37, var45);
				}
			}

			block.minY = var14;
			block.maxY = var16;
			return var9;
		}
	}

	private float getFluidHeight(int x, int y, int z, Material material) {
		int var5 = 0;
		float var6 = 0.0F;

		for(int var7 = 0; var7 < 4; ++var7) {
			int var8 = x - (var7 & 1);
			int var10 = z - (var7 >> 1 & 1);
			if(this.blockAccess.getBlockMaterial(var8, y + 1, var10) == material) {
				return 1.0F;
			}

			Material var11 = this.blockAccess.getBlockMaterial(var8, y, var10);
			if(var11 == material) {
				int var12 = this.blockAccess.getBlockMetadata(var8, y, var10);
				if(var12 >= 8 || var12 == 0) {
					var6 += BlockFluid.getFluidHeightPercent(var12) * 10.0F;
					var5 += 10;
				}

				var6 += BlockFluid.getFluidHeightPercent(var12);
				++var5;
			} else if(!var11.isSolid()) {
				++var6;
				++var5;
			}
		}

		return 1.0F - var6 / (float)var5;
	}

	public void renderBlockFallingSand(Block block, World worldObj, int x, int y, int z) {
		float var6 = 0.5F;
		float var7 = 1.0F;
		float var8 = 0.8F;
		float var9 = 0.6F;
		Tessellator var10 = Tessellator.instance;
		var10.startDrawingQuads();
		float var11 = block.getBlockBrightness(worldObj, x, y, z);
		float var12 = block.getBlockBrightness(worldObj, x, y - 1, z);
		if(var12 < var11) {
			var12 = var11;
		}

		var10.setColorOpaque_F(var6 * var12, var6 * var12, var6 * var12);
		this.renderBottomFace(block, -0.5D, -0.5D, -0.5D, block.getBlockTextureFromSide(0));
		var12 = block.getBlockBrightness(worldObj, x, y + 1, z);
		if(var12 < var11) {
			var12 = var11;
		}

		var10.setColorOpaque_F(var7 * var12, var7 * var12, var7 * var12);
		this.renderTopFace(block, -0.5D, -0.5D, -0.5D, block.getBlockTextureFromSide(1));
		var12 = block.getBlockBrightness(worldObj, x, y, z - 1);
		if(var12 < var11) {
			var12 = var11;
		}

		var10.setColorOpaque_F(var8 * var12, var8 * var12, var8 * var12);
		this.renderEastFace(block, -0.5D, -0.5D, -0.5D, block.getBlockTextureFromSide(2));
		var12 = block.getBlockBrightness(worldObj, x, y, z + 1);
		if(var12 < var11) {
			var12 = var11;
		}

		var10.setColorOpaque_F(var8 * var12, var8 * var12, var8 * var12);
		this.renderWestFace(block, -0.5D, -0.5D, -0.5D, block.getBlockTextureFromSide(3));
		var12 = block.getBlockBrightness(worldObj, x - 1, y, z);
		if(var12 < var11) {
			var12 = var11;
		}

		var10.setColorOpaque_F(var9 * var12, var9 * var12, var9 * var12);
		this.renderNorthFace(block, -0.5D, -0.5D, -0.5D, block.getBlockTextureFromSide(4));
		var12 = block.getBlockBrightness(worldObj, x + 1, y, z);
		if(var12 < var11) {
			var12 = var11;
		}

		var10.setColorOpaque_F(var9 * var12, var9 * var12, var9 * var12);
		this.renderSouthFace(block, -0.5D, -0.5D, -0.5D, block.getBlockTextureFromSide(5));
		var10.draw();
	}

	public boolean renderStandardBlock(Block block, int x, int y, int z) {
		int var5 = block.colorMultiplier(this.blockAccess, x, y, z);
		float var6 = (float)(var5 >> 16 & 255) / 255.0F;
		float var7 = (float)(var5 >> 8 & 255) / 255.0F;
		float var8 = (float)(var5 & 255) / 255.0F;
		return this.renderStandardBlockWithColorMultiplier(block, x, y, z, var6, var7, var8);
	}

	public boolean renderStandardBlockWithColorMultiplier(Block block, int x, int y, int z, float var5, float var6, float var7) {
		Tessellator var8 = Tessellator.instance;
		boolean var9 = false;
		float var10 = 0.5F;
		float var11 = 1.0F;
		float var12 = 0.8F;
		float var13 = 0.6F;
		float var14 = var10 * var5;
		float var15 = var11 * var5;
		float var16 = var12 * var5;
		float var17 = var13 * var5;
		float var18 = var10 * var6;
		float var19 = var11 * var6;
		float var20 = var12 * var6;
		float var21 = var13 * var6;
		float var22 = var10 * var7;
		float var23 = var11 * var7;
		float var24 = var12 * var7;
		float var25 = var13 * var7;
		float var26 = block.getBlockBrightness(this.blockAccess, x, y, z);
		float var27;
		if(this.renderAllFaces || block.shouldSideBeRendered(this.blockAccess, x, y - 1, z, 0)) {
			var27 = block.getBlockBrightness(this.blockAccess, x, y - 1, z);
			var8.setColorOpaque_F(var14 * var27, var18 * var27, var22 * var27);
			this.renderBottomFace(block, (double)x, (double)y, (double)z, block.getBlockTexture(this.blockAccess, x, y, z, 0));
			var9 = true;
		}

		if(this.renderAllFaces || block.shouldSideBeRendered(this.blockAccess, x, y + 1, z, 1)) {
			var27 = block.getBlockBrightness(this.blockAccess, x, y + 1, z);
			if(block.maxY != 1.0D && !block.material.getIsLiquid()) {
				var27 = var26;
			}

			var8.setColorOpaque_F(var15 * var27, var19 * var27, var23 * var27);
			this.renderTopFace(block, (double)x, (double)y, (double)z, block.getBlockTexture(this.blockAccess, x, y, z, 1));
			var9 = true;
		}

		if(this.renderAllFaces || block.shouldSideBeRendered(this.blockAccess, x, y, z - 1, 2)) {
			var27 = block.getBlockBrightness(this.blockAccess, x, y, z - 1);
			if(block.minZ > 0.0D) {
				var27 = var26;
			}

			var8.setColorOpaque_F(var16 * var27, var20 * var27, var24 * var27);
			this.renderEastFace(block, (double)x, (double)y, (double)z, block.getBlockTexture(this.blockAccess, x, y, z, 2));
			var9 = true;
		}

		if(this.renderAllFaces || block.shouldSideBeRendered(this.blockAccess, x, y, z + 1, 3)) {
			var27 = block.getBlockBrightness(this.blockAccess, x, y, z + 1);
			if(block.maxZ < 1.0D) {
				var27 = var26;
			}

			var8.setColorOpaque_F(var16 * var27, var20 * var27, var24 * var27);
			this.renderWestFace(block, (double)x, (double)y, (double)z, block.getBlockTexture(this.blockAccess, x, y, z, 3));
			var9 = true;
		}

		if(this.renderAllFaces || block.shouldSideBeRendered(this.blockAccess, x - 1, y, z, 4)) {
			var27 = block.getBlockBrightness(this.blockAccess, x - 1, y, z);
			if(block.minX > 0.0D) {
				var27 = var26;
			}

			var8.setColorOpaque_F(var17 * var27, var21 * var27, var25 * var27);
			this.renderNorthFace(block, (double)x, (double)y, (double)z, block.getBlockTexture(this.blockAccess, x, y, z, 4));
			var9 = true;
		}

		if(this.renderAllFaces || block.shouldSideBeRendered(this.blockAccess, x + 1, y, z, 5)) {
			var27 = block.getBlockBrightness(this.blockAccess, x + 1, y, z);
			if(block.maxX < 1.0D) {
				var27 = var26;
			}

			var8.setColorOpaque_F(var17 * var27, var21 * var27, var25 * var27);
			this.renderSouthFace(block, (double)x, (double)y, (double)z, block.getBlockTexture(this.blockAccess, x, y, z, 5));
			var9 = true;
		}

		return var9;
	}

	public boolean renderBlockCactus(Block blocki, int x, int y, int z) {
		int var5 = blocki.colorMultiplier(this.blockAccess, x, y, z);
		float var6 = (float)(var5 >> 16 & 255) / 255.0F;
		float var7 = (float)(var5 >> 8 & 255) / 255.0F;
		float var8 = (float)(var5 & 255) / 255.0F;
		return this.renderBlockCactusImpl(blocki, x, y, z, var6, var7, var8);
	}

	public boolean renderBlockCactusImpl(Block block, int x, int y, int z, float var5, float var6, float var7) {
		Tessellator var8 = Tessellator.instance;
		boolean var9 = false;
		float var10 = 0.5F;
		float var11 = 1.0F;
		float var12 = 0.8F;
		float var13 = 0.6F;
		float var14 = var10 * var5;
		float var15 = var11 * var5;
		float var16 = var12 * var5;
		float var17 = var13 * var5;
		float var18 = var10 * var6;
		float var19 = var11 * var6;
		float var20 = var12 * var6;
		float var21 = var13 * var6;
		float var22 = var10 * var7;
		float var23 = var11 * var7;
		float var24 = var12 * var7;
		float var25 = var13 * var7;
		float var26 = 0.0625F;
		float var27 = block.getBlockBrightness(this.blockAccess, x, y, z);
		float var28;
		if(this.renderAllFaces || block.shouldSideBeRendered(this.blockAccess, x, y - 1, z, 0)) {
			var28 = block.getBlockBrightness(this.blockAccess, x, y - 1, z);
			var8.setColorOpaque_F(var14 * var28, var18 * var28, var22 * var28);
			this.renderBottomFace(block, (double)x, (double)y, (double)z, block.getBlockTexture(this.blockAccess, x, y, z, 0));
			var9 = true;
		}

		if(this.renderAllFaces || block.shouldSideBeRendered(this.blockAccess, x, y + 1, z, 1)) {
			var28 = block.getBlockBrightness(this.blockAccess, x, y + 1, z);
			if(block.maxY != 1.0D && !block.material.getIsLiquid()) {
				var28 = var27;
			}

			var8.setColorOpaque_F(var15 * var28, var19 * var28, var23 * var28);
			this.renderTopFace(block, (double)x, (double)y, (double)z, block.getBlockTexture(this.blockAccess, x, y, z, 1));
			var9 = true;
		}

		if(this.renderAllFaces || block.shouldSideBeRendered(this.blockAccess, x, y, z - 1, 2)) {
			var28 = block.getBlockBrightness(this.blockAccess, x, y, z - 1);
			if(block.minZ > 0.0D) {
				var28 = var27;
			}

			var8.setColorOpaque_F(var16 * var28, var20 * var28, var24 * var28);
			var8.setTranslationF(0.0F, 0.0F, var26);
			this.renderEastFace(block, (double)x, (double)y, (double)z, block.getBlockTexture(this.blockAccess, x, y, z, 2));
			var8.setTranslationF(0.0F, 0.0F, -var26);
			var9 = true;
		}

		if(this.renderAllFaces || block.shouldSideBeRendered(this.blockAccess, x, y, z + 1, 3)) {
			var28 = block.getBlockBrightness(this.blockAccess, x, y, z + 1);
			if(block.maxZ < 1.0D) {
				var28 = var27;
			}

			var8.setColorOpaque_F(var16 * var28, var20 * var28, var24 * var28);
			var8.setTranslationF(0.0F, 0.0F, -var26);
			this.renderWestFace(block, (double)x, (double)y, (double)z, block.getBlockTexture(this.blockAccess, x, y, z, 3));
			var8.setTranslationF(0.0F, 0.0F, var26);
			var9 = true;
		}

		if(this.renderAllFaces || block.shouldSideBeRendered(this.blockAccess, x - 1, y, z, 4)) {
			var28 = block.getBlockBrightness(this.blockAccess, x - 1, y, z);
			if(block.minX > 0.0D) {
				var28 = var27;
			}

			var8.setColorOpaque_F(var17 * var28, var21 * var28, var25 * var28);
			var8.setTranslationF(var26, 0.0F, 0.0F);
			this.renderNorthFace(block, (double)x, (double)y, (double)z, block.getBlockTexture(this.blockAccess, x, y, z, 4));
			var8.setTranslationF(-var26, 0.0F, 0.0F);
			var9 = true;
		}

		if(this.renderAllFaces || block.shouldSideBeRendered(this.blockAccess, x + 1, y, z, 5)) {
			var28 = block.getBlockBrightness(this.blockAccess, x + 1, y, z);
			if(block.maxX < 1.0D) {
				var28 = var27;
			}

			var8.setColorOpaque_F(var17 * var28, var21 * var28, var25 * var28);
			var8.setTranslationF(-var26, 0.0F, 0.0F);
			this.renderSouthFace(block, (double)x, (double)y, (double)z, block.getBlockTexture(this.blockAccess, x, y, z, 5));
			var8.setTranslationF(var26, 0.0F, 0.0F);
			var9 = true;
		}

		return var9;
	}

	public boolean renderBlockFence(Block block, int x, int y, int z) {
		boolean var5 = false;
		float var6 = 0.375F;
		float var7 = 0.625F;
		block.setBlockBounds(var6, 0.0F, var6, var7, 1.0F, var7);
		this.renderStandardBlock(block, x, y, z);
		boolean var8 = false;
		boolean var9 = false;
		if(this.blockAccess.getBlockId(x - 1, y, z) == block.blockID || this.blockAccess.getBlockId(x + 1, y, z) == block.blockID) {
			var8 = true;
		}

		if(this.blockAccess.getBlockId(x, y, z - 1) == block.blockID || this.blockAccess.getBlockId(x, y, z + 1) == block.blockID) {
			var9 = true;
		}

		boolean var10 = this.blockAccess.getBlockId(x - 1, y, z) == block.blockID;
		boolean var11 = this.blockAccess.getBlockId(x + 1, y, z) == block.blockID;
		boolean var12 = this.blockAccess.getBlockId(x, y, z - 1) == block.blockID;
		boolean var13 = this.blockAccess.getBlockId(x, y, z + 1) == block.blockID;
		if(!var8 && !var9) {
			var8 = true;
		}

		var6 = 0.4375F;
		var7 = 0.5625F;
		float var14 = 0.75F;
		float var15 = 0.9375F;
		float var16 = var10 ? 0.0F : var6;
		float var17 = var11 ? 1.0F : var7;
		float var18 = var12 ? 0.0F : var6;
		float var19 = var13 ? 1.0F : var7;
		if(var8) {
			block.setBlockBounds(var16, var14, var6, var17, var15, var7);
			this.renderStandardBlock(block, x, y, z);
		}

		if(var9) {
			block.setBlockBounds(var6, var14, var18, var7, var15, var19);
			this.renderStandardBlock(block, x, y, z);
		}

		var14 = 0.375F;
		var15 = 0.5625F;
		if(var8) {
			block.setBlockBounds(var16, var14, var6, var17, var15, var7);
			this.renderStandardBlock(block, x, y, z);
		}

		if(var9) {
			block.setBlockBounds(var6, var14, var18, var7, var15, var19);
			this.renderStandardBlock(block, x, y, z);
		}

		block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		return var5;
	}

	public boolean renderBlockStairs(Block block, int x, int y, int z) {
		boolean var5 = false;
		int var6 = this.blockAccess.getBlockMetadata(x, y, z);
		if(var6 == 0) {
			block.setBlockBounds(0.0F, 0.0F, 0.0F, 0.5F, 0.5F, 1.0F);
			this.renderStandardBlock(block, x, y, z);
			block.setBlockBounds(0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
			this.renderStandardBlock(block, x, y, z);
		} else if(var6 == 1) {
			block.setBlockBounds(0.0F, 0.0F, 0.0F, 0.5F, 1.0F, 1.0F);
			this.renderStandardBlock(block, x, y, z);
			block.setBlockBounds(0.5F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
			this.renderStandardBlock(block, x, y, z);
		} else if(var6 == 2) {
			block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 0.5F);
			this.renderStandardBlock(block, x, y, z);
			block.setBlockBounds(0.0F, 0.0F, 0.5F, 1.0F, 1.0F, 1.0F);
			this.renderStandardBlock(block, x, y, z);
		} else if(var6 == 3) {
			block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.5F);
			this.renderStandardBlock(block, x, y, z);
			block.setBlockBounds(0.0F, 0.0F, 0.5F, 1.0F, 0.5F, 1.0F);
			this.renderStandardBlock(block, x, y, z);
		}

		block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		return var5;
	}

	public boolean renderBlockDoor(Block block, int x, int y, int z) {
		Tessellator var5 = Tessellator.instance;
		BlockDoor var6 = (BlockDoor)block;
		boolean var7 = false;
		float var8 = 0.5F;
		float var9 = 1.0F;
		float var10 = 0.8F;
		float var11 = 0.6F;
		float var12 = block.getBlockBrightness(this.blockAccess, x, y, z);
		float var13 = block.getBlockBrightness(this.blockAccess, x, y - 1, z);
		if(var6.minY > 0.0D) {
			var13 = var12;
		}

		if(Block.lightValue[block.blockID] > 0) {
			var13 = 1.0F;
		}

		var5.setColorOpaque_F(var8 * var13, var8 * var13, var8 * var13);
		this.renderBottomFace(block, (double)x, (double)y, (double)z, block.getBlockTexture(this.blockAccess, x, y, z, 0));
		var7 = true;
		var13 = block.getBlockBrightness(this.blockAccess, x, y + 1, z);
		if(var6.maxY < 1.0D) {
			var13 = var12;
		}

		if(Block.lightValue[block.blockID] > 0) {
			var13 = 1.0F;
		}

		var5.setColorOpaque_F(var9 * var13, var9 * var13, var9 * var13);
		this.renderTopFace(block, (double)x, (double)y, (double)z, block.getBlockTexture(this.blockAccess, x, y, z, 1));
		var7 = true;
		var13 = block.getBlockBrightness(this.blockAccess, x, y, z - 1);
		if(var6.minZ > 0.0D) {
			var13 = var12;
		}

		if(Block.lightValue[block.blockID] > 0) {
			var13 = 1.0F;
		}

		var5.setColorOpaque_F(var10 * var13, var10 * var13, var10 * var13);
		int var14 = block.getBlockTexture(this.blockAccess, x, y, z, 2);
		if(var14 < 0) {
			this.flipTexture = true;
			var14 = -var14;
		}

		this.renderEastFace(block, (double)x, (double)y, (double)z, var14);
		var7 = true;
		this.flipTexture = false;
		var13 = block.getBlockBrightness(this.blockAccess, x, y, z + 1);
		if(var6.maxZ < 1.0D) {
			var13 = var12;
		}

		if(Block.lightValue[block.blockID] > 0) {
			var13 = 1.0F;
		}

		var5.setColorOpaque_F(var10 * var13, var10 * var13, var10 * var13);
		var14 = block.getBlockTexture(this.blockAccess, x, y, z, 3);
		if(var14 < 0) {
			this.flipTexture = true;
			var14 = -var14;
		}

		this.renderWestFace(block, (double)x, (double)y, (double)z, var14);
		var7 = true;
		this.flipTexture = false;
		var13 = block.getBlockBrightness(this.blockAccess, x - 1, y, z);
		if(var6.minX > 0.0D) {
			var13 = var12;
		}

		if(Block.lightValue[block.blockID] > 0) {
			var13 = 1.0F;
		}

		var5.setColorOpaque_F(var11 * var13, var11 * var13, var11 * var13);
		var14 = block.getBlockTexture(this.blockAccess, x, y, z, 4);
		if(var14 < 0) {
			this.flipTexture = true;
			var14 = -var14;
		}

		this.renderNorthFace(block, (double)x, (double)y, (double)z, var14);
		var7 = true;
		this.flipTexture = false;
		var13 = block.getBlockBrightness(this.blockAccess, x + 1, y, z);
		if(var6.maxX < 1.0D) {
			var13 = var12;
		}

		if(Block.lightValue[block.blockID] > 0) {
			var13 = 1.0F;
		}

		var5.setColorOpaque_F(var11 * var13, var11 * var13, var11 * var13);
		var14 = block.getBlockTexture(this.blockAccess, x, y, z, 5);
		if(var14 < 0) {
			this.flipTexture = true;
			var14 = -var14;
		}

		this.renderSouthFace(block, (double)x, (double)y, (double)z, var14);
		var7 = true;
		this.flipTexture = false;
		return var7;
	}

	public void renderBottomFace(Block block, double var2, double var4, double var6, int var8) {
		Tessellator var9 = Tessellator.instance;
		if(this.overrideBlockTexture >= 0) {
			var8 = this.overrideBlockTexture;
		}

		int var10 = (var8 & 15) << 4;
		int var11 = var8 & 240;
		double var12 = ((double)var10 + block.minX * 16.0D) / 256.0D;
		double var14 = ((double)var10 + block.maxX * 16.0D - 0.01D) / 256.0D;
		double var16 = ((double)var11 + block.minZ * 16.0D) / 256.0D;
		double var18 = ((double)var11 + block.maxZ * 16.0D - 0.01D) / 256.0D;
		if(block.minX < 0.0D || block.maxX > 1.0D) {
			var12 = (double)(((float)var10 + 0.0F) / 256.0F);
			var14 = (double)(((float)var10 + 15.99F) / 256.0F);
		}

		if(block.minZ < 0.0D || block.maxZ > 1.0D) {
			var16 = (double)(((float)var11 + 0.0F) / 256.0F);
			var18 = (double)(((float)var11 + 15.99F) / 256.0F);
		}

		double var20 = var2 + block.minX;
		double var22 = var2 + block.maxX;
		double var24 = var4 + block.minY;
		double var26 = var6 + block.minZ;
		double var28 = var6 + block.maxZ;
		var9.addVertexWithUV(var20, var24, var28, var12, var18);
		var9.addVertexWithUV(var20, var24, var26, var12, var16);
		var9.addVertexWithUV(var22, var24, var26, var14, var16);
		var9.addVertexWithUV(var22, var24, var28, var14, var18);
	}

	public void renderTopFace(Block block, double var2, double var4, double var6, int var8) {
		Tessellator var9 = Tessellator.instance;
		if(this.overrideBlockTexture >= 0) {
			var8 = this.overrideBlockTexture;
		}

		int var10 = (var8 & 15) << 4;
		int var11 = var8 & 240;
		double var12 = ((double)var10 + block.minX * 16.0D) / 256.0D;
		double var14 = ((double)var10 + block.maxX * 16.0D - 0.01D) / 256.0D;
		double var16 = ((double)var11 + block.minZ * 16.0D) / 256.0D;
		double var18 = ((double)var11 + block.maxZ * 16.0D - 0.01D) / 256.0D;
		if(block.minX < 0.0D || block.maxX > 1.0D) {
			var12 = (double)(((float)var10 + 0.0F) / 256.0F);
			var14 = (double)(((float)var10 + 15.99F) / 256.0F);
		}

		if(block.minZ < 0.0D || block.maxZ > 1.0D) {
			var16 = (double)(((float)var11 + 0.0F) / 256.0F);
			var18 = (double)(((float)var11 + 15.99F) / 256.0F);
		}

		double var20 = var2 + block.minX;
		double var22 = var2 + block.maxX;
		double var24 = var4 + block.maxY;
		double var26 = var6 + block.minZ;
		double var28 = var6 + block.maxZ;
		var9.addVertexWithUV(var22, var24, var28, var14, var18);
		var9.addVertexWithUV(var22, var24, var26, var14, var16);
		var9.addVertexWithUV(var20, var24, var26, var12, var16);
		var9.addVertexWithUV(var20, var24, var28, var12, var18);
	}

	public void renderEastFace(Block block, double var2, double var4, double var6, int var8) {
		Tessellator var9 = Tessellator.instance;
		if(this.overrideBlockTexture >= 0) {
			var8 = this.overrideBlockTexture;
		}

		int var10 = (var8 & 15) << 4;
		int var11 = var8 & 240;
		double var12 = ((double)var10 + block.minX * 16.0D) / 256.0D;
		double var14 = ((double)var10 + block.maxX * 16.0D - 0.01D) / 256.0D;
		double var16 = ((double)var11 + block.minY * 16.0D) / 256.0D;
		double var18 = ((double)var11 + block.maxY * 16.0D - 0.01D) / 256.0D;
		double var20;
		if(this.flipTexture) {
			var20 = var12;
			var12 = var14;
			var14 = var20;
		}

		if(block.minX < 0.0D || block.maxX > 1.0D) {
			var12 = (double)(((float)var10 + 0.0F) / 256.0F);
			var14 = (double)(((float)var10 + 15.99F) / 256.0F);
		}

		if(block.minY < 0.0D || block.maxY > 1.0D) {
			var16 = (double)(((float)var11 + 0.0F) / 256.0F);
			var18 = (double)(((float)var11 + 15.99F) / 256.0F);
		}

		var20 = var2 + block.minX;
		double var22 = var2 + block.maxX;
		double var24 = var4 + block.minY;
		double var26 = var4 + block.maxY;
		double var28 = var6 + block.minZ;
		var9.addVertexWithUV(var20, var26, var28, var14, var16);
		var9.addVertexWithUV(var22, var26, var28, var12, var16);
		var9.addVertexWithUV(var22, var24, var28, var12, var18);
		var9.addVertexWithUV(var20, var24, var28, var14, var18);
	}

	public void renderWestFace(Block block, double var2, double var4, double var6, int var8) {
		Tessellator var9 = Tessellator.instance;
		if(this.overrideBlockTexture >= 0) {
			var8 = this.overrideBlockTexture;
		}

		int var10 = (var8 & 15) << 4;
		int var11 = var8 & 240;
		double var12 = ((double)var10 + block.minX * 16.0D) / 256.0D;
		double var14 = ((double)var10 + block.maxX * 16.0D - 0.01D) / 256.0D;
		double var16 = ((double)var11 + block.minY * 16.0D) / 256.0D;
		double var18 = ((double)var11 + block.maxY * 16.0D - 0.01D) / 256.0D;
		double var20;
		if(this.flipTexture) {
			var20 = var12;
			var12 = var14;
			var14 = var20;
		}

		if(block.minX < 0.0D || block.maxX > 1.0D) {
			var12 = (double)(((float)var10 + 0.0F) / 256.0F);
			var14 = (double)(((float)var10 + 15.99F) / 256.0F);
		}

		if(block.minY < 0.0D || block.maxY > 1.0D) {
			var16 = (double)(((float)var11 + 0.0F) / 256.0F);
			var18 = (double)(((float)var11 + 15.99F) / 256.0F);
		}

		var20 = var2 + block.minX;
		double var22 = var2 + block.maxX;
		double var24 = var4 + block.minY;
		double var26 = var4 + block.maxY;
		double var28 = var6 + block.maxZ;
		var9.addVertexWithUV(var20, var26, var28, var12, var16);
		var9.addVertexWithUV(var20, var24, var28, var12, var18);
		var9.addVertexWithUV(var22, var24, var28, var14, var18);
		var9.addVertexWithUV(var22, var26, var28, var14, var16);
	}

	public void renderNorthFace(Block block, double var2, double var4, double var6, int var8) {
		Tessellator var9 = Tessellator.instance;
		if(this.overrideBlockTexture >= 0) {
			var8 = this.overrideBlockTexture;
		}

		int var10 = (var8 & 15) << 4;
		int var11 = var8 & 240;
		double var12 = ((double)var10 + block.minZ * 16.0D) / 256.0D;
		double var14 = ((double)var10 + block.maxZ * 16.0D - 0.01D) / 256.0D;
		double var16 = ((double)var11 + block.minY * 16.0D) / 256.0D;
		double var18 = ((double)var11 + block.maxY * 16.0D - 0.01D) / 256.0D;
		double var20;
		if(this.flipTexture) {
			var20 = var12;
			var12 = var14;
			var14 = var20;
		}

		if(block.minZ < 0.0D || block.maxZ > 1.0D) {
			var12 = (double)(((float)var10 + 0.0F) / 256.0F);
			var14 = (double)(((float)var10 + 15.99F) / 256.0F);
		}

		if(block.minY < 0.0D || block.maxY > 1.0D) {
			var16 = (double)(((float)var11 + 0.0F) / 256.0F);
			var18 = (double)(((float)var11 + 15.99F) / 256.0F);
		}

		var20 = var2 + block.minX;
		double var22 = var4 + block.minY;
		double var24 = var4 + block.maxY;
		double var26 = var6 + block.minZ;
		double var28 = var6 + block.maxZ;
		var9.addVertexWithUV(var20, var24, var28, var14, var16);
		var9.addVertexWithUV(var20, var24, var26, var12, var16);
		var9.addVertexWithUV(var20, var22, var26, var12, var18);
		var9.addVertexWithUV(var20, var22, var28, var14, var18);
	}

	public void renderSouthFace(Block block, double var2, double var4, double var6, int var8) {
		Tessellator var9 = Tessellator.instance;
		if(this.overrideBlockTexture >= 0) {
			var8 = this.overrideBlockTexture;
		}

		int var10 = (var8 & 15) << 4;
		int var11 = var8 & 240;
		double var12 = ((double)var10 + block.minZ * 16.0D) / 256.0D;
		double var14 = ((double)var10 + block.maxZ * 16.0D - 0.01D) / 256.0D;
		double var16 = ((double)var11 + block.minY * 16.0D) / 256.0D;
		double var18 = ((double)var11 + block.maxY * 16.0D - 0.01D) / 256.0D;
		double var20;
		if(this.flipTexture) {
			var20 = var12;
			var12 = var14;
			var14 = var20;
		}

		if(block.minZ < 0.0D || block.maxZ > 1.0D) {
			var12 = (double)(((float)var10 + 0.0F) / 256.0F);
			var14 = (double)(((float)var10 + 15.99F) / 256.0F);
		}

		if(block.minY < 0.0D || block.maxY > 1.0D) {
			var16 = (double)(((float)var11 + 0.0F) / 256.0F);
			var18 = (double)(((float)var11 + 15.99F) / 256.0F);
		}

		var20 = var2 + block.maxX;
		double var22 = var4 + block.minY;
		double var24 = var4 + block.maxY;
		double var26 = var6 + block.minZ;
		double var28 = var6 + block.maxZ;
		var9.addVertexWithUV(var20, var22, var28, var12, var18);
		var9.addVertexWithUV(var20, var22, var26, var14, var18);
		var9.addVertexWithUV(var20, var24, var26, var14, var16);
		var9.addVertexWithUV(var20, var24, var28, var12, var16);
	}

	public void renderBlockAsItem(Block var1, float var2) {
		int var3 = var1.getRenderType();
		Tessellator var4 = Tessellator.instance;
		if(var3 == 0) {
			var1.setBlockBoundsForItemRender();
			GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
			float var5 = 0.5F;
			float var6 = 1.0F;
			float var7 = 0.8F;
			float var8 = 0.6F;
			var4.startDrawingQuads();
			var4.setColorRGBA_F(var6, var6, var6, var2);
			this.renderBottomFace(var1, 0.0D, 0.0D, 0.0D, var1.getBlockTextureFromSide(0));
			var4.setColorRGBA_F(var5, var5, var5, var2);
			this.renderTopFace(var1, 0.0D, 0.0D, 0.0D, var1.getBlockTextureFromSide(1));
			var4.setColorRGBA_F(var7, var7, var7, var2);
			this.renderEastFace(var1, 0.0D, 0.0D, 0.0D, var1.getBlockTextureFromSide(2));
			this.renderWestFace(var1, 0.0D, 0.0D, 0.0D, var1.getBlockTextureFromSide(3));
			var4.setColorRGBA_F(var8, var8, var8, var2);
			this.renderNorthFace(var1, 0.0D, 0.0D, 0.0D, var1.getBlockTextureFromSide(4));
			this.renderSouthFace(var1, 0.0D, 0.0D, 0.0D, var1.getBlockTextureFromSide(5));
			var4.draw();
			GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		}

	}

	public void renderBlockOnInventory(Block var1) {
		byte var2 = -1;
		Tessellator var3 = Tessellator.instance;
		int var4 = var1.getRenderType();
		if(var4 == 0) {
			var1.setBlockBoundsForItemRender();
			GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
			var3.startDrawingQuads();
			var3.setNormal(0.0F, -1.0F, 0.0F);
			this.renderBottomFace(var1, 0.0D, 0.0D, 0.0D, var1.getBlockTextureFromSide(0));
			var3.draw();
			var3.startDrawingQuads();
			var3.setNormal(0.0F, 1.0F, 0.0F);
			this.renderTopFace(var1, 0.0D, 0.0D, 0.0D, var1.getBlockTextureFromSide(1));
			var3.draw();
			var3.startDrawingQuads();
			var3.setNormal(0.0F, 0.0F, -1.0F);
			this.renderEastFace(var1, 0.0D, 0.0D, 0.0D, var1.getBlockTextureFromSide(2));
			var3.draw();
			var3.startDrawingQuads();
			var3.setNormal(0.0F, 0.0F, 1.0F);
			this.renderWestFace(var1, 0.0D, 0.0D, 0.0D, var1.getBlockTextureFromSide(3));
			var3.draw();
			var3.startDrawingQuads();
			var3.setNormal(-1.0F, 0.0F, 0.0F);
			this.renderNorthFace(var1, 0.0D, 0.0D, 0.0D, var1.getBlockTextureFromSide(4));
			var3.draw();
			var3.startDrawingQuads();
			var3.setNormal(1.0F, 0.0F, 0.0F);
			this.renderSouthFace(var1, 0.0D, 0.0D, 0.0D, var1.getBlockTextureFromSide(5));
			var3.draw();
			GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		} else if(var4 == 1) {
			var3.startDrawingQuads();
			var3.setNormal(0.0F, -1.0F, 0.0F);
			this.renderCrossedSquares(var1, var2, -0.5D, -0.5D, -0.5D);
			var3.draw();
		} else if(var4 == 13) {
			var1.setBlockBoundsForItemRender();
			GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
			float var5 = 0.0625F;
			var3.startDrawingQuads();
			var3.setNormal(0.0F, -1.0F, 0.0F);
			this.renderBottomFace(var1, 0.0D, 0.0D, 0.0D, var1.getBlockTextureFromSide(0));
			var3.draw();
			var3.startDrawingQuads();
			var3.setNormal(0.0F, 1.0F, 0.0F);
			this.renderTopFace(var1, 0.0D, 0.0D, 0.0D, var1.getBlockTextureFromSide(1));
			var3.draw();
			var3.startDrawingQuads();
			var3.setNormal(0.0F, 0.0F, -1.0F);
			var3.setTranslationF(0.0F, 0.0F, var5);
			this.renderEastFace(var1, 0.0D, 0.0D, 0.0D, var1.getBlockTextureFromSide(2));
			var3.setTranslationF(0.0F, 0.0F, -var5);
			var3.draw();
			var3.startDrawingQuads();
			var3.setNormal(0.0F, 0.0F, 1.0F);
			var3.setTranslationF(0.0F, 0.0F, -var5);
			this.renderWestFace(var1, 0.0D, 0.0D, 0.0D, var1.getBlockTextureFromSide(3));
			var3.setTranslationF(0.0F, 0.0F, var5);
			var3.draw();
			var3.startDrawingQuads();
			var3.setNormal(-1.0F, 0.0F, 0.0F);
			var3.setTranslationF(var5, 0.0F, 0.0F);
			this.renderNorthFace(var1, 0.0D, 0.0D, 0.0D, var1.getBlockTextureFromSide(4));
			var3.setTranslationF(-var5, 0.0F, 0.0F);
			var3.draw();
			var3.startDrawingQuads();
			var3.setNormal(1.0F, 0.0F, 0.0F);
			var3.setTranslationF(-var5, 0.0F, 0.0F);
			this.renderSouthFace(var1, 0.0D, 0.0D, 0.0D, var1.getBlockTextureFromSide(5));
			var3.setTranslationF(var5, 0.0F, 0.0F);
			var3.draw();
			GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		} else if(var4 == 6) {
			var3.startDrawingQuads();
			var3.setNormal(0.0F, -1.0F, 0.0F);
			this.renderBlockCropsImpl(var1, var2, -0.5D, -0.5D, -0.5D);
			var3.draw();
		} else if(var4 == 2) {
			var3.startDrawingQuads();
			var3.setNormal(0.0F, -1.0F, 0.0F);
			this.renderTorchAtAngle(var1, -0.5D, -0.5D, -0.5D, 0.0D, 0.0D);
			var3.draw();
		} else {
			int var7;
			if(var4 == 10) {
				for(var7 = 0; var7 < 2; ++var7) {
					if(var7 == 0) {
						var1.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.5F);
					}

					if(var7 == 1) {
						var1.setBlockBounds(0.0F, 0.0F, 0.5F, 1.0F, 0.5F, 1.0F);
					}

					GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
					var3.startDrawingQuads();
					var3.setNormal(0.0F, -1.0F, 0.0F);
					this.renderBottomFace(var1, 0.0D, 0.0D, 0.0D, var1.getBlockTextureFromSide(0));
					var3.draw();
					var3.startDrawingQuads();
					var3.setNormal(0.0F, 1.0F, 0.0F);
					this.renderTopFace(var1, 0.0D, 0.0D, 0.0D, var1.getBlockTextureFromSide(1));
					var3.draw();
					var3.startDrawingQuads();
					var3.setNormal(0.0F, 0.0F, -1.0F);
					this.renderEastFace(var1, 0.0D, 0.0D, 0.0D, var1.getBlockTextureFromSide(2));
					var3.draw();
					var3.startDrawingQuads();
					var3.setNormal(0.0F, 0.0F, 1.0F);
					this.renderWestFace(var1, 0.0D, 0.0D, 0.0D, var1.getBlockTextureFromSide(3));
					var3.draw();
					var3.startDrawingQuads();
					var3.setNormal(-1.0F, 0.0F, 0.0F);
					this.renderNorthFace(var1, 0.0D, 0.0D, 0.0D, var1.getBlockTextureFromSide(4));
					var3.draw();
					var3.startDrawingQuads();
					var3.setNormal(1.0F, 0.0F, 0.0F);
					this.renderSouthFace(var1, 0.0D, 0.0D, 0.0D, var1.getBlockTextureFromSide(5));
					var3.draw();
					GL11.glTranslatef(0.5F, 0.5F, 0.5F);
				}
			} else if(var4 == 11) {
				for(var7 = 0; var7 < 4; ++var7) {
					float var6 = 0.125F;
					if(var7 == 0) {
						var1.setBlockBounds(0.5F - var6, 0.0F, 0.0F, 0.5F + var6, 1.0F, var6 * 2.0F);
					}

					if(var7 == 1) {
						var1.setBlockBounds(0.5F - var6, 0.0F, 1.0F - var6 * 2.0F, 0.5F + var6, 1.0F, 1.0F);
					}

					var6 = 0.0625F;
					if(var7 == 2) {
						var1.setBlockBounds(0.5F - var6, 1.0F - var6 * 3.0F, -var6 * 2.0F, 0.5F + var6, 1.0F - var6, 1.0F + var6 * 2.0F);
					}

					if(var7 == 3) {
						var1.setBlockBounds(0.5F - var6, 0.5F - var6 * 3.0F, -var6 * 2.0F, 0.5F + var6, 0.5F - var6, 1.0F + var6 * 2.0F);
					}

					GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
					var3.startDrawingQuads();
					var3.setNormal(0.0F, -1.0F, 0.0F);
					this.renderBottomFace(var1, 0.0D, 0.0D, 0.0D, var1.getBlockTextureFromSide(0));
					var3.draw();
					var3.startDrawingQuads();
					var3.setNormal(0.0F, 1.0F, 0.0F);
					this.renderTopFace(var1, 0.0D, 0.0D, 0.0D, var1.getBlockTextureFromSide(1));
					var3.draw();
					var3.startDrawingQuads();
					var3.setNormal(0.0F, 0.0F, -1.0F);
					this.renderEastFace(var1, 0.0D, 0.0D, 0.0D, var1.getBlockTextureFromSide(2));
					var3.draw();
					var3.startDrawingQuads();
					var3.setNormal(0.0F, 0.0F, 1.0F);
					this.renderWestFace(var1, 0.0D, 0.0D, 0.0D, var1.getBlockTextureFromSide(3));
					var3.draw();
					var3.startDrawingQuads();
					var3.setNormal(-1.0F, 0.0F, 0.0F);
					this.renderNorthFace(var1, 0.0D, 0.0D, 0.0D, var1.getBlockTextureFromSide(4));
					var3.draw();
					var3.startDrawingQuads();
					var3.setNormal(1.0F, 0.0F, 0.0F);
					this.renderSouthFace(var1, 0.0D, 0.0D, 0.0D, var1.getBlockTextureFromSide(5));
					var3.draw();
					GL11.glTranslatef(0.5F, 0.5F, 0.5F);
				}

				var1.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
			}
		}

	}

	public static boolean renderItemIn3d(int var0) {
		return var0 == 0 ? true : (var0 == 13 ? true : (var0 == 10 ? true : var0 == 11));
	}
}
