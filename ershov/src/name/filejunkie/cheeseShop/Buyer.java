package name.filejunkie.cheeseShop;

class Buyer implements Runnable{
	private Cashier cashier;
	private int number;
	private ProcessType type;
	
	public Buyer(int number, ProcessType type, Cashier cashier){
		this.number = number;
		this.type = type;
		this.cashier = cashier;
	}
	
	@Override
	public void run() {
		while (!Thread.currentThread().isInterrupted()) {
			synchronized(this.cashier){
				System.out.println((type == ProcessType.arrogant ? "Arrogant" : "Plain") + " thread " + number + " started");
				cashier.getCheese(type, number);
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