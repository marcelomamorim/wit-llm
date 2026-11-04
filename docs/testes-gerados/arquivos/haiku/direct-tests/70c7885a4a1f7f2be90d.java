/*
 * @test
 * @summary Tests Scanner.hasNextByte() with default and custom radix, range limits and non-consuming behavior
 * @run main ScannerWitupTest
 */

import java.io.StringReader;
import java.util.Scanner;

public class ScannerWitupTest {

    public static void main(String[] args) {
        testValidByteInDefaultRadix();
        testBoundaryValues();
        testOutOfRangeValues();
        testNonNumericInput();
        testEmptyAndWhitespaceInput();
        testIdempotentAndNonConsuming();
        testCustomRadix();
    }

    static void testValidByteInDefaultRadix() {
        check(new Scanner(new StringReader("42 ")).hasNextByte(), "42 should be a byte");
        check(new Scanner(new StringReader("0")).hasNextByte(), "0 should be a byte");
    }

    static void testBoundaryValues() {
        Scanner s1 = new Scanner(new StringReader("-128"));
        check(s1.hasNextByte(), "-128 should be a byte");
        check(s1.nextByte() == (byte) -128, "nextByte should return -128");

        Scanner s2 = new Scanner(new StringReader("127"));
        check(s2.hasNextByte(), "127 should be a byte");
        check(s2.nextByte() == (byte) 127, "nextByte should return 127");
    }

    static void testOutOfRangeValues() {
        check(!new Scanner(new StringReader("128")).hasNextByte(), "128 should not be a byte");
        check(!new Scanner(new StringReader("-129")).hasNextByte(), "-129 should not be a byte");
        check(!new Scanner(new StringReader("100000")).hasNextByte(), "100000 should not be a byte");
    }

    static void testNonNumericInput() {
        check(!new Scanner(new StringReader("abc")).hasNextByte(), "abc should not be a byte");
    }

    static void testEmptyAndWhitespaceInput() {
        check(!new Scanner(new StringReader("")).hasNextByte(), "empty input should not have a byte");
        check(!new Scanner(new StringReader("   ")).hasNextByte(), "whitespace-only input should not have a byte");
    }

    static void testIdempotentAndNonConsuming() {
        Scanner s = new Scanner(new StringReader("12 abc"));
        check(s.hasNextByte(), "first hasNextByte should be true");
        check(s.hasNextByte(), "second hasNextByte should still be true");
        check(s.nextByte() == (byte) 12, "nextByte should return 12 after hasNextByte calls");
    }

    static void testCustomRadix() {
        Scanner s1 = new Scanner(new StringReader("7f"));
        s1.useRadix(16);
        check(s1.hasNextByte(), "7f in radix 16 should be a byte");
        check(s1.nextByte(16) == (byte) 127, "nextByte(16) should return 127");

        Scanner s2 = new Scanner(new StringReader("80"));
        s2.useRadix(16);
        check(!s2.hasNextByte(), "80 in radix 16 should not be a byte");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
