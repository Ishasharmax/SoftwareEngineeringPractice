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
     */
    public void withdraw(double amount) throws InsufficientFundsException, IllegalArgumentException  {
        if(amount < 0 && (amount * 100) % 1 != 0){
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
     */
    public static boolean isAmountValid(double amount){
        if((amount * 100) % 1 != 0 && (amount < 0 ))
            return false;
        else
            return true;
    }


    public static boolean isEmailValid(String email){
        return email.matches("(\\w)+((_|\\.|-)+\\w+)?@(\\w)+\\.\\w{2,}$");
    }
}
