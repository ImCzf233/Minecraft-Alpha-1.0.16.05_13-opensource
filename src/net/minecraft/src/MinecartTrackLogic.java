package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;

class MinecartTrackLogic {
	private World worldObj;
	private int trackX;
	private int trackY;
	private int trackZ;
	private int trackMetadata;
	private List connectedTracks;
	final Block a;

	public MinecartTrackLogic(Block var1, World var2, int var3, int var4, int var5) {
		this.a = var1;
		this.connectedTracks = new ArrayList();
		this.worldObj = var2;
		this.trackX = var3;
		this.trackY = var4;
		this.trackZ = var5;
		this.trackMetadata = var2.getBlockMetadata(var3, var4, var5);
		this.calculateConnectedTracks();
	}

	private void calculateConnectedTracks() {
		this.connectedTracks.clear();
		if(this.trackMetadata == 0) {
			this.connectedTracks.add(new ChunkPosition(this.trackX, this.trackY, this.trackZ - 1));
			this.connectedTracks.add(new ChunkPosition(this.trackX, this.trackY, this.trackZ + 1));
		} else if(this.trackMetadata == 1) {
			this.connectedTracks.add(new ChunkPosition(this.trackX - 1, this.trackY, this.trackZ));
			this.connectedTracks.add(new ChunkPosition(this.trackX + 1, this.trackY, this.trackZ));
		} else if(this.trackMetadata == 2) {
			this.connectedTracks.add(new ChunkPosition(this.trackX - 1, this.trackY, this.trackZ));
			this.connectedTracks.add(new ChunkPosition(this.trackX + 1, this.trackY + 1, this.trackZ));
		} else if(this.trackMetadata == 3) {
			this.connectedTracks.add(new ChunkPosition(this.trackX - 1, this.trackY + 1, this.trackZ));
			this.connectedTracks.add(new ChunkPosition(this.trackX + 1, this.trackY, this.trackZ));
		} else if(this.trackMetadata == 4) {
			this.connectedTracks.add(new ChunkPosition(this.trackX, this.trackY + 1, this.trackZ - 1));
			this.connectedTracks.add(new ChunkPosition(this.trackX, this.trackY, this.trackZ + 1));
		} else if(this.trackMetadata == 5) {
			this.connectedTracks.add(new ChunkPosition(this.trackX, this.trackY, this.trackZ - 1));
			this.connectedTracks.add(new ChunkPosition(this.trackX, this.trackY + 1, this.trackZ + 1));
		} else if(this.trackMetadata == 6) {
			this.connectedTracks.add(new ChunkPosition(this.trackX + 1, this.trackY, this.trackZ));
			this.connectedTracks.add(new ChunkPosition(this.trackX, this.trackY, this.trackZ + 1));
		} else if(this.trackMetadata == 7) {
			this.connectedTracks.add(new ChunkPosition(this.trackX - 1, this.trackY, this.trackZ));
			this.connectedTracks.add(new ChunkPosition(this.trackX, this.trackY, this.trackZ + 1));
		} else if(this.trackMetadata == 8) {
			this.connectedTracks.add(new ChunkPosition(this.trackX - 1, this.trackY, this.trackZ));
			this.connectedTracks.add(new ChunkPosition(this.trackX, this.trackY, this.trackZ - 1));
		} else if(this.trackMetadata == 9) {
			this.connectedTracks.add(new ChunkPosition(this.trackX + 1, this.trackY, this.trackZ));
			this.connectedTracks.add(new ChunkPosition(this.trackX, this.trackY, this.trackZ - 1));
		}

	}

	private void refreshConnectedTracks() {
		for(int var1 = 0; var1 < this.connectedTracks.size(); ++var1) {
			MinecartTrackLogic var2 = this.getMinecartTrackLogic((ChunkPosition)this.connectedTracks.get(var1));
			if(var2 != null && var2.isConnectedTo(this)) {
				this.connectedTracks.set(var1, new ChunkPosition(var2.trackX, var2.trackY, var2.trackZ));
			} else {
				this.connectedTracks.remove(var1--);
			}
		}

	}

	private boolean isMinecartTrack(int x, int y, int z) {
		return this.worldObj.getBlockId(x, y, z) == this.a.blockID || this.worldObj.getBlockId(x, y + 1, z) == this.a.blockID || this.worldObj.getBlockId(x, y - 1, z) == this.a.blockID;
	}

	private MinecartTrackLogic getMinecartTrackLogic(ChunkPosition chunkPos) {
		return this.worldObj.getBlockId(chunkPos.x, chunkPos.y, chunkPos.z) == this.a.blockID ? new MinecartTrackLogic(this.a, this.worldObj, chunkPos.x, chunkPos.y, chunkPos.z) : (this.worldObj.getBlockId(chunkPos.x, chunkPos.y + 1, chunkPos.z) == this.a.blockID ? new MinecartTrackLogic(this.a, this.worldObj, chunkPos.x, chunkPos.y + 1, chunkPos.z) : (this.worldObj.getBlockId(chunkPos.x, chunkPos.y - 1, chunkPos.z) == this.a.blockID ? new MinecartTrackLogic(this.a, this.worldObj, chunkPos.x, chunkPos.y - 1, chunkPos.z) : null));
	}

	private boolean isConnectedTo(MinecartTrackLogic minecartTrackLogic) {
		for(int var2 = 0; var2 < this.connectedTracks.size(); ++var2) {
			ChunkPosition var3 = (ChunkPosition)this.connectedTracks.get(var2);
			if(var3.x == minecartTrackLogic.trackX && var3.z == minecartTrackLogic.trackZ) {
				return true;
			}
		}

		return false;
	}

	private boolean isInTrack(int x, int y, int z) {
		for(int var4 = 0; var4 < this.connectedTracks.size(); ++var4) {
			ChunkPosition var5 = (ChunkPosition)this.connectedTracks.get(var4);
			if(var5.x == x && var5.z == z) {
				return true;
			}
		}

		return false;
	}

	public int getAdjacentTracks() {
		int var1 = 0;
		if(this.isMinecartTrack(this.trackX, this.trackY, this.trackZ - 1)) {
			++var1;
		}

		if(this.isMinecartTrack(this.trackX, this.trackY, this.trackZ + 1)) {
			++var1;
		}

		if(this.isMinecartTrack(this.trackX - 1, this.trackY, this.trackZ)) {
			++var1;
		}

		if(this.isMinecartTrack(this.trackX + 1, this.trackY, this.trackZ)) {
			++var1;
		}

		return var1;
	}

	private boolean canConnectTo(MinecartTrackLogic minecartTrackLogic) {
		if(this.isConnectedTo(minecartTrackLogic)) {
			return true;
		} else if(this.connectedTracks.size() == 2) {
			return false;
		} else if(this.connectedTracks.size() == 0) {
			return true;
		} else {
			ChunkPosition var2 = (ChunkPosition)this.connectedTracks.get(0);
			if(minecartTrackLogic.trackY == this.trackY && var2.y == this.trackY) {
			}

			return true;
		}
	}

	private void connectToNeighbor(MinecartTrackLogic minecartTrackLogic) {
		this.connectedTracks.add(new ChunkPosition(minecartTrackLogic.trackX, minecartTrackLogic.trackY, minecartTrackLogic.trackZ));
		boolean var2 = this.isInTrack(this.trackX, this.trackY, this.trackZ - 1);
		boolean var3 = this.isInTrack(this.trackX, this.trackY, this.trackZ + 1);
		boolean var4 = this.isInTrack(this.trackX - 1, this.trackY, this.trackZ);
		boolean var5 = this.isInTrack(this.trackX + 1, this.trackY, this.trackZ);
		byte var6 = -1;
		if(var2 || var3) {
			var6 = 0;
		}

		if(var4 || var5) {
			var6 = 1;
		}

		if(var3 && var5 && !var2 && !var4) {
			var6 = 6;
		}

		if(var3 && var4 && !var2 && !var5) {
			var6 = 7;
		}

		if(var2 && var4 && !var3 && !var5) {
			var6 = 8;
		}

		if(var2 && var5 && !var3 && !var4) {
			var6 = 9;
		}

		if(var6 == 0) {
			if(this.worldObj.getBlockId(this.trackX, this.trackY + 1, this.trackZ - 1) == this.a.blockID) {
				var6 = 4;
			}

			if(this.worldObj.getBlockId(this.trackX, this.trackY + 1, this.trackZ + 1) == this.a.blockID) {
				var6 = 5;
			}
		}

		if(var6 == 1) {
			if(this.worldObj.getBlockId(this.trackX + 1, this.trackY + 1, this.trackZ) == this.a.blockID) {
				var6 = 2;
			}

			if(this.worldObj.getBlockId(this.trackX - 1, this.trackY + 1, this.trackZ) == this.a.blockID) {
				var6 = 3;
			}
		}

		if(var6 < 0) {
			var6 = 0;
		}

		this.worldObj.setBlockMetadataWithNotify(this.trackX, this.trackY, this.trackZ, var6);
	}

	private boolean canConnectFrom(int x, int y, int z) {
		MinecartTrackLogic var4 = this.getMinecartTrackLogic(new ChunkPosition(x, y, z));
		if(var4 == null) {
			return false;
		} else {
			var4.refreshConnectedTracks();
			return var4.canConnectTo(this);
		}
	}

	public void place(boolean powered) {
		boolean var2 = this.canConnectFrom(this.trackX, this.trackY, this.trackZ - 1);
		boolean var3 = this.canConnectFrom(this.trackX, this.trackY, this.trackZ + 1);
		boolean var4 = this.canConnectFrom(this.trackX - 1, this.trackY, this.trackZ);
		boolean var5 = this.canConnectFrom(this.trackX + 1, this.trackY, this.trackZ);
		byte var6 = -1;
		if((var2 || var3) && !var4 && !var5) {
			var6 = 0;
		}

		if((var4 || var5) && !var2 && !var3) {
			var6 = 1;
		}

		if(var3 && var5 && !var2 && !var4) {
			var6 = 6;
		}

		if(var3 && var4 && !var2 && !var5) {
			var6 = 7;
		}

		if(var2 && var4 && !var3 && !var5) {
			var6 = 8;
		}

		if(var2 && var5 && !var3 && !var4) {
			var6 = 9;
		}

		if(var6 == -1) {
			if(var2 || var3) {
				var6 = 0;
			}

			if(var4 || var5) {
				var6 = 1;
			}

			if(powered) {
				if(var3 && var5) {
					var6 = 6;
				}

				if(var4 && var3) {
					var6 = 7;
				}

				if(var5 && var2) {
					var6 = 9;
				}

				if(var2 && var4) {
					var6 = 8;
				}
			} else {
				if(var2 && var4) {
					var6 = 8;
				}

				if(var5 && var2) {
					var6 = 9;
				}

				if(var4 && var3) {
					var6 = 7;
				}

				if(var3 && var5) {
					var6 = 6;
				}
			}
		}

		if(var6 == 0) {
			if(this.worldObj.getBlockId(this.trackX, this.trackY + 1, this.trackZ - 1) == this.a.blockID) {
				var6 = 4;
			}

			if(this.worldObj.getBlockId(this.trackX, this.trackY + 1, this.trackZ + 1) == this.a.blockID) {
				var6 = 5;
			}
		}

		if(var6 == 1) {
			if(this.worldObj.getBlockId(this.trackX + 1, this.trackY + 1, this.trackZ) == this.a.blockID) {
				var6 = 2;
			}

			if(this.worldObj.getBlockId(this.trackX - 1, this.trackY + 1, this.trackZ) == this.a.blockID) {
				var6 = 3;
			}
		}

		if(var6 < 0) {
			var6 = 0;
		}

		this.trackMetadata = var6;
		this.calculateConnectedTracks();
		this.worldObj.setBlockMetadataWithNotify(this.trackX, this.trackY, this.trackZ, var6);

		for(int var7 = 0; var7 < this.connectedTracks.size(); ++var7) {
			MinecartTrackLogic var8 = this.getMinecartTrackLogic((ChunkPosition)this.connectedTracks.get(var7));
			if(var8 != null) {
				var8.refreshConnectedTracks();
				if(var8.canConnectTo(this)) {
					var8.connectToNeighbor(this);
				}
			}
		}

	}

	public int getTrackMetadata() {
		return trackMetadata;
	}
}
