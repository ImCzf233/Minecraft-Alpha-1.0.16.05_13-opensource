package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagLong extends NBTBase {
	public long longValue;

	public NBTTagLong() {
	}

	public NBTTagLong(long value) {
		this.longValue = value;
	}

	void writeTagContents(DataOutput var1) throws IOException {
		var1.writeLong(this.longValue);
	}

	void readTagContents(DataInput var1) throws IOException {
		this.longValue = var1.readLong();
	}

	public byte getType() {
		return (byte)4;
	}

	public String toString() {
		return "" + this.longValue;
	}
}
