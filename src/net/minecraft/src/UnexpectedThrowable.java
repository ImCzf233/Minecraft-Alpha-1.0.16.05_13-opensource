package net.minecraft.src;

public class UnexpectedThrowable {
	public final String description;
	public final Throwable exception;

	public UnexpectedThrowable(String desc, Throwable throwable) {
		this.description = desc;
		this.exception = throwable;
	}
}
