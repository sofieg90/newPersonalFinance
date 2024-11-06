package money;

public interface TransactionSaver {
    void saveTransaction(MoneyCheck.Transaction transaction);

    void deleteTransaction(int id);
}