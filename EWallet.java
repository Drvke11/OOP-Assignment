public class EWallet implements Payment, Transfer {
	private int phoneNumber; // 7 numbers by default
	private double balance;

	// EWallet use phoneNumber in order to initialize
	// And the phoneNumber is based on ID Card
	public EWallet(int phoneNumber) {
		this.phoneNumber = phoneNumber;
		this.balance = 0;
	}

	public void topUp(double amount) {
		balance += amount;
	}

	public double checkBalance() {
		return balance;
	}

	public boolean pay(double amount) {
		if (amount <= balance) {
			balance -= amount;
			return true;
		}
		return false;
	}

	public boolean transfer(double amount, Transfer to) {
		double total = amount + Transfer.transferFee * amount;
		if (total <= balance) {
			balance -= total;
			if (to instanceof EWallet) {
				EWallet temp = (EWallet) to;
				temp.topUp(amount);
			} else {
				BankAccount temp = (BankAccount) to;
				temp.topUp(amount);
			}
			return true;
		}
		return false;
	}

	// Get phoneNumber of the EWallet account
	public int getPhoneNumber() {
		return phoneNumber;
	}

	public String toString() {
		return phoneNumber + "," + balance;
	}
}
