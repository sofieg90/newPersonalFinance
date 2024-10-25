package personalFinance;

import java.util.Date;

public class Transaction {
    public double amount;
    public Date date;
    public String type;

    public Transaction(String type, double amount, Date date) {
        this.amount = amount;
        this.date = date;
        this.type = type;
    }

    public double getAmount() {

        return amount;
    }

    public Date getDate() {

        return date;
    }

    public String getType() {

        return type;
    }
}
