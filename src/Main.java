import Lecture4_interfaces_abstract_classes.BankAccount;
import Lecture4_interfaces_abstract_classes.BaseTransaction;
import Lecture4_interfaces_abstract_classes.InsufficientFundsException;
import Lecture4_interfaces_abstract_classes.WithdrawalTransaction;
import Lecture4_interfaces_abstract_classes.DepositTransaction;

import java.util.Calendar;

public class Main {
    public static void main(String[] args) {
        // Create a bank account
        BankAccount account = new BankAccount(500.00);
        Calendar date = Calendar.getInstance();

        // Create transactions
        DepositTransaction deposit = new DepositTransaction(200, date);
        WithdrawalTransaction withdrawalFull = new WithdrawalTransaction(300, date);
        WithdrawalTransaction withdrawalPartial = new WithdrawalTransaction(600, date);

        // Test DepositTransaction
        System.out.println("Testing DepositTransaction:");
        deposit.apply(account);
        System.out.println("Account balance after deposit: " + account.getBalance());

        // Test WithdrawalTransaction (full withdrawal)
        System.out.println("\nTesting WithdrawalTransaction (Full):");
        try {
            withdrawalFull.apply(account);
            System.out.println("Account balance after withdrawal: " + account.getBalance());
        } catch (InsufficientFundsException e) {
            System.out.println("Exception: " + e.getMessage());
        }

        // Test WithdrawalTransaction (partial withdrawal)
        System.out.println("\nTesting WithdrawalTransaction (Partial):");
        withdrawalPartial.apply(account, true);
        System.out.println("Account balance after partial withdrawal: " + account.getBalance());
        System.out.println("Shortfall from partial withdrawal: " + withdrawalPartial.getShortfallAmount());

        // Test polymorphic behavior using type casting
        System.out.println("\nTesting Polymorphism:");
        BaseTransaction baseTransactionDeposit = (BaseTransaction) deposit;
        BaseTransaction baseTransactionWithdrawal = (BaseTransaction) withdrawalFull;

        // Apply transactions using the base class reference
        try {
            baseTransactionDeposit.apply(account);
        } catch (InsufficientFundsException e) {
            e.printStackTrace();
        }
        System.out.println("Account balance after polymorphic deposit: " + account.getBalance());

        try {
            baseTransactionWithdrawal.apply(account);
            System.out.println("Account balance after polymorphic withdrawal: " + account.getBalance());
        } catch (InsufficientFundsException e) {
            System.out.println("Exception during polymorphic withdrawal: " + e.getMessage());
        }

        // Print details of the transactions
        System.out.println("\nTransaction Details:");
        deposit.printTransactionDetails();
        withdrawalFull.printTransactionDetails();
        withdrawalPartial.printTransactionDetails();
    }
}