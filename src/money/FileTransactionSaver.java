package money;

import personalFinance.Transaction;

import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class FileTransactionSaver implements TransactionSaver {
    private static final String FILE_NAME = "transaction.txt";

    @Override
    public void saveTransaction(Transaction transaction) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(transaction.getId() + "," + transaction.getAmount() + "," +
                    transaction.getDateAsString() + "," + transaction.getType());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteTransaction(int id) {
        List<Transaction> transactions = new ArrayList<>();

        // Läs in alla transaktioner
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int transactionId = Integer.parseInt(parts[0]);

                if (transactionId != id) { // Lägg bara till om ID inte matchar
                    double amount = Double.parseDouble(parts[1]);
                    String date = parts[2];
                    String type = parts[3];
                    transactions.add(new Transaction(transactionId, amount, date, type));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        // Skriv tillbaka transaktionerna som finns kvar till filen
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Transaction transaction : transactions) {
                writer.write(transaction.getId() + "," + transaction.getAmount() + "," +
                        transaction.getDateAsString() + "," + transaction.getType());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
