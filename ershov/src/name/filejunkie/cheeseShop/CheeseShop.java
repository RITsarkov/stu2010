package name.filejunkie.cheeseShop;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.TreeSet;

public class CheeseShop {
	private final int THREAD_COUNT = 2;
	
	private enum ProcessType {
		arrogant, plain;
	}
	
	ArrayList<Arrogant> arrogant = null;
	ArrayList<Plain> plain = null;
	TreeSet<Ticket> tickets = null;
	Cashier cashier = null;
	
	private class Arrogant implements Runnable{
		private int number;
		
		public Arrogant(int number){
			this.number = number;
		}
		
		@Override
		public void run() {
			while (!Thread.currentThread().isInterrupted()) {
				System.out.println("Arrogant thread " + number + " started");
				synchronized(tickets){
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
				System.out.println("Plain thread " + number + " started");
				synchronized(tickets){
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
				/*try {
					synchronized(this){
						wait();
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}*/
				while(!tickets.isEmpty()){
					Ticket t = tickets.last();
					tickets.remove(t);
					try {
						Thread.sleep(1000);
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
	
	private class Ticket implements Comparable<Ticket>{
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


		@Override
		public int compareTo(Ticket e) {			
			if(this.getType() == e.getType()){
				return this.getNumber() - e.getNumber();
			}
			
			if(this.getType() == ProcessType.arrogant){
				return 1;
			}
			else{
				return -1;
			}		
		}
		
		
	}
	
	public static void main(String[] args) {
		new CheeseShop().run();
	}

	private void run() {
		arrogant = new ArrayList<Arrogant>();
		plain = new ArrayList<Plain>();
		tickets = new TreeSet<Ticket>();
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
