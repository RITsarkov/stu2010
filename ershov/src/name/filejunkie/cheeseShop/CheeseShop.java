package name.filejunkie.cheeseShop;

import java.util.Vector;
import java.util.LinkedList;

public class CheeseShop {
	private final int THREAD_COUNT = 2;
	
	private enum ProcessType {
		arrogant, plain;
	}
	
	Vector<Arrogant> arrogant = null;
	Vector<Plain> plain = null;
	Vector<Ticket> tickets = null;
	Cashier cashier = null;
	
	private class Arrogant implements Runnable{
		private int number;
		
		public Arrogant(int number){
			this.number = number;
		}
		
		@Override
		public void run() {
			while (!Thread.currentThread().isInterrupted()) {
				synchronized(tickets){
					System.out.println("Arrogant thread " + number + " started");
					tickets.add(new Ticket(ProcessType.arrogant, number));
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
				System.out.println("Arrogant thread " + number + " finished");
				break;
			}
		}
	}
	
	private class Plain implements Runnable{
		private int number;
		
		public Plain(int number){
			this.number = number;
		}
				
		@Override
		public void run() {
			while (!Thread.currentThread().isInterrupted()) {
				synchronized(tickets){
					System.out.println("Plain thread " + number + " started");
					tickets.add(new Ticket(ProcessType.plain, number));
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
				System.out.println("Plain thread " + number + " finished");
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
				while(!tickets.isEmpty()){
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
					if(t.getType() == ProcessType.arrogant){
						Arrogant a = arrogant.get(t.getNumber());
						synchronized(a){
							a.notify();
						}
					}
					else{
						Plain p = plain.get(t.getNumber());
						synchronized(p){
							p.notify();
						}
					}
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
		arrogant = new Vector<Arrogant>();
		plain = new Vector<Plain>();
		tickets = new Vector<Ticket>();
		cashier = new Cashier();

		Thread tCashier = new Thread(cashier, "Cashier thread ");
		tCashier.start();
		
		LinkedList<Thread> threads = new LinkedList<Thread>();
		
		for(int i = 0; i < THREAD_COUNT; i++){
			Arrogant a = new Arrogant(i);
			Plain p = new Plain(i);
			arrogant.add(a);
			plain.add(p);
			threads.add(new Thread(a, "Arrogant thread " + i));
			threads.add(new Thread(p, "Plain thread " + i));			
		}
		for(Thread t: threads){
			t.start();
		}
	}

}
