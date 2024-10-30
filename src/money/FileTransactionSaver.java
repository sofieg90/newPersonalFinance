package money;

import personalFinance.Transaction;

public class FileTransactionSaver implements TransactionSaver {

    @Override
    public void saveTransaction(Transaction transaction) {
        // Implementera kod för att spara en transaktion, t.ex. till en fil eller databas
    }

    @Override
    public void deleteTransaction(int id) {
        // Implementera kod för att radera en transaktion baserat på ID
    }
}

