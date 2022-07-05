package net.minecraft.src;

import net.minecraft.client.Minecraft;

public class ThreadSleepForever extends Thread {
	final Minecraft mc;

	public ThreadSleepForever(Minecraft minecraft, String name) {
		super(name);
		this.mc = minecraft;
		this.setDaemon(true);
		this.start();
	}

	public void run() {
		while(this.mc.running) {
			try {
				Thread.sleep(2147483647L);
			} catch (InterruptedException var2) {
			}
		}

	}
}
