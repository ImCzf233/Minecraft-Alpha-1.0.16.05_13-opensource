package net.minecraft.src;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

class MusInputStream extends InputStream {
	private int hash;
	private InputStream inputStream;
	byte[] buffer;
	final CodecMus codec;

	public MusInputStream(CodecMus codec, URL url, InputStream inputStream) {
		this.codec = codec;
		this.buffer = new byte[1];
		this.inputStream = inputStream;
		String var4 = url.getPath();
		var4 = var4.substring(var4.lastIndexOf("/") + 1);
		this.hash = var4.hashCode();
	}

	public int read() throws IOException {
		int var1 = this.read(this.buffer, 0, 1);
		return var1 < 0 ? var1 : this.buffer[0];
	}

	public int read(byte[] var1, int var2, int var3) throws IOException {
		var3 = this.inputStream.read(var1, var2, var3);

		for(int var4 = 0; var4 < var3; ++var4) {
			byte var5 = var1[var2 + var4] = (byte)(var1[var2 + var4] ^ this.hash >> 8);
			this.hash = this.hash * 498729871 + 85731 * var5;
		}

		return var3;
	}
}
