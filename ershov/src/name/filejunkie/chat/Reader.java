package name.filejunkie.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class Reader implements Runnable {

	private Socket s;
	private List<PrintWriter> clients;
	private LinkedBlockingQueue<String> messages;
	
	public Reader(Socket s, List<PrintWriter>  clients, LinkedBlockingQueue<String> messages) {
		this.s = s;
		this.clients = clients;
		this.messages = messages;
	}

	@Override
	public void run() {
		while(!Thread.currentThread().isInterrupted()){
			try {
				BufferedReader sIn = new BufferedReader(new InputStreamReader(s.getInputStream()));
				PrintWriter sOut = new PrintWriter(s.getOutputStream(), true);

				String name = sIn.readLine();
				clients.add(sOut);
				
				while(true){
					messages.put(name + ": " + sIn.readLine());
				}
				
			} catch (IOException e) {
				e.printStackTrace();
				break;
			} catch (InterruptedException e) {
				e.printStackTrace();
				break;
			}
		}
	}

}
