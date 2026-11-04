/*
 * @test
 * @summary Test for Scanner.hasNextShort(int radix) method.
 * @run main ScannerWitupTest
 */

import java.util.*;
import java.io.*;

public class ScannerWitupTest {

    public static void main(String[] args) {
        testHasNextShortWithValidRadix();
        testHasNextShortWithRadixTooSmall();
        testHasNextShortWithRadixTooLarge();
        testHasNextShortWithNumberFormatException();
    }

    private static void testHasNextShortWithValidRadix() {
        String input = "123";
        Scanner scanner = new Scanner(input);
        try {
            boolean result = scanner.hasNextShort(10);
            System.out.println("testHasNextShortWithValidRadix: " + result);
        } catch (Exception e) {
            System.out.println("testHasNextShortWithValidRadix: Exception - " + e);
        } finally {
            scanner.close();
        }
    }

    private static void testHasNextShortWithRadixTooSmall() {
        String input = "123";
        Scanner scanner = new Scanner(input);
        try {
            boolean result = scanner.hasNextShort(1);
            System.out.println("testHasNextShortWithRadixTooSmall: " + result);
        } catch (IllegalArgumentException e) {
            System.out.println("testHasNextShortWithRadixTooSmall: Caught expected exception: " + e);
        } catch (Exception e) {
            System.out.println("testHasNextShortWithRadixTooSmall: Unexpected exception: " + e);
        } finally {
            scanner.close();
        }
    }

    private static void testHasNextShortWithRadixTooLarge() {
        String input = "123";
        Scanner scanner = new Scanner(input);
        try {
            boolean result = scanner.hasNextShort(37);
            System.out.println("testHasNextShortWithRadixTooLarge: " + result);
        } catch (IllegalArgumentException e) {
            System.out.println("testHasNextShortWithRadixTooLarge: Caught expected exception: " + e);
        } catch (Exception e) {
            System.out.println("testHasNextShortWithRadixTooLarge: Unexpected exception: " + e);
        } finally {
            scanner.close();
        }
    }

    private static void testHasNextShortWithNumberFormatException() {
        String input = "notANumber";
        Scanner scanner = new Scanner(input);
        try {
            boolean result = scanner.hasNextShort(10);
            System.out.println("testHasNextShortWithNumberFormatException: " + result);
        } catch (Exception e) {
            System.out.println("testHasNextShortWithNumberFormatException: Exception - " + e);
        } finally {
            scanner.close();
        }
    }
}
