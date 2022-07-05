package adaptive.ds5java;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.Scanner;

public class DS5Connection {
	public DatagramSocket dgramSock;
	public int port = -1;
	public InetAddress addr;
	public boolean connected = false;

	public boolean Connect() {
		File var1 = new File("C:\\Temp\\DualSenseX\\DualSenseX_PortNumber.txt");

		try {
			Scanner var2 = new Scanner(var1);
			if(!var2.hasNextInt()) {
				var2.close();
				return false;
			}

			this.port = var2.nextInt();
			var2.close();
		} catch (FileNotFoundException var5) {
			return false;
		}

		try {
			this.addr = InetAddress.getByName("localhost");
			this.dgramSock = new DatagramSocket();
		} catch (UnknownHostException var3) {
			return false;
		} catch (SocketException var4) {
			return false;
		}

		this.connected = true;
		return true;
	}

	public void Disconnect() {
		this.dgramSock.close();
		this.connected = false;
	}

	public boolean Send(DS5Packet var1) {
		if(!this.connected) {
			return false;
		} else {
			byte[] var2 = var1.buildJSON().getBytes(Charset.forName("ASCII"));
			DatagramPacket var3 = new DatagramPacket(var2, var2.length, this.addr, this.port);

			try {
				this.dgramSock.send(var3);
				return true;
			} catch (IOException var5) {
				return false;
			}
		}
	}
}
