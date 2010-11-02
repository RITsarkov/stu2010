package name.filejunkie.cheeseShop;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

class Cashier implements Runnable{

	List<Ticket> tickets = null;
	LinkedList<Buyer> buyers = null;
	
	public Cashier(LinkedList<Buyer> buyers){
		this.tickets = Collections.synchronizedList(new LinkedList<Ticket>());
		this.buyers = buyers;
	}
	
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
				b.cheese();
			}
		}
	}

	public synchronized void getCheese(ProcessType type, int number) {
		tickets.add(new Ticket(type, number));
		this.notify();
	}
	
}