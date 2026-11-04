/*
 * @test
 * @summary Test hasNextInt() method of java.util.Scanner
 * @run main ScannerWitupTest
 */

import java.util.*;
import java.io.*;

public class ScannerWitupTest {

    public static void main(String[] args) {
        testHasNextIntWithIntegerInput();
        testHasNextIntWithNonIntegerInput();
        testHasNextIntWithEmptyInput();
    }

    private static void testHasNextIntWithIntegerInput() {
        String inputStr = "12345";
        Scanner scanner = new Scanner(inputStr);
        boolean result = scanner.hasNextInt();
        if (!result) {
            throw new AssertionError("Expected hasNextInt() to return true for input '12345'");
        }
        scanner.close();
    }

    private static void testHasNextIntWithNonIntegerInput() {
        String inputStr = "abc";
        Scanner scanner = new Scanner(inputStr);
        boolean result = scanner.hasNextInt();
        if (result) {
            throw new AssertionError("Expected hasNextInt() to return false for input 'abc'");
        }
        scanner.close();
    }

    private static void testHasNextIntWithEmptyInput() {
        String inputStr = "";
        Scanner scanner = new Scanner(inputStr);
        boolean result = scanner.hasNextInt();
        if (result) {
            throw new AssertionError("Expected hasNextInt() to return false for empty input");
        }
        scanner.close();
    }
}
