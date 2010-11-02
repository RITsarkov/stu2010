package name.filejunkie.cheeseShop;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class CheeseShop {
	private final int THREAD_COUNT = 4;
	
	private enum ProcessType {
		arrogant, plain;
	}
	
	LinkedList<Buyer> buyers = null;
	List<Ticket> tickets = null;
	Cashier cashier = null;
	
	private class Buyer implements Runnable{
		private int number;
		private ProcessType type;
		
		public Buyer(int number, ProcessType type){
			this.number = number;
			this.type = type;
		}
		
		@Override
		public void run() {
			while (!Thread.currentThread().isInterrupted()) {
				synchronized(tickets){
					System.out.println((type == ProcessType.arrogant ? "Arrogant" : "Plain") + " thread " + number + " started");
					tickets.add(new Ticket(type, number));
				}
				synchronized(cashier){
					cashier.notify();
				}
				synchronized(this){
					try {
						wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				System.out.println((type == ProcessType.arrogant ? "Arrogant" : "Plain") + " thread " + number + " finished");
				break;
			}
		}
	}
	
	private class Cashier implements Runnable{
		@Override
		public void run() {
			while (!Thread.currentThread().isInterrupted()) {
				try {
					synchronized(this){
						wait(15);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if(tickets.isEmpty()){
					break;
				}
				Ticket t = null;
				synchronized(tickets){
					for(Ticket i: tickets){
						if(i.getType() == ProcessType.arrogant){
							t = i;
							tickets.remove(i);
							break;
					}
					}
					if(t == null){
						t = tickets.get(0);
						tickets.remove(0);
					}
				}
				
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Buyer b = buyers.get(t.getNumber());
				synchronized (b) {
					b.notify();
				}
			}
		}
		
	}
	
	private class Ticket{
		private ProcessType type;
		private int number;
		
		public Ticket(ProcessType type, int number) {
			this.type = type;
			this.number = number;
		}
		
		public ProcessType getType() {
			return type;
		}

		public int getNumber() {
			return number;
		}
	}
	
	public static void main(String[] args) {
		new CheeseShop().run();
	}

	private void run() {
		buyers = new LinkedList<Buyer>();
		tickets = Collections.synchronizedList(new LinkedList<Ticket>());
		cashier = new Cashier();

		Thread tCashier = new Thread(cashier, "Cashier thread ");
		tCashier.start();
		
		LinkedList<Thread> threads = new LinkedList<Thread>();
		
		for(int i = 0; i < THREAD_COUNT; i++){
			Buyer a = new Buyer(i, i%2 == 0 ? ProcessType.arrogant : ProcessType.plain);
			buyers.add(a);
			threads.add(new Thread(a, (i%2 == 0 ? "Arrogant" : "Plain") + " thread " + i));			
		}
		for(Thread t: threads){
			t.start();
		}
	}

}
