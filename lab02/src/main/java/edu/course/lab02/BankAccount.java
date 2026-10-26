package edu.course.lab02;


public final class BankAccount {

    private int balance;

    BankAccount(int balance){
        if (balance >= 0){
            this.balance = balance;
        }
    }
    int getBalance(){
        return this.balance;
        }

    int withdraw(int amount) {
        if (amount <= 0 || amount >= this.getBalance()) {
            throw new IllegalArgumentException("poshel naxui");
        }
        this.balance -= amount;
        return balance;
    }
    int deposit(int amount){
        if (amount < 0){
            throw new IllegalArgumentException("poshel naxui");
        }
        this.balance += amount;
        return balance;
    }
}
