package net.minecraft.src;

import org.lwjgl.opengl.GL11;

public class RenderPlayer extends RenderLiving<EntityPlayer> {
	private ModelBiped modelBipedMain = (ModelBiped)this.mainModel;
	private ModelBiped modelArmorChestplate = new ModelBiped(1.0F);
	private ModelBiped modelArmor = new ModelBiped(0.5F);
	private static final String[] armorFilenamePrefix = new String[]{"cloth", "chain", "iron", "diamond", "gold", "obsidian"};

	public RenderPlayer() {
		super(new ModelBiped(0.0F), 0.5F);
	}

	protected boolean shouldRenderPass(EntityPlayer var1, int var2) {
		ItemStack var3 = var1.inventory.armorItemInSlot(3 - var2);
		if(var3 != null) {
			Item var4 = var3.getItem();
			if(var4 instanceof ItemArmor) {
				ItemArmor var5 = (ItemArmor)var4;
				this.loadTexture("/armor/" + armorFilenamePrefix[var5.renderIndex] + "_" + (var2 == 2 ? 2 : 1) + ".png");
				ModelBiped var6 = var2 == 2 ? this.modelArmor : this.modelArmorChestplate;
				var6.bipedHead.showModel = var2 == 0;
				var6.bipedHeadwear.showModel = var2 == 0;
				var6.bipedBody.showModel = var2 == 1 || var2 == 2;
				var6.bipedRightArm.showModel = var2 == 1;
				var6.bipedLeftArm.showModel = var2 == 1;
				var6.bipedRightLeg.showModel = var2 == 2 || var2 == 3;
				var6.bipedLeftLeg.showModel = var2 == 2 || var2 == 3;
				this.setRenderPassModel(var6);
				return true;
			}
		}

		return false;
	}

	public void doRender(EntityPlayer var1, double var2, double var4, double var6, float var8, float var9) {
		ItemStack var10 = var1.inventory.getCurrentItem();
		ModelBiped var11 = (ModelBiped)this.mainModel;
		var11.heldItemRight = var10 != null;
		super.doRender(var1, var2, var4 - (double)var1.yOffset, var6, var8, var9);
		var11.heldItemRight = false;
		FontRenderer var12 = this.getFontRendererFromRenderManager();
		float var13 = 1.6F;
		float var14 = 0.016666668F * var13;
		if(!var1.username.startsWith("\u0107") && !var1.username.startsWith("\u015b")) {
			GL11.glPushMatrix();
			GL11.glTranslatef((float)var2 + 0.0F, (float)var4 + 2.3F, (float)var6);
			GL11.glNormal3f(0.0F, 1.0F, 0.0F);
			GL11.glRotatef(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
			float var15 = var1.getDistanceToEntity(this.renderManager.player);
			var14 = (float)((double)var14 * (Math.sqrt((double)var15) / 2.0D));
			GL11.glScalef(-var14, -var14, var14);
			String var16 = var1.username;
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glDepthMask(false);
			GL11.glDisable(GL11.GL_DEPTH_TEST);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			Tessellator var17 = Tessellator.instance;
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			var17.startDrawingQuads();
			int var18 = var12.getStringWidth(var16) / 2;
			var17.setColorRGBA_F(0.0F, 0.0F, 0.0F, 0.25F);
			var17.addVertex((double)(-var18 - 1), -1.0D, 0.0D);
			var17.addVertex((double)(-var18 - 1), 8.0D, 0.0D);
			var17.addVertex((double)(var18 + 1), 8.0D, 0.0D);
			var17.addVertex((double)(var18 + 1), -1.0D, 0.0D);
			var17.draw();
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			var12.drawString(var16, -var12.getStringWidth(var16) / 2, 0, 553648127);
			GL11.glEnable(GL11.GL_DEPTH_TEST);
			GL11.glDepthMask(true);
			var12.drawString(var16, -var12.getStringWidth(var16) / 2, 0, -1);
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glPopMatrix();
		}
	}

	protected void renderEquippedItems(EntityPlayer var1, float var2) {
		ItemStack var3 = var1.inventory.getCurrentItem();
		if(var3 != null) {
			GL11.glPushMatrix();
			this.modelBipedMain.bipedRightArm.renderWithRotation(0.0625F);
			GL11.glTranslatef(-0.0625F, 0.4375F, 0.0625F);
			float var4;
			if(var3.itemID < 256 && RenderBlocks.renderItemIn3d(Block.blocksList[var3.itemID].getRenderType())) {
				var4 = 0.5F;
				GL11.glTranslatef(0.0F, 0.1875F, -0.3125F);
				var4 *= 0.75F;
				GL11.glRotatef(20.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
				GL11.glScalef(var4, -var4, var4);
			} else if(Item.itemsList[var3.itemID].isFull3D()) {
				var4 = 0.625F;
				GL11.glTranslatef(0.0F, 0.1875F, 0.0F);
				GL11.glScalef(var4, -var4, var4);
				GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
			} else {
				var4 = 0.375F;
				GL11.glTranslatef(0.25F, 0.1875F, -0.1875F);
				GL11.glScalef(var4, var4, var4);
				GL11.glRotatef(60.0F, 0.0F, 0.0F, 1.0F);
				GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(20.0F, 0.0F, 0.0F, 1.0F);
			}

			this.renderManager.itemRenderer.renderItem(var3);
			GL11.glPopMatrix();
		}

	}

	protected void preRenderCallback(EntityPlayer var1, float var2) {
		float var3 = 0.9375F;
		GL11.glScalef(var3, var3, var3);
	}

	public void drawFirstPersonHand() {
		this.modelBipedMain.swingProgress = 0.0F;
		this.modelBipedMain.setRotationAngles(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
		this.modelBipedMain.bipedRightArm.render(0.0625F);
	}
}
