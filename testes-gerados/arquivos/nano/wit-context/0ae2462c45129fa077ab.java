/*
 * @test
 * @summary Test for Scanner.hasNextBigInteger(int radix)
 * @run main ScannerWitupTest
 */

import java.util.*;
import java.io.*;

public class ScannerWitupTest {

    public static void main(String[] args) {
        testHasNextBigIntegerWithValidRadix();
        testHasNextBigIntegerWithInvalidRadix();
        testHasNextBigIntegerWithNumberFormatException();
    }

    private static void testHasNextBigIntegerWithValidRadix() {
        String input = "12345678901234567890";
        try (Scanner scanner = new Scanner(input)) {
            int radix = 10;
            boolean result = scanner.hasNextBigInteger(radix);
            System.out.println("testHasNextBigIntegerWithValidRadix: " + result);
        }
    }

    private static void testHasNextBigIntegerWithInvalidRadix() {
        String input = "123";
        try (Scanner scanner = new Scanner(input)) {
            int radix = 37; // invalid radix, should throw IllegalArgumentException
            boolean result = false;
            try {
                result = scanner.hasNextBigInteger(radix);
                System.out.println("testHasNextBigIntegerWithInvalidRadix: " + result);
            } catch (IllegalArgumentException e) {
                System.out.println("testHasNextBigIntegerWithInvalidRadix: Caught expected IllegalArgumentException");
            }
        }
    }

    private static void testHasNextBigIntegerWithNumberFormatException() {
        String input = "notanumber";
        try (Scanner scanner = new Scanner(input)) {
            int radix = 10;
            boolean result = false;
            // Prepare the scanner to match a number pattern but with invalid number
            // Since the method calls setRadix and then hasNext, we simulate that scenario
            // but in this test, we focus on the exception thrown inside hasNextBigInteger
            result = scanner.hasNextBigInteger(radix);
            System.out.println("testHasNextBigIntegerWithNumberFormatException: " + result);
        } catch (NumberFormatException e) {
            System.out.println("testHasNextBigIntegerWithNumberFormatException: Caught NumberFormatException");
        }
    }
}
