/*
 * @test
 * @summary Behavioral tests for Scanner.hasNextByte() using the default radix
 * @run main ScannerWitupTest
 */

import java.util.Scanner;

public class ScannerWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testValidByteToken() {
        Scanner sc = new Scanner("123 abc");
        check(sc.hasNextByte(), "expected hasNextByte() true for '123'");
    }

    static void testNonNumericToken() {
        Scanner sc = new Scanner("abc");
        check(!sc.hasNextByte(), "expected hasNextByte() false for 'abc'");
    }

    static void testEmptyInput() {
        Scanner sc = new Scanner("");
        check(!sc.hasNextByte(), "expected hasNextByte() false for empty input");
    }

    static void testOutOfRangeToken() {
        Scanner sc = new Scanner("300");
        check(!sc.hasNextByte(), "expected hasNextByte() false for '300'");
    }

    static void testByteBoundaries() {
        Scanner sc1 = new Scanner("127");
        check(sc1.hasNextByte(), "expected hasNextByte() true for '127'");
        Scanner sc2 = new Scanner("-128");
        check(sc2.hasNextByte(), "expected hasNextByte() true for '-128'");
    }

    static void testDoesNotConsumeToken() {
        Scanner sc = new Scanner("42");
        check(sc.hasNextByte(), "first hasNextByte() should be true");
        check(sc.hasNextByte(), "second hasNextByte() should be true (no consumption)");
        check(sc.nextByte() == 42, "nextByte() should return 42 after hasNextByte()");
    }

    static void testCustomRadix() {
        Scanner sc = new Scanner("ff");
        sc.setRadix(16);
        check(sc.hasNextByte(), "expected hasNextByte() true for 'ff' in radix 16");
        Scanner sc2 = new Scanner("ff");
        check(!sc2.hasNextByte(), "expected hasNextByte() false for 'ff' in radix 10");
    }

    public static void main(String[] args) {
        testValidByteToken();
        testNonNumericToken();
        testEmptyInput();
        testOutOfRangeToken();
        testByteBoundaries();
        testDoesNotConsumeToken();
        testCustomRadix();
    }
}
