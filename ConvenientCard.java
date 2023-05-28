public class ConvenientCard implements Payment {
	private String type;
	private IDCard idCard;
	private double balance;

	// Convenient Card use IDCard in order to initialize
	public ConvenientCard(IDCard idCard) throws CannotCreateCard {
		if (idCard.getAge() < 12) {
			throw new CannotCreateCard("Not enough age");
		}

		if (idCard.getAge() <= 18) {
			this.type = "Student";
		} else {
			this.type = "Adult";
		}
		this.idCard = idCard;
		this.balance = 100;
	}

	public boolean pay(double amount) {
		if (type.equals("Adult")) {
			amount = 1.01 * amount;
		}
		if (amount <= balance) {
			balance -= amount;
			return true;
		}
		return false;
	}

	public double checkBalance() {
		return balance;
	}

	public void topUp(double amount) {
		balance += amount;
	}

	// Get the entire IDCard information
	public String getIDCard() {
		return idCard.toString();
	}

	// Get identification number on the IDCard
	public int getIdenNumber() {
		return idCard.getIdenNumber();
	}

	public String getType() {
		return this.type;
	}

	public String toString() {
		return idCard + "," + type + "," + balance;
	}
}
