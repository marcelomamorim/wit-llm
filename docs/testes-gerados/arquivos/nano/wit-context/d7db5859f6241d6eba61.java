/*
 * @test
 * @summary Test for Scanner.hasNextInt(int radix) method.
 * @run main ScannerWitupTest
 */

import java.util.*;
import java.io.*;

public class ScannerWitupTest {

    public static void main(String[] args) {
        testHasNextIntWithValidRadix();
        testHasNextIntWithInvalidRadix();
        testHasNextIntWithNumberFormatException();
    }

    private static void testHasNextIntWithValidRadix() {
        String input = "123 456";
        Scanner scanner = new Scanner(input);
        try {
            boolean result = scanner.hasNextInt(10);
            System.out.println("hasNextInt(10): " + result);
            if (result) {
                int value = scanner.nextInt(10);
                System.out.println("Parsed int: " + value);
            }
        } catch (Exception e) {
            System.out.println("Unexpected exception in valid radix test: " + e);
        } finally {
            scanner.close();
        }
    }

    private static void testHasNextIntWithInvalidRadix() {
        String input = "123";
        Scanner scanner = new Scanner(input);
        try {
            // radix > 36
            boolean resultHigh = scanner.hasNextInt(37);
            System.out.println("hasNextInt(37): " + resultHigh);
        } catch (IllegalArgumentException e) {
            System.out.println("Caught expected IllegalArgumentException for radix > 36");
        } catch (Exception e) {
            System.out.println("Unexpected exception for radix > 36: " + e);
        } finally {
            scanner.close();
        }

        scanner = new Scanner(input);
        try {
            // radix < 2
            boolean resultLow = scanner.hasNextInt(1);
            System.out.println("hasNextInt(1): " + resultLow);
        } catch (IllegalArgumentException e) {
            System.out.println("Caught expected IllegalArgumentException for radix < 2");
        } catch (Exception e) {
            System.out.println("Unexpected exception for radix < 2: " + e);
        } finally {
            scanner.close();
        }
    }

    private static void testHasNextIntWithNumberFormatException() {
        String input = "notANumber";
        Scanner scanner = new Scanner(input);
        try {
            boolean result = scanner.hasNextInt(10);
            System.out.println("hasNextInt(10) with non-number: " + result);
            if (result) {
                int value = scanner.nextInt(10);
                System.out.println("Parsed int: " + value);
            }
        } catch (NumberFormatException e) {
            System.out.println("Caught expected NumberFormatException");
        } catch (Exception e) {
            System.out.println("Unexpected exception: " + e);
        } finally {
            scanner.close();
        }
    }
}
