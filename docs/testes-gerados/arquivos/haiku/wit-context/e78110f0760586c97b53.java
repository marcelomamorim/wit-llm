/*
 * @test
 * @summary Witup-derived tests for StringBuilder.replace exception and clamping behavior
 * @run main StringBuilderWitupTest
 */

public class StringBuilderWitupTest {

    public static void main(String[] args) {
        testBasicReplace();
        testEndClampedToLength();
        testReturnsSameInstance();
        testStartGreaterThanEndThrows();
        testNegativeStartThrows();
        testStartBeyondLengthThrows();
        testNullStrThrowsNPE();
        System.out.println("PASS");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testBasicReplace() {
        StringBuilder sb = new StringBuilder("abcdef");
        sb.replace(2, 4, "XY");
        check(sb.toString().equals("abXYef"), "basic replace failed: " + sb);
    }

    static void testEndClampedToLength() {
        StringBuilder sb = new StringBuilder("abc");
        sb.replace(1, 100, "Z");
        check(sb.toString().equals("aZ"), "end beyond length should be clamped: " + sb);
    }

    static void testReturnsSameInstance() {
        StringBuilder sb = new StringBuilder("abc");
        StringBuilder result = sb.replace(0, 1, "x");
        check(result == sb, "replace should return this");
        check(sb.toString().equals("xbc"), "unexpected content: " + sb);
    }

    static void testStartGreaterThanEndThrows() {
        final StringBuilder sb = new StringBuilder("abc");
        expectSIOOBE(new Runnable() {
            public void run() {
                sb.replace(3, 2, "x");
            }
        }, "start > end");
    }

    static void testNegativeStartThrows() {
        final StringBuilder sb = new StringBuilder("abc");
        expectSIOOBE(new Runnable() {
            public void run() {
                sb.replace(-1, 2, "x");
            }
        }, "negative start");
    }

    static void testStartBeyondLengthThrows() {
        final StringBuilder sb = new StringBuilder("abc");
        expectSIOOBE(new Runnable() {
            public void run() {
                sb.replace(7, 7, "x");
            }
        }, "start beyond length");
    }

    static void testNullStrThrowsNPE() {
        final StringBuilder sb = new StringBuilder("abc");
        try {
            sb.replace(0, 1, null);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("null str should throw NullPointerException");
    }

    static void expectSIOOBE(Runnable r, String message) {
        try {
            r.run();
        } catch (StringIndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException(message + ": no StringIndexOutOfBoundsException");
    }
}
