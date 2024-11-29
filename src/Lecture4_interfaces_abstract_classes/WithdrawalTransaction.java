package Lecture4_interfaces_abstract_classes;

import org.jetbrains.annotations.NotNull;
import java.util.Calendar;

public class WithdrawalTransaction extends BaseTransaction {

    private double originalBalance; // To store the balance before withdrawal for reversal

    public WithdrawalTransaction(int amount, @NotNull Calendar date) {
        super(amount, date);
    }

    private boolean checkWithdrawalAmount(int amt) {
        return amt > 0; // A valid withdrawal amount must be positive
    }

    // Method to reverse the transaction
    public boolean reverse(BankAccount ba) {
        if (originalBalance > 0) {
            ba.setBalance(originalBalance); // Restore the original balance
            System.out.println("Transaction reversed successfully. Balance restored to: " + originalBalance);
            return true;
        }
        System.out.println("Reversal failed. No original balance recorded.");
        return false;
    }

    // Method to print a transaction receipt or details
    @Override
    public void printTransactionDetails() {
        System.out.println("Transaction Type: Withdrawal");
        System.out.println("Transaction ID: " + getTransactionID());
        System.out.println("Transaction Amount: " + getAmount());
        System.out.println("Transaction Date: " + getDate().getTime());
    }

    // Overriding the apply method for withdrawals
    @Override
    public void apply(BankAccount ba) {
        double currBalance = ba.getBalance();
        originalBalance = currBalance; // Save the current balance for potential reversal

        if (!checkWithdrawalAmount((int) getAmount())) {
            System.out.println("Invalid withdrawal amount. Transaction failed.");
            return;
        }

        try {
            if (currBalance >= getAmount()) {
                ba.setBalance(currBalance - getAmount());
                System.out.println("Withdrawal of " + getAmount() + " applied successfully.");
                System.out.println("New Balance: " + ba.getBalance());
            } else if (currBalance > 0) {
                // Partial withdrawal if balance is less than withdrawal amount
                System.out.println("Insufficient funds. Withdrawing available balance: " + currBalance);
                ba.setBalance(0);
            } else {
                throw new InsufficientFundsException("Insufficient funds. Withdrawal failed.");
            }
        } catch (InsufficientFundsException e) {
            System.out.println(e.getMessage());
        }
    }
}
