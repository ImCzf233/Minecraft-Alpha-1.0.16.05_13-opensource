package net.minecraft.src;

import java.util.ArrayList;
import java.util.Random;

public class Block {
	public static final StepSound soundPowderFootstep = new StepSound("stone", 1.0F, 1.0F);
	public static final StepSound soundWoodFootstep = new StepSound("wood", 1.0F, 1.0F);
	public static final StepSound soundGravelFootstep = new StepSound("gravel", 1.0F, 1.0F);
	public static final StepSound soundGrassFootstep = new StepSound("grass", 1.0F, 1.0F);
	public static final StepSound soundStoneFootstep = new StepSound("stone", 1.0F, 1.0F);
	public static final StepSound soundMetalFootstep = new StepSound("stone", 1.0F, 1.5F);
	public static final StepSound soundGlassFootstep = new StepSoundGlass("stone", 1.0F, 1.0F);
	public static final StepSound soundClothFootstep = new StepSound("cloth", 1.0F, 1.0F);
	public static final StepSound soundSandFootstep = new StepSoundSand("sand", 1.0F, 1.0F);
	public static final Block[] blocksList = new Block[256];
	public static final boolean[] tickOnLoad = new boolean[256];
	public static final boolean[] opaqueCubeLookup = new boolean[256];
	public static final boolean[] isBlockContainer = new boolean[256];
	public static final int[] lightOpacity = new int[256];
	public static final boolean[] canBlockGrass = new boolean[256];
	public static final int[] lightValue = new int[256];
	public static final Block stone = (new BlockStone(1, 1)).setHardness(1.5F).setResistance(10.0F).setStepSound(soundStoneFootstep);
	public static final BlockGrass grass = (BlockGrass)(new BlockGrass(2)).setHardness(0.6F).setStepSound(soundGrassFootstep);
	public static final Block dirt = (new BlockDirt(3, 2)).setHardness(0.5F).setStepSound(soundGravelFootstep);
	public static final Block cobblestone = (new Block(4, 16, Material.rock)).setHardness(2.0F).setResistance(10.0F).setStepSound(soundStoneFootstep);
	public static final Block planks = (new Block(5, 4, Material.wood)).setHardness(2.0F).setResistance(5.0F).setStepSound(soundWoodFootstep);
	public static final Block sapling = (new BlockSapling(6, 15)).setHardness(0.0F).setStepSound(soundGrassFootstep);
	public static final Block bedrock = (new Block(7, 17, Material.rock)).setHardness(-1.0F).setResistance(6000.0F).setStepSound(soundStoneFootstep);
	public static final Block waterMoving = (new BlockFlowing(8, Material.water)).setHardness(100.0F).setLightOpacity(3);
	public static final Block waterStill = (new BlockStationary(9, Material.water)).setHardness(100.0F).setLightOpacity(3);
	public static final Block lavaMoving = (new BlockFlowing(10, Material.lava)).setHardness(0.0F).setLightValue(1.0F).setLightOpacity(255);
	public static final Block lavaStill = (new BlockStationary(11, Material.lava)).setHardness(100.0F).setLightValue(1.0F).setLightOpacity(255);
	public static final Block sand = (new BlockSand(12, 18)).setHardness(0.5F).setStepSound(soundSandFootstep);
	public static final Block gravel = (new BlockGravel(13, 19)).setHardness(0.6F).setStepSound(soundGravelFootstep);
	public static final Block oreGold = (new BlockOre(14, 32)).setHardness(3.0F).setResistance(5.0F).setStepSound(soundStoneFootstep);
	public static final Block oreIron = (new BlockOre(15, 33)).setHardness(3.0F).setResistance(5.0F).setStepSound(soundStoneFootstep);
	public static final Block oreCoal = (new BlockOre(16, 34)).setHardness(3.0F).setResistance(5.0F).setStepSound(soundStoneFootstep);
	public static final Block wood = (new BlockLog(17)).setHardness(2.0F).setStepSound(soundWoodFootstep);
	public static final BlockLeaves leaves = (BlockLeaves)(new BlockLeaves(18, 52)).setHardness(0.2F).setLightOpacity(1).setStepSound(soundGrassFootstep);
	public static final Block sponge = (new BlockSponge(19)).setHardness(0.6F).setStepSound(soundGrassFootstep);
	public static final Block glass = (new BlockGlass(20, 49, Material.glass, false)).setHardness(0.3F).setStepSound(soundGlassFootstep);
	public static final Block clothRed = null;
	public static final Block clothOrange = null;
	public static final Block clothYellow = null;
	public static final Block clothChartreuse = null;
	public static final Block clothGreen = null;
	public static final Block clothSpringGreen = null;
	public static final Block clothCyan = null;
	public static final Block clothCapri = null;
	public static final Block clothUltramarine = null;
	public static final Block clothViolet = null;
	public static final Block clothPurple = null;
	public static final Block clothMagenta = null;
	public static final Block clothRose = null;
	public static final Block clothDarkGray = null;
	public static final Block cloth = (new Block(35, 64, Material.cloth)).setHardness(0.8F).setStepSound(soundClothFootstep);
	public static final Block clothWhite = null;
	public static final BlockFlower plantYellow = (BlockFlower)(new BlockFlower(37, 13)).setHardness(0.0F).setStepSound(soundGrassFootstep);
	public static final BlockFlower plantRed = (BlockFlower)(new BlockFlower(38, 12)).setHardness(0.0F).setStepSound(soundGrassFootstep);
	public static final BlockFlower mushroomBrown = (BlockFlower)(new BlockMushroom(39, 29)).setHardness(0.0F).setStepSound(soundGrassFootstep).setLightValue(0.125F);
	public static final BlockFlower mushroomRed = (BlockFlower)(new BlockMushroom(40, 28)).setHardness(0.0F).setStepSound(soundGrassFootstep);
	public static final Block blockGold = (new BlockOreBlock(41, 39)).setHardness(3.0F).setResistance(10.0F).setStepSound(soundMetalFootstep);
	public static final Block blockSteel = (new BlockOreBlock(42, 38)).setHardness(5.0F).setResistance(10.0F).setStepSound(soundMetalFootstep);
	public static final Block stairDouble = (new BlockStep(43, true)).setHardness(2.0F).setResistance(10.0F).setStepSound(soundStoneFootstep);
	public static final Block stairSingle = (new BlockStep(44, false)).setHardness(2.0F).setResistance(10.0F).setStepSound(soundStoneFootstep);
	public static final Block brick = (new Block(45, 7, Material.rock)).setHardness(2.0F).setResistance(10.0F).setStepSound(soundStoneFootstep);
	public static final Block tnt = (new BlockTNT(46, 8)).setHardness(0.0F).setStepSound(soundGrassFootstep);
	public static final Block bookshelf = (new BlockBookshelf(47, 35)).setHardness(1.5F).setStepSound(soundWoodFootstep);
	public static final Block cobblestoneMossy = (new Block(48, 36, Material.rock)).setHardness(2.0F).setResistance(10.0F).setStepSound(soundStoneFootstep);
	public static final Block obsidian = (new BlockObsidian(49, 37)).setHardness(10.0F).setResistance(2000.0F).setStepSound(soundStoneFootstep);
	public static final Block torch = (new BlockTorch(50, 80)).setHardness(0.0F).setLightValue(0.9375F).setStepSound(soundWoodFootstep);
	public static final BlockFire fire = (BlockFire)(new BlockFire(51, 31)).setHardness(0.0F).setLightValue(1.0F).setStepSound(soundWoodFootstep);
	public static final Block mobSpawner = (new BlockMobSpawner(52, 65)).setHardness(5.0F).setStepSound(soundMetalFootstep);
	public static final Block stairCompactWood = new BlockStairs(53, planks);
	public static final Block chest = (new BlockChest(54)).setHardness(2.5F).setStepSound(soundWoodFootstep);
	public static final Block redstoneWire = (new BlockRedstoneWire(55, 84)).setHardness(0.0F).setStepSound(soundPowderFootstep);
	public static final Block oreDiamond = (new BlockOre(56, 50)).setHardness(3.0F).setResistance(5.0F).setStepSound(soundStoneFootstep);
	public static final Block blockDiamond = (new BlockOreBlock(57, 40)).setHardness(5.0F).setResistance(10.0F).setStepSound(soundMetalFootstep);
	public static final Block workbench = (new BlockWorkbench(58)).setHardness(2.5F).setStepSound(soundWoodFootstep);
	public static final Block crops = (new BlockCrops(59, 88)).setHardness(0.0F).setStepSound(soundGrassFootstep);
	public static final Block tilledField = (new BlockFarmland(60)).setHardness(0.6F).setStepSound(soundGravelFootstep);
	public static final Block stoneOvenIdle = (new BlockFurnace(61, false)).setHardness(3.5F).setStepSound(soundStoneFootstep);
	public static final Block stoneOvenActive = (new BlockFurnace(62, true)).setHardness(3.5F).setStepSound(soundStoneFootstep).setLightValue(0.875F);
	public static final Block signStanding = (new BlockSign(63, TileEntitySign.class, true)).setHardness(1.0F).setStepSound(soundWoodFootstep);
	public static final Block doorWood = (new BlockDoor(64, Material.wood)).setHardness(3.0F).setStepSound(soundWoodFootstep);
	public static final Block ladder = (new BlockLadder(65, 83)).setHardness(0.4F).setStepSound(soundWoodFootstep);
	public static final Block minecartTrack = (new ClassIf(66, 128)).setHardness(0.7F).setStepSound(soundMetalFootstep);
	public static final Block stairCompactStone = new BlockStairs(67, cobblestone);
	public static final Block signWall = (new BlockSign(68, TileEntitySign.class, false)).setHardness(1.0F).setStepSound(soundWoodFootstep);
	public static final Block lever = (new BlockLever(69, 96)).setHardness(0.5F).setStepSound(soundWoodFootstep);
	public static final Block pressurePlateStone = (new BlockPressurePlate(70, stone.blockIndexInTexture, EnumMobType.mobs)).setHardness(0.5F).setStepSound(soundStoneFootstep);
	public static final Block doorSteel = (new BlockDoor(71, Material.iron)).setHardness(5.0F).setStepSound(soundMetalFootstep);
	public static final Block pressurePlateWood = (new BlockPressurePlate(72, planks.blockIndexInTexture, EnumMobType.everything)).setHardness(0.5F).setStepSound(soundWoodFootstep);
	public static final Block oreRedstone = (new BlockRedstoneOre(73, 51, false)).setHardness(3.0F).setResistance(5.0F).setStepSound(soundStoneFootstep);
	public static final Block oreRedstoneGlowing = (new BlockRedstoneOre(74, 51, true)).setLightValue(0.625F).setHardness(3.0F).setResistance(5.0F).setStepSound(soundStoneFootstep);
	public static final Block torchRedstoneIdle = (new BlockRedstoneTorch(75, 115, false)).setHardness(0.0F).setStepSound(soundWoodFootstep);
	public static final Block torchRedstoneActive = (new BlockRedstoneTorch(76, 99, true)).setHardness(0.0F).setLightValue(0.5F).setStepSound(soundWoodFootstep);
	public static final Block button = (new hu(77, stone.blockIndexInTexture)).setHardness(0.5F).setStepSound(soundStoneFootstep);
	public static final Block snow = (new BlockSnow(78, 66)).setHardness(0.1F).setStepSound(soundClothFootstep);
	public static final Block ice = (new BlockIce(79, 67)).setHardness(0.5F).setLightOpacity(3).setStepSound(soundGlassFootstep);
	public static final Block blockSnow = (new BlockSnowBlock(80, 66)).setHardness(0.2F).setStepSound(soundClothFootstep);
	public static final Block cactus = (new BlockCactus(81, 70)).setHardness(0.4F).setStepSound(soundClothFootstep);
	public static final Block blockClay = (new BlockClay(82, 72)).setHardness(0.6F).setStepSound(soundGravelFootstep);
	public static final Block reed = (new BlockReed(83, 73)).setHardness(0.0F).setStepSound(soundGrassFootstep);
	public static Block jukebox = (new BlockJukeBox(84, 74)).setHardness(2.0F).setResistance(10.0F).setStepSound(soundStoneFootstep);
	public static final Block fence = (new BlockFence(85, 4)).setHardness(2.0F).setResistance(5.0F).setStepSound(soundWoodFootstep);
	public int blockIndexInTexture;
	public final int blockID;
	protected float hardness;
	protected float resistance;
	public double minX;
	public double minY;
	public double minZ;
	public double maxX;
	public double maxY;
	public double maxZ;
	public StepSound stepSound;
	public float blockParticleGravity;
	public final Material material;
	public float slipperiness;
	public static final Block QuadWindowGlassBlock = (new BlockGlass(90, 145, Material.glass, false)).setHardness(0.3F).setStepSound(soundGlassFootstep);
	public static final Block PillarBlock = (new Block(91, 144, Material.wood)).setHardness(2.0F).setResistance(5.0F).setStepSound(soundWoodFootstep);
	public static final Block TileBlock = (new Block(92, 146, Material.rock)).setHardness(2.0F).setResistance(10.0F).setStepSound(soundStoneFootstep);
	public static final Block TileBlock2 = (new Block(93, 147, Material.rock)).setHardness(2.0F).setResistance(10.0F).setStepSound(soundStoneFootstep);
	public static final Block TileBlock2NonFucked = (new BlockGlass(94, 147, Material.glass, false)).setHardness(0.3F).setStepSound(soundGlassFootstep);
	public static final Block dimensionFloorBlock = (new Block(95, 148, Material.rock)).setHardness(2.0F).setResistance(10.0F).setStepSound(soundStoneFootstep);
	public static final Block dimensionWallBlock = (new Block(96, 149, Material.rock)).setHardness(2.0F).setResistance(10.0F).setStepSound(soundStoneFootstep);
	public static final Block dbgBlock = (new Block(97, 150, Material.rock)).setHardness(2.0F).setResistance(10.0F).setStepSound(soundStoneFootstep);
	public static final Block TileBlock3 = (new Block(98, 151, Material.rock)).setHardness(2.0F).setResistance(10.0F).setStepSound(soundStoneFootstep);
	public static final Block TileBlock4 = (new Block(99, 152, Material.rock)).setHardness(2.0F).setResistance(10.0F).setStepSound(soundStoneFootstep);
	public static final Block fakeGrass = (new BlockMultiSided(100, 153, 154, 155)).setHardness(0.6F).setStepSound(soundGrassFootstep);
	public static final Block AltMojangHypostasisAnemo = (new Block(101, 156, Material.rock)).setHardness(2.0F).setResistance(10.0F).setStepSound(soundStoneFootstep);
	public static final Block imgur9F0A3un = (new Block(102, 157, Material.rock)).setHardness(2.0F).setResistance(10.0F).setStepSound(soundStoneFootstep);
	public static final Block yeahWeHadFunWithThisAsYouCanSee = (new Block(103, 158, Material.rock)).setHardness(2.0F).setResistance(10.0F).setStepSound(soundStoneFootstep);
	public static final Block BarrierBlock = (new BlockHidable(104, 159, Material.glass, false)).setHardness(0.3F).setStepSound(soundGlassFootstep);
	public static final Block StairLadderBlock = (new BlockLadder(105, 161)).setHardness(0.4F).setStepSound(soundWoodFootstep);
	public static final Block fakeDirt = (new Block(106, 162, Material.rock)).setHardness(0.6F).setResistance(10.0F).setStepSound(soundGrassFootstep);
	public static final Block fakeRock = (new Block(107, 163, Material.rock)).setHardness(2.0F).setResistance(10.0F).setStepSound(soundStoneFootstep);
	public static final Block fakeSand = (new BlockSand(108, 164)).setHardness(0.5F).setStepSound(soundSandFootstep);
	public static final Block woolPink = (new Block(109, 165, Material.cloth)).setHardness(0.8F).setStepSound(soundClothFootstep);
	public static final Block woolBlue = (new Block(110, 166, Material.cloth)).setHardness(0.8F).setStepSound(soundClothFootstep);
	public static final Block woolGreen = (new Block(111, 167, Material.cloth)).setHardness(0.8F).setStepSound(soundClothFootstep);
	public static final Block woolBlack = (new Block(112, 168, Material.cloth)).setHardness(0.8F).setStepSound(soundClothFootstep);
	public static final Block dbg2Block = (new BlockGlowing(113, 150, 1)).setHardness(0.0F).setLightValue(0.9375F).setStepSound(soundStoneFootstep);
	public static final Block saltBlock = (new BlockMultiSided(114, 170, 169, 170)).setHardness(0.6F).setStepSound(soundStoneFootstep);
	public static final Block glowingFlower = (new BlockGlowingFlower(115, 171, 1)).setHardness(0.0F).setLightValue(0.9375F).setStepSound(soundGrassFootstep);
	public static final Block glowingFlowerInfBasic = (new BlockGlowingFlowerInfused(117, 173, 1, 2)).setHardness(0.0F).setLightValue(0.9375F).setStepSound(soundGrassFootstep);
	public static final Block glowingFlowerInfGold = (new BlockGlowingFlowerInfused(118, 174, 1, 4)).setHardness(0.0F).setLightValue(0.9375F).setStepSound(soundGrassFootstep);
	public static final Block glowingFlowerInfObsidian = (new BlockGlowingFlowerInfused(119, 175, 1, 7)).setHardness(0.0F).setLightValue(0.9375F).setStepSound(soundGrassFootstep);
	public static final Block blueFireIdk = (new BlockGlowing(116, 172, 2)).setHardness(0.0F).setLightValue(0.9375F).setStepSound(soundStoneFootstep);
	public static final Block safe = (new BlockSafe(120, 177, 176, 177)).setHardness(1.5F).setResistance(10.0F).setStepSound(soundStoneFootstep);

	protected Block(int id, Material material) {
		this.stepSound = soundPowderFootstep;
		this.blockParticleGravity = 1.0F;
		this.slipperiness = 0.6F;
		if(blocksList[id] != null) {
			throw new IllegalArgumentException("Slot " + id + " is already occupied by " + blocksList[id] + " when adding " + this);
		} else {
			this.material = material;
			blocksList[id] = this;
			this.blockID = id;
			this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
			opaqueCubeLookup[id] = this.isOpaqueCube();
			lightOpacity[id] = this.isOpaqueCube() ? 255 : 0;
			canBlockGrass[id] = this.getCanBlockGrass();
			isBlockContainer[id] = false;
		}
	}

	protected Block(int id, int tex, Material material) {
		this(id, material);
		this.blockIndexInTexture = tex;
	}

	protected Block setStepSound(StepSound stepSound) {
		this.stepSound = stepSound;
		return this;
	}

	protected Block setLightOpacity(int opacity) {
		lightOpacity[this.blockID] = opacity;
		return this;
	}

	protected Block setLightValue(float value) {
		lightValue[this.blockID] = (int)(15.0F * value);
		return this;
	}

	protected Block setResistance(float resistance) {
		this.resistance = resistance * 3.0F;
		return this;
	}

	private boolean getCanBlockGrass() {
		return false;
	}

	public boolean renderAsNormalBlock() {
		return true;
	}

	public int getRenderType() {
		return 0;
	}

	protected Block setHardness(float hardness) {
		this.hardness = hardness;
		if(this.resistance < hardness * 5.0F) {
			this.resistance = hardness * 5.0F;
		}

		return this;
	}

	protected void setTickOnLoad(boolean ticksOnLoad) {
		tickOnLoad[this.blockID] = ticksOnLoad;
	}

	public void setBlockBounds(float minX, float minY, float minZ, float maxX, float maxY, float maxZ) {
		this.minX = (double)minX;
		this.minY = (double)minY;
		this.minZ = (double)minZ;
		this.maxX = (double)maxX;
		this.maxY = (double)maxY;
		this.maxZ = (double)maxZ;
	}

	public float getBlockBrightness(IBlockAccess blockAccess, int x, int y, int z) {
		return blockAccess.getBrightness(x, y, z);
	}

	public boolean shouldSideBeRendered(IBlockAccess blockAccess, int x, int y, int z, int side) {
		return side == 0 && this.minY > 0.0D || side == 1 && this.maxY < 1.0D || side == 2 && this.minZ > 0.0D || side == 3 && this.maxZ < 1.0D || side == 4 && this.minX > 0.0D || side == 5 && this.maxX < 1.0D || !blockAccess.isBlockNormalCube(x, y, z);
	}

	public int getBlockTexture(IBlockAccess blockAccess, int x, int y, int z, int side) {
		return this.getBlockTextureFromSideAndMetadata(side, blockAccess.getBlockMetadata(x, y, z));
	}

	public int getBlockTextureFromSideAndMetadata(int side, int metadata) {
		return this.getBlockTextureFromSide(side);
	}

	public int getBlockTextureFromSide(int side) {
		return this.blockIndexInTexture;
	}

	public AxisAlignedBB getSelectedBoundingBoxFromPool(World worldObj, int x, int y, int z) {
		return AxisAlignedBB.getBoundingBoxFromPool((double)x + this.minX, (double)y + this.minY, (double)z + this.minZ, (double)x + this.maxX, (double)y + this.maxY, (double)z + this.maxZ);
	}

	public void getCollidingBoundingBoxes(World worldObj, int x, int y, int z, AxisAlignedBB aabb, ArrayList collidingBoundingBoxes) {
		AxisAlignedBB var7 = this.getCollisionBoundingBoxFromPool(worldObj, x, y, z);
		if(var7 != null && aabb.intersectsWith(var7)) {
			collidingBoundingBoxes.add(var7);
		}

	}

	public AxisAlignedBB getCollisionBoundingBoxFromPool(World worldObj, int x, int y, int z) {
		return AxisAlignedBB.getBoundingBoxFromPool((double)x + this.minX, (double)y + this.minY, (double)z + this.minZ, (double)x + this.maxX, (double)y + this.maxY, (double)z + this.maxZ);
	}

	public boolean isOpaqueCube() {
		return true;
	}

	public boolean canCollideCheck(int metadata, boolean var2) {
		return this.isCollidable();
	}

	public boolean isCollidable() {
		return true;
	}

	public void updateTick(World worldObj, int x, int y, int z, Random rand) {
	}

	public void randomDisplayTick(World worldObj, int x, int y, int z, Random rand) {
	}

	public void onBlockDestroyedByPlayer(World worldObj, int x, int y, int z, int metadata) {
	}

	public void onNeighborBlockChange(World worldObj, int x, int y, int z, int id) {
	}

	public int tickRate() {
		return 10;
	}

	public void onBlockAdded(World worldObj, int x, int y, int z) {
	}

	public void onBlockRemoval(World worldObj, int x, int y, int z) {
	}

	public int quantityDropped(Random rand) {
		return 1;
	}

	public int idDropped(int metadata, Random rand) {
		return this.blockID;
	}

	public float blockStrength(EntityPlayer entityPlayer) {
		return this.hardness < 0.0F ? (entityPlayer.inventory.getCurrentItem() != null && entityPlayer.inventory.getCurrentItem().getItem() == Item.obsidianPick ? entityPlayer.getCurrentPlayerStrVsBlock(this) / 2.0F / 30.0F : 0.0F) : (!entityPlayer.canHarvestBlock(this) ? 1.0F / this.hardness / 100.0F : entityPlayer.getCurrentPlayerStrVsBlock(this) / this.hardness / 30.0F);
	}

	public void dropBlockAsItem(World worldObj, int x, int y, int z, int metadata) {
		this.dropBlockAsItemWithChance(worldObj, x, y, z, metadata, 1.0F);
	}

	public void dropBlockAsItemWithChance(World worldObj, int x, int y, int z, int metadata, float chance) {
		if(!worldObj.multiplayerWorld) {
			int var7 = this.quantityDropped(worldObj.rand);

			for(int var8 = 0; var8 < var7; ++var8) {
				if(worldObj.rand.nextFloat() <= chance) {
					int var9 = this.idDropped(metadata, worldObj.rand);
					if(var9 > 0) {
						EntityItem var11 = new EntityItem(worldObj, (double)x + (double)(worldObj.rand.nextFloat() * 0.7F) + (double)0.15F, (double)y + (double)(worldObj.rand.nextFloat() * 0.7F) + (double)0.15F, (double)z + (double)(worldObj.rand.nextFloat() * 0.7F) + (double)0.15F, new ItemStack(var9));
						var11.delayBeforeCanPickup = 10;
						worldObj.spawnEntityInWorld(var11);
					}
				}
			}

		}
	}

	public float getExplosionResistance(Entity entity) {
		return this.resistance / 5.0F;
	}

	public MovingObjectPosition collisionRayTrace(World worldObj, int x, int y, int z, Vec3D vector1, Vec3D vector2) {
		this.setBlockBoundsBasedOnState(worldObj, x, y, z);
		vector1 = vector1.addVector((double)(-x), (double)(-y), (double)(-z));
		vector2 = vector2.addVector((double)(-x), (double)(-y), (double)(-z));
		Vec3D var7 = vector1.getIntermediateWithXValue(vector2, this.minX);
		Vec3D var8 = vector1.getIntermediateWithXValue(vector2, this.maxX);
		Vec3D var9 = vector1.getIntermediateWithYValue(vector2, this.minY);
		Vec3D var10 = vector1.getIntermediateWithYValue(vector2, this.maxY);
		Vec3D var11 = vector1.getIntermediateWithZValue(vector2, this.minZ);
		Vec3D var12 = vector1.getIntermediateWithZValue(vector2, this.maxZ);
		if(!this.isVecInsideYZBounds(var7)) {
			var7 = null;
		}

		if(!this.isVecInsideYZBounds(var8)) {
			var8 = null;
		}

		if(!this.isVecInsideXZBounds(var9)) {
			var9 = null;
		}

		if(!this.isVecInsideXZBounds(var10)) {
			var10 = null;
		}

		if(!this.isVecInsideXYBounds(var11)) {
			var11 = null;
		}

		if(!this.isVecInsideXYBounds(var12)) {
			var12 = null;
		}

		Vec3D var13 = null;
		if(var7 != null && (var13 == null || vector1.distanceTo(var7) < vector1.distanceTo(var13))) {
			var13 = var7;
		}

		if(var8 != null && (var13 == null || vector1.distanceTo(var8) < vector1.distanceTo(var13))) {
			var13 = var8;
		}

		if(var9 != null && (var13 == null || vector1.distanceTo(var9) < vector1.distanceTo(var13))) {
			var13 = var9;
		}

		if(var10 != null && (var13 == null || vector1.distanceTo(var10) < vector1.distanceTo(var13))) {
			var13 = var10;
		}

		if(var11 != null && (var13 == null || vector1.distanceTo(var11) < vector1.distanceTo(var13))) {
			var13 = var11;
		}

		if(var12 != null && (var13 == null || vector1.distanceTo(var12) < vector1.distanceTo(var13))) {
			var13 = var12;
		}

		if(var13 == null) {
			return null;
		} else {
			byte var14 = -1;
			if(var13 == var7) {
				var14 = 4;
			}

			if(var13 == var8) {
				var14 = 5;
			}

			if(var13 == var9) {
				var14 = 0;
			}

			if(var13 == var10) {
				var14 = 1;
			}

			if(var13 == var11) {
				var14 = 2;
			}

			if(var13 == var12) {
				var14 = 3;
			}

			return new MovingObjectPosition(x, y, z, var14, var13.addVector((double)x, (double)y, (double)z));
		}
	}

	private boolean isVecInsideYZBounds(Vec3D vector) {
		return vector != null && vector.yCoord >= this.minY && vector.yCoord <= this.maxY && vector.zCoord >= this.minZ && vector.zCoord <= this.maxZ;
	}

	private boolean isVecInsideXZBounds(Vec3D vector) {
		return vector != null && vector.xCoord >= this.minX && vector.xCoord <= this.maxX && vector.zCoord >= this.minZ && vector.zCoord <= this.maxZ;
	}

	private boolean isVecInsideXYBounds(Vec3D vector) {
		return vector != null && vector.xCoord >= this.minX && vector.xCoord <= this.maxX && vector.yCoord >= this.minY && vector.yCoord <= this.maxY;
	}

	public void onBlockDestroyedByExplosion(World worldObj, int x, int y, int z) {
	}

	public int getRenderBlockPass() {
		return 0;
	}

	public boolean canPlaceBlockAt(World world, int x, int y, int z) {
		int var5 = world.getBlockId(x, y, z);
		return var5 == 0 || blocksList[var5].material.getIsLiquid();
	}

	public boolean blockActivated(World worldObj, int x, int y, int z, EntityPlayer entityPlayer) {
		return false;
	}

	public void onEntityWalking(World worldObj, int x, int y, int z, Entity entity) {
	}

	public void onBlockPlaced(World worldObj, int x, int y, int z, int metadata) {
	}

	public void onBlockClicked(World worldObj, int x, int y, int z, EntityPlayer entityPlayer) {
	}

	public void velocityToAddToEntity(World worldObj, int x, int y, int z, Entity entity, Vec3D velocityVector) {
	}

	public void setBlockBoundsBasedOnState(IBlockAccess blockAccess, int x, int y, int z) {
	}

	public int colorMultiplier(IBlockAccess blockAccess, int x, int y, int z) {
		return 0xFFFFFF;
	}

	public boolean isPoweringTo(IBlockAccess blockAccess, int x, int y, int z, int metadata) {
		return false;
	}

	public boolean canProvidePower() {
		return false;
	}

	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
	}

	public boolean isIndirectlyPoweringTo(World worldObj, int x, int y, int z, int side) {
		return false;
	}

	public void setBlockBoundsForItemRender() {
	}

	public void harvestBlock(World worldObj, int x, int y, int z, int metadata) {
		this.dropBlockAsItem(worldObj, x, y, z, metadata);
	}

	public boolean altG(World var1, int var2, int var3, int var4) {
		boolean var5 = var2 == grass.blockID || var2 == dirt.blockID || var2 == tilledField.blockID;
		return (var1.getBlockLightValue(var2, var3, var4) >= 8 || var1.canBlockSeeTheSky(var2, var3, var4)) && var5;
	}

	public boolean canBlockStay(World world, int x, int y, int z) {
		return true;
	}

	static {
		for(int var0 = 0; var0 < 256; ++var0) {
			if(blocksList[var0] != null) {
				Item.itemsList[var0] = new ItemBlock(var0 - 256);
			}
		}

	}
}
