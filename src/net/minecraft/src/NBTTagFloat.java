package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagFloat extends NBTBase {
	public float floatValue;

	public NBTTagFloat() {
	}

	public NBTTagFloat(float value) {
		this.floatValue = value;
	}

	void writeTagContents(DataOutput var1) throws IOException {
		var1.writeFloat(this.floatValue);
	}

	void readTagContents(DataInput var1) throws IOException {
		this.floatValue = var1.readFloat();
	}

	public byte getType() {
		return (byte)5;
	}

	public String toString() {
		return "" + this.floatValue;
	}
}
