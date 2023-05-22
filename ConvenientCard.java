public class ConvenientCard implements Payment {
	private String type; // 2 types : Adult or Student
	private IDCard idCard;
	private double balance;

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

	public String getType() {
		return this.type;
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

	public String toString() {
		return idCard + "," + type + "," + balance;
	}
}
