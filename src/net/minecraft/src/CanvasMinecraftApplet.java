package net.minecraft.src;

import java.awt.Canvas;
import net.minecraft.client.MinecraftApplet;

public class CanvasMinecraftApplet extends Canvas {
	final MinecraftApplet mcApplet;

	public CanvasMinecraftApplet(MinecraftApplet mcApplet) {
		this.mcApplet = mcApplet;
	}

	public synchronized void addNotify() {
		super.addNotify();
		this.mcApplet.startMainThread();
	}

	public synchronized void removeNotify() {
		this.mcApplet.shutdown();
		super.removeNotify();
	}
}
