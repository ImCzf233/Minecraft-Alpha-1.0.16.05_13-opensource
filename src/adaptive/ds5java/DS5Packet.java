package adaptive.ds5java;

import java.util.ArrayList;
import java.util.List;

public class DS5Packet {
	public List<DS5Instruction> ds5Instructions = new ArrayList();

	public void AddInstruction(DS5Instruction var1) {
		this.ds5Instructions.add(var1);
	}

	public String buildJSON() {
		String var1 = "{\"instructions\":[";

		for(int var2 = 0; var2 != this.ds5Instructions.size(); ++var2) {
			var1 = var1 + ((DS5Instruction)this.ds5Instructions.get(var2)).GetJSON();
			if(var2 != this.ds5Instructions.size() - 1) {
				var1 = var1 + ",";
			}
		}

		var1 = var1 + "]}";
		return var1;
	}
}
