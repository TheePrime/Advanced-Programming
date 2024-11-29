package Lecture4_interfaces_abstract_classes;

import org.jetbrains.annotations.NotNull;
import java.util.Calendar;

public class DepositTransaction extends BaseTransaction {

    public DepositTransaction(int amount, @NotNull Calendar date) {
        super(amount, date);
    }

    private boolean checkDepositAmount(int amt) {
        return amt > 0; // Simplified condition for better readability
    }

    // Method to print a transaction receipt or details
    @Override
    public void printTransactionDetails() {
        System.out.println("Transaction Type: Deposit");
        System.out.println("Transaction ID: " + getTransactionID());
        System.out.println("Transaction Amount: " + getAmount());
        System.out.println("Transaction Date: " + getDate().getTime());
    }

    // Overriding the apply method for deposits
    @Override
    public void apply(BankAccount ba) {
        if (!checkDepositAmount((int) getAmount())) {
            System.out.println("Invalid deposit amount. Transaction failed.");
            return;
        }

        double currBalance = ba.getBalance();
        double newBalance = currBalance + getAmount();
        ba.setBalance(newBalance);

        System.out.println("Deposit of " + getAmount() + " applied successfully.");
        System.out.println("New Balance: " + ba.getBalance());
    }
}
