package name.filejunkie.chat;

import java.io.BufferedReader;
import java.io.IOException;

public class ClientReader implements Runnable {
	private BufferedReader sIn;
	
	public ClientReader(BufferedReader sIn) {
		this.sIn = sIn;
	}

	@Override
	public void run() {
		while(!Thread.currentThread().isInterrupted()){
			try {
				System.out.println(sIn.readLine());
			} catch (IOException e) {
				e.printStackTrace();
				break;
			}
		}
	}

}
