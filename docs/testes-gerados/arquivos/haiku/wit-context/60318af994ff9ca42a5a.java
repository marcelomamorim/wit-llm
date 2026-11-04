/*
 * @test
 * @summary Witup-generated behavioral checks for Scanner.hasNextShort(int radix) radix validation and short range handling
 * @run main ScannerWitupTest
 */

import java.io.StringReader;
import java.util.Scanner;

public class ScannerWitupTest {

    public static void main(String[] args) {
        testValidRadixAndShortRange();
        testOutOfRangeShortReturnsFalse();
        testRadixControlsDigits();
        testInvalidRadixThrowsIllegalArgumentException();
    }

    static void testValidRadixAndShortRange() {
        Scanner sc = new Scanner(new StringReader("123"));
        check(sc.hasNextShort(10), true);
        sc.close();

        sc = new Scanner(new StringReader("32767"));
        check(sc.hasNextShort(10), true);
        sc.close();

        sc = new Scanner(new StringReader("-32768"));
        check(sc.hasNextShort(10), true);
        sc.close();
    }

    static void testOutOfRangeShortReturnsFalse() {
        Scanner sc = new Scanner(new StringReader("32768"));
        check(sc.hasNextShort(10), false);
        sc.close();

        sc = new Scanner(new StringReader("99999"));
        check(sc.hasNextShort(10), false);
        sc.close();
    }

    static void testRadixControlsDigits() {
        Scanner sc = new Scanner(new StringReader("ff"));
        check(sc.hasNextShort(16), true);
        sc.close();

        sc = new Scanner(new StringReader("ff"));
        check(sc.hasNextShort(10), false);
        sc.close();

        sc = new Scanner(new StringReader("101"));
        check(sc.hasNextShort(2), true);
        sc.close();
    }

    static void testInvalidRadixThrowsIllegalArgumentException() {
        expectIllegalArgument(1);
        expectIllegalArgument(37);
        expectIllegalArgument(-1);
    }

    static void expectIllegalArgument(int radix) {
        Scanner sc = new Scanner(new StringReader("1"));
        boolean thrown = false;
        try {
            sc.hasNextShort(radix);
        } catch (IllegalArgumentException e) {
            thrown = true;
        } finally {
            sc.close();
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException for radix " + radix);
        }
    }

    static void check(boolean actual, boolean expected) {
        if (actual != expected) {
            throw new RuntimeException("Expected " + expected + " but was " + actual);
        }
    }
}
