/*
 * @test
 * @summary Verifies Scanner.hasNextLong(int radix) rejects invalid radixes and handles valid radix tokens and overflow publicly
 * @run main ScannerWitupTest
 */

import java.io.StringReader;

public class ScannerWitupTest {

    public static void main(String[] args) throws Exception {
        testInvalidRadixThrowsIllegalArgumentException();
        testValidRadixTokenReturnsTrueAndDoesNotConsume();
        testTokenOutsideRadixReturnsFalse();
        testOverflowReturnsFalse();
        testHexTokenWithinLongRange();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testInvalidRadixThrowsIllegalArgumentException() {
        int[] radixes = {1, 0, -1, 37, Integer.MAX_VALUE};
        for (int radix : radixes) {
            try (Scanner sc = new Scanner(new StringReader("123"))) {
                sc.hasNextLong(radix);
                throw new RuntimeException("expected IllegalArgumentException for radix " + radix);
            } catch (IllegalArgumentException expected) {
                // expected
            }
        }
    }

    static void testValidRadixTokenReturnsTrueAndDoesNotConsume() {
        try (Scanner sc = new Scanner(new StringReader("123"))) {
            check(sc.hasNextLong(10), "hasNextLong(10) should be true for 123");
            check(sc.hasNextLong(10), "hasNextLong(10) should not consume the token");
            check(sc.nextLong(10) == 123L, "nextLong(10) should return 123");
        }
    }

    static void testTokenOutsideRadixReturnsFalse() {
        try (Scanner sc = new Scanner(new StringReader("102"))) {
            check(!sc.hasNextLong(2), "hasNextLong(2) should be false for 102");
        }
    }

    static void testOverflowReturnsFalse() {
        try (Scanner sc = new Scanner(new StringReader("9223372036854775808"))) {
            check(!sc.hasNextLong(10), "hasNextLong(10) should be false on long overflow");
        }
    }

    static void testHexTokenWithinLongRange() {
        try (Scanner sc = new Scanner(new StringReader("123456"))) {
            check(sc.hasNextLong(16), "hasNextLong(16) should be true for 123456");
            check(sc.nextLong(16) == 0x123456L, "nextLong(16) should return 0x123456");
        }
    }
}
