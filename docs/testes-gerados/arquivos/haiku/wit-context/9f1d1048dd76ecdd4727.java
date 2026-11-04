/*
 * @test
 * @summary Witup-derived tests for String.repeat(int)
 * @run main StringWitupTest
 */

public class StringWitupTest {

    public static void main(String[] args) {
        testNegativeCountThrows();
        testMinValueCountThrows();
        testZeroCountReturnsEmpty();
        testCountOneReturnsSameInstance();
        testEmptyReceiverReturnsEmpty();
        testSingleCharReceiver();
        testMultiCharReceiver();
        testLargeValidCount();
        testOverflowGuardThrowsOutOfMemoryError();
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testNegativeCountThrows() {
        try {
            "ab".repeat(-1);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("expected IllegalArgumentException for count -1");
    }

    static void testMinValueCountThrows() {
        try {
            "ab".repeat(Integer.MIN_VALUE);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("expected IllegalArgumentException for Integer.MIN_VALUE");
    }

    static void testZeroCountReturnsEmpty() {
        check("abc".repeat(0).equals(""), "abc.repeat(0) must be empty");
        check("x".repeat(0).isEmpty(), "x.repeat(0) must be empty");
    }

    static void testCountOneReturnsSameInstance() {
        String s = "abc";
        check(s.repeat(1) == s, "repeat(1) must return this");
    }

    static void testEmptyReceiverReturnsEmpty() {
        check("".repeat(5).isEmpty(), "empty.repeat(5) must be empty");
        check("".repeat(1).isEmpty(), "empty.repeat(1) must be empty");
    }

    static void testSingleCharReceiver() {
        check("x".repeat(4).equals("xxxx"), "x.repeat(4) must be xxxx");
        check("\u00e9".repeat(3).equals("\u00e9\u00e9\u00e9"), "non-ASCII single char repeat failed");
    }

    static void testMultiCharReceiver() {
        check("ab".repeat(3).equals("ababab"), "ab.repeat(3) must be ababab");
        check("abc".repeat(2).equals("abcabc"), "abc.repeat(2) must be abcabc");
        check("ab".repeat(2).length() == 4, "length mismatch");
    }

    static void testLargeValidCount() {
        String r = "ab".repeat(1000);
        check(r.length() == 2000, "expected length 2000, got " + r.length());
        for (int i = 0; i < r.length(); i += 2) {
            check(r.charAt(i) == 'a' && r.charAt(i + 1) == 'b', "pattern broken at " + i);
        }
    }

    static void testOverflowGuardThrowsOutOfMemoryError() {
        try {
            "ab".repeat(1073741824);
        } catch (OutOfMemoryError e) {
            return;
        }
        throw new RuntimeException("expected OutOfMemoryError for overflowing repeat");
    }
}
