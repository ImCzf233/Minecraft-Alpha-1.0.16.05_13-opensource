package adaptive.ds5java;

public class DS5InstrPlayerUpdate implements DS5Instruction {
	public int controllerIndex;
	public boolean p1;
	public boolean p2;
	public boolean p3;
	public boolean p4;
	public boolean p5 = false;

	public DS5InstrPlayerUpdate(int var1, boolean var2, boolean var3, boolean var4, boolean var5, boolean var6) {
		this.controllerIndex = var1;
		this.p1 = var2;
		this.p2 = var3;
		this.p3 = var4;
		this.p4 = var5;
		this.p5 = var6;
	}

	public String GetJSON() {
		return "{\"type\":3,\"parameters\":[" + this.controllerIndex + "," + this.p1 + "," + this.p2 + "," + this.p3 + "," + this.p4 + "," + this.p5 + "]}";
	}
}
