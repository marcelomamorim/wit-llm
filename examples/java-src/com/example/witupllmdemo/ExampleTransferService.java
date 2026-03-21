package com.example.witupllmdemo;

public class ExampleTransferService {

  public int transfer(final int balance, final int amount, final boolean accountLocked) {
    if (accountLocked) {
      throw new IllegalStateException("account is locked");
    }
    if (amount <= 0) {
      throw new IllegalArgumentException("amount must be positive");
    }
    if (amount > balance) {
      throw new RuntimeException("insufficient funds");
    }

    int remainingBalance = balance - amount;
    if (remainingBalance < 10) {
      throw new ArithmeticException("minimum balance violation");
    }

    return remainingBalance;
  }
}
