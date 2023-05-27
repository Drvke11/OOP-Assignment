public class BankAccount implements Payment, Transfer {
    private int accountNumber; // 6 numbers by default and accountNumber = idenNumber
    private double interestRate;
    private double balance;

    public BankAccount(int accountNumber, double interestRate) {
        this.accountNumber = accountNumber;
        this.interestRate = interestRate;
        this.balance = 50; // balance = 50 by default as initialized
    }

    public void topUp(double amount) {
        balance += amount;
    }

    public boolean pay(double amount) {
        if (balance - amount >= 50) {
            balance -= amount;
            return true;
        }
        return false;
    }

    public double checkBalance() {
        return balance;
    }

    public boolean transfer(double amount, Transfer to) {
        double total = amount + Transfer.transferFee * amount;
        if (balance - total >= 50) {
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

    // Get accountNumber of the BankAccount
    public int getAccountNumber() {
        return accountNumber;
    }

    public String toString() {
        return accountNumber + "," + interestRate + "," + balance;
    }
}
