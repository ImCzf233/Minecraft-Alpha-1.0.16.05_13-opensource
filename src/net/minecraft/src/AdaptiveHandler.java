package net.minecraft.src;

import adaptive.ds5java.DS5Connection;
import adaptive.ds5java.DS5InstrPlayerUpdate;
import adaptive.ds5java.DS5InstrRGBUpdate;
import adaptive.ds5java.DS5InstrTriggerUpdate;
import adaptive.ds5java.DS5Packet;

public class AdaptiveHandler extends Thread {
	public DS5Connection ds5 = new DS5Connection();
	public boolean DS5enabled = false;
	public DS5Packet ds5_conf_default = new DS5Packet();
	public DS5Packet ds5_conf_ingame_aimblock = new DS5Packet();
	public DS5Packet ds5_conf_ingame_aimentity = new DS5Packet();
	public DS5Packet ds5_conf_ingame_aimnone = new DS5Packet();
	public long lastPacketSent = 0L;
	public int lastPacketType = -1;

	public void ConstructDefaultPacket() {
		this.ds5_conf_default.AddInstruction(new DS5InstrRGBUpdate(0, 0, 0, 255));
		this.ds5_conf_default.AddInstruction(new DS5InstrPlayerUpdate(0, false, false, true, false, false));
		this.ds5_conf_default.AddInstruction(new DS5InstrTriggerUpdate(0, DS5InstrTriggerUpdate.TriggerIndex.Left, DS5InstrTriggerUpdate.TriggerMode.Normal, new int[]{0}));
		this.ds5_conf_default.AddInstruction(new DS5InstrTriggerUpdate(0, DS5InstrTriggerUpdate.TriggerIndex.Right, DS5InstrTriggerUpdate.TriggerMode.Normal, new int[]{0}));
		this.ds5_conf_ingame_aimblock.AddInstruction(new DS5InstrRGBUpdate(0, 0, 0, 0));
		this.ds5_conf_ingame_aimblock.AddInstruction(new DS5InstrPlayerUpdate(0, false, false, true, false, false));
		this.ds5_conf_ingame_aimblock.AddInstruction(new DS5InstrTriggerUpdate(0, DS5InstrTriggerUpdate.TriggerIndex.Left, DS5InstrTriggerUpdate.TriggerMode.VibrateTrigger, new int[]{3}));
		this.ds5_conf_ingame_aimblock.AddInstruction(new DS5InstrTriggerUpdate(0, DS5InstrTriggerUpdate.TriggerIndex.Right, DS5InstrTriggerUpdate.TriggerMode.AutomaticGun, new int[]{4, 8, 5}));
		this.ds5_conf_ingame_aimentity.AddInstruction(new DS5InstrRGBUpdate(0, 0, 0, 0));
		this.ds5_conf_ingame_aimentity.AddInstruction(new DS5InstrPlayerUpdate(0, false, false, true, false, false));
		this.ds5_conf_ingame_aimentity.AddInstruction(new DS5InstrTriggerUpdate(0, DS5InstrTriggerUpdate.TriggerIndex.Left, DS5InstrTriggerUpdate.TriggerMode.Normal, new int[]{0}));
		this.ds5_conf_ingame_aimentity.AddInstruction(new DS5InstrTriggerUpdate(0, DS5InstrTriggerUpdate.TriggerIndex.Right, DS5InstrTriggerUpdate.TriggerMode.Bow, new int[]{3, 6, 1, 1}));
		this.ds5_conf_ingame_aimnone.AddInstruction(new DS5InstrRGBUpdate(0, 0, 0, 0));
		this.ds5_conf_ingame_aimnone.AddInstruction(new DS5InstrPlayerUpdate(0, false, false, true, false, false));
		this.ds5_conf_ingame_aimnone.AddInstruction(new DS5InstrTriggerUpdate(0, DS5InstrTriggerUpdate.TriggerIndex.Left, DS5InstrTriggerUpdate.TriggerMode.Normal, new int[]{0}));
		this.ds5_conf_ingame_aimnone.AddInstruction(new DS5InstrTriggerUpdate(0, DS5InstrTriggerUpdate.TriggerIndex.Right, DS5InstrTriggerUpdate.TriggerMode.Normal, new int[]{0}));
	}

	public void UpdatePLEDBasedOnDiff(DS5Packet var1) {
		DS5InstrPlayerUpdate var2 = (DS5InstrPlayerUpdate)var1.ds5Instructions.get(1);
		boolean var3;
		boolean var4;
		boolean var5;
		boolean var6;
		boolean var7;
		switch(InputHandler.mc.options.difficulty) {
		case 0:
			var7 = false;
			var6 = false;
			var4 = false;
			var3 = false;
			var5 = true;
			break;
		case 1:
			var7 = false;
			var5 = false;
			var3 = false;
			var6 = true;
			var4 = true;
			break;
		case 2:
			var6 = false;
			var4 = false;
			var7 = true;
			var5 = true;
			var3 = true;
			break;
		case 3:
			var5 = false;
			var7 = true;
			var6 = true;
			var4 = true;
			var3 = true;
			break;
		case 4:
			var7 = true;
			var6 = true;
			var5 = true;
			var4 = true;
			var3 = true;
			break;
		default:
			var7 = false;
			var6 = false;
			var5 = false;
			var4 = false;
			var3 = false;
		}

		var2.p1 = var3;
		var2.p2 = var4;
		var2.p3 = var5;
		var2.p4 = var6;
		var2.p5 = var7;
	}

	public void UpdateIngamePacket(DS5Packet var1) {
		DS5InstrRGBUpdate var2 = (DS5InstrRGBUpdate)var1.ds5Instructions.get(0);
		this.UpdatePLEDBasedOnDiff(var1);
		if(InputHandler.mc.options.difficulty == 4 && (float)InputHandler.mc.thePlayer.health != 0.0F) {
			float var6 = (float)(System.currentTimeMillis() % 2000L) / 1000.0F;
			float var4 = 0.5F + (var6 <= 1.0F ? 0.5F * var6 : (1.0F - (var6 - 1.0F)) * 0.5F);
			int var5 = (int)(255.0F * var4);
			var2.r = var2.g = var2.b = var5;
		} else if((float)InputHandler.mc.thePlayer.health <= 4.0F && System.currentTimeMillis() % 2000L >= 1000L) {
			var2.r = var2.g = var2.b = 0;
		} else {
			float[] var3 = ColorUtil.BlendColorA((float)InputHandler.mc.thePlayer.health / 20.0F, 1.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F);
			var2.r = (int)(var3[0] * 255.0F);
			var2.g = (int)(var3[1] * 255.0F);
			var2.b = (int)(var3[2] * 255.0F);
		}
	}

	public void SendPacket() {
		if(System.currentTimeMillis() - this.lastPacketSent > 16L) {
			this.lastPacketSent = System.currentTimeMillis();
			if((InputHandler.mc == null || InputHandler.mc.thePlayer == null) && this.lastPacketType != 0) {
				this.ds5.Send(this.ds5_conf_default);
				this.lastPacketType = 0;
			} else if(InputHandler.mc.thePlayer != null) {
				this.lastPacketType = 1;
				DS5Packet var1 = null;
				if(InputHandler.mc.objectMouseOver != null) {
					switch(InputHandler.mc.objectMouseOver.typeOfHit) {
					case 0:
						var1 = this.ds5_conf_ingame_aimblock;
						break;
					case 1:
						var1 = this.ds5_conf_ingame_aimentity;
					}
				} else {
					var1 = this.ds5_conf_ingame_aimnone;
				}

				this.UpdateIngamePacket(var1);
				this.ds5.Send(var1);
			}
		}

	}

	public AdaptiveHandler() {
		this.ConstructDefaultPacket();
	}

	public void run() {
		System.out.println("");
		if(this.ds5.Connect()) {
			while(true) {
				this.SendPacket();

				try {
					Thread.sleep(64L);
				} catch (InterruptedException var2) {
				}
			}
		}

	}
}
