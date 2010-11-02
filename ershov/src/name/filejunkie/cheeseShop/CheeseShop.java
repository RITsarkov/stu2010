package name.filejunkie.cheeseShop;

import java.util.LinkedList;

public class CheeseShop {
	private final int THREAD_COUNT = 4;
	
	LinkedList<Buyer> buyers = null;
	Cashier cashier = null;
	
	public static void main(String[] args) {
		new CheeseShop().run();
	}

	private void run() {
		buyers = new LinkedList<Buyer>();
		cashier = new Cashier(buyers);

		Thread tCashier = new Thread(cashier, "Cashier thread ");
		tCashier.start();
		
		LinkedList<Thread> threads = new LinkedList<Thread>();
		
		for(int i = 0; i < THREAD_COUNT; i++){
			Buyer a = new Buyer(i, i%2 == 0 ? ProcessType.arrogant : ProcessType.plain, cashier);
			buyers.add(a);
			threads.add(new Thread(a, (i%2 == 0 ? "Arrogant" : "Plain") + " thread " + i));			
		}
		for(Thread t: threads){
			t.start();
		}
	}

}
