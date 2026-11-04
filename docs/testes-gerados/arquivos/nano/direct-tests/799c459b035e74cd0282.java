/*
 * @test
 * @summary Test hasNextBigInteger() method of java.util.Scanner
 * @run main ScannerWitupTest
 */

import java.util.*;
import java.io.*;

public class ScannerWitupTest {

    public static void main(String[] args) {
        testHasNextBigIntegerWithBigIntegerInput();
        testHasNextBigIntegerWithNonBigIntegerInput();
        testHasNextBigIntegerWithEmptyInput();
    }

    private static void testHasNextBigIntegerWithBigIntegerInput() {
        String inputStr = "123456789012345678901234567890";
        Scanner scanner = new Scanner(inputStr);
        try {
            boolean result = scanner.hasNextBigInteger();
            System.out.println("hasNextBigInteger with BigInteger input: " + result);
            // Expect true because input is a valid BigInteger
            if (!result) {
                throw new AssertionError("Expected true for valid BigInteger input");
            }
        } finally {
            scanner.close();
        }
    }

    private static void testHasNextBigIntegerWithNonBigIntegerInput() {
        String inputStr = "not_a_number";
        Scanner scanner = new Scanner(inputStr);
        try {
            boolean result = scanner.hasNextBigInteger();
            System.out.println("hasNextBigInteger with non-BigInteger input: " + result);
            // Expect false because input is not a valid BigInteger
            if (result) {
                throw new AssertionError("Expected false for non-BigInteger input");
            }
        } finally {
            scanner.close();
        }
    }

    private static void testHasNextBigIntegerWithEmptyInput() {
        String inputStr = "";
        Scanner scanner = new Scanner(inputStr);
        try {
            boolean result = scanner.hasNextBigInteger();
            System.out.println("hasNextBigInteger with empty input: " + result);
            // Expect false because input is empty
            if (result) {
                throw new AssertionError("Expected false for empty input");
            }
        } finally {
            scanner.close();
        }
    }
}
