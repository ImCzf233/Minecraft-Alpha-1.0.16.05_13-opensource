package net.minecraft.src;

import org.lwjgl.opengl.GL11;

public class TextureFX {
	public byte[] imageData = new byte[1024];
	public int iconIndex;
	public boolean anaglyphEnabled = false;
	public int textureId = 0;
	public int tileSize = 1;
	public int tileImage = 0;

	public TextureFX(int index) {
		this.iconIndex = index;
	}

	public void onTick() {
	}

	public void bindImage(RenderEngine renderEngine) {
		if(this.tileImage == 0) {
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, renderEngine.getTexture("/terrain.png"));
		} else if(this.tileImage == 1) {
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, renderEngine.getTexture("/gui/items.png"));
		}

	}
}
