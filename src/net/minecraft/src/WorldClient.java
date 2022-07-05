package net.minecraft.src;

import java.io.File;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class WorldClient extends World {
	private LinkedList blocksToReceive = new LinkedList();
	private NetClientHandler sendQueue;
	private ChunkProviderClient clientChunkProvider;
	private boolean noTileEntityUpdates = false;
	private MCHashTable entityHashTable = new MCHashTable();
	private Set entityList = new HashSet();
	private Set entitySpawnQueue = new HashSet();

	public WorldClient(NetClientHandler netClientHandler) {
		super("MpServer");
		this.sendQueue = netClientHandler;
		this.spawnX = 8;
		this.spawnY = 64;
		this.spawnZ = 8;
	}

	public void tick() {
		++this.worldTime;
		int var1 = this.calculateSkylightSubtracted(1.0F);
		int var2;
		if(var1 != this.skylightSubtracted) {
			this.skylightSubtracted = var1;

			for(var2 = 0; var2 < this.worldAccesses.size(); ++var2) {
				((IWorldAccess)this.worldAccesses.get(var2)).updateAllRenderers();
			}
		}

		for(var2 = 0; var2 < 10 && !this.entitySpawnQueue.isEmpty(); ++var2) {
			Entity var3 = (Entity)this.entitySpawnQueue.iterator().next();
			this.spawnEntityInWorld(var3);
		}

		this.sendQueue.processReadPackets();

		for(var2 = 0; var2 < this.blocksToReceive.size(); ++var2) {
			WorldBlockPositionType var4 = (WorldBlockPositionType)this.blocksToReceive.get(var2);
			if(--var4.acceptCountdown == 0) {
				super.setBlockAndMetadata(var4.posX, var4.posY, var4.posZ, var4.blockID, var4.metadata);
				super.markBlockNeedsUpdate(var4.posX, var4.posY, var4.posZ);
				this.blocksToReceive.remove(var2--);
			}
		}

	}

	public void invalidateBlockReceiveRegion(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
		for(int var7 = 0; var7 < this.blocksToReceive.size(); ++var7) {
			WorldBlockPositionType var8 = (WorldBlockPositionType)this.blocksToReceive.get(var7);
			if(var8.posX >= minX && var8.posY >= minY && var8.posZ >= minZ && var8.posX <= maxX && var8.posY <= maxY && var8.posZ <= maxZ) {
				this.blocksToReceive.remove(var7--);
			}
		}

	}

	protected IChunkProvider getChunkProvider(File var1) {
		this.clientChunkProvider = new ChunkProviderClient(this);
		return this.clientChunkProvider;
	}

	public void setSpawnLocation() {
		this.spawnX = 8;
		this.spawnY = 64;
		this.spawnZ = 8;
	}

	protected void updateBlocksAndPlayCaveSounds() {
	}

	public void scheduleBlockUpdate(int var1, int var2, int var3, int var4) {
	}

	public boolean tickUpdates(boolean var1) {
		return false;
	}

	public void doPreChunk(int x, int z, boolean mode) {
		if(mode) {
			this.clientChunkProvider.loadChunk(x, z);
		} else {
			this.clientChunkProvider.unloadChunk(x, z);
		}

		if(!mode) {
			this.markBlocksDirty(x * 16, 0, z * 16, x * 16 + 15, 128, z * 16 + 15);
		}

	}

	public boolean spawnEntityInWorld(Entity var1) {
		boolean var2 = super.spawnEntityInWorld(var1);
		if(var1 instanceof EntityPlayerSP) {
			this.entityList.add(var1);
		}

		return var2;
	}

	public void setEntityDead(Entity var1) {
		super.setEntityDead(var1);
		if(var1 instanceof EntityPlayerSP) {
			this.entityList.remove(var1);
		}

	}

	protected void obtainEntitySkin(Entity entity) {
		super.obtainEntitySkin(entity);
		if(this.entitySpawnQueue.contains(entity)) {
			this.entitySpawnQueue.remove(entity);
		}

	}

	protected void releaseEntitySkin(Entity entity) {
		super.releaseEntitySkin(entity);
		if(this.entityList.contains(entity)) {
			this.entitySpawnQueue.add(entity);
		}

	}

	public void addEntityToWorld(int id, Entity entity) {
		this.entityList.add(entity);
		if(!this.spawnEntityInWorld(entity)) {
			this.entitySpawnQueue.add(entity);
		}

		this.entityHashTable.addKey(id, entity);
	}

	public Entity getEntityByID(int id) {
		return (Entity)this.entityHashTable.lookup(id);
	}

	public Entity removeEntityFromWorld(int id) {
		Entity var2 = (Entity)this.entityHashTable.removeObject(id);
		if(var2 != null) {
			this.entityList.remove(var2);
			this.setEntityDead(var2);
		}

		return var2;
	}

	public boolean setBlockMetadata(int x, int y, int z, int metadata) {
		int var5 = this.getBlockId(x, y, z);
		int var6 = this.getBlockMetadata(x, y, z);
		if(super.setBlockMetadata(x, y, z, metadata)) {
			this.blocksToReceive.add(new WorldBlockPositionType(this, x, y, z, var5, var6));
			return true;
		} else {
			return false;
		}
	}

	public boolean setBlockAndMetadata(int var1, int var2, int var3, int var4, int var5) {
		int var6 = this.getBlockId(var1, var2, var3);
		int var7 = this.getBlockMetadata(var1, var2, var3);
		if(super.setBlockAndMetadata(var1, var2, var3, var4, var5)) {
			this.blocksToReceive.add(new WorldBlockPositionType(this, var1, var2, var3, var6, var7));
			return true;
		} else {
			return false;
		}
	}

	public boolean setBlock(int x, int y, int z, int id) {
		int var5 = this.getBlockId(x, y, z);
		int var6 = this.getBlockMetadata(x, y, z);
		if(super.setBlock(x, y, z, id)) {
			this.blocksToReceive.add(new WorldBlockPositionType(this, x, y, z, var5, var6));
			return true;
		} else {
			return false;
		}
	}

	public boolean handleBlockChange(int x, int y, int z, int id, int metadata) {
		this.invalidateBlockReceiveRegion(x, y, z, x, y, z);
		if(super.setBlockAndMetadata(x, y, z, id, metadata)) {
			this.notifyBlockChange(x, y, z, id);
			return true;
		} else {
			return false;
		}
	}

	public void updateTileEntityChunkAndDoNothing(int var1, int var2, int var3, TileEntity var4) {
		if(!this.noTileEntityUpdates) {
			this.sendQueue.addToSendQueue(new Packet59ComplexEntity(var1, var2, var3, var4));
		}
	}

	public void sendQuittingDisconnectingPacket() {
		this.sendQueue.addToSendQueue(new Packet255KickDisconnect("Quitting"));
	}
}
