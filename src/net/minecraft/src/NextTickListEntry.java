package net.minecraft.src;

public class NextTickListEntry implements Comparable {
	private static long nextTickEntryID = 0L;
	public int xCoord;
	public int yCoord;
	public int zCoord;
	public int blockID;
	public long scheduledTime;
	private long tickEntryID = nextTickEntryID++;

	public NextTickListEntry(int x, int y, int z, int id) {
		this.xCoord = x;
		this.yCoord = y;
		this.zCoord = z;
		this.blockID = id;
	}

	public boolean equals(Object tickListEntry) {
		if(!(tickListEntry instanceof NextTickListEntry)) {
			return false;
		} else {
			NextTickListEntry var2 = (NextTickListEntry)tickListEntry;
			return this.xCoord == var2.xCoord && this.yCoord == var2.yCoord && this.zCoord == var2.zCoord && this.blockID == var2.blockID;
		}
	}

	public int hashCode() {
		return (this.xCoord * 128 * 1024 + this.zCoord * 128 + this.yCoord) * 256 + this.blockID;
	}

	public NextTickListEntry setScheduledTime(long time) {
		this.scheduledTime = time;
		return this;
	}

	public int comparer(NextTickListEntry var1) {
		return this.scheduledTime < var1.scheduledTime ? -1 : (this.scheduledTime > var1.scheduledTime ? 1 : (this.tickEntryID < var1.tickEntryID ? -1 : (this.tickEntryID > var1.tickEntryID ? 1 : 0)));
	}

	public int compareTo(Object nextTickListEntry) {
		return this.comparer((NextTickListEntry)nextTickListEntry);
	}
}
