package commands;

import money.MoneyCheck;

import java.util.Scanner;

public class DeleteTransaction extends Command {
    private MoneyCheck account;

    public DeleteTransaction(MoneyCheck moneyCheck) {
        this.account = moneyCheck;
    }


    @Override
    public void execute() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Ange beoppet på transaktionen du vill radera: ");
        int belopp = scan.nextInt();

        account.deleteTransaction(belopp);
        account.saveTransactionsToFile("transactions.txt");
    }

}
