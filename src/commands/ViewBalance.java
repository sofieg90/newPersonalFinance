package commands;

import money.MoneyCheck;

public class ViewBalance extends Command {
    private MoneyCheck account;

    public ViewBalance(MoneyCheck moneyCheck) {
        this.account = moneyCheck;

    }

    @Override
    public void execute() {
        System.out.println("Nuvarande saldo: " + account.getBalance() + " kr.");

    }
}
