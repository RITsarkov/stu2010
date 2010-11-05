package name.filejunkie.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

	private static int port = 12345;
	private static String host = "localhost";
	
	public static void main(String[] args) throws UnknownHostException, IOException {
		System.out.println("Trying to connect to " + host + ":" + port);
		Socket s = new Socket(host, port);
		
		Scanner in = new Scanner(System.in);
		System.out.println("What is your name?");
		String name = in.nextLine();		
		
		PrintWriter sOut = new PrintWriter(s.getOutputStream(), true);
		sOut.println(name);
		
		BufferedReader sIn = new BufferedReader(new InputStreamReader(s.getInputStream()));
		new Thread(new ClientReader(sIn)).start();
		
		String line;
		while(true){
			line = in.nextLine();
			if(line.equalsIgnoreCase("quit")){
				break;
			}
			sOut.println(line);
		}
		
	}
}
