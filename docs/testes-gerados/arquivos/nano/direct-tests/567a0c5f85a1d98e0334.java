/*
 * @test
 * @summary Test hasNextLong(int radix) method of java.util.Scanner
 * @run main ScannerWitupTest
 */

import java.util.*;
import java.io.*;

public class ScannerWitupTest {

    public static void main(String[] args) {
        testHasNextLongWithValidNumber();
        testHasNextLongWithInvalidNumber();
        testHasNextLongWithDifferentRadix();
    }

    private static void testHasNextLongWithValidNumber() {
        String input = "123456789";
        Scanner scanner = new Scanner(input);
        boolean result = scanner.hasNextLong(10);
        System.out.println("Test valid number (radix 10): " + result);
        assert result : "Expected true for valid number with radix 10";
        scanner.close();
    }

    private static void testHasNextLongWithInvalidNumber() {
        String input = "notANumber";
        Scanner scanner = new Scanner(input);
        boolean result = scanner.hasNextLong(10);
        System.out.println("Test invalid number (radix 10): " + result);
        assert !result : "Expected false for invalid number";
        scanner.close();
    }

    private static void testHasNextLongWithDifferentRadix() {
        String input = "FF";
        Scanner scanner = new Scanner(input);
        boolean result = scanner.hasNextLong(16);
        System.out.println("Test valid hex number (radix 16): " + result);
        assert result : "Expected true for valid hex number";
        scanner.close();
    }
}
