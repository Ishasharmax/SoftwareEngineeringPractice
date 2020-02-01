package edu.ithaca.dragon.bank;
import java.lang.*;


public class BankAccount {

    private String email;
    private double balance;

    /**
     * @throws IllegalArgumentException if email is invalid
     */
    public BankAccount(String email, double startingBalance){
        if (isEmailValid(email)){
            this.email = email;
            this.balance = startingBalance;
        }
        else if ((startingBalance * 100) % 1 != 0){
            throw new IllegalArgumentException("invalid input");
        }
        else {
            throw new IllegalArgumentException("Email address: " + email + " is invalid, cannot create account");
        }
    }

    public double getBalance(){
        return balance;
    }

    public String getEmail(){
        return email;
    }

    /**
     * @post reduces the balance by amount if amount is non-negative and smaller than balance
     * throws InsufficientFundsException if the amount is larger than the balance
     * If balance is negative, do nothing
     * @post withdraws money from a bank account
     */
    public void withdraw(double amount) throws InsufficientFundsException, IllegalArgumentException  {
        if((amount * 100) % 1 != 0){
            throw new IllegalArgumentException("invalid input");
        }
        else if (balance >= amount && amount > 0) {
            balance -= amount;
        }
        else if(balance < amount){
            throw new InsufficientFundsException("Amount requested is more than in your account by " + (amount - balance));
        }
    }

    /**
     *  isAmountValid that takes a double and returns true if the amount is positive
     *  and has two decimal points or less, and false otherwise.
     * @post checks whether amount is valid or not
     */
    public static boolean isAmountValid(double amount){
        if((amount * 100) % 1 != 0 || (amount < 0 ))
            return false;
        else
            return true;
    }

    /**
     *  isAmountValid takes a String and returns true if the syntax of the input email is valid
     *  otherwise returns false
     * @post checks whether email is valid or not

     */
    public static boolean isEmailValid(String email){
        return email.matches("(\\w)+((_|\\.|-)+\\w+)?@(\\w)+\\.\\w{2,}$");
    }

    /**
     * Deposits money into the bank account
     * @param amount the amount to deposit
     * @post deposits money to a bank account
     */
    public void deposit(double amount) throws IllegalArgumentException {
        if(amount< 0 || (amount * 100) % 1 != 0){
            throw new IllegalArgumentException("invalid input");
        }
        else {
            double newBalance = balance + amount;
            balance = newBalance;
        }
    }

    /**
     * Transfer funds from one bank account object to another
     * @param amount the amount of money to be transferred
     * @post transfers amount from one bank acc. to another
     */
    public static void transfer(BankAccount src, BankAccount dest, double amount)throws InsufficientFundsException, IllegalArgumentException {
        if(amount < 0 || (amount * 100) % 1 != 0){
            throw new IllegalArgumentException("invalid input");
        }
        else{
            src.withdraw(amount);
            dest.deposit(amount);
        }
    }
}
