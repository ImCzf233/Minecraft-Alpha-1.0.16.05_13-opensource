package net.minecraft.src;

class ThreadRunIsoClient extends Thread {
	final CanvasIsomPreview isomPreview;

	ThreadRunIsoClient(CanvasIsomPreview canvas) {
		this.isomPreview = canvas;
	}

	public void run() {
		while(CanvasIsomPreview.isRunning(this.isomPreview)) {
			this.isomPreview.render();

			try {
				Thread.sleep(1L);
			} catch (Exception var2) {
			}
		}

	}
}
