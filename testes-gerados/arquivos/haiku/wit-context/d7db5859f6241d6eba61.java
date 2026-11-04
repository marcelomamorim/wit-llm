/*
 * @test
 * @summary Witup-derived checks for Scanner.hasNextInt(int radix): radix validation and integer token detection
 * @run main ScannerWitupTest
 */

import java.io.StringReader;
import java.util.Scanner;

public class ScannerWitupTest {

    public static void main(String[] args) {
        checkTrue(new Scanner(new StringReader("123")).hasNextInt(10), "decimal token should be an int");

        Scanner s = new Scanner(new StringReader("123"));
        checkTrue(s.hasNextInt(10), "hasNextInt(10) should be true before nextInt");
        checkEquals(123, s.nextInt(10), "nextInt(10) should return cached value 123");

        checkTrue(new Scanner(new StringReader("ff")).hasNextInt(16), "'ff' should be a hex int");
        checkFalse(new Scanner(new StringReader("ff")).hasNextInt(10), "'ff' should not be a decimal int");
        checkFalse(new Scanner(new StringReader("102")).hasNextInt(2), "'102' should not be a binary int");
        checkFalse(new Scanner(new StringReader("abc")).hasNextInt(10), "'abc' should not be a decimal int");
        checkFalse(new Scanner(new StringReader("99999999999")).hasNextInt(10), "overflowing token should yield false");
        checkFalse(new Scanner(new StringReader("")).hasNextInt(10), "empty input should yield false");

        expectIllegalArgument(1);
        expectIllegalArgument(0);
        expectIllegalArgument(-1);
        expectIllegalArgument(37);
    }

    private static void expectIllegalArgument(int radix) {
        try {
            new Scanner(new StringReader("1")).hasNextInt(radix);
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new RuntimeException("IllegalArgumentException expected for radix " + radix);
    }

    private static void checkTrue(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    private static void checkFalse(boolean condition, String message) {
        if (condition) {
            throw new RuntimeException(message);
        }
    }

    private static void checkEquals(int expected, int actual, String message) {
        if (expected != actual) {
            throw new RuntimeException(message + ": expected " + expected + " but was " + actual);
        }
    }
}
