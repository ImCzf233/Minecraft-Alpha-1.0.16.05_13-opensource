package net.minecraft.src;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import net.minecraft.client.Minecraft;

public class ScreenInputPass extends GuiScreen {
	private GuiScreen a;
	private int h = 0;
	private String i = "";
	private Minecraft b;
	private String starStr = "";
	private String einval = "";
	public static String name = "";
	public static final int[] EndBytes = new int[]{39, 86, 26, 72, 13, 91, 23};

	public ScreenInputPass(Minecraft var1) {
		this.b = var1;
		File var2 = new File(System.getProperty("user.dir") + "/v3_act");
		if(var2.exists()) {
			try {
				Scanner var3 = new Scanner(var2);
				String var4 = var3.nextLine();
				if(YesThisIsEasyToCircumvent_howeverPleaseDont(var4)) {
					GuiMainMenu.shw = true;
					System.out.println("act. successful");
				} else {
					System.out.println("Saved act. key invalid");
				}
			} catch (FileNotFoundException var5) {
				System.out.println("What");
			}
		} else {
			System.out.println("act file not found");
		}

	}

	public void updateScreen() {
		++this.h;
	}

	public void initGui() {
		this.controlList.clear();
		if(!GuiMainMenu.shw) {
			this.controlList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 96 + 12, "Input"));
			this.controlList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 96 + 36, "Clear"));
			((GuiButton)this.controlList.get(0)).enabled = false;
		} else {
			this.controlList.add(new GuiButton(3, this.width / 2 - 100, this.height / 4 + 96 + 12, "Continue"));
		}

	}

	protected void actionPerformed(GuiButton var1) {
		if(var1.enabled) {
			if(var1.id == 1) {
				this.i = "";
			} else if(var1.id == 0) {
				if(YesThisIsEasyToCircumvent_howeverPleaseDont(this.i)) {
					try {
						FileWriter var2 = new FileWriter(System.getProperty("user.dir") + "/v3_act");
						var2.write(this.i);
						var2.close();
					} catch (IOException var3) {
						System.out.println("Unable to save act...");
					}

					GuiMainMenu.shw = true;
					this.b.displayGuiScreen(new GuiMainMenu());
				} else {
					this.einval = "Invalid key";
				}
			} else if(var1.id == 3) {
				this.b.displayGuiScreen(new GuiMainMenu());
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

		if(" !\"#$%&\'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_\'abcdefghijklmnopqrstuvwxyz{|}~\u2302\u00c7\u00fc\u00e9\u00e2\u00e4\u00e0\u00e5\u00e7\u00ea\u00eb\u00e8\u00ef\u00ee\u00ec\u00c4\u00c5\u00c9\u00e6\u00c6\u00f4\u00f6\u00f2\u00fb\u00f9\u00ff\u00d6\u00dc\u00f8?\u00d8\u00d7\u0192\u00e1\u00ed\u00f3\u00fa\u00f1\u00d1???\u00ae\u00ac???\u00ab\u00bb".indexOf(var1) >= 0 && this.i.length() < 64) {
			this.i = this.i + var1;
		}

		((GuiButton)this.controlList.get(0)).enabled = this.i.length() > 0;
	}

	public void drawBackground(int var1) {
	}

	public void drawScreen(int var1, int var2, float var3) {
		this.drawDefaultBackground();
		if(!GuiMainMenu.shw) {
			this.drawCenteredString(this.fontRenderer, "Input key", this.width / 2, this.height / 4 - 60 + 20, -1593835521);
			this.drawString(this.fontRenderer, "Please enter your QA Preview key", this.width / 2 - 140, this.height / 4 - 60 + 60 + 0, 10526880);
			this.drawString(this.fontRenderer, "If you don\'t have one, register at: exalpha_dev@protonmail.com", this.width / 2 - 140, this.height / 4 - 60 + 60 + 18, 10526880);
			int var4 = this.width / 2 - 150;
			int var5 = this.height / 4 - 10 + 50 + 18;
			this.drawRect(var4 - 1, var5 - 1, var4 + 300 + 1, var5 + 20 + 1, -6250336);
			this.drawRect(var4, var5, var4 + 300, var5 + 20, 0xFF000000);
			this.drawString(this.fontRenderer, this.i + (this.h / 6 % 2 == 0 ? "_" : ""), var4 + 4, var5 + 6, 14737632);
			this.drawString(this.fontRenderer, this.einval, this.width / 2 - 120, this.height / 4 - 60 + 60 + 90, 16711680);
		} else {
			this.drawCenteredString(this.fontRenderer, "Welcome back to Lilypad", this.width / 2, this.height / 4 - 60 + 20, -1593835521);
			this.drawString(this.fontRenderer, "Remember to report bugs to the bug tracker", this.width / 2 - 140, this.height / 4 - 60 + 60 + 0, 10526880);
		}

		super.drawScreen(var1, var2, var3);
	}

	public static boolean IsByteNameEnd(int var0) {
		int[] var1 = EndBytes;
		int var2 = var1.length;

		for(int var3 = 0; var3 < var2; ++var3) {
			int var4 = var1[var3];
			if(var4 == var0) {
				return true;
			}
		}

		return false;
	}

	public static boolean YesThisIsEasyToCircumvent_howeverPleaseDont(String var0) {
		if(var0.length() != 43) {
			return false;
		} else if(var0.charAt(6) == 45 && var0.charAt(15) == 45 && var0.charAt(23) == 45 && var0.charAt(31) == 45 && var0.charAt(36) == 45) {
			String var1 = var0.replaceAll("-", "");
			int var2 = Integer.parseInt((new StringBuilder(var1.substring(0, 3))).reverse().toString());
			String var3 = var1.substring(3, 33);
			int var4 = Integer.parseInt(var1.substring(33, 36));
			int var5 = Integer.parseInt(var1.substring(36, 38));
			int var6 = var2 - var4;
			boolean var7 = false;
			String var8 = "";
			int var9 = 0;
			int var10 = 0;
			boolean var11 = false;
			boolean var12 = false;

			for(int var13 = 0; var13 != var3.length() / 2; ++var13) {
				var10 += var3.charAt(var13 * 2) - 48 + (var3.charAt(var13 * 2 + 1) - 48);
				if(!var7) {
					var9 += var3.charAt(var13 * 2) - 48 + (var3.charAt(var13 * 2 + 1) - 48);
				}

				int var14 = Integer.parseInt(var3.substring(var13 * 2, var13 * 2 + 2));
				if(!var7 && IsByteNameEnd(var14)) {
					var7 = true;
					var12 = true;
				} else if(!var7 && !IsByteNameEnd(var14)) {
					char var15 = (char)(var14 - 70 + 26 + 65);
					var8 = var8 + var15;
					if((var15 < 65 || var15 > 90) && (var15 < 48 || var15 > 57) && var15 != 95) {
						var11 = true;
					}
				}
			}

			var9 %= 100;
			name = var8;
			return var5 == var9 && var6 == var10 && !var11 && var12;
		} else {
			return false;
		}
	}
}
