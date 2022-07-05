package net.minecraft.src;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import net.minecraft.client.Minecraft;

public class ScreenKeyInput extends GuiScreen {
	private GuiScreen a;
	private int h = 0;
	private String i = "";
	private Minecraft b;
	private String starStr = "";
	private String einval = "";
	public static String name = "";
	private int keyIndex;
	static String str = "/X*Y).)W/\'\'[YX-Y(\'\\,\'\'[W&.\\,/Z+.Z(+Z/+&\',+.\'-X+/X[\\Z[\\(/-.\\&\'X\\&-";
	String[] md5s = new String[]{"", "676655d0337e63a3e0abd30e683ce639", "f816352e775a85592718d5d016888a33", "63bde9809ecbf1e3f1d4613b3ae6a88a", "ab99d76f9a4c41a0f3591b122bbc985c", "21741b9e1076d281b58c881ef6315c86", "9892a43b1ceb0f2175663fef1120d824", "0b8ffcdae74fab65891d2d2ba7e14707", "4da0a7353e9ec58890d81f135d53af1c"};
	String[] sha256s = new String[]{"", "65d9ac073f4da8453b9ad134a137620a816579be10b5e561ab2d3d098d5d0f4e", "155469cf5bf319cf4c134b65c76259859e5b0b69a581fa5f89b29b90f9455d78", "6de43140b9a682893a37c1528e89301313865bccbd6b7f93a6fc533e71224050", "4807e2b120d5b060aeca3f1e29be86a6a0b12ba7f9ac0fa196f7e718b35046eb", "9dd68e36a9b1f6e3869681d6e8c1b82b55e9172c833216bed5ddca78b57ec63f", "61bc906a6896d1cbd8310b52419a2993c114eeafa8f4c5bbe8da61a5d18b73f0", "c74329a1eef811c3affedba7645099d10c0f6441b78f73a893ccb405abe319ab", "a87527c2d10fbbe74917474b60f2fe44619ef6c6d6260488ae44e6a5ddd27842"};

	public ScreenKeyInput(Minecraft var1, int var2) {
		this.b = var1;
		this.keyIndex = var2;
	}

	public static int playerIndex() {
		int var0 = 0;

		for(int var1 = ScreenInputPass.name.length(); var1-- > 0; var0 += ScreenInputPass.name.toCharArray()[var1] - 48) {
		}

		return var0 % 5;
	}

	public static String calcString(long var0, int var2) {
		long var3 = ~(~(~(~(~(~(~(~var0))))))) >> ~(~(~(~(~(~(~(~(64 - (str.toCharArray()[str.length() - 1] - "+".toCharArray()[0] + 6)))))))));
		char[] var5 = str.substring((int)var3 * 13, (int)var3 * 13 + 13).toCharArray();

		for(int var6 = 13; ~(~(~(~(var6--)))) > 0; var5[var6] = (char)((int)((long)var5[var6] - -(~(~var3) + (long)(~var2) + (long)str.toCharArray()[str.length() - 1] - (long)"+".toCharArray()[0])))) {
		}

		return (new String(var5)).substring(~(~str.toCharArray()[0]) ^ 47);
	}

	public static String byteArrayToHex(byte[] var0) {
		StringBuilder var1 = new StringBuilder(var0.length * 2);
		byte[] var2 = var0;
		int var3 = var0.length;

		for(int var4 = 0; var4 < var3; ++var4) {
			byte var5 = var2[var4];
			var1.append(String.format("%02x", new Object[]{Byte.valueOf(var5)}));
		}

		return var1.toString();
	}

	private boolean KeyValid(String var1) {
		try {
			var1 = this.keyIndex + var1;
			MessageDigest var2 = MessageDigest.getInstance("SHA-256");
			MessageDigest var3 = MessageDigest.getInstance("MD5");
			byte[] var4 = var2.digest(var1.getBytes(StandardCharsets.UTF_8));
			byte[] var5 = var3.digest(var1.getBytes(StandardCharsets.UTF_8));
			return byteArrayToHex(var4).equals(this.sha256s[this.keyIndex]) && byteArrayToHex(var5).equals(this.md5s[this.keyIndex]);
		} catch (NoSuchAlgorithmException var6) {
			System.out.println("Unknown error");
			return false;
		}
	}

	public void updateScreen() {
		++this.h;
	}

	public void initGui() {
		this.controlList.clear();
		this.controlList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 96 + 12, "Input"));
		((GuiButton)this.controlList.get(0)).enabled = false;
	}

	protected void actionPerformed(GuiButton var1) {
		if(var1.enabled) {
			if(var1.id == 0 && this.KeyValid(this.i)) {
				if(this.keyIndex == 8) {
					System.out.println(this.sha256s[playerIndex() * 4 - playerIndex() * 3 + playerIndex() * 0 - playerIndex() + 7]);
				} else {
					if(this.keyIndex == 3) {
						System.out.println(this.md5s[4]);
					}

					this.b.displayGuiScreen(new ScreenKeyInput(this.b, this.keyIndex + 1));
				}
			}

		}
	}

	protected void keyTyped(char var1, int var2) {
		if(var1 == 22) {
			String var3 = GuiScreen.getClipboardString();
			if(var3 == null) {
				var3 = "";
			}

			int var4 = 64 - this.i.length();
			if(var4 > var3.length()) {
				var4 = var3.length();
			}

			if(var4 > 0) {
				this.i = this.i + var3.substring(0, var4);
			}
		}

		if(var1 == 13) {
			this.actionPerformed((GuiButton)this.controlList.get(0));
		}

		if(var2 == 14 && this.i.length() > 0) {
			this.i = this.i.substring(0, this.i.length() - 1);
		}

		if(" !\"#$%&\'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_\'abcdefghijklmnopqrstuvwxyz{|}".indexOf(var1) >= 0 && this.i.length() < 64) {
			this.i = this.i + var1;
		}

		((GuiButton)this.controlList.get(0)).enabled = this.i.length() > 0;
		super.keyTyped(var1, var2);
	}

	public boolean doesGuiPauseGame() {
		return false;
	}

	public void drawScreen(int var1, int var2, float var3) {
		this.drawDefaultBackground();
		this.drawCenteredString(this.fontRenderer, "Input " + this.keyIndex, this.width / 2, this.height / 4 - 60 + 20, -1593835521);
		int var4 = this.width / 2 - 70;
		int var5 = this.height / 4 - 10 + 50 + 18;
		this.drawRect(var4 - 1, var5 - 1, var4 + 100 + 1, var5 + 20 + 1, -6250336);
		this.drawRect(var4, var5, var4 + 100, var5 + 20, 0xFF000000);
		this.drawString(this.fontRenderer, this.i + (this.h / 6 % 2 == 0 ? "|" : ""), var4 + 4, var5 + 6, 14737632);
		this.drawString(this.fontRenderer, this.einval, this.width / 2 - 120, this.height / 4 - 60 + 60 + 90, 16711680);
		super.drawScreen(var1, var2, var3);
	}
}
