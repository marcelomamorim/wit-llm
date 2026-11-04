/*
 * @test
 * @summary Tests for java.util.Scanner.hasNextByte(int radix)
 * @run main ScannerWitupTest
 */

import java.util.Scanner;
import java.io.*;

public class ScannerWitupTest {

    public static void main(String[] args) {
        testHasNextByteWithValidRadix();
        testHasNextByteWithRadixTooSmall();
        testHasNextByteWithRadixTooLarge();
        testHasNextByteWithNumberFormatException();
    }

    private static void testHasNextByteWithValidRadix() {
        String input = "127";
        try (Scanner scanner = new Scanner(new StringReader(input))) {
            boolean result = scanner.hasNextByte(10);
            System.out.println("testHasNextByteWithValidRadix: " + result);
        }
    }

    private static void testHasNextByteWithRadixTooSmall() {
        String input = "127";
        try (Scanner scanner = new Scanner(new StringReader(input))) {
            boolean exceptionThrown = false;
            try {
                scanner.hasNextByte(1);
            } catch (IllegalArgumentException e) {
                exceptionThrown = true;
            }
            System.out.println("testHasNextByteWithRadixTooSmall: " + exceptionThrown);
        }
    }

    private static void testHasNextByteWithRadixTooLarge() {
        String input = "127";
        try (Scanner scanner = new Scanner(new StringReader(input))) {
            boolean exceptionThrown = false;
            try {
                scanner.hasNextByte(37);
            } catch (IllegalArgumentException e) {
                exceptionThrown = true;
            }
            System.out.println("testHasNextByteWithRadixTooLarge: " + exceptionThrown);
        }
    }

    private static void testHasNextByteWithNumberFormatException() {
        String input = "notANumber";
        try (Scanner scanner = new Scanner(new StringReader(input))) {
            boolean result = scanner.hasNextByte(10);
            System.out.println("testHasNextByteWithNumberFormatException: " + result);
        }
    }
}
