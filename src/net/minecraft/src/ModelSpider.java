package net.minecraft.src;

public class ModelSpider extends ModelBase {
	public ModelRenderer spiderHead;
	public ModelRenderer spiderNeck;
	public ModelRenderer spiderBody;
	public ModelRenderer spiderLeg1;
	public ModelRenderer spiderLeg2;
	public ModelRenderer spiderLeg3;
	public ModelRenderer spiderLeg4;
	public ModelRenderer spiderLeg5;
	public ModelRenderer spiderLeg6;
	public ModelRenderer spiderLeg7;
	public ModelRenderer spiderLeg8;

	public ModelSpider() {
		float var1 = 0.0F;
		byte var2 = 15;
		this.spiderHead = new ModelRenderer(32, 4);
		this.spiderHead.addBox(-4.0F, -4.0F, -8.0F, 8, 8, 8, var1);
		this.spiderHead.setRotationPoint(0.0F, (float)(0 + var2), -3.0F);
		this.spiderNeck = new ModelRenderer(0, 0);
		this.spiderNeck.addBox(-3.0F, -3.0F, -3.0F, 6, 6, 6, var1);
		this.spiderNeck.setRotationPoint(0.0F, (float)var2, 0.0F);
		this.spiderBody = new ModelRenderer(0, 12);
		this.spiderBody.addBox(-5.0F, -4.0F, -6.0F, 10, 8, 12, var1);
		this.spiderBody.setRotationPoint(0.0F, (float)(0 + var2), 9.0F);
		this.spiderLeg1 = new ModelRenderer(18, 0);
		this.spiderLeg1.addBox(-15.0F, -1.0F, -1.0F, 16, 2, 2, var1);
		this.spiderLeg1.setRotationPoint(-4.0F, (float)(0 + var2), 2.0F);
		this.spiderLeg2 = new ModelRenderer(18, 0);
		this.spiderLeg2.addBox(-1.0F, -1.0F, -1.0F, 16, 2, 2, var1);
		this.spiderLeg2.setRotationPoint(4.0F, (float)(0 + var2), 2.0F);
		this.spiderLeg3 = new ModelRenderer(18, 0);
		this.spiderLeg3.addBox(-15.0F, -1.0F, -1.0F, 16, 2, 2, var1);
		this.spiderLeg3.setRotationPoint(-4.0F, (float)(0 + var2), 1.0F);
		this.spiderLeg4 = new ModelRenderer(18, 0);
		this.spiderLeg4.addBox(-1.0F, -1.0F, -1.0F, 16, 2, 2, var1);
		this.spiderLeg4.setRotationPoint(4.0F, (float)(0 + var2), 1.0F);
		this.spiderLeg5 = new ModelRenderer(18, 0);
		this.spiderLeg5.addBox(-15.0F, -1.0F, -1.0F, 16, 2, 2, var1);
		this.spiderLeg5.setRotationPoint(-4.0F, (float)(0 + var2), 0.0F);
		this.spiderLeg6 = new ModelRenderer(18, 0);
		this.spiderLeg6.addBox(-1.0F, -1.0F, -1.0F, 16, 2, 2, var1);
		this.spiderLeg6.setRotationPoint(4.0F, (float)(0 + var2), 0.0F);
		this.spiderLeg7 = new ModelRenderer(18, 0);
		this.spiderLeg7.addBox(-15.0F, -1.0F, -1.0F, 16, 2, 2, var1);
		this.spiderLeg7.setRotationPoint(-4.0F, (float)(0 + var2), -1.0F);
		this.spiderLeg8 = new ModelRenderer(18, 0);
		this.spiderLeg8.addBox(-1.0F, -1.0F, -1.0F, 16, 2, 2, var1);
		this.spiderLeg8.setRotationPoint(4.0F, (float)(0 + var2), -1.0F);
	}

	public void render(float var1, float var2, float var3, float var4, float var5, float var6) {
		this.setRotationAngles(var1, var2, var3, var4, var5, var6);
		this.spiderHead.render(var6);
		this.spiderNeck.render(var6);
		this.spiderBody.render(var6);
		this.spiderLeg1.render(var6);
		this.spiderLeg2.render(var6);
		this.spiderLeg3.render(var6);
		this.spiderLeg4.render(var6);
		this.spiderLeg5.render(var6);
		this.spiderLeg6.render(var6);
		this.spiderLeg7.render(var6);
		this.spiderLeg8.render(var6);
	}

	public void setRotationAngles(float var1, float var2, float var3, float var4, float var5, float var6) {
		this.spiderHead.rotateAngleY = var4 / 57.295776F;
		this.spiderHead.rotateAngleX = var5 / 57.295776F;
		float var7 = 0.7853982F;
		this.spiderLeg1.rotateAngleZ = -var7;
		this.spiderLeg2.rotateAngleZ = var7;
		this.spiderLeg3.rotateAngleZ = -var7 * 0.74F;
		this.spiderLeg4.rotateAngleZ = var7 * 0.74F;
		this.spiderLeg5.rotateAngleZ = -var7 * 0.74F;
		this.spiderLeg6.rotateAngleZ = var7 * 0.74F;
		this.spiderLeg7.rotateAngleZ = -var7;
		this.spiderLeg8.rotateAngleZ = var7;
		float var8 = -0.0F;
		float var9 = 0.3926991F;
		this.spiderLeg1.rotateAngleY = var9 * 2.0F + var8;
		this.spiderLeg2.rotateAngleY = -var9 * 2.0F - var8;
		this.spiderLeg3.rotateAngleY = var9 * 1.0F + var8;
		this.spiderLeg4.rotateAngleY = -var9 * 1.0F - var8;
		this.spiderLeg5.rotateAngleY = -var9 * 1.0F + var8;
		this.spiderLeg6.rotateAngleY = var9 * 1.0F - var8;
		this.spiderLeg7.rotateAngleY = -var9 * 2.0F + var8;
		this.spiderLeg8.rotateAngleY = var9 * 2.0F - var8;
		float var10 = -(MathHelper.cos(var1 * 0.6662F * 2.0F + 0.0F) * 0.4F) * var2;
		float var11 = -(MathHelper.cos(var1 * 0.6662F * 2.0F + (float)Math.PI) * 0.4F) * var2;
		float var12 = -(MathHelper.cos(var1 * 0.6662F * 2.0F + (float)Math.PI / 2F) * 0.4F) * var2;
		float var13 = -(MathHelper.cos(var1 * 0.6662F * 2.0F + 4.712389F) * 0.4F) * var2;
		float var14 = Math.abs(MathHelper.sin(var1 * 0.6662F + 0.0F) * 0.4F) * var2;
		float var15 = Math.abs(MathHelper.sin(var1 * 0.6662F + (float)Math.PI) * 0.4F) * var2;
		float var16 = Math.abs(MathHelper.sin(var1 * 0.6662F + (float)Math.PI / 2F) * 0.4F) * var2;
		float var17 = Math.abs(MathHelper.sin(var1 * 0.6662F + 4.712389F) * 0.4F) * var2;
		this.spiderLeg1.rotateAngleY += var10;
		this.spiderLeg2.rotateAngleY += -var10;
		this.spiderLeg3.rotateAngleY += var11;
		this.spiderLeg4.rotateAngleY += -var11;
		this.spiderLeg5.rotateAngleY += var12;
		this.spiderLeg6.rotateAngleY += -var12;
		this.spiderLeg7.rotateAngleY += var13;
		this.spiderLeg8.rotateAngleY += -var13;
		this.spiderLeg1.rotateAngleZ += var14;
		this.spiderLeg2.rotateAngleZ += -var14;
		this.spiderLeg3.rotateAngleZ += var15;
		this.spiderLeg4.rotateAngleZ += -var15;
		this.spiderLeg5.rotateAngleZ += var16;
		this.spiderLeg6.rotateAngleZ += -var16;
		this.spiderLeg7.rotateAngleZ += var17;
		this.spiderLeg8.rotateAngleZ += -var17;
	}
}
