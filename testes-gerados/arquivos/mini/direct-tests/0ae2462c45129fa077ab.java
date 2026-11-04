/*
 * @test
 * @summary Test java.util.Scanner.hasNextBigInteger(int radix) method
 * @run main java.util.ScannerWitupTest
 */

import java.util.Scanner;
import java.math.BigInteger;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

public class ScannerWitupTest {
    public static void main(String[] args) throws Exception {
        testHasNextBigIntegerValidDecimal();
        testHasNextBigIntegerValidHex();
        testHasNextBigIntegerInvalidNumber();
        testHasNextBigIntegerInvalidRadix();
        testHasNextBigIntegerEmptyInput();
    }

    private static void testHasNextBigIntegerValidDecimal() {
        String input = "12345678901234567890";
        Scanner scanner = new Scanner(input);
        boolean hasBigInt = scanner.hasNextBigInteger(10);
        if (!hasBigInt) {
            throw new RuntimeException("Expected hasNextBigInteger(10) to be true for valid decimal number");
        }
        BigInteger bigInt = scanner.nextBigInteger(10);
        if (!bigInt.equals(new BigInteger(input))) {
            throw new RuntimeException("Parsed BigInteger does not match input decimal string");
        }
        scanner.close();
    }

    private static void testHasNextBigIntegerValidHex() {
        String input = "1a2b3c4d5e6f";
        Scanner scanner = new Scanner(input);
        boolean hasBigInt = scanner.hasNextBigInteger(16);
        if (!hasBigInt) {
            throw new RuntimeException("Expected hasNextBigInteger(16) to be true for valid hex number");
        }
        BigInteger bigInt = scanner.nextBigInteger(16);
        if (!bigInt.equals(new BigInteger(input, 16))) {
            throw new RuntimeException("Parsed BigInteger does not match input hex string");
        }
        scanner.close();
    }

    private static void testHasNextBigIntegerInvalidNumber() {
        String input = "123abc";
        Scanner scanner = new Scanner(input);
        boolean hasBigInt = scanner.hasNextBigInteger(10);
        if (hasBigInt) {
            throw new RuntimeException("Expected hasNextBigInteger(10) to be false for invalid decimal number");
        }
        scanner.close();
    }

    private static void testHasNextBigIntegerInvalidRadix() {
        String input = "123";
        Scanner scanner = new Scanner(input);
        try {
            // radix 1 is invalid, expect hasNextBigInteger to return false or handle gracefully
            boolean hasBigInt = scanner.hasNextBigInteger(1);
            if (hasBigInt) {
                throw new RuntimeException("Expected hasNextBigInteger(1) to be false for invalid radix");
            }
        } catch (IllegalArgumentException e) {
            // Acceptable if IllegalArgumentException is thrown for invalid radix
        } finally {
            scanner.close();
        }
    }

    private static void testHasNextBigIntegerEmptyInput() {
        String input = "";
        Scanner scanner = new Scanner(input);
        boolean hasBigInt = scanner.hasNextBigInteger(10);
        if (hasBigInt) {
            throw new RuntimeException("Expected hasNextBigInteger(10) to be false for empty input");
        }
        scanner.close();
    }
}
