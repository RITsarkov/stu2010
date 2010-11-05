package name.filejunkie.chat;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class Server{
	private static int port = 12345;
	
	public static void main(String[] args) throws IOException{
		List<PrintWriter>  clients = Collections.synchronizedList(new LinkedList<PrintWriter>());
		ServerSocket serverSocket = new ServerSocket(port);
		LinkedBlockingQueue<String> messages = new LinkedBlockingQueue<String>();

		new Thread(new Writer(clients, messages)).start();
		
		while(true){
			new Thread(new Reader(serverSocket.accept(), clients, messages)).start();
		}
		
	}
}