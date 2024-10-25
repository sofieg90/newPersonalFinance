package commands;

import money.MoneyCheck;
import java.util.Calendar;

public class Income extends Command {
    private MoneyCheck account;

    public Income(MoneyCheck moneyCheck) {
        this.account = moneyCheck;
    }

    @Override
    public void execute() {

        double incomeYear = account.getTotalForPeriod(Calendar.YEAR, "LÖN");
        double incomeMonth = account.getTotalForPeriod(Calendar.MONTH, "LÖN");
        double incomeWeek = account.getTotalForPeriod(Calendar.WEEK_OF_YEAR, "LÖN");
        double incomeDay = account.getTotalForPeriod(Calendar.DAY_OF_YEAR, "LÖN");

        System.out.println("Din inkomst årsvis: " + incomeYear + " kr.");
        System.out.println("Din inkomst månadsvis: " + incomeMonth + " kr.");
        System.out.println("Din inkomst veckovis: " + incomeWeek + " kr.");
        System.out.println("Din inkomst idag: " + incomeDay + " kr.");

    }
}
