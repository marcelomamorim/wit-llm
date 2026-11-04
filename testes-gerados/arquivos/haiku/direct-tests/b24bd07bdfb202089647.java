/*
 * @test
 * @summary Verifies Scanner.hasNextLong() for valid, invalid, empty, overflow and non-consuming cases
 * @run main ScannerWitupTest
 */

import java.io.StringReader;
import java.util.Scanner;

public class ScannerWitupTest {

    public static void main(String[] args) {
        testValidLong();
        testNegativeLong();
        testNonNumericToken();
        testEmptyInput();
        testOverflowLong();
        testDoesNotConsumeToken();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testValidLong() {
        Scanner sc = new Scanner(new StringReader("123456789012"));
        check(sc.hasNextLong(), "expected hasNextLong() true for valid long");
        sc.close();
    }

    static void testNegativeLong() {
        Scanner sc = new Scanner(new StringReader("-42"));
        check(sc.hasNextLong(), "expected hasNextLong() true for negative long");
        check(sc.nextLong() == -42L, "expected nextLong() to return -42");
        sc.close();
    }

    static void testNonNumericToken() {
        Scanner sc = new Scanner(new StringReader("abc"));
        check(!sc.hasNextLong(), "expected hasNextLong() false for non-numeric token");
        sc.close();
    }

    static void testEmptyInput() {
        Scanner sc = new Scanner(new StringReader(""));
        check(!sc.hasNextLong(), "expected hasNextLong() false for empty input");
        sc.close();
    }

    static void testOverflowLong() {
        Scanner sc = new Scanner(new StringReader("9999999999999999999999999"));
        check(!sc.hasNextLong(), "expected hasNextLong() false for value overflowing long");
        sc.close();
    }

    static void testDoesNotConsumeToken() {
        Scanner sc = new Scanner(new StringReader("42"));
        check(sc.hasNextLong(), "first hasNextLong() should be true");
        check(sc.hasNextLong(), "second hasNextLong() should still be true");
        check(sc.nextLong() == 42L, "nextLong() should return 42 after hasNextLong() calls");
        sc.close();
    }
}
