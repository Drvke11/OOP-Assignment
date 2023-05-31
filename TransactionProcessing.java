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
                            break;
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
            System.out.println(e);
            return false;
        }
    }

    // Requirement 4
    public ArrayList<ConvenientCard> getAdultConvenientCards() {
        ArrayList<ConvenientCard> adultCard = new ArrayList<ConvenientCard>();
        for (Payment temp : paymentObjects) {
            if (temp instanceof ConvenientCard) {
                ConvenientCard cc = (ConvenientCard) temp;
                if (cc.getType().equals("Adult")) {
                    adultCard.add(cc);
                }
            }
        }
        if (adultCard.size() > 0) {
            return adultCard;
        }
        return null;
    }

    // Requirement 5
    public ArrayList<IDCard> getCustomersHaveBoth() {
        ArrayList<IDCard> result = new ArrayList<IDCard>();
        ArrayList<IDCard> idCardInfo = idcm.getIDCards();
        for (IDCard idc : idCardInfo) {
            int count = 0;
            for (Payment p : paymentObjects) {
                if (p instanceof ConvenientCard) {
                    ConvenientCard temp = (ConvenientCard) p;
                    if (temp.getIDCard().equals(idc.toString())) {
                        count++;
                    }
                } else if (p instanceof EWallet) {
                    EWallet temp = (EWallet) p;
                    if (temp.getPhoneNumber() == idc.getPhoneNumber()) {
                        count++;
                    }
                } else {
                    BankAccount temp = (BankAccount) p;
                    if (temp.getAccountNumber() == idc.getIdenNumber()) {
                        count++;
                    }
                }
            }
            if (count == 3) {
                result.add(idc);
            }
        }
        if (result.size() > 0) {
            return result;
        }
        return null;
    }

    // Requirement 6
    public void processTopUp(String path) {
        try {
            File f = new File(path);
            Scanner sc = new Scanner(f);
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] components = line.split(",");
                for (Payment p : paymentObjects) {
                    if (components[0].equals("CC")) {
                        if (p instanceof ConvenientCard) {
                            ConvenientCard temp = (ConvenientCard) p;
                            if (temp.getIdenNumber() == Integer.parseInt(components[1])) {
                                temp.topUp((Double.parseDouble(components[2])));
                            }
                        }
                    } else if (components[0].equals("EW")) {
                        if (p instanceof EWallet) {
                            EWallet temp = (EWallet) p;
                            if (temp.getPhoneNumber() == Integer.parseInt(components[1])) {
                                temp.topUp(Double.parseDouble(components[2]));
                            }
                        }
                    } else {
                        if (p instanceof BankAccount) {
                            BankAccount temp = (BankAccount) p;
                            if (temp.getAccountNumber() == Integer.parseInt(components[1])) {
                                temp.topUp(Double.parseDouble(components[2]));
                            }
                        }
                    }
                }
            }
            sc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Requirement 7
    public ArrayList<Bill> getUnsuccessfulTransactions(String path) {
        ArrayList<Bill> result = new ArrayList<Bill>();
        try {
            File f = new File(path);
            Scanner sc = new Scanner(f);
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] components = line.split(",");
                for (Payment p : paymentObjects) {
                    if (components[3].equals("CC")) {
                        if (p instanceof ConvenientCard) {
                            ConvenientCard temp = (ConvenientCard) p;
                            if (temp.getIdenNumber() == Integer.parseInt(components[4])) {
                                if (!temp.pay(Double.parseDouble(components[1]))) {
                                    result.add(new Bill(Integer.parseInt(components[0]),
                                            Double.parseDouble(components[1]), components[2]));
                                }
                                break;
                            }
                        }
                    } else if (components[3].equals("EW")) {
                        if (p instanceof EWallet) {
                            EWallet temp = (EWallet) p;
                            if (temp.getPhoneNumber() == Integer.parseInt(components[4])) {
                                if (!temp.pay(Double.parseDouble(components[1]))) {
                                    result.add(new Bill(Integer.parseInt(components[0]),
                                            Double.parseDouble(components[1]), components[2]));
                                }
                                break;
                            }
                        }
                    } else {
                        if (p instanceof BankAccount) {
                            BankAccount temp = (BankAccount) p;
                            if (temp.getAccountNumber() == Integer.parseInt(components[4])) {
                                if (!temp.pay(Double.parseDouble(components[1]))) {
                                    result.add(new Bill(Integer.parseInt(components[0]),
                                            Double.parseDouble(components[1]), components[2]));
                                }
                                break;
                            }
                        }
                    }
                }
            }
            sc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (result.size() > 0) {
            return result;
        }
        return null;
    }

    // Requirement 8
    public ArrayList<BankAccount> getLargestPaymentByBA(String path) {
        ArrayList<BankAccount> result = new ArrayList<BankAccount>();
        HashMap<Integer, Double> paymentByBA = new HashMap<Integer, Double>();
        try {
            File f = new File(path);
            Scanner sc = new Scanner(f);
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] components = line.split(",");
                for (Payment p : paymentObjects) {
                    if (components[3].equals("CC")) {
                        if (p instanceof ConvenientCard) {
                            ConvenientCard temp = (ConvenientCard) p;
                            if (temp.getIdenNumber() == Integer.parseInt(components[4])) {
                                temp.pay(Double.parseDouble(components[1]));
                                break;
                            }
                        }
                    } else if (components[3].equals("EW")) {
                        if (p instanceof EWallet) {
                            EWallet temp = (EWallet) p;
                            if (temp.getPhoneNumber() == Integer.parseInt(components[4])) {
                                temp.pay(Double.parseDouble(components[1]));
                                break;
                            }
                        }
                    } else {
                        if (p instanceof BankAccount) {
                            BankAccount temp = (BankAccount) p;
                            if (temp.getAccountNumber() == Integer.parseInt(components[4])) {
                                if (temp.pay(Double.parseDouble(components[1]))) {
                                    if (!paymentByBA.containsKey(temp.getAccountNumber())) {
                                        paymentByBA.put(temp.getAccountNumber(), Double.parseDouble(components[1]));
                                    } else {
                                        double amount = paymentByBA.get(temp.getAccountNumber());
                                        paymentByBA.put(temp.getAccountNumber(),
                                                amount + Double.parseDouble(components[1]));
                                    }
                                } else {
                                    if (!paymentByBA.containsKey(temp.getAccountNumber())) {
                                        paymentByBA.put(temp.getAccountNumber(), 0.0);
                                    }
                                }
                                break;
                            }
                        }
                    }
                }
            }
            double max = 0;
            for (Double temp : paymentByBA.values()) {
                if (temp > max) {
                    max = temp;
                }
            }
            for (Integer temp : paymentByBA.keySet()) {
                if (paymentByBA.get(temp) == max) {
                    for (Payment p : paymentObjects) {
                        if (p instanceof BankAccount) {
                            BankAccount ba = (BankAccount) p;
                            if (ba.getAccountNumber() == temp) {
                                result.add(ba);
                                break;
                            }
                        }
                    }
                }
            }
            sc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (result.size() > 0) {
            return result;
        }
        return null;
    }

    // Requirement 9
    public void processTransactionWithDiscount(String path) {
        ArrayList<IDCard> idCardInfo = idcm.getIDCards();
        try {
            File f = new File(path);
            Scanner sc = new Scanner(f);
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] components = line.split(",");
                if (Double.parseDouble(components[1]) > 500 && components[2].equals("Clothing")
                        && components[3].equals("EW")) {
                    for (Payment p : paymentObjects) {
                        if (p instanceof EWallet) {
                            EWallet ew = (EWallet) p;
                            if (ew.getPhoneNumber() == Integer.parseInt(components[4])) {
                                for (IDCard temp : idCardInfo) {
                                    if (temp.getPhoneNumber() == Integer.parseInt(components[4])) {
                                        if ((temp.getSex().equals("Female") && temp.getAge() < 18)
                                                || (temp.getSex().equals("Male") && temp.getAge() < 20)) {
                                            ew.pay(Double.parseDouble(components[1]) * 0.85);
                                        } else {
                                            ew.pay(Double.parseDouble(components[1]));
                                        }
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                    }
                } else {
                    for (Payment p : paymentObjects) {
                        if (components[3].equals("BA")) {
                            if (p instanceof BankAccount) {
                                BankAccount temp = (BankAccount) p;
                                if (temp.getAccountNumber() == Integer.parseInt(components[4])) {
                                    temp.pay(Double.parseDouble(components[1]));
                                    break;
                                }
                            }
                        } else if (components[3].equals("CC")) {
                            if (p instanceof ConvenientCard) {
                                ConvenientCard temp = (ConvenientCard) p;
                                if (temp.getIdenNumber() == Integer.parseInt(components[4])) {
                                    temp.pay(Double.parseDouble(components[1]));
                                    break;
                                }
                            }
                        } else {
                            if (p instanceof EWallet) {
                                EWallet temp = (EWallet) p;
                                if (temp.getPhoneNumber() == Integer.parseInt(components[4])) {
                                    temp.pay(Double.parseDouble(components[1]));
                                }
                            }
                        }
                    }
                }
            }
            sc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
