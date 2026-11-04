/*
 * @test
 * @summary Test for Scanner.hasNextInt(int radix)
 * @run main ScannerWitupTest
 */

import java.util.*;
import java.io.*;

public class ScannerWitupTest {

    public static void main(String[] args) {
        testHasNextIntWithValidNumber();
        testHasNextIntWithInvalidNumber();
        testHasNextIntWithDifferentRadix();
    }

    private static void testHasNextIntWithValidNumber() {
        String input = "12345";
        Scanner scanner = new Scanner(input);
        boolean result = scanner.hasNextInt(10);
        System.out.println("hasNextInt(10) on '12345': " + result); // Expected: true
        scanner.close();
    }

    private static void testHasNextIntWithInvalidNumber() {
        String input = "abc";
        Scanner scanner = new Scanner(input);
        boolean result = scanner.hasNextInt(10);
        System.out.println("hasNextInt(10) on 'abc': " + result); // Expected: false
        scanner.close();
    }

    private static void testHasNextIntWithDifferentRadix() {
        String input = "1011";
        Scanner scanner = new Scanner(input);
        boolean resultBinary = scanner.hasNextInt(2);
        boolean resultDecimal = scanner.hasNextInt(10);
        System.out.println("hasNextInt(2) on '1011': " + resultBinary); // Expected: true
        System.out.println("hasNextInt(10) on '1011': " + resultDecimal); // Expected: false
        scanner.close();
    }
}
