package net.minecraft.src;

class NetworkWriterThread extends Thread {
	final NetworkManager netManager;

	NetworkWriterThread(NetworkManager networkManager, String name) {
		super(name);
		this.netManager = networkManager;
	}

	public void run() {
		Object var1 = NetworkManager.threadSyncObject;
		synchronized(NetworkManager.threadSyncObject) {
			++NetworkManager.numWriteThreads;
		}

		while(true) {
			boolean var11 = false;

			try {
				var11 = true;
				if(!NetworkManager.isRunning(this.netManager)) {
					var11 = false;
					break;
				}

				NetworkManager.sendNetworkPacket(this.netManager);
			} finally {
				if(var11) {
					Object var5 = NetworkManager.threadSyncObject;
					synchronized(NetworkManager.threadSyncObject) {
						--NetworkManager.numWriteThreads;
					}
				}
			}
		}

		var1 = NetworkManager.threadSyncObject;
		synchronized(NetworkManager.threadSyncObject) {
			--NetworkManager.numWriteThreads;
		}
	}
}
