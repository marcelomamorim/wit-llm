package com.example.witupllmdemo;

public class ExampleTextRules {

  public String normalizeTag(final String rawTag) {
    if (rawTag == null) {
      throw new IllegalArgumentException("tag cannot be null");
    }
    String value = rawTag.trim();
    if (value.isEmpty()) {
      throw new IllegalArgumentException("tag cannot be blank");
    }
    if (value.length() > 20) {
      throw new IllegalStateException("tag too long");
    }
    if (!value.matches("[a-zA-Z0-9_-]+")) {
      throw new RuntimeException("tag contains invalid chars");
    }
    return value.toLowerCase();
  }
}
