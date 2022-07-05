package net.minecraft.src;

public class ModelBoat extends ModelBase {
	public ModelRenderer[] boatSides = new ModelRenderer[5];

	public ModelBoat() {
		this.boatSides[0] = new ModelRenderer(0, 8);
		this.boatSides[1] = new ModelRenderer(0, 0);
		this.boatSides[2] = new ModelRenderer(0, 0);
		this.boatSides[3] = new ModelRenderer(0, 0);
		this.boatSides[4] = new ModelRenderer(0, 0);
		byte var1 = 24;
		byte var2 = 6;
		byte var3 = 20;
		byte var4 = 4;
		this.boatSides[0].addBox((float)(-var1 / 2), (float)(-var3 / 2 + 2), -3.0F, var1, var3 - 4, 4, 0.0F);
		this.boatSides[0].setRotationPoint(0.0F, (float)(0 + var4), 0.0F);
		this.boatSides[1].addBox((float)(-var1 / 2 + 2), (float)(-var2 - 1), -1.0F, var1 - 4, var2, 2, 0.0F);
		this.boatSides[1].setRotationPoint((float)(-var1 / 2 + 1), (float)(0 + var4), 0.0F);
		this.boatSides[2].addBox((float)(-var1 / 2 + 2), (float)(-var2 - 1), -1.0F, var1 - 4, var2, 2, 0.0F);
		this.boatSides[2].setRotationPoint((float)(var1 / 2 - 1), (float)(0 + var4), 0.0F);
		this.boatSides[3].addBox((float)(-var1 / 2 + 2), (float)(-var2 - 1), -1.0F, var1 - 4, var2, 2, 0.0F);
		this.boatSides[3].setRotationPoint(0.0F, (float)(0 + var4), (float)(-var3 / 2 + 1));
		this.boatSides[4].addBox((float)(-var1 / 2 + 2), (float)(-var2 - 1), -1.0F, var1 - 4, var2, 2, 0.0F);
		this.boatSides[4].setRotationPoint(0.0F, (float)(0 + var4), (float)(var3 / 2 - 1));
		this.boatSides[0].rotateAngleX = (float)Math.PI / 2F;
		this.boatSides[1].rotateAngleY = 4.712389F;
		this.boatSides[2].rotateAngleY = (float)Math.PI / 2F;
		this.boatSides[3].rotateAngleY = (float)Math.PI;
	}

	public void render(float var1, float var2, float var3, float var4, float var5, float var6) {
		for(int var7 = 0; var7 < 5; ++var7) {
			this.boatSides[var7].render(var6);
		}

	}

	public void setRotationAngles(float var1, float var2, float var3, float var4, float var5, float var6) {
	}
}
