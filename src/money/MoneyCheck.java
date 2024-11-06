package money;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class MoneyCheck {
    public static List<Transaction> transactions = new ArrayList<>(); // Lista för att spara alla uttag
    private double balance;
    private final TransactionSaver transactionSaver;

    public MoneyCheck(TransactionSaver transactionSaver) {
        this.transactionSaver = transactionSaver;
    }

    public void addTransaction(double amount, Date date, String type) {
        Transaction transaction = new Transaction(type, amount, date);
        System.out.println("Du har lagt till: " + type + " på " + amount + ".");

        // Spara transaktionen
        transactionSaver.saveTransaction(transaction);

        transactions.add(transaction);
        balance += (type.equalsIgnoreCase("lön") ? amount : -amount); // Om det är lön, öka saldo, annars minska
    }

    public void deleteTransaction(int id) {
        boolean found = false; //False tills en transaktion med samma ID hittas
        Iterator<Transaction> iterator = transactions.iterator(); //Gör så att man kan gå igenom hela transaktionslistan

        while (iterator.hasNext()) { //Går igenom listan
            Transaction t = iterator.next();
            if (t.getId() == id) { // Ändrad för att jämföra med ID
                iterator.remove();
                balance -= t.getAmount(); // Använd beloppet från transaktionen

                // Radera transaktionen
                transactionSaver.deleteTransaction(t.getId());

                found = true; // När ID:t hittas
                System.out.println("Transaktionen har raderats.");
                break;
            }
        }
        if (!found) {
            System.out.println("Ingen transaktion med detta ID hittades.");
        }
    }

    public double getBalance() { // Visa saldo
        balance = 0;
        for (Transaction i : transactions) {
            if (i.getType().equalsIgnoreCase("lön")) {
                balance += i.getAmount();
            } else {
                balance -= i.getAmount();
            }
        }
        return balance;
    }

    public double getTotalForPeriod(int calendarField, String type) {
        Calendar cal = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        double total = 0;

        for (Transaction i : transactions) {
            if (!i.getType().equalsIgnoreCase(type)) continue;  // Fortsätt om typen inte matchar

            cal.setTime(i.getDate());

            boolean match = false;
            switch (calendarField) {
                case Calendar.YEAR:
                    match = (cal.get(Calendar.YEAR) == now.get(Calendar.YEAR));
                    break;
                case Calendar.MONTH:
                    match = (cal.get(Calendar.YEAR) == now.get(Calendar.YEAR) &&
                            cal.get(Calendar.MONTH) == now.get(Calendar.MONTH));
                    break;
                case Calendar.WEEK_OF_YEAR:
                    match = (cal.get(Calendar.YEAR) == now.get(Calendar.YEAR) &&
                            cal.get(Calendar.WEEK_OF_YEAR) == now.get(Calendar.WEEK_OF_YEAR));
                    break;
                case Calendar.DAY_OF_YEAR:
                    match = (cal.get(Calendar.YEAR) == now.get(Calendar.YEAR) &&
                            cal.get(Calendar.DAY_OF_YEAR) == now.get(Calendar.DAY_OF_YEAR));
                    break;
            }
            if (match) {
                total += i.getAmount();
            }
        }
        return total;
    }

    public void saveTransactionsToFile(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) { //Öppna filen fileName rad för rad med bufferedReader:
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            for (Transaction t : transactions) { //Går igenom hela listan med transaktioner
                String formattedDate = formatter.format(t.getDate());
                writer.write(t.getAmount() + "," + formattedDate + "," + t.getType());
                writer.newLine(); //Gör en ny rad efter varje transaiton
            }
            System.out.println("Transaktionen har sparats till filen.");
        } catch (IOException e) {
            System.out.println("Transaktionen kunde inte sparas: " + e.getMessage());
        }
    }

    public void loadTransactionsFromFile(String fileName) {
        transactions.clear(); // Rensa tidigare transaktioner för att undvika dubblering

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) { //Öppna filen fileName rad för rad med bufferedReader:
            String line;
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); //Skriv ut datumet i rätt format
            while ((line = reader.readLine()) != null) { //Läser filen rad för rad tills det tar slut
                String[] data = line.split(","); //delar upp datumet med kommatecken
                double amount = Double.parseDouble(data[0]);
                Date date = formatter.parse(data[1]);
                String type = data[2];
                //Formaterar summa, datum och typ(lön lr köp)

                transactions.add(new Transaction(type, amount, date));
                if (type.equalsIgnoreCase("lön")) {
                    balance += amount; // Lägg till beloppet om det är lön
                } else {
                    balance -= amount; // Dra av beloppet om det är köp
                }
            }
            System.out.println("Transaktioner har laddats från filen.");
        } catch (Exception e) {
            System.out.println("Transaktionen misslyckades tyvärr! " + e.getMessage());
        }
    }

    public static class Transaction {
        private static int counter = 0;
        private final int id;
        private final double amount;
        private final String type;
        private final Date date;

        public Transaction(String type, double amount, Date date) {
            this.id = ++counter;
            this.amount = amount;
            this.type = type;
            this.date = date;
        }

        public Transaction(int id, double amount, String date, String type) throws ParseException {
            this.id = id;
            this.amount = amount;
            this.type = type;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // Format för datum
            this.date = sdf.parse(date);
        }

        public int getId() {
            return id;
        }

        public double getAmount() {
            return amount;
        }

        public String getDateAsString() {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(date);
        }

        public String getType() {
            return type;
        }


        public Date getDate() {
            return date;
        }
    }
}

