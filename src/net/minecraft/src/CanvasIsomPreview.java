package net.minecraft.src;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.awt.image.ImageObserver;
import java.io.File;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class CanvasIsomPreview extends Canvas implements KeyListener, MouseListener, MouseMotionListener, Runnable {
	private int currentRender = 0;
	private int zoomLevel = 2;
	private boolean displayHelpText = true;
	private World level;
	private File dataFolder = this.getWorkingDirectory();
	private boolean running = true;
	private List zonesToRender = Collections.synchronizedList(new LinkedList());
	private IsoImageBuffer[][] zoneMap = new IsoImageBuffer[64][64];
	private int translateX;
	private int translateY;
	private int xPosition;
	private int yPosition;

	public File getWorkingDirectory() {
		if(this.dataFolder == null) {
			this.dataFolder = this.getWorkingDirectory("minecraft");
		}

		return this.dataFolder;
	}

	public File getWorkingDirectory(String name) {
		String var2 = System.getProperty("user.home", ".");
		File var3;
		switch(CanvasIsomPreview.SyntheticClass_1.$SwitchMap$net$minecraft$src$EnumOSIsom[getPlatform().ordinal()]) {
		case 1:
		case 2:
			var3 = new File(var2, '.' + name + '/');
			break;
		case 3:
			String var4 = System.getenv("APPDATA");
			if(var4 != null) {
				var3 = new File(var4, "." + name + '/');
			} else {
				var3 = new File(var2, '.' + name + '/');
			}
			break;
		case 4:
			var3 = new File(var2, "Library/Application Support/" + name);
			break;
		default:
			var3 = new File(var2, name + '/');
		}

		if(!var3.exists() && !var3.mkdirs()) {
			throw new RuntimeException("The working directory could not be created: " + var3);
		} else {
			return var3;
		}
	}

	private static EnumOSIsom getPlatform() {
		String var0 = System.getProperty("os.name").toLowerCase();
		return var0.contains("win") ? EnumOSIsom.windows : (var0.contains("mac") ? EnumOSIsom.macos : (var0.contains("solaris") ? EnumOSIsom.solaris : (var0.contains("sunos") ? EnumOSIsom.solaris : (var0.contains("linux") ? EnumOSIsom.linux : (var0.contains("unix") ? EnumOSIsom.linux : EnumOSIsom.unknown)))));
	}

	public CanvasIsomPreview() {
		for(int var1 = 0; var1 < 64; ++var1) {
			for(int var2 = 0; var2 < 64; ++var2) {
				this.zoneMap[var1][var2] = new IsoImageBuffer((World)null, var1, var2);
			}
		}

		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.addKeyListener(this);
		this.setFocusable(true);
		this.requestFocus();
		this.setBackground(Color.red);
	}

	public void loadLevel(String levelName) {
		this.translateX = this.translateY = 0;
		this.level = new WorldIso(this, new File(this.dataFolder, "saves"), levelName);
		this.level.skylightSubtracted = 0;
		List var2 = this.zonesToRender;
		synchronized(this.zonesToRender) {
			this.zonesToRender.clear();

			for(int var3 = 0; var3 < 64; ++var3) {
				for(int var4 = 0; var4 < 64; ++var4) {
					this.zoneMap[var3][var4].setLevel(this.level, var3, var4);
				}
			}

		}
	}

	private void setBrightness(int brightness) {
		List var2 = this.zonesToRender;
		synchronized(this.zonesToRender) {
			this.level.skylightSubtracted = brightness;
			this.zonesToRender.clear();

			for(int var3 = 0; var3 < 64; ++var3) {
				for(int var4 = 0; var4 < 64; ++var4) {
					this.zoneMap[var3][var4].setLevel(this.level, var3, var4);
				}
			}

		}
	}

	public void start() {
		(new ThreadRunIsoClient(this)).start();

		for(int var1 = 0; var1 < 8; ++var1) {
			(new Thread(this)).start();
		}

	}

	public void stop() {
		this.running = false;
	}

	private IsoImageBuffer getZone(int x, int z) {
		int var3 = x & 63;
		int var4 = z & 63;
		IsoImageBuffer var5 = this.zoneMap[var3][var4];
		if(var5.x == x && var5.y == z) {
			return var5;
		} else {
			List var6 = this.zonesToRender;
			synchronized(this.zonesToRender) {
				this.zonesToRender.remove(var5);
			}

			var5.init(x, z);
			return var5;
		}
	}

	public void run() {
		TerrainTextureManager var1 = new TerrainTextureManager();

		while(this.running) {
			IsoImageBuffer var2 = null;
			List var3 = this.zonesToRender;
			synchronized(this.zonesToRender) {
				if(this.zonesToRender.size() > 0) {
					var2 = (IsoImageBuffer)this.zonesToRender.remove(0);
				}
			}

			if(var2 != null) {
				if(this.currentRender - var2.lastVisible < 2) {
					var1.render(var2);
					this.repaint();
				} else {
					var2.addedToRenderQueue = false;
				}
			}

			try {
				Thread.sleep(2L);
			} catch (InterruptedException var5) {
				var5.printStackTrace();
			}
		}

	}

	public void update(Graphics graphics) {
	}

	public void paint(Graphics graphics) {
	}

	public void render() {
		BufferStrategy var1 = this.getBufferStrategy();
		if(var1 == null) {
			this.createBufferStrategy(2);
		} else {
			this.render((Graphics2D)var1.getDrawGraphics());
			var1.show();
		}
	}

	public void render(Graphics2D graphics2D) {
		++this.currentRender;
		AffineTransform var2 = graphics2D.getTransform();
		graphics2D.setClip(0, 0, this.getWidth(), this.getHeight());
		graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
		graphics2D.translate(this.getWidth() / 2, this.getHeight() / 2);
		graphics2D.scale((double)this.zoomLevel, (double)this.zoomLevel);
		graphics2D.translate(this.translateX, this.translateY);
		if(this.level != null) {
			graphics2D.translate(-(this.level.spawnX + this.level.spawnZ), -(-this.level.spawnX + this.level.spawnZ) + 64);
		}

		Rectangle var3 = graphics2D.getClipBounds();
		graphics2D.setColor(new Color(-15724512));
		graphics2D.fillRect(var3.x, var3.y, var3.width, var3.height);
		byte var4 = 16;
		byte var5 = 3;
		int var6 = var3.x / var4 / 2 - 2 - var5;
		int var7 = (var3.x + var3.width) / var4 / 2 + 1 + var5;
		int var8 = var3.y / var4 - 1 - var5 * 2;
		int var9 = (var3.y + var3.height + 16 + 128) / var4 + 1 + var5 * 2;

		int var10;
		for(var10 = var8; var10 <= var9; ++var10) {
			for(int var11 = var6; var11 <= var7; ++var11) {
				int var12 = var11 - (var10 >> 1);
				int var13 = var11 + (var10 + 1 >> 1);
				IsoImageBuffer var14 = this.getZone(var12, var13);
				var14.lastVisible = this.currentRender;
				if(!var14.rendered) {
					if(!var14.addedToRenderQueue) {
						var14.addedToRenderQueue = true;
						this.zonesToRender.add(var14);
					}
				} else {
					var14.addedToRenderQueue = false;
					if(!var14.noContent) {
						int var15 = var11 * var4 * 2 + (var10 & 1) * var4;
						int var16 = var10 * var4 - 128 - 16;
						graphics2D.drawImage(var14.image, var15, var16, (ImageObserver)null);
					}
				}
			}
		}

		if(this.displayHelpText) {
			graphics2D.setTransform(var2);
			var10 = this.getHeight() - 32 - 4;
			graphics2D.setColor(new Color(Integer.MIN_VALUE, true));
			graphics2D.fillRect(4, this.getHeight() - 32 - 4, this.getWidth() - 8, 32);
			graphics2D.setColor(Color.WHITE);
			String var17 = "F1 - F5: load levels   |   0-9: Set time of day   |   Space: return to spawn   |   Double click: zoom   |   Escape: hide this text";
			graphics2D.drawString(var17, this.getWidth() / 2 - graphics2D.getFontMetrics().stringWidth(var17) / 2, var10 + 20);
		}

		graphics2D.dispose();
	}

	public void mouseDragged(MouseEvent mouseEvent) {
		int var2 = mouseEvent.getX() / this.zoomLevel;
		int var3 = mouseEvent.getY() / this.zoomLevel;
		this.translateX += var2 - this.xPosition;
		this.translateY += var3 - this.yPosition;
		this.xPosition = var2;
		this.yPosition = var3;
		this.repaint();
	}

	public void mouseMoved(MouseEvent mouseEvent) {
	}

	public void mouseClicked(MouseEvent mouseEvent) {
		if(mouseEvent.getClickCount() == 2) {
			this.zoomLevel = 3 - this.zoomLevel;
			this.repaint();
		}

	}

	public void mouseEntered(MouseEvent mouseEvent) {
	}

	public void mouseExited(MouseEvent mouseEvent) {
	}

	public void mousePressed(MouseEvent mouseEvent) {
		int var2 = mouseEvent.getX() / this.zoomLevel;
		int var3 = mouseEvent.getY() / this.zoomLevel;
		this.xPosition = var2;
		this.yPosition = var3;
	}

	public void mouseReleased(MouseEvent mouseEvent) {
	}

	public void keyPressed(KeyEvent keyEvent) {
		if(keyEvent.getKeyCode() == 48) {
			this.setBrightness(11);
		}

		if(keyEvent.getKeyCode() == 49) {
			this.setBrightness(10);
		}

		if(keyEvent.getKeyCode() == 50) {
			this.setBrightness(9);
		}

		if(keyEvent.getKeyCode() == 51) {
			this.setBrightness(7);
		}

		if(keyEvent.getKeyCode() == 52) {
			this.setBrightness(6);
		}

		if(keyEvent.getKeyCode() == 53) {
			this.setBrightness(5);
		}

		if(keyEvent.getKeyCode() == 54) {
			this.setBrightness(3);
		}

		if(keyEvent.getKeyCode() == 55) {
			this.setBrightness(2);
		}

		if(keyEvent.getKeyCode() == 56) {
			this.setBrightness(1);
		}

		if(keyEvent.getKeyCode() == 57) {
			this.setBrightness(0);
		}

		if(keyEvent.getKeyCode() == 112) {
			this.loadLevel("World1");
		}

		if(keyEvent.getKeyCode() == 113) {
			this.loadLevel("World2");
		}

		if(keyEvent.getKeyCode() == 114) {
			this.loadLevel("World3");
		}

		if(keyEvent.getKeyCode() == 115) {
			this.loadLevel("World4");
		}

		if(keyEvent.getKeyCode() == 116) {
			this.loadLevel("World5");
		}

		if(keyEvent.getKeyCode() == 32) {
			this.translateX = this.translateY = 0;
		}

		if(keyEvent.getKeyCode() == 27) {
			this.displayHelpText = !this.displayHelpText;
		}

		this.repaint();
	}

	public void keyReleased(KeyEvent keyEvent) {
	}

	public void keyTyped(KeyEvent keyEvent) {
	}

	static boolean isRunning(CanvasIsomPreview var0) {
		return var0.running;
	}

	static final class SyntheticClass_1 {
		static final int[] $SwitchMap$net$minecraft$src$EnumOSIsom = new int[EnumOSIsom.values().length];

		static {
			try {
				$SwitchMap$net$minecraft$src$EnumOSIsom[EnumOSIsom.linux.ordinal()] = 1;
			} catch (NoSuchFieldError var4) {
			}

			try {
				$SwitchMap$net$minecraft$src$EnumOSIsom[EnumOSIsom.solaris.ordinal()] = 2;
			} catch (NoSuchFieldError var3) {
			}

			try {
				$SwitchMap$net$minecraft$src$EnumOSIsom[EnumOSIsom.windows.ordinal()] = 3;
			} catch (NoSuchFieldError var2) {
			}

			try {
				$SwitchMap$net$minecraft$src$EnumOSIsom[EnumOSIsom.macos.ordinal()] = 4;
			} catch (NoSuchFieldError var1) {
			}

		}
	}
}
