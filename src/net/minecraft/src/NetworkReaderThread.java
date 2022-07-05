package net.minecraft.src;

class NetworkReaderThread extends Thread {
	final NetworkManager netManager;

	NetworkReaderThread(NetworkManager networkManager, String name) {
		super(name);
		this.netManager = networkManager;
	}

	public void run() {
		Object var1 = NetworkManager.threadSyncObject;
		synchronized(NetworkManager.threadSyncObject) {
			++NetworkManager.numReadThreads;
		}

		while(true) {
			boolean var11 = false;

			try {
				var11 = true;
				if(NetworkManager.isRunning(this.netManager)) {
					if(!NetworkManager.isServerTerminating(this.netManager)) {
						NetworkManager.readNetworkPacket(this.netManager);
						continue;
					}

					var11 = false;
					break;
				}

				var11 = false;
				break;
			} finally {
				if(var11) {
					Object var5 = NetworkManager.threadSyncObject;
					synchronized(NetworkManager.threadSyncObject) {
						--NetworkManager.numReadThreads;
					}
				}
			}
		}

		var1 = NetworkManager.threadSyncObject;
		synchronized(NetworkManager.threadSyncObject) {
			--NetworkManager.numReadThreads;
		}
	}
}
