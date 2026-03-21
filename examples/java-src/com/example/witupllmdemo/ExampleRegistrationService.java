package com.example.witupllmdemo;

public class ExampleRegistrationService {

  public String createAccount(final String email, final int age, final String password) {
    if (email == null || email.isBlank()) {
      throw new IllegalArgumentException("email is required");
    }
    if (!email.contains("@")) {
      throw new IllegalArgumentException("email format is invalid");
    }
    if (age < 18) {
      throw new UnsupportedOperationException("minimum age is 18");
    }
    if (password == null || password.length() < 8) {
      throw new IllegalStateException("password must have at least 8 chars");
    }
    if (password.contains("123456")) {
      throw new SecurityException("password is too weak");
    }

    return email.trim().toLowerCase();
  }
}
