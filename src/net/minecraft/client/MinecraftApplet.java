package net.minecraft.client;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Canvas;
import net.minecraft.src.CanvasMinecraftApplet;
import net.minecraft.src.MinecraftAppletImpl;
import net.minecraft.src.Session;

public class MinecraftApplet extends Applet {
	private Canvas mcCanvas;
	private Minecraft mc;
	private Thread mcThread = null;

	public void init() {
		this.mcCanvas = new CanvasMinecraftApplet(this);
		boolean var1 = false;
		if(this.getParameter("fullscreen") != null) {
			var1 = this.getParameter("fullscreen").equalsIgnoreCase("true");
		}

		this.mc = new MinecraftAppletImpl(this, this, this.mcCanvas, this, this.getWidth(), this.getHeight(), var1);
		this.mc.minecraftUri = this.getDocumentBase().getHost();
		if(this.getDocumentBase().getPort() > 0) {
			this.mc.minecraftUri = this.mc.minecraftUri + ":" + this.getDocumentBase().getPort();
		}

		if(this.getParameter("username") != null && this.getParameter("sessionid") != null) {
			this.mc.session = new Session(this.getParameter("username"), this.getParameter("sessionid"));
			System.out.println("Setting user: " + this.mc.session.username + ", " + this.mc.session.sessionId);
			if(this.getParameter("mppass") != null) {
				this.mc.session.mpPassParameter = this.getParameter("mppass");
			}
		} else {
			this.mc.session = new Session("Player", "");
		}

		if(this.getParameter("loadmap_user") != null && this.getParameter("loadmap_id") != null) {
			this.mc.loadMapUser = this.getParameter("loadmap_user");
			this.mc.loadMapID = Integer.parseInt(this.getParameter("loadmap_id"));
		} else if(this.getParameter("server") != null && this.getParameter("port") != null) {
			this.mc.setServer(this.getParameter("server"), Integer.parseInt(this.getParameter("port")));
		}

		this.mc.appletMode = true;
		this.setLayout(new BorderLayout());
		this.add(this.mcCanvas, "Center");
		this.mcCanvas.setFocusable(true);
		this.validate();
	}

	public void startMainThread() {
		if(this.mcThread == null) {
			this.mcThread = new Thread(this.mc, "Minecraft main thread");
			this.mcThread.start();
		}
	}

	public void start() {
		if(this.mc != null) {
			this.mc.isGamePaused = false;
		}

	}

	public void stop() {
		if(this.mc != null) {
			this.mc.isGamePaused = true;
		}

	}

	public void destroy() {
		this.shutdown();
	}

	public void shutdown() {
		if(this.mcThread != null) {
			this.mc.shutdown();

			try {
				this.mcThread.join(10000L);
			} catch (InterruptedException var4) {
				try {
					this.mc.shutdownMinecraftApplet();
				} catch (Exception var3) {
					var3.printStackTrace();
				}
			}

			this.mcThread = null;
		}
	}

	public void clearApplet() {
		this.mcCanvas = null;
		this.mc = null;
		this.mcThread = null;

		try {
			this.removeAll();
			this.validate();
		} catch (Exception var2) {
		}

	}
}
