/*
 * @test
 * @summary Test for Scanner.hasNextShort(int radix)
 * @run main ScannerWitupTest
 */

import java.util.*;
import java.io.*;

public class ScannerWitupTest {

    public static void main(String[] args) {
        testHasNextShortWithValidInput();
        testHasNextShortWithInvalidInput();
        testHasNextShortWithDifferentRadix();
    }

    private static void testHasNextShortWithValidInput() {
        String input = "32767 -32768 12345";
        try (Scanner scanner = new Scanner(input)) {
            // Test with default radix 10
            assert scanner.hasNextShort(10) : "Should have next short (10)";
            String token = scanner.match().group();
            assert token.equals("32767") : "Expected 32767";
            scanner.next();
            assert scanner.hasNextShort(10) : "Should have next short (10)";
            token = scanner.match().group();
            assert token.equals("-32768") : "Expected -32768";
            scanner.next();
            assert scanner.hasNextShort(10) : "Should have next short (10)";
            token = scanner.match().group();
            assert token.equals("12345") : "Expected 12345";
            scanner.next();
            assert !scanner.hasNextShort(10) : "No more tokens";
        }
    }

    private static void testHasNextShortWithInvalidInput() {
        String input = "notANumber 70000";
        try (Scanner scanner = new Scanner(input)) {
            // First token invalid for short
            assert !scanner.hasNextShort(10) : "First token invalid for short";
            // Move to second token
            scanner.next();
            // Second token out of range for short
            assert !scanner.hasNextShort(10) : "Second token out of range for short";
        }
    }

    private static void testHasNextShortWithDifferentRadix() {
        String input = "7B 177 123"; // 7B in hex is 123 decimal
        try (Scanner scanner = new Scanner(input)) {
            // Radix 16 for first token
            assert scanner.hasNextShort(16) : "Should have next short in radix 16";
            String token = scanner.match().group();
            assert token.equals("7B") : "Expected 7B";
            scanner.next();
            // Radix 8 for second token (177 octal = 127 decimal), should fail for short
            assert !scanner.hasNextShort(8) : "177 in octal exceeds short range";
            // Radix 10 for third token
            assert scanner.hasNextShort(10) : "Should have next short in radix 10";
            token = scanner.match().group();
            assert token.equals("123") : "Expected 123";
        }
    }
}
