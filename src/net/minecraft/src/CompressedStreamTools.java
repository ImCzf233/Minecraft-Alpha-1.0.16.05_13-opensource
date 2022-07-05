package net.minecraft.src;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class CompressedStreamTools {
	public static NBTTagCompound readCompressed(InputStream inputStream) throws IOException {
		DataInputStream var1 = new DataInputStream(new GZIPInputStream(inputStream));

		NBTTagCompound var2;
		try {
			var2 = read(var1);
		} finally {
			var1.close();
		}

		return var2;
	}

	public static void writeCompressed(NBTTagCompound compoundTag, OutputStream outputStream) throws IOException {
		DataOutputStream var2 = new DataOutputStream(new GZIPOutputStream(outputStream));

		try {
			write(compoundTag, var2);
		} finally {
			var2.close();
		}

	}

	public static NBTTagCompound decompress(byte[] data) throws IOException {
		DataInputStream var1 = new DataInputStream(new GZIPInputStream(new ByteArrayInputStream(data)));

		NBTTagCompound var2;
		try {
			var2 = read(var1);
		} finally {
			var1.close();
		}

		return var2;
	}

	public static byte[] compress(NBTTagCompound compoundTag) throws IOException {
		ByteArrayOutputStream var1 = new ByteArrayOutputStream();
		DataOutputStream var2 = new DataOutputStream(new GZIPOutputStream(var1));

		try {
			write(compoundTag, var2);
		} finally {
			var2.close();
		}

		return var1.toByteArray();
	}

	public static NBTTagCompound read(DataInput dataInput) throws IOException {
		NBTBase var1 = NBTBase.readNamedTag(dataInput);
		if(var1 instanceof NBTTagCompound) {
			return (NBTTagCompound)var1;
		} else {
			throw new IOException("Root tag must be a named compound tag");
		}
	}

	public static void write(NBTTagCompound compoundTag, DataOutput dataOutput) throws IOException {
		NBTBase.writeNamedTag(compoundTag, dataOutput);
	}
}
