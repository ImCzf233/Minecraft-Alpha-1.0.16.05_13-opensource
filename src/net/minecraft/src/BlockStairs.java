package net.minecraft.src;

import java.util.ArrayList;
import java.util.Random;

public class BlockStairs extends Block {
	private Block modelBlock;

	protected BlockStairs(int id, Block modelBlock) {
		super(id, modelBlock.blockIndexInTexture, modelBlock.material);
		this.modelBlock = modelBlock;
		this.setHardness(modelBlock.hardness);
		this.setResistance(modelBlock.resistance / 3.0F);
		this.setStepSound(modelBlock.stepSound);
	}

	public boolean isOpaqueCube() {
		return false;
	}

	public boolean renderAsNormalBlock() {
		return false;
	}

	public int getRenderType() {
		return 10;
	}

	public boolean shouldSideBeRendered(IBlockAccess var1, int var2, int var3, int var4, int var5) {
		return super.shouldSideBeRendered(var1, var2, var3, var4, var5);
	}

	public void getCollidingBoundingBoxes(World var1, int var2, int var3, int var4, AxisAlignedBB var5, ArrayList var6) {
		int var7 = var1.getBlockMetadata(var2, var3, var4);
		if(var7 == 0) {
			this.setBlockBounds(0.0F, 0.0F, 0.0F, 0.5F, 0.5F, 1.0F);
			super.getCollidingBoundingBoxes(var1, var2, var3, var4, var5, var6);
			this.setBlockBounds(0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
			super.getCollidingBoundingBoxes(var1, var2, var3, var4, var5, var6);
		} else if(var7 == 1) {
			this.setBlockBounds(0.0F, 0.0F, 0.0F, 0.5F, 1.0F, 1.0F);
			super.getCollidingBoundingBoxes(var1, var2, var3, var4, var5, var6);
			this.setBlockBounds(0.5F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
			super.getCollidingBoundingBoxes(var1, var2, var3, var4, var5, var6);
		} else if(var7 == 2) {
			this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 0.5F);
			super.getCollidingBoundingBoxes(var1, var2, var3, var4, var5, var6);
			this.setBlockBounds(0.0F, 0.0F, 0.5F, 1.0F, 1.0F, 1.0F);
			super.getCollidingBoundingBoxes(var1, var2, var3, var4, var5, var6);
		} else if(var7 == 3) {
			this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.5F);
			super.getCollidingBoundingBoxes(var1, var2, var3, var4, var5, var6);
			this.setBlockBounds(0.0F, 0.0F, 0.5F, 1.0F, 0.5F, 1.0F);
			super.getCollidingBoundingBoxes(var1, var2, var3, var4, var5, var6);
		}

		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	}

	public void onNeighborBlockChange(World var1, int var2, int var3, int var4, int var5) {
		if(!var1.multiplayerWorld) {
			if(var1.getBlockMaterial(var2, var3 + 1, var4).isSolid()) {
				var1.setBlockWithNotify(var2, var3, var4, this.modelBlock.blockID);
			} else {
				this.updateState(var1, var2, var3, var4);
				this.updateState(var1, var2 + 1, var3 - 1, var4);
				this.updateState(var1, var2 - 1, var3 - 1, var4);
				this.updateState(var1, var2, var3 - 1, var4 - 1);
				this.updateState(var1, var2, var3 - 1, var4 + 1);
				this.updateState(var1, var2 + 1, var3 + 1, var4);
				this.updateState(var1, var2 - 1, var3 + 1, var4);
				this.updateState(var1, var2, var3 + 1, var4 - 1);
				this.updateState(var1, var2, var3 + 1, var4 + 1);
			}

			this.modelBlock.onNeighborBlockChange(var1, var2, var3, var4, var5);
		}
	}

	private void updateState(World worldObj, int x, int y, int z) {
		if(this.isBlockStair(worldObj, x, y, z)) {
			byte var5 = -1;
			if(this.isBlockStair(worldObj, x + 1, y + 1, z)) {
				var5 = 0;
			}

			if(this.isBlockStair(worldObj, x - 1, y + 1, z)) {
				var5 = 1;
			}

			if(this.isBlockStair(worldObj, x, y + 1, z + 1)) {
				var5 = 2;
			}

			if(this.isBlockStair(worldObj, x, y + 1, z - 1)) {
				var5 = 3;
			}

			if(var5 < 0) {
				if(this.isBlockSolid(worldObj, x + 1, y, z) && !this.isBlockSolid(worldObj, x - 1, y, z)) {
					var5 = 0;
				}

				if(this.isBlockSolid(worldObj, x - 1, y, z) && !this.isBlockSolid(worldObj, x + 1, y, z)) {
					var5 = 1;
				}

				if(this.isBlockSolid(worldObj, x, y, z + 1) && !this.isBlockSolid(worldObj, x, y, z - 1)) {
					var5 = 2;
				}

				if(this.isBlockSolid(worldObj, x, y, z - 1) && !this.isBlockSolid(worldObj, x, y, z + 1)) {
					var5 = 3;
				}
			}

			if(var5 < 0) {
				if(this.isBlockStair(worldObj, x - 1, y - 1, z)) {
					var5 = 0;
				}

				if(this.isBlockStair(worldObj, x + 1, y - 1, z)) {
					var5 = 1;
				}

				if(this.isBlockStair(worldObj, x, y - 1, z - 1)) {
					var5 = 2;
				}

				if(this.isBlockStair(worldObj, x, y - 1, z + 1)) {
					var5 = 3;
				}
			}

			if(var5 >= 0) {
				worldObj.setBlockMetadataWithNotify(x, y, z, var5);
			}

		}
	}

	private boolean isBlockSolid(World worldObj, int x, int y, int z) {
		return worldObj.getBlockMaterial(x, y, z).isSolid();
	}

	private boolean isBlockStair(World worldObj, int x, int y, int z) {
		int var5 = worldObj.getBlockId(x, y, z);
		return var5 == 0 ? false : Block.blocksList[var5].getRenderType() == 10;
	}

	public void randomDisplayTick(World var1, int var2, int var3, int var4, Random var5) {
		this.modelBlock.randomDisplayTick(var1, var2, var3, var4, var5);
	}

	public void onBlockClicked(World worldObj, int x, int y, int z, EntityPlayer entityPlayer) {
		this.modelBlock.onBlockClicked(worldObj, x, y, z, entityPlayer);
	}

	public void onBlockDestroyedByPlayer(World var1, int var2, int var3, int var4, int var5) {
		this.modelBlock.onBlockDestroyedByPlayer(var1, var2, var3, var4, var5);
	}

	public float getBlockBrightness(IBlockAccess blockAccess, int x, int y, int z) {
		return this.modelBlock.getBlockBrightness(blockAccess, x, y, z);
	}

	public float getExplosionResistance(Entity var1) {
		return this.modelBlock.getExplosionResistance(var1);
	}

	public int getRenderBlockPass() {
		return this.modelBlock.getRenderBlockPass();
	}

	public int idDropped(int var1, Random var2) {
		return this.modelBlock.idDropped(var1, var2);
	}

	public int quantityDropped(Random var1) {
		return this.modelBlock.quantityDropped(var1);
	}

	public int getBlockTextureFromSideAndMetadata(int var1, int var2) {
		return this.modelBlock.getBlockTextureFromSideAndMetadata(var1, var2);
	}

	public int getBlockTextureFromSide(int var1) {
		return this.modelBlock.getBlockTextureFromSide(var1);
	}

	public int getBlockTexture(IBlockAccess var1, int var2, int var3, int var4, int var5) {
		return this.modelBlock.getBlockTexture(var1, var2, var3, var4, var5);
	}

	public int tickRate() {
		return this.modelBlock.tickRate();
	}

	public AxisAlignedBB getSelectedBoundingBoxFromPool(World var1, int var2, int var3, int var4) {
		return this.modelBlock.getSelectedBoundingBoxFromPool(var1, var2, var3, var4);
	}

	public void velocityToAddToEntity(World var1, int var2, int var3, int var4, Entity var5, Vec3D var6) {
		this.modelBlock.velocityToAddToEntity(var1, var2, var3, var4, var5, var6);
	}

	public boolean isCollidable() {
		return this.modelBlock.isCollidable();
	}

	public boolean canCollideCheck(int var1, boolean var2) {
		return this.modelBlock.canCollideCheck(var1, var2);
	}

	public boolean canPlaceBlockAt(World var1, int var2, int var3, int var4) {
		return this.modelBlock.canPlaceBlockAt(var1, var2, var3, var4);
	}

	public void onBlockAdded(World var1, int var2, int var3, int var4) {
		this.onNeighborBlockChange(var1, var2, var3, var4, 0);
		this.modelBlock.onBlockAdded(var1, var2, var3, var4);
	}

	public void onBlockRemoval(World var1, int var2, int var3, int var4) {
		this.modelBlock.onBlockRemoval(var1, var2, var3, var4);
	}

	public void dropBlockAsItemWithChance(World worldObj, int x, int y, int z, int metadata, float chance) {
		this.modelBlock.dropBlockAsItemWithChance(worldObj, x, y, z, metadata, chance);
	}

	public void dropBlockAsItem(World var1, int var2, int var3, int var4, int var5) {
		this.modelBlock.dropBlockAsItem(var1, var2, var3, var4, var5);
	}

	public void onEntityWalking(World worldObj, int x, int y, int z, Entity entity) {
		this.modelBlock.onEntityWalking(worldObj, x, y, z, entity);
	}

	public void updateTick(World worldObj, int x, int y, int z, Random rand) {
		this.modelBlock.updateTick(worldObj, x, y, z, rand);
	}

	public boolean blockActivated(World var1, int var2, int var3, int var4, EntityPlayer var5) {
		return this.modelBlock.blockActivated(var1, var2, var3, var4, var5);
	}

	public void onBlockDestroyedByExplosion(World var1, int var2, int var3, int var4) {
		this.modelBlock.onBlockDestroyedByExplosion(var1, var2, var3, var4);
	}
}
