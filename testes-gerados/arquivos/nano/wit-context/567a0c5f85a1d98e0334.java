/*
 * @test
 * @summary Test for Scanner.hasNextLong(int radix) method.
 * @run main ScannerWitupTest
 */

import java.util.*;
import java.io.*;

public class ScannerWitupTest {

    public static void main(String[] args) {
        testHasNextLongWithValidRadix();
        testHasNextLongWithRadixTooSmall();
        testHasNextLongWithRadixTooLarge();
        testHasNextLongWithNumberFormatException();
    }

    private static void testHasNextLongWithValidRadix() {
        Scanner scanner = new Scanner("1234567890");
        try {
            boolean result = scanner.hasNextLong(10);
            System.out.println("testHasNextLongWithValidRadix: " + result);
        } catch (Exception e) {
            System.out.println("testHasNextLongWithValidRadix: Exception - " + e);
        }
    }

    private static void testHasNextLongWithRadixTooSmall() {
        Scanner scanner = new Scanner("123");
        try {
            boolean result = scanner.hasNextLong(1);
            System.out.println("testHasNextLongWithRadixTooSmall: " + result);
        } catch (IllegalArgumentException e) {
            System.out.println("testHasNextLongWithRadixTooSmall: Caught expected exception: " + e);
        } catch (Exception e) {
            System.out.println("testHasNextLongWithRadixTooSmall: Unexpected exception: " + e);
        }
    }

    private static void testHasNextLongWithRadixTooLarge() {
        Scanner scanner = new Scanner("123");
        try {
            boolean result = scanner.hasNextLong(37);
            System.out.println("testHasNextLongWithRadixTooLarge: " + result);
        } catch (IllegalArgumentException e) {
            System.out.println("testHasNextLongWithRadixTooLarge: Caught expected exception: " + e);
        } catch (Exception e) {
            System.out.println("testHasNextLongWithRadixTooLarge: Unexpected exception: " + e);
        }
    }

    private static void testHasNextLongWithNumberFormatException() {
        Scanner scanner = new Scanner("notANumber");
        try {
            boolean result = scanner.hasNextLong(10);
            System.out.println("testHasNextLongWithNumberFormatException: " + result);
        } catch (NumberFormatException e) {
            System.out.println("testHasNextLongWithNumberFormatException: Caught expected exception: " + e);
        } catch (Exception e) {
            System.out.println("testHasNextLongWithNumberFormatException: Unexpected exception: " + e);
        }
    }
}
