/*
 * @test
 * @summary Test hasNextBigInteger(int radix) method of java.util.Scanner
 * @run main ScannerWitupTest
 */

import java.util.*;
import java.io.*;

public class ScannerWitupTest {

    public static void main(String[] args) {
        testHasNextBigIntegerWithValidInput();
        testHasNextBigIntegerWithInvalidInput();
        testHasNextBigIntegerWithEmptyInput();
    }

    private static void testHasNextBigIntegerWithValidInput() {
        String inputStr = "12345678901234567890";
        Scanner scanner = new Scanner(inputStr);
        boolean result = scanner.hasNextBigInteger(10);
        System.out.println("Test valid input: " + result); // Expected: true
        scanner.close();
    }

    private static void testHasNextBigIntegerWithInvalidInput() {
        String inputStr = "notANumber";
        Scanner scanner = new Scanner(inputStr);
        boolean result = scanner.hasNextBigInteger(10);
        System.out.println("Test invalid input: " + result); // Expected: false
        scanner.close();
    }

    private static void testHasNextBigIntegerWithEmptyInput() {
        String inputStr = "";
        Scanner scanner = new Scanner(inputStr);
        boolean result = scanner.hasNextBigInteger(10);
        System.out.println("Test empty input: " + result); // Expected: false
        scanner.close();
    }
}
