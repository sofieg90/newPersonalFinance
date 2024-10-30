package personalFinance;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Transaction {
    public double amount;
    public Date date;
    public String type;
    private final int id;
    private static int counter = 0;

    public Transaction(String type, double amount, Date date) {
        this.id = ++counter;
        this.amount = amount;
        this.date = date;
        this.type = type;
    }
    public Transaction(int id, double amount, String dateAsString, String type) throws ParseException {
        this.id = id;
        this.amount = amount;
        this.date = new SimpleDateFormat("yyyy-MM-dd").parse(dateAsString);
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

    public int getId() {
        return id;
    }

    public String getDateAsString() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(date);
    }
}
