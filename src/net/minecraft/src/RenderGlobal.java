package net.minecraft.src;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.ARBOcclusionQuery;
import org.lwjgl.opengl.GL11;

public class RenderGlobal implements IWorldAccess {
	public List tileEntities = new ArrayList();
	private World theWorld;
	private RenderEngine renderEngine;
	private List worldRenderersToUpdate = new ArrayList();
	private WorldRenderer[] sortedWorldRenderers;
	private WorldRenderer[] worldRenderers;
	private int renderChunksWide;
	private int renderChunksTall;
	private int renderChunksDeep;
	private int glRenderListBase;
	private Minecraft mc;
	private RenderBlocks globalRenderBlocks;
	private IntBuffer glOcclusionQueryBase;
	private boolean occlusionEnabled = false;
	private int cloudTickCounter = 0;
	private int starGLCallList;
	private int glSkyList;
	private int glSkyList2;
	private int minBlockX;
	private int minBlockY;
	private int minBlockZ;
	private int maxBlockX;
	private int maxBlockY;
	private int maxBlockZ;
	private int renderDistance = -1;
	private int renderEntitiesStartupCounter = 2;
	private int countEntitiesTotal;
	private int countEntitiesRendered;
	private int countEntitiesHidden;
	int[] dummyBuf50k = new int['\uc350'];
	IntBuffer occlusionResult = GLAllocation.createDirectIntBuffer(64);
	private int renderersLoaded;
	private int renderersBeingClipped;
	private int renderersBeingOccluded;
	private int renderersBeingRendered;
	private int renderersSkippingRenderPass;
	private List glRenderLists = new ArrayList();
	private RenderList[] allRenderLists = new RenderList[]{new RenderList(), new RenderList(), new RenderList(), new RenderList()};
	int dummyRenderInt = 0;
	int unusedGLCallList = GLAllocation.generateDisplayLists(1);
	double prevSortX = -9999.0D;
	double prevSortY = -9999.0D;
	double prevSortZ = -9999.0D;
	public float damagePartialTime;
	int frustumCheckOffset = 0;

	public RenderGlobal(Minecraft var1, RenderEngine var2) {
		this.mc = var1;
		this.renderEngine = var2;
		this.glRenderListBase = GLAllocation.generateDisplayLists(786432);
		this.occlusionEnabled = var1.getOpenGlCapsChecker().checkARBOcclusion();
		if(this.occlusionEnabled) {
			this.occlusionResult.clear();
			(this.glOcclusionQueryBase = GLAllocation.createDirectIntBuffer(262144)).clear();
			this.glOcclusionQueryBase.position(0);
			this.glOcclusionQueryBase.limit(262144);
			ARBOcclusionQuery.glGenQueriesARB(this.glOcclusionQueryBase);
		}

		this.starGLCallList = GLAllocation.generateDisplayLists(3);
		GL11.glPushMatrix();
		GL11.glNewList(this.starGLCallList, GL11.GL_COMPILE);
		this.renderStars();
		GL11.glEndList();
		GL11.glPopMatrix();
		Tessellator var4 = Tessellator.instance;
		GL11.glNewList(this.glSkyList = this.starGLCallList + 1, GL11.GL_COMPILE);

		int var9;
		for(int var8 = -384; var8 <= 384; var8 += 64) {
			for(var9 = -384; var9 <= 384; var9 += 64) {
				var4.startDrawingQuads();
				var4.addVertex((double)(var8 + 0), 16.0D, (double)(var9 + 0));
				var4.addVertex((double)(var8 + 64), 16.0D, (double)(var9 + 0));
				var4.addVertex((double)(var8 + 64), 16.0D, (double)(var9 + 64));
				var4.addVertex((double)(var8 + 0), 16.0D, (double)(var9 + 64));
				var4.draw();
			}
		}

		GL11.glEndList();
		GL11.glNewList(this.glSkyList2 = this.starGLCallList + 2, GL11.GL_COMPILE);
		var4.startDrawingQuads();

		for(var9 = -384; var9 <= 384; var9 += 64) {
			for(int var10 = -384; var10 <= 384; var10 += 64) {
				var4.addVertex((double)(var9 + 64), -16.0D, (double)(var10 + 0));
				var4.addVertex((double)(var9 + 0), -16.0D, (double)(var10 + 0));
				var4.addVertex((double)(var9 + 0), -16.0D, (double)(var10 + 64));
				var4.addVertex((double)(var9 + 64), -16.0D, (double)(var10 + 64));
			}
		}

		var4.draw();
		GL11.glEndList();
	}

	private void renderStars() {
		Random var1 = new Random(10842L);
		Tessellator var2 = Tessellator.instance;
		var2.startDrawingQuads();

		for(int var3 = 0; var3 < 1500; ++var3) {
			double var4 = (double)(var1.nextFloat() * 2.0F - 1.0F);
			double var6 = (double)(var1.nextFloat() * 2.0F - 1.0F);
			double var8 = (double)(var1.nextFloat() * 2.0F - 1.0F);
			double var10 = (double)(0.25F + var1.nextFloat() * 0.25F);
			double var12 = var4 * var4 + var6 * var6 + var8 * var8;
			if(var12 < 1.0D && var12 > 0.01D) {
				double var14 = 1.0D / Math.sqrt(var12);
				double var16 = var4 * var14;
				double var18 = var6 * var14;
				double var20 = var8 * var14;
				double var22 = var16 * 100.0D;
				double var24 = var18 * 100.0D;
				double var26 = var20 * 100.0D;
				double var28 = Math.atan2(var16, var20);
				double var30 = Math.sin(var28);
				double var32 = Math.cos(var28);
				double var34 = Math.atan2(Math.sqrt(var16 * var16 + var20 * var20), var18);
				double var36 = Math.sin(var34);
				double var38 = Math.cos(var34);
				double var40 = var1.nextDouble() * Math.PI * 2.0D;
				double var42 = Math.sin(var40);
				double var44 = Math.cos(var40);

				for(int var46 = 0; var46 < 4; ++var46) {
					double var49 = (double)((var46 & 2) - 1) * var10;
					double var51 = (double)((var46 + 1 & 2) - 1) * var10;
					double var55 = var49 * var44 - var51 * var42;
					double var57 = var51 * var44 + var49 * var42;
					double var59 = var55 * var36 + 0.0D * var38;
					double var61 = 0.0D * var36 - var55 * var38;
					var2.addVertex(var22 + (var61 * var30 - var57 * var32), var24 + var59, var26 + var57 * var30 + var61 * var32);
				}
			}
		}

		var2.draw();
	}

	public void changeWorld(World var1) {
		if(this.theWorld != null) {
			this.theWorld.removeWorldAccess(this);
		}

		this.prevSortX = -9999.0D;
		this.prevSortY = -9999.0D;
		this.prevSortZ = -9999.0D;
		RenderManager.instance.set(var1);
		this.theWorld = var1;
		this.globalRenderBlocks = new RenderBlocks(var1);
		if(var1 != null) {
			var1.addWorldAccess(this);
			this.loadRenderers();
		}

	}

	public void loadRenderers() {
		Block.leaves.setGraphicsLevel(this.mc.options.fancyGraphics);
		this.renderDistance = this.mc.options.renderDistance;
		int var1;
		if(this.worldRenderers != null) {
			for(var1 = 0; var1 < this.worldRenderers.length; ++var1) {
				this.worldRenderers[var1].stopRendering();
			}
		}

		var1 = 64 << 3 - this.renderDistance;
		if(var1 > 400) {
			var1 = 400;
		}

		this.renderChunksWide = var1 / 16 + 1;
		this.renderChunksTall = 8;
		this.renderChunksDeep = var1 / 16 + 1;
		this.worldRenderers = new WorldRenderer[this.renderChunksWide * this.renderChunksTall * this.renderChunksDeep];
		this.sortedWorldRenderers = new WorldRenderer[this.renderChunksWide * this.renderChunksTall * this.renderChunksDeep];
		int var2 = 0;
		int var3 = 0;
		this.minBlockX = 0;
		this.minBlockY = 0;
		this.minBlockZ = 0;
		this.maxBlockX = this.renderChunksWide;
		this.maxBlockY = this.renderChunksTall;
		this.maxBlockZ = this.renderChunksDeep;

		int var4;
		for(var4 = 0; var4 < this.worldRenderersToUpdate.size(); ++var4) {
			((WorldRenderer)this.worldRenderersToUpdate.get(var4)).needsUpdate = false;
		}

		this.worldRenderersToUpdate.clear();
		this.tileEntities.clear();

		for(var4 = 0; var4 < this.renderChunksWide; ++var4) {
			for(int var5 = 0; var5 < this.renderChunksTall; ++var5) {
				for(int var6 = 0; var6 < this.renderChunksDeep; ++var6) {
					this.worldRenderers[(var6 * this.renderChunksTall + var5) * this.renderChunksWide + var4] = new WorldRenderer(this.theWorld, this.tileEntities, var4 * 16, var5 * 16, var6 * 16, 16, this.glRenderListBase + var2);
					if(this.occlusionEnabled) {
						this.worldRenderers[(var6 * this.renderChunksTall + var5) * this.renderChunksWide + var4].glOcclusionQuery = this.glOcclusionQueryBase.get(var3);
					}

					this.worldRenderers[(var6 * this.renderChunksTall + var5) * this.renderChunksWide + var4].isWaitingOnOcclusionQuery = false;
					this.worldRenderers[(var6 * this.renderChunksTall + var5) * this.renderChunksWide + var4].isVisible = true;
					this.worldRenderers[(var6 * this.renderChunksTall + var5) * this.renderChunksWide + var4].isInFrustum = true;
					this.worldRenderers[(var6 * this.renderChunksTall + var5) * this.renderChunksWide + var4].chunkIndex = var3++;
					this.worldRenderers[(var6 * this.renderChunksTall + var5) * this.renderChunksWide + var4].markDirty();
					this.sortedWorldRenderers[(var6 * this.renderChunksTall + var5) * this.renderChunksWide + var4] = this.worldRenderers[(var6 * this.renderChunksTall + var5) * this.renderChunksWide + var4];
					this.worldRenderersToUpdate.add(this.worldRenderers[(var6 * this.renderChunksTall + var5) * this.renderChunksWide + var4]);
					var2 += 3;
				}
			}
		}

		if(this.theWorld != null) {
			EntityPlayerSP var7 = this.mc.thePlayer;
			this.markRenderersForNewPosition(MathHelper.floor_double(var7.posX), MathHelper.floor_double(var7.posY), MathHelper.floor_double(var7.posZ));
			Arrays.sort(this.sortedWorldRenderers, new EntitySorter(var7));
		}

		this.renderEntitiesStartupCounter = 2;
	}

	public void renderEntities(Vec3D vector, ICamera camera, float renderPartialTick) {
		if(this.renderEntitiesStartupCounter > 0) {
			--this.renderEntitiesStartupCounter;
		} else {
			TileEntityRenderer.instance.cacheActiveRenderInfo(this.theWorld, this.renderEngine, this.mc.fontRenderer, this.mc.thePlayer, renderPartialTick);
			RenderManager.instance.cacheActiveRenderInfo(this.theWorld, this.renderEngine, this.mc.fontRenderer, this.mc.thePlayer, this.mc.options, renderPartialTick);
			this.countEntitiesTotal = 0;
			this.countEntitiesRendered = 0;
			this.countEntitiesHidden = 0;
			EntityPlayerSP var4 = this.mc.thePlayer;
			RenderManager.renderPosX = var4.lastTickPosX + (var4.posX - var4.lastTickPosX) * (double)renderPartialTick;
			RenderManager.renderPosY = var4.lastTickPosY + (var4.posY - var4.lastTickPosY) * (double)renderPartialTick;
			RenderManager.renderPosZ = var4.lastTickPosZ + (var4.posZ - var4.lastTickPosZ) * (double)renderPartialTick;
			TileEntityRenderer.staticPlayerX = var4.lastTickPosX + (var4.posX - var4.lastTickPosX) * (double)renderPartialTick;
			TileEntityRenderer.staticPlayerY = var4.lastTickPosY + (var4.posY - var4.lastTickPosY) * (double)renderPartialTick;
			TileEntityRenderer.staticPlayerZ = var4.lastTickPosZ + (var4.posZ - var4.lastTickPosZ) * (double)renderPartialTick;
			List var5 = this.theWorld.getLoadedEntityList();
			this.countEntitiesTotal = var5.size();

			int var6;
			for(var6 = 0; var6 < var5.size(); ++var6) {
				Entity var7 = (Entity)var5.get(var6);
				if(var7.isInRangeToRenderVec3D(vector) && camera.isBoundingBoxInFrustum(var7.boundingBox) && (var7 != this.mc.thePlayer || this.mc.options.thirdPersonView)) {
					++this.countEntitiesRendered;
					RenderManager.instance.renderEntity(var7, renderPartialTick);
				}
			}

			for(var6 = 0; var6 < this.tileEntities.size(); ++var6) {
				TileEntityRenderer.instance.renderTileEntity((TileEntity)this.tileEntities.get(var6), renderPartialTick);
			}

		}
	}

	public String getDebugInfoRenders() {
		return "C: " + this.renderersBeingRendered + "/" + this.renderersLoaded + ". F: " + this.renderersBeingClipped + ", O: " + this.renderersBeingOccluded + ", E: " + this.renderersSkippingRenderPass;
	}

	public String getDebugInfoEntities() {
		return "E: " + this.countEntitiesRendered + "/" + this.countEntitiesTotal + ". B: " + this.countEntitiesHidden + ", I: " + (this.countEntitiesTotal - this.countEntitiesHidden - this.countEntitiesRendered);
	}

	private void markRenderersForNewPosition(int var1, int var2, int var3) {
		var1 -= 8;
		var2 -= 8;
		var3 -= 8;
		this.minBlockX = Integer.MAX_VALUE;
		this.minBlockY = Integer.MAX_VALUE;
		this.minBlockZ = Integer.MAX_VALUE;
		this.maxBlockX = Integer.MIN_VALUE;
		this.maxBlockY = Integer.MIN_VALUE;
		this.maxBlockZ = Integer.MIN_VALUE;
		int var4 = this.renderChunksWide * 16;
		int var5 = var4 / 2;

		for(int var6 = 0; var6 < this.renderChunksWide; ++var6) {
			int var7 = var6 * 16;
			int var8 = var7 + var5 - var1;
			if(var8 < 0) {
				var8 -= var4 - 1;
			}

			int var9 = var7 - var8 / var4 * var4;
			if(var9 < this.minBlockX) {
				this.minBlockX = var9;
			}

			if(var9 > this.maxBlockX) {
				this.maxBlockX = var9;
			}

			for(int var10 = 0; var10 < this.renderChunksDeep; ++var10) {
				int var11 = var10 * 16;
				int var12 = var11 + var5 - var3;
				if(var12 < 0) {
					var12 -= var4 - 1;
				}

				int var13 = var11 - var12 / var4 * var4;
				if(var13 < this.minBlockZ) {
					this.minBlockZ = var13;
				}

				if(var13 > this.maxBlockZ) {
					this.maxBlockZ = var13;
				}

				for(int var14 = 0; var14 < this.renderChunksTall; ++var14) {
					int var15 = var14 * 16;
					if(var15 < this.minBlockY) {
						this.minBlockY = var15;
					}

					if(var15 > this.maxBlockY) {
						this.maxBlockY = var15;
					}

					WorldRenderer var16 = this.worldRenderers[(var10 * this.renderChunksTall + var14) * this.renderChunksWide + var6];
					boolean var17 = var16.needsUpdate;
					var16.setPosition(var9, var15, var13);
					if(!var17 && var16.needsUpdate) {
						this.worldRenderersToUpdate.add(var16);
					}
				}
			}
		}

	}

	public int sortAndRender(EntityPlayer var1, int var2, double var3) {
		if(this.mc.options.renderDistance != this.renderDistance) {
			this.loadRenderers();
		}

		if(var2 == 0) {
			this.renderersLoaded = 0;
			this.renderersBeingClipped = 0;
			this.renderersBeingOccluded = 0;
			this.renderersBeingRendered = 0;
			this.renderersSkippingRenderPass = 0;
		}

		double var5 = var1.lastTickPosX + (var1.posX - var1.lastTickPosX) * var3;
		double var7 = var1.lastTickPosY + (var1.posY - var1.lastTickPosY) * var3;
		double var9 = var1.lastTickPosZ + (var1.posZ - var1.lastTickPosZ) * var3;
		double var11 = var1.posX - this.prevSortX;
		double var13 = var1.posY - this.prevSortY;
		double var15 = var1.posZ - this.prevSortZ;
		if(var11 * var11 + var13 * var13 + var15 * var15 > 16.0D) {
			this.prevSortX = var1.posX;
			this.prevSortY = var1.posY;
			this.prevSortZ = var1.posZ;
			this.markRenderersForNewPosition(MathHelper.floor_double(var1.posX), MathHelper.floor_double(var1.posY), MathHelper.floor_double(var1.posZ));
			Arrays.sort(this.sortedWorldRenderers, new EntitySorter(var1));
		}

		int var18;
		if(this.occlusionEnabled && !this.mc.options.anaglyph && var2 == 0) {
			int var20 = 16;
			this.checkOcclusionQueryResult(0, var20);

			int var21;
			for(var21 = 0; var21 < var20; ++var21) {
				this.sortedWorldRenderers[var21].isVisible = true;
			}

			var18 = 0 + this.renderSortedRenderers(0, var20, var2, var3);

			do {
				var21 = var20;
				var20 *= 2;
				if(var20 > this.sortedWorldRenderers.length) {
					var20 = this.sortedWorldRenderers.length;
				}

				GL11.glDisable(GL11.GL_TEXTURE_2D);
				GL11.glDisable(GL11.GL_LIGHTING);
				GL11.glDisable(GL11.GL_ALPHA_TEST);
				GL11.glDisable(GL11.GL_FOG);
				GL11.glColorMask(false, false, false, false);
				GL11.glDepthMask(false);
				this.checkOcclusionQueryResult(var21, var20);
				GL11.glPushMatrix();
				float var22 = 0.0F;
				float var23 = 0.0F;
				float var24 = 0.0F;

				for(int var25 = var21; var25 < var20; ++var25) {
					if(this.sortedWorldRenderers[var25].skipAllRenderPasses()) {
						this.sortedWorldRenderers[var25].isInFrustum = false;
					} else {
						if(!this.sortedWorldRenderers[var25].isInFrustum) {
							this.sortedWorldRenderers[var25].isVisible = true;
						}

						if(this.sortedWorldRenderers[var25].isInFrustum && !this.sortedWorldRenderers[var25].isWaitingOnOcclusionQuery) {
							int var26 = (int)(1.0F + MathHelper.sqrt_float(this.sortedWorldRenderers[var25].distanceToEntitySquared(var1)) / 128.0F);
							if(this.cloudTickCounter % var26 == var25 % var26) {
								WorldRenderer var27 = this.sortedWorldRenderers[var25];
								float var28 = (float)((double)var27.posXMinus - var5);
								float var29 = (float)((double)var27.posYMinus - var7);
								float var30 = (float)((double)var27.posZMinus - var9);
								float var31 = var28 - var22;
								float var32 = var29 - var23;
								float var33 = var30 - var24;
								if(var31 != 0.0F || var32 != 0.0F || var33 != 0.0F) {
									GL11.glTranslatef(var31, var32, var33);
									var22 += var31;
									var23 += var32;
									var24 += var33;
								}

								ARBOcclusionQuery.glBeginQueryARB('\u8914', this.sortedWorldRenderers[var25].glOcclusionQuery);
								this.sortedWorldRenderers[var25].callOcclusionQueryList();
								ARBOcclusionQuery.glEndQueryARB('\u8914');
								this.sortedWorldRenderers[var25].isWaitingOnOcclusionQuery = true;
							}
						}
					}
				}

				GL11.glPopMatrix();
				GL11.glColorMask(true, true, true, true);
				GL11.glDepthMask(true);
				GL11.glEnable(GL11.GL_TEXTURE_2D);
				GL11.glEnable(GL11.GL_ALPHA_TEST);
				GL11.glEnable(GL11.GL_FOG);
				var18 += this.renderSortedRenderers(var21, var20, var2, var3);
			} while(var20 < this.sortedWorldRenderers.length);
		} else {
			var18 = 0 + this.renderSortedRenderers(0, this.sortedWorldRenderers.length, var2, var3);
		}

		return var18;
	}

	private void checkOcclusionQueryResult(int var1, int var2) {
		for(int var3 = var1; var3 < var2; ++var3) {
			if(this.sortedWorldRenderers[var3].isWaitingOnOcclusionQuery) {
				this.occlusionResult.clear();
				ARBOcclusionQuery.glGetQueryObjectuARB(this.sortedWorldRenderers[var3].glOcclusionQuery, '\u8867', this.occlusionResult);
				if(this.occlusionResult.get(0) != 0) {
					this.sortedWorldRenderers[var3].isWaitingOnOcclusionQuery = false;
					this.occlusionResult.clear();
					ARBOcclusionQuery.glGetQueryObjectuARB(this.sortedWorldRenderers[var3].glOcclusionQuery, '\u8866', this.occlusionResult);
					this.sortedWorldRenderers[var3].isVisible = this.occlusionResult.get(0) != 0;
				}
			}
		}

	}

	private int renderSortedRenderers(int var1, int var2, int var3, double var4) {
		this.glRenderLists.clear();
		int var6 = 0;

		for(int var7 = var1; var7 < var2; ++var7) {
			if(var3 == 0) {
				++this.renderersLoaded;
				if(this.sortedWorldRenderers[var7].skipRenderPass[var3]) {
					++this.renderersSkippingRenderPass;
				} else if(!this.sortedWorldRenderers[var7].isInFrustum) {
					++this.renderersBeingClipped;
				} else if(this.occlusionEnabled && !this.sortedWorldRenderers[var7].isVisible) {
					++this.renderersBeingOccluded;
				} else {
					++this.renderersBeingRendered;
				}
			}

			if(!this.sortedWorldRenderers[var7].skipRenderPass[var3] && this.sortedWorldRenderers[var7].isInFrustum && this.sortedWorldRenderers[var7].isVisible && this.sortedWorldRenderers[var7].getGLCallListForPass(var3) >= 0) {
				this.glRenderLists.add(this.sortedWorldRenderers[var7]);
				++var6;
			}
		}

		EntityPlayerSP var19 = this.mc.thePlayer;
		double var8 = var19.lastTickPosX + (var19.posX - var19.lastTickPosX) * var4;
		double var10 = var19.lastTickPosY + (var19.posY - var19.lastTickPosY) * var4;
		double var12 = var19.lastTickPosZ + (var19.posZ - var19.lastTickPosZ) * var4;
		int var14 = 0;

		int var15;
		for(var15 = 0; var15 < this.allRenderLists.length; ++var15) {
			this.allRenderLists[var15].reset();
		}

		for(var15 = 0; var15 < this.glRenderLists.size(); ++var15) {
			WorldRenderer var16 = (WorldRenderer)this.glRenderLists.get(var15);
			int var17 = -1;

			for(int var18 = 0; var18 < var14; ++var18) {
				if(this.allRenderLists[var18].isRenderedAt(var16.posXMinus, var16.posYMinus, var16.posZMinus)) {
					var17 = var18;
				}
			}

			if(var17 < 0) {
				var17 = var14++;
				this.allRenderLists[var17].setLocation(var16.posXMinus, var16.posYMinus, var16.posZMinus, var8, var10, var12);
			}

			this.allRenderLists[var17].render(var16.getGLCallListForPass(var3));
		}

		this.renderAllRenderLists(var3, var4);
		return var6;
	}

	public void renderAllRenderLists(int var1, double var2) {
		for(int var4 = 0; var4 < this.allRenderLists.length; ++var4) {
			this.allRenderLists[var4].render();
		}

	}

	public void updateClouds() {
		++this.cloudTickCounter;
	}

	public void renderSky(float renderPartialTick) {
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		Vec3D var2 = this.theWorld.getSkyColor(renderPartialTick);
		float var3 = (float)var2.xCoord;
		float var4 = (float)var2.yCoord;
		float var5 = (float)var2.zCoord;
		if(this.mc.options.anaglyph) {
			float var6 = (var3 * 30.0F + var4 * 59.0F + var5 * 11.0F) / 100.0F;
			float var7 = (var3 * 30.0F + var4 * 70.0F) / 100.0F;
			float var8 = (var3 * 30.0F + var5 * 70.0F) / 100.0F;
			var3 = var6;
			var4 = var7;
			var5 = var8;
		}

		GL11.glColor3f(var3, var4, var5);
		Tessellator var13 = Tessellator.instance;
		GL11.glDepthMask(false);
		GL11.glEnable(GL11.GL_FOG);
		GL11.glColor3f(var3, var4, var5);
		GL11.glCallList(this.glSkyList);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_FOG);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
		GL11.glPushMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glTranslatef(0.0F, 0.0F, 0.0F);
		GL11.glRotatef(0.0F, 0.0F, 0.0F, 1.0F);
		GL11.glRotatef(this.theWorld.getCelestialAngle(renderPartialTick) * 360.0F, 1.0F, 0.0F, 0.0F);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.renderEngine.getTexture("/terrain/sun.png"));
		var13.startDrawingQuads();
		var13.addVertexWithUV(-30.0D, 100.0D, -30.0D, 0.0D, 0.0D);
		var13.addVertexWithUV(30.0D, 100.0D, -30.0D, 1.0D, 0.0D);
		var13.addVertexWithUV(30.0D, 100.0D, 30.0D, 1.0D, 1.0D);
		var13.addVertexWithUV(-30.0D, 100.0D, 30.0D, 0.0D, 1.0D);
		var13.draw();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.renderEngine.getTexture("/terrain/moon.png"));
		var13.startDrawingQuads();
		var13.addVertexWithUV(-20.0D, -100.0D, 20.0D, 1.0D, 1.0D);
		var13.addVertexWithUV(20.0D, -100.0D, 20.0D, 0.0D, 1.0D);
		var13.addVertexWithUV(20.0D, -100.0D, -20.0D, 0.0D, 0.0D);
		var13.addVertexWithUV(-20.0D, -100.0D, -20.0D, 1.0D, 0.0D);
		var13.draw();
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		float var12 = this.theWorld.getStarBrightness(renderPartialTick);
		if(var12 > 0.0F) {
			GL11.glColor4f(var12, var12, var12, var12);
			GL11.glCallList(this.starGLCallList);
		}

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glEnable(GL11.GL_FOG);
		GL11.glPopMatrix();
		GL11.glColor3f(var3 * 0.2F + 0.04F, var4 * 0.2F + 0.04F, var5 * 0.6F + 0.1F);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glCallList(this.glSkyList2);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDepthMask(true);
	}

	public void renderClouds(float var1) {
		if(this.mc.options.fancyGraphics) {
			this.renderCloudsFancy(var1);
		} else {
			GL11.glDisable(GL11.GL_CULL_FACE);
			float var2 = (float)(this.mc.thePlayer.lastTickPosY + (this.mc.thePlayer.posY - this.mc.thePlayer.lastTickPosY) * (double)var1);
			Tessellator var5 = Tessellator.instance;
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.renderEngine.getTexture("/clouds.png"));
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			Vec3D var6 = this.theWorld.getCloudColor(var1);
			float var7 = (float)var6.xCoord;
			float var8 = (float)var6.yCoord;
			float var9 = (float)var6.zCoord;
			if(this.mc.options.anaglyph) {
				float var10 = (var7 * 30.0F + var8 * 59.0F + var9 * 11.0F) / 100.0F;
				float var11 = (var7 * 30.0F + var8 * 70.0F) / 100.0F;
				float var12 = (var7 * 30.0F + var9 * 70.0F) / 100.0F;
				var7 = var10;
				var8 = var11;
				var9 = var12;
			}

			double var26 = this.mc.thePlayer.prevPosX + (this.mc.thePlayer.posX - this.mc.thePlayer.prevPosX) * (double)var1 + (double)(((float)this.cloudTickCounter + var1) * 0.03F);
			double var13 = this.mc.thePlayer.prevPosZ + (this.mc.thePlayer.posZ - this.mc.thePlayer.prevPosZ) * (double)var1;
			int var15 = MathHelper.floor_double(var26 / 2048.0D);
			int var16 = MathHelper.floor_double(var13 / 2048.0D);
			double var17 = var26 - (double)(var15 * 2048);
			double var19 = var13 - (double)(var16 * 2048);
			float var21 = 120.0F - var2 + 0.33F;
			float var22 = (float)(var17 * 4.8828125E-4D);
			float var23 = (float)(var19 * 4.8828125E-4D);
			var5.startDrawingQuads();
			var5.setColorRGBA_F(var7, var8, var9, 0.8F);

			for(int var24 = -256; var24 < 256; var24 += 32) {
				for(int var25 = -256; var25 < 256; var25 += 32) {
					var5.addVertexWithUV((double)(var24 + 0), (double)var21, (double)(var25 + 32), (double)((float)(var24 + 0) * 4.8828125E-4F + var22), (double)((float)(var25 + 32) * 4.8828125E-4F + var23));
					var5.addVertexWithUV((double)(var24 + 32), (double)var21, (double)(var25 + 32), (double)((float)(var24 + 32) * 4.8828125E-4F + var22), (double)((float)(var25 + 32) * 4.8828125E-4F + var23));
					var5.addVertexWithUV((double)(var24 + 32), (double)var21, (double)(var25 + 0), (double)((float)(var24 + 32) * 4.8828125E-4F + var22), (double)((float)(var25 + 0) * 4.8828125E-4F + var23));
					var5.addVertexWithUV((double)(var24 + 0), (double)var21, (double)(var25 + 0), (double)((float)(var24 + 0) * 4.8828125E-4F + var22), (double)((float)(var25 + 0) * 4.8828125E-4F + var23));
				}
			}

			var5.draw();
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glEnable(GL11.GL_CULL_FACE);
		}
	}

	public void renderCloudsFancy(float var1) {
		GL11.glDisable(GL11.GL_CULL_FACE);
		float var2 = (float)(this.mc.thePlayer.lastTickPosY + (this.mc.thePlayer.posY - this.mc.thePlayer.lastTickPosY) * (double)var1);
		Tessellator var3 = Tessellator.instance;
		double var6 = (this.mc.thePlayer.prevPosX + (this.mc.thePlayer.posX - this.mc.thePlayer.prevPosX) * (double)var1 + (double)(((float)this.cloudTickCounter + var1) * 0.03F)) / 12.0D;
		double var8 = (this.mc.thePlayer.prevPosZ + (this.mc.thePlayer.posZ - this.mc.thePlayer.prevPosZ) * (double)var1) / 12.0D + (double)0.33F;
		float var10 = 108.0F - var2 + 0.33F;
		int var11 = MathHelper.floor_double(var6 / 2048.0D);
		int var12 = MathHelper.floor_double(var8 / 2048.0D);
		double var13 = var6 - (double)(var11 * 2048);
		double var15 = var8 - (double)(var12 * 2048);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.renderEngine.getTexture("/clouds.png"));
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		Vec3D var17 = this.theWorld.getCloudColor(var1);
		float var18 = (float)var17.xCoord;
		float var19 = (float)var17.yCoord;
		float var20 = (float)var17.zCoord;
		float var21;
		float var22;
		if(this.mc.options.anaglyph) {
			var21 = (var18 * 30.0F + var19 * 59.0F + var20 * 11.0F) / 100.0F;
			var22 = (var18 * 30.0F + var19 * 70.0F) / 100.0F;
			float var23 = (var18 * 30.0F + var20 * 70.0F) / 100.0F;
			var18 = var21;
			var19 = var22;
			var20 = var23;
		}

		var21 = (float)(var13 * 0.0D);
		var22 = (float)(var15 * 0.0D);
		float var24 = (float)MathHelper.floor_double(var13) * 0.00390625F;
		float var25 = (float)MathHelper.floor_double(var15) * 0.00390625F;
		float var26 = (float)(var13 - (double)MathHelper.floor_double(var13));
		float var27 = (float)(var15 - (double)MathHelper.floor_double(var15));
		GL11.glScalef(12.0F, 1.0F, 12.0F);

		for(int var31 = 0; var31 < 2; ++var31) {
			if(var31 == 0) {
				GL11.glColorMask(false, false, false, false);
			} else {
				GL11.glColorMask(true, true, true, true);
			}

			for(int var32 = -2; var32 <= 3; ++var32) {
				for(int var33 = -2; var33 <= 3; ++var33) {
					var3.startDrawingQuads();
					float var34 = (float)(var32 * 8);
					float var35 = (float)(var33 * 8);
					float var36 = var34 - var26;
					float var37 = var35 - var27;
					if(var10 > -5.0F) {
						var3.setColorRGBA_F(var18 * 0.7F, var19 * 0.7F, var20 * 0.7F, 0.8F);
						var3.setNormal(0.0F, -1.0F, 0.0F);
						var3.addVertexWithUV((double)(var36 + 0.0F), (double)(var10 + 0.0F), (double)(var37 + 8.0F), (double)((var34 + 0.0F) * 0.00390625F + var24), (double)((var35 + 8.0F) * 0.00390625F + var25));
						var3.addVertexWithUV((double)(var36 + 8.0F), (double)(var10 + 0.0F), (double)(var37 + 8.0F), (double)((var34 + 8.0F) * 0.00390625F + var24), (double)((var35 + 8.0F) * 0.00390625F + var25));
						var3.addVertexWithUV((double)(var36 + 8.0F), (double)(var10 + 0.0F), (double)(var37 + 0.0F), (double)((var34 + 8.0F) * 0.00390625F + var24), (double)((var35 + 0.0F) * 0.00390625F + var25));
						var3.addVertexWithUV((double)(var36 + 0.0F), (double)(var10 + 0.0F), (double)(var37 + 0.0F), (double)((var34 + 0.0F) * 0.00390625F + var24), (double)((var35 + 0.0F) * 0.00390625F + var25));
					}

					if(var10 <= 5.0F) {
						var3.setColorRGBA_F(var18, var19, var20, 0.8F);
						var3.setNormal(0.0F, 1.0F, 0.0F);
						var3.addVertexWithUV((double)(var36 + 0.0F), (double)(var10 + 4.0F - 9.765625E-4F), (double)(var37 + 8.0F), (double)((var34 + 0.0F) * 0.00390625F + var24), (double)((var35 + 8.0F) * 0.00390625F + var25));
						var3.addVertexWithUV((double)(var36 + 8.0F), (double)(var10 + 4.0F - 9.765625E-4F), (double)(var37 + 8.0F), (double)((var34 + 8.0F) * 0.00390625F + var24), (double)((var35 + 8.0F) * 0.00390625F + var25));
						var3.addVertexWithUV((double)(var36 + 8.0F), (double)(var10 + 4.0F - 9.765625E-4F), (double)(var37 + 0.0F), (double)((var34 + 8.0F) * 0.00390625F + var24), (double)((var35 + 0.0F) * 0.00390625F + var25));
						var3.addVertexWithUV((double)(var36 + 0.0F), (double)(var10 + 4.0F - 9.765625E-4F), (double)(var37 + 0.0F), (double)((var34 + 0.0F) * 0.00390625F + var24), (double)((var35 + 0.0F) * 0.00390625F + var25));
					}

					var3.setColorRGBA_F(var18 * 0.9F, var19 * 0.9F, var20 * 0.9F, 0.8F);
					int var38;
					if(var32 > -1) {
						var3.setNormal(-1.0F, 0.0F, 0.0F);

						for(var38 = 0; var38 < 8; ++var38) {
							var3.addVertexWithUV((double)(var36 + (float)var38 + 0.0F), (double)(var10 + 0.0F), (double)(var37 + 8.0F), (double)((var34 + (float)var38 + 0.5F) * 0.00390625F + var24), (double)((var35 + 8.0F) * 0.00390625F + var25));
							var3.addVertexWithUV((double)(var36 + (float)var38 + 0.0F), (double)(var10 + 4.0F), (double)(var37 + 8.0F), (double)((var34 + (float)var38 + 0.5F) * 0.00390625F + var24), (double)((var35 + 8.0F) * 0.00390625F + var25));
							var3.addVertexWithUV((double)(var36 + (float)var38 + 0.0F), (double)(var10 + 4.0F), (double)(var37 + 0.0F), (double)((var34 + (float)var38 + 0.5F) * 0.00390625F + var24), (double)((var35 + 0.0F) * 0.00390625F + var25));
							var3.addVertexWithUV((double)(var36 + (float)var38 + 0.0F), (double)(var10 + 0.0F), (double)(var37 + 0.0F), (double)((var34 + (float)var38 + 0.5F) * 0.00390625F + var24), (double)((var35 + 0.0F) * 0.00390625F + var25));
						}
					}

					if(var32 <= 1) {
						var3.setNormal(1.0F, 0.0F, 0.0F);

						for(var38 = 0; var38 < 8; ++var38) {
							var3.addVertexWithUV((double)(var36 + (float)var38 + 1.0F - 9.765625E-4F), (double)(var10 + 0.0F), (double)(var37 + 8.0F), (double)((var34 + (float)var38 + 0.5F) * 0.00390625F + var24), (double)((var35 + 8.0F) * 0.00390625F + var25));
							var3.addVertexWithUV((double)(var36 + (float)var38 + 1.0F - 9.765625E-4F), (double)(var10 + 4.0F), (double)(var37 + 8.0F), (double)((var34 + (float)var38 + 0.5F) * 0.00390625F + var24), (double)((var35 + 8.0F) * 0.00390625F + var25));
							var3.addVertexWithUV((double)(var36 + (float)var38 + 1.0F - 9.765625E-4F), (double)(var10 + 4.0F), (double)(var37 + 0.0F), (double)((var34 + (float)var38 + 0.5F) * 0.00390625F + var24), (double)((var35 + 0.0F) * 0.00390625F + var25));
							var3.addVertexWithUV((double)(var36 + (float)var38 + 1.0F - 9.765625E-4F), (double)(var10 + 0.0F), (double)(var37 + 0.0F), (double)((var34 + (float)var38 + 0.5F) * 0.00390625F + var24), (double)((var35 + 0.0F) * 0.00390625F + var25));
						}
					}

					var3.setColorRGBA_F(var18 * 0.8F, var19 * 0.8F, var20 * 0.8F, 0.8F);
					if(var33 > -1) {
						var3.setNormal(0.0F, 0.0F, -1.0F);

						for(var38 = 0; var38 < 8; ++var38) {
							var3.addVertexWithUV((double)(var36 + 0.0F), (double)(var10 + 4.0F), (double)(var37 + (float)var38 + 0.0F), (double)((var34 + 0.0F) * 0.00390625F + var24), (double)((var35 + (float)var38 + 0.5F) * 0.00390625F + var25));
							var3.addVertexWithUV((double)(var36 + 8.0F), (double)(var10 + 4.0F), (double)(var37 + (float)var38 + 0.0F), (double)((var34 + 8.0F) * 0.00390625F + var24), (double)((var35 + (float)var38 + 0.5F) * 0.00390625F + var25));
							var3.addVertexWithUV((double)(var36 + 8.0F), (double)(var10 + 0.0F), (double)(var37 + (float)var38 + 0.0F), (double)((var34 + 8.0F) * 0.00390625F + var24), (double)((var35 + (float)var38 + 0.5F) * 0.00390625F + var25));
							var3.addVertexWithUV((double)(var36 + 0.0F), (double)(var10 + 0.0F), (double)(var37 + (float)var38 + 0.0F), (double)((var34 + 0.0F) * 0.00390625F + var24), (double)((var35 + (float)var38 + 0.5F) * 0.00390625F + var25));
						}
					}

					if(var33 <= 1) {
						var3.setNormal(0.0F, 0.0F, 1.0F);

						for(var38 = 0; var38 < 8; ++var38) {
							var3.addVertexWithUV((double)(var36 + 0.0F), (double)(var10 + 4.0F), (double)(var37 + (float)var38 + 1.0F - 9.765625E-4F), (double)((var34 + 0.0F) * 0.00390625F + var24), (double)((var35 + (float)var38 + 0.5F) * 0.00390625F + var25));
							var3.addVertexWithUV((double)(var36 + 8.0F), (double)(var10 + 4.0F), (double)(var37 + (float)var38 + 1.0F - 9.765625E-4F), (double)((var34 + 8.0F) * 0.00390625F + var24), (double)((var35 + (float)var38 + 0.5F) * 0.00390625F + var25));
							var3.addVertexWithUV((double)(var36 + 8.0F), (double)(var10 + 0.0F), (double)(var37 + (float)var38 + 1.0F - 9.765625E-4F), (double)((var34 + 8.0F) * 0.00390625F + var24), (double)((var35 + (float)var38 + 0.5F) * 0.00390625F + var25));
							var3.addVertexWithUV((double)(var36 + 0.0F), (double)(var10 + 0.0F), (double)(var37 + (float)var38 + 1.0F - 9.765625E-4F), (double)((var34 + 0.0F) * 0.00390625F + var24), (double)((var35 + (float)var38 + 0.5F) * 0.00390625F + var25));
						}
					}

					var3.draw();
				}
			}
		}

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_CULL_FACE);
	}

	public boolean updateRenderers(EntityPlayer var1, boolean var2) {
		try {
			Collections.sort(this.worldRenderersToUpdate, new RenderSorter(var1));
		} catch (IllegalArgumentException var7) {
			System.out.println("NOT THIS TIME TIMOTHY");
		}

		int var3 = this.worldRenderersToUpdate.size() - 1;
		int var4 = this.worldRenderersToUpdate.size();

		for(int var5 = 0; var5 < var4; ++var5) {
			WorldRenderer var6 = (WorldRenderer)this.worldRenderersToUpdate.get(var3 - var5);
			if(!var2) {
				if(var6.distanceToEntitySquared(var1) > 1024.0F) {
					if(var6.isInFrustum) {
						if(var5 >= 3) {
							return false;
						}
					} else if(var5 >= 1) {
						return false;
					}
				}
			} else if(!var6.isInFrustum) {
				continue;
			}

			var6.updateRenderer();
			this.worldRenderersToUpdate.remove(var6);
			var6.needsUpdate = false;
		}

		return this.worldRenderersToUpdate.size() == 0;
	}

	public void drawBlockBreaking(EntityPlayer var1, MovingObjectPosition var2, int var3, ItemStack var4, float var5) {
		Tessellator var6 = Tessellator.instance;
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, (MathHelper.sin((float)System.currentTimeMillis() / 100.0F) * 0.2F + 0.4F) * 0.5F);
		if(var3 == 0) {
			if(this.damagePartialTime > 0.0F) {
				GL11.glBlendFunc(GL11.GL_DST_COLOR, GL11.GL_SRC_COLOR);
				GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.renderEngine.getTexture("/terrain.png"));
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);
				GL11.glPushMatrix();
				int var7 = this.theWorld.getBlockId(var2.blockX, var2.blockY, var2.blockZ);
				Block var8 = var7 > 0 ? Block.blocksList[var7] : null;
				GL11.glDisable(GL11.GL_ALPHA_TEST);
				GL11.glPolygonOffset(-3.0F, -3.0F);
				GL11.glEnable('\u8037');
				var6.startDrawingQuads();
				var6.setTranslationD(-(var1.lastTickPosX + (var1.posX - var1.lastTickPosX) * (double)var5), -(var1.lastTickPosY + (var1.posY - var1.lastTickPosY) * (double)var5), -(var1.lastTickPosZ + (var1.posZ - var1.lastTickPosZ) * (double)var5));
				var6.disableColor();
				if(var8 == null) {
					var8 = Block.stone;
				}

				this.globalRenderBlocks.renderBlockUsingTexture(var8, var2.blockX, var2.blockY, var2.blockZ, 240 + (int)(this.damagePartialTime * 10.0F));
				var6.draw();
				var6.setTranslationD(0.0D, 0.0D, 0.0D);
				GL11.glPolygonOffset(0.0F, 0.0F);
				GL11.glDisable('\u8037');
				GL11.glEnable(GL11.GL_ALPHA_TEST);
				GL11.glDepthMask(true);
				GL11.glPopMatrix();
			}
		} else if(var4 != null) {
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			float var11 = MathHelper.sin((float)System.currentTimeMillis() / 100.0F) * 0.2F + 0.8F;
			GL11.glColor4f(var11, var11, var11, MathHelper.sin((float)System.currentTimeMillis() / 200.0F) * 0.2F + 0.5F);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.renderEngine.getTexture("/terrain.png"));
			int var12 = var2.blockX;
			int var9 = var2.blockY;
			int var10 = var2.blockZ;
			if(var2.sideHit == 0) {
				--var9;
			}

			if(var2.sideHit == 1) {
				++var9;
			}

			if(var2.sideHit == 2) {
				--var10;
			}

			if(var2.sideHit == 3) {
				++var10;
			}

			if(var2.sideHit == 4) {
				--var12;
			}

			if(var2.sideHit == 5) {
				++var12;
			}
		}

		GL11.glDisable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
	}

	public void drawSelectionBox(EntityPlayer var1, MovingObjectPosition var2, int var3, ItemStack var4, float var5) {
		if(var3 == 0 && var2.typeOfHit == 0) {
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.4F);
			GL11.glLineWidth(2.0F);
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glDepthMask(false);
			int var7 = this.theWorld.getBlockId(var2.blockX, var2.blockY, var2.blockZ);
			if(var7 > 0) {
				Block.blocksList[var7].setBlockBoundsBasedOnState(this.theWorld, var2.blockX, var2.blockY, var2.blockZ);
				this.drawOutlinedBoundingBox(Block.blocksList[var7].getSelectedBoundingBoxFromPool(this.theWorld, var2.blockX, var2.blockY, var2.blockZ).expand(0.0020000000949949026D, 0.0020000000949949026D, 0.0020000000949949026D).getOffsetBoundingBox(-(var1.lastTickPosX + (var1.posX - var1.lastTickPosX) * (double)var5), -(var1.lastTickPosY + (var1.posY - var1.lastTickPosY) * (double)var5), -(var1.lastTickPosZ + (var1.posZ - var1.lastTickPosZ) * (double)var5)));
			}

			GL11.glDepthMask(true);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glDisable(GL11.GL_BLEND);
		}

	}

	private void drawOutlinedBoundingBox(AxisAlignedBB var1) {
		Tessellator var2 = Tessellator.instance;
		var2.startDrawing(3);
		var2.addVertex(var1.minX, var1.minY, var1.minZ);
		var2.addVertex(var1.maxX, var1.minY, var1.minZ);
		var2.addVertex(var1.maxX, var1.minY, var1.maxZ);
		var2.addVertex(var1.minX, var1.minY, var1.maxZ);
		var2.addVertex(var1.minX, var1.minY, var1.minZ);
		var2.draw();
		var2.startDrawing(3);
		var2.addVertex(var1.minX, var1.maxY, var1.minZ);
		var2.addVertex(var1.maxX, var1.maxY, var1.minZ);
		var2.addVertex(var1.maxX, var1.maxY, var1.maxZ);
		var2.addVertex(var1.minX, var1.maxY, var1.maxZ);
		var2.addVertex(var1.minX, var1.maxY, var1.minZ);
		var2.draw();
		var2.startDrawing(1);
		var2.addVertex(var1.minX, var1.minY, var1.minZ);
		var2.addVertex(var1.minX, var1.maxY, var1.minZ);
		var2.addVertex(var1.maxX, var1.minY, var1.minZ);
		var2.addVertex(var1.maxX, var1.maxY, var1.minZ);
		var2.addVertex(var1.maxX, var1.minY, var1.maxZ);
		var2.addVertex(var1.maxX, var1.maxY, var1.maxZ);
		var2.addVertex(var1.minX, var1.minY, var1.maxZ);
		var2.addVertex(var1.minX, var1.maxY, var1.maxZ);
		var2.draw();
	}

	public void markBlocksForUpdate(int var1, int var2, int var3, int var4, int var5, int var6) {
		int var7 = MathHelper.bucketInt(var1, 16);
		int var8 = MathHelper.bucketInt(var2, 16);
		int var9 = MathHelper.bucketInt(var3, 16);
		int var10 = MathHelper.bucketInt(var4, 16);
		int var11 = MathHelper.bucketInt(var5, 16);
		int var12 = MathHelper.bucketInt(var6, 16);

		for(int var13 = var7; var13 <= var10; ++var13) {
			int var14 = var13 % this.renderChunksWide;
			if(var14 < 0) {
				var14 += this.renderChunksWide;
			}

			for(int var15 = var8; var15 <= var11; ++var15) {
				int var16 = var15 % this.renderChunksTall;
				if(var16 < 0) {
					var16 += this.renderChunksTall;
				}

				for(int var17 = var9; var17 <= var12; ++var17) {
					int var18 = var17 % this.renderChunksDeep;
					if(var18 < 0) {
						var18 += this.renderChunksDeep;
					}

					WorldRenderer var19 = this.worldRenderers[(var18 * this.renderChunksTall + var16) * this.renderChunksWide + var14];
					if(!var19.needsUpdate) {
						this.worldRenderersToUpdate.add(var19);
					}

					var19.markDirty();
				}
			}
		}

	}

	public void markBlockAndNeighborsNeedsUpdate(int var1, int var2, int var3) {
		this.markBlocksForUpdate(var1 - 1, var2 - 1, var3 - 1, var1 + 1, var2 + 1, var3 + 1);
	}

	public void markBlockRangeNeedsUpdate(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
		this.markBlocksForUpdate(minX - 1, minY - 1, minZ - 1, maxX + 1, maxY + 1, maxZ + 1);
	}

	public void clipRenderersByFrustum(ICamera var1, float var2) {
		for(int var3 = 0; var3 < this.worldRenderers.length; ++var3) {
			if(!this.worldRenderers[var3].skipAllRenderPasses() && (!this.worldRenderers[var3].isInFrustum || (var3 + this.frustumCheckOffset & 15) == 0)) {
				this.worldRenderers[var3].updateInFrustum(var1);
			}
		}

		++this.frustumCheckOffset;
	}

	public void playRecord(String record, int x, int y, int z) {
		if(record != null) {
			this.mc.ingameGUI.setRecordPlayingMessage("C418 - " + record);
		}

		this.mc.sndManager.playStreaming(record, (float)x, (float)y, (float)z, 1.0F, 1.0F);
	}

	public void playSound(String sound, double posX, double posY, double posZ, float volume, float pitch) {
		float var10 = 16.0F;
		if(volume > 1.0F) {
			var10 *= volume;
		}

		if(this.mc.thePlayer.getDistanceSq(posX, posY, posZ) < (double)(var10 * var10)) {
			this.mc.sndManager.playSound(sound, (float)posX, (float)posY, (float)posZ, volume, pitch);
		}

	}

	public void spawnParticle(String var1, double var2, double var4, double var6, double var8, double var10, double var12) {
		double var14 = this.mc.thePlayer.posX - var2;
		double var16 = this.mc.thePlayer.posY - var4;
		double var18 = this.mc.thePlayer.posZ - var6;
		if(var14 * var14 + var16 * var16 + var18 * var18 <= 256.0D) {
			if(var1 == "bubble") {
				this.mc.effectRenderer.addEffect(new EntityBubbleFX(this.theWorld, var2, var4, var6, var8, var10, var12));
			} else if(var1 == "smoke") {
				this.mc.effectRenderer.addEffect(new EntitySmokeFX(this.theWorld, var2, var4, var6));
			} else if(var1 == "explode") {
				this.mc.effectRenderer.addEffect(new EntityExplodeFX(this.theWorld, var2, var4, var6, var8, var10, var12));
			} else if(var1 == "flame") {
				this.mc.effectRenderer.addEffect(new EntityFlameFX(this.theWorld, var2, var4, var6, var8, var10, var12));
			} else if(var1 == "lava") {
				this.mc.effectRenderer.addEffect(new EntityLavaFX(this.theWorld, var2, var4, var6));
			} else if(var1 == "splash") {
				this.mc.effectRenderer.addEffect(new EntitySplashFX(this.theWorld, var2, var4, var6, var8, var10, var12));
			} else if(var1 == "largesmoke") {
				this.mc.effectRenderer.addEffect(new EntitySmokeFX(this.theWorld, var2, var4, var6, 2.5F));
			} else if(var1 == "reddust") {
				this.mc.effectRenderer.addEffect(new EntityReddustFX(this.theWorld, var2, var4, var6));
			} else if(var1 == "snowballpoof") {
				this.mc.effectRenderer.addEffect(new EntitySlimeFX(this.theWorld, var2, var4, var6, Item.snowball));
			} else if(var1 == "slime") {
				this.mc.effectRenderer.addEffect(new EntitySlimeFX(this.theWorld, var2, var4, var6, Item.slimeBall));
			}

		}
	}

	public void obtainEntitySkin(Entity entity) {
		if(entity.skinUrl != null) {
			this.renderEngine.obtainImageData(entity.skinUrl, new ImageBufferDownload());
		}

	}

	public void releaseEntitySkin(Entity entity) {
		if(entity.skinUrl != null) {
			this.renderEngine.releaseImageData(entity.skinUrl);
		}

	}

	public void updateAllRenderers() {
		for(int var1 = 0; var1 < this.worldRenderers.length; ++var1) {
			if(this.worldRenderers[var1].isChunkLit) {
				if(!this.worldRenderers[var1].needsUpdate) {
					this.worldRenderersToUpdate.add(this.worldRenderers[var1]);
				}

				this.worldRenderers[var1].markDirty();
			}
		}

	}

	public void doNothingWithTileEntity(int x, int y, int z, TileEntity tileEntity) {
	}
}
