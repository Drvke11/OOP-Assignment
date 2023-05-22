import java.util.*;
import java.io.*;

public class TransactionProcessing {
    private ArrayList<Payment> paymentObjects;
    private IDCardManagement idcm;

    public TransactionProcessing(String idCardPath, String paymentPath) {
        idcm = new IDCardManagement(idCardPath);
        readPaymentObject(paymentPath);

    }

    public ArrayList<Payment> getPaymentObject() {
        return this.paymentObjects;
    }

    // Requirement 3
    public boolean readPaymentObject(String path) {
        ArrayList<IDCard> idCardInfo = idcm.getIDCards();
        paymentObjects = new ArrayList<Payment>();
        try {
            File f = new File(path);
            Scanner sc = new Scanner(f);
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (line.length() == 6) {
                    for (IDCard temp : idCardInfo) {
                        if (Integer.parseInt(line) == temp.getIdenNumber()) {
                            paymentObjects.add(new ConvenientCard(temp));
                        }
                    }
                } else if (line.length() == 7) {
                    paymentObjects.add(new EWallet(Integer.parseInt(line)));
                } else {
                    String[] components = line.split(",");
                    paymentObjects
                            .add(new BankAccount(Integer.parseInt(components[0]),
                                    Double.parseDouble(components[1])));
                }
            }
            sc.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Requirement 4
    public ArrayList<ConvenientCard> getAdultConvenientCards() {
        ArrayList<ConvenientCard> adultType = new ArrayList<ConvenientCard>();
        for (Payment temp : paymentObjects) {
            if (temp instanceof ConvenientCard) {
                ConvenientCard cc = (ConvenientCard) temp;
                if (cc.getType().equals("Adult")) {
                    adultType.add(cc);
                }
            }
        }
        if (adultType.size() > 0) {
            return adultType;
        }
        return null;
    }

    // Requirement 5
    public ArrayList<IDCard> getCustomersHaveBoth() {
        return null;
    }

    // Requirement 6
    public void processTopUp(String path) {
        // code here
    }

    // Requirement 7
    public ArrayList<Bill> getUnsuccessfulTransactions(String path) {
        // code here
        return null;
    }

    // Requirement 8
    public ArrayList<BankAccount> getLargestPaymentByBA(String path) {
        // code here
        return null;
    }

    // Requirement 9
    public void processTransactionWithDiscount(String path) {
        // code here
    }
}
