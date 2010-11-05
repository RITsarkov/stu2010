package name.filejunkie.chat;

import java.io.PrintWriter;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class Writer implements Runnable {

	private List<PrintWriter> clients;
	private LinkedBlockingQueue<String> messages;
	
	public Writer(List<PrintWriter> clients, LinkedBlockingQueue<String> messages) {
		this.clients = clients;
		this.messages = messages;
	}

	@Override
	public void run() {
		while(!Thread.currentThread().isInterrupted()){
			try {
				String message = messages.take();
				for(PrintWriter sOut: clients){
					sOut.println(message);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
				break;
			}
		}
	}

}
