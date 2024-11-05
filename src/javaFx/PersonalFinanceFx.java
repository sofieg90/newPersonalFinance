package javaFx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import money.FileTransactionSaver;
import money.MoneyCheck;

import java.util.Date;

public class PersonalFinanceFx extends Application {
    private final MoneyCheck account = new MoneyCheck(new FileTransactionSaver());
    private final String fileName = "transactions.txt";

    @Override
    public void start(Stage primaryStage) {
        account.loadTransactionsFromFile(fileName);

        VBox layout = new VBox(10);
        Label welcomeLabel = new Label("Välkommen!");
        welcomeLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Label welcomeLabel2 = new Label("Skriv i summan, välj lön eller köp och sedan lägg till transaktion:");
        Label welcomeLabel3 = new Label("Alternativt skriv in summan och välj Radera transaktion: ");

        layout.getChildren().addAll(welcomeLabel, welcomeLabel2, welcomeLabel3);

        ComboBox<String> transactionTypeBox = new ComboBox<>();
        transactionTypeBox.getItems().addAll("Lön", "Köp");
        transactionTypeBox.setValue("Lön");

        TextField amountField = new TextField();
        amountField.setPromptText("Ange belopp");


        Button addTransactionButton = new Button("Lägg till transaktion");
        Button deleteTransactionButton = new Button("Radera transaktion");
        Button balanceButton = new Button("Kontoinformation");
        Button expenseButton = new Button("Visa utgifter");
        Button incomeButton = new Button("Visa inkomster");
        Button quitButton = new Button("Avsluta");

        Label statusLabel = new Label();

        addTransactionButton.setOnAction(e -> { //lägger till transaktion
            try {
                String type = transactionTypeBox.getValue();
                double amount = Double.parseDouble(amountField.getText());
                account.addTransaction(amount, new Date(), type);
                account.saveTransactionsToFile(fileName);
                statusLabel.setText(type + ": " + amount + " har lagts till.");
            } catch (NumberFormatException ex) {
                statusLabel.setText("Ogiltigt belopp.");
            }
        });

        deleteTransactionButton.setOnAction(e -> { //raderar transaktion
            try {
                double amount = Double.parseDouble(amountField.getText());
                account.deleteTransaction((int) amount); //radera transaktionen
                account.saveTransactionsToFile(fileName); //spara till filen
                statusLabel.setText("Transaktion raderad: " + amount);
            } catch (NumberFormatException ex) {
                statusLabel.setText("Ogiltigt belopp.");
            }
        });


        balanceButton.setOnAction(e -> { //kontoinfo
            double balance = account.getBalance();
            statusLabel.setText("Du har " + balance + " kr på kontot.");
        });


        expenseButton.setOnAction(e -> { //visar köp år,vecka mm.
            String report = getExpenseReport();
            statusLabel.setText(report);
        });


        incomeButton.setOnAction(e -> { //inkomst år vecka mm
            String report = getIncomeReport();
            statusLabel.setText(report);
        });


        quitButton.setOnAction(e -> { //avsluta
            account.saveTransactionsToFile(fileName); //sparar transaktionerna innan det avslutas
            System.out.println("Programmet avslutas. Ha en fin dag!");
            System.exit(0);
        });

        layout.getChildren().addAll(amountField, transactionTypeBox, addTransactionButton, deleteTransactionButton,
                balanceButton, expenseButton, incomeButton, quitButton, statusLabel);
        //gör alla knappar synliga


        Scene scene = new Scene(layout, 400, 500);
        primaryStage.setTitle("Personal Finance");
        primaryStage.setScene(scene);
        primaryStage.show();
        //skapar en "ruta" o öppnar den
    }

    private String getExpenseReport() {
        return "Utgifter - Årsvis: " + account.getTotalForPeriod(java.util.Calendar.YEAR, "köp") + " kr\n" +
                "Månadsvis: " + account.getTotalForPeriod(java.util.Calendar.MONTH, "köp") + " kr\n" +
                "Veckovis: " + account.getTotalForPeriod(java.util.Calendar.WEEK_OF_YEAR, "köp") + " kr\n" +
                "Dagvis: " + account.getTotalForPeriod(java.util.Calendar.DAY_OF_YEAR, "köp") + " kr";
    }

    private String getIncomeReport() {
        return "Inkomster - Årsvis: " + account.getTotalForPeriod(java.util.Calendar.YEAR, "lön") + " kr\n" +
                "Månadsvis: " + account.getTotalForPeriod(java.util.Calendar.MONTH, "lön") + " kr\n" +
                "Veckovis: " + account.getTotalForPeriod(java.util.Calendar.WEEK_OF_YEAR, "lön") + " kr\n" +
                "Dagvis: " + account.getTotalForPeriod(java.util.Calendar.DAY_OF_YEAR, "lön") + " kr";
    }
}
