package money;

import personalFinance.Transaction;

public interface TransactionSaver {
    void saveTransaction(Transaction transaction);

    void deleteTransaction(int id);
}
