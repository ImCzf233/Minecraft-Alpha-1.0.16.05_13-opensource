package net.minecraft.src;

import org.lwjgl.opengl.GLContext;

public class OpenGlCapsChecker {
	private static boolean tryCheckOcclusionCapable = false;

	public boolean checkARBOcclusion() {
		return tryCheckOcclusionCapable && GLContext.getCapabilities().GL_ARB_occlusion_query;
	}
}
