package name.filejunkie.cheeseShop;

enum ProcessType {
	arrogant, plain;
}

class Ticket{
	
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