/*
 * @test
 * @summary Verifies observable behavior of StringBuilder.replace(int, int, String)
 * @run main StringBuilderWitupTest
 */

public class StringBuilderWitupTest {

    static int failures = 0;

    static void check(boolean cond, String msg) {
        if (!cond) {
            failures++;
            System.err.println("FAILED: " + msg);
        }
    }

    static void checkEquals(Object expected, Object actual, String msg) {
        boolean eq = (expected == null) ? (actual == null) : expected.equals(actual);
        if (!eq) {
            failures++;
            System.err.println("FAILED: " + msg + " expected=<" + expected + "> actual=<" + actual + ">");
        }
    }

    static void testBasicReplacement() {
        StringBuilder sb = new StringBuilder("hello world");
        sb.replace(0, 5, "HELLO");
        checkEquals("HELLO world", sb.toString(), "basic replacement");
    }

    static void testEndClampedToLength() {
        StringBuilder sb = new StringBuilder("hello world");
        sb.replace(6, 100, "there");
        checkEquals("hello there", sb.toString(), "end beyond length is clamped");
    }

    static void testInsertWhenStartEqualsEnd() {
        StringBuilder sb = new StringBuilder("xy");
        sb.replace(0, 0, "ab");
        checkEquals("abxy", sb.toString(), "start == end acts as insert");
    }

    static void testReplaceWithEmptyString() {
        StringBuilder sb = new StringBuilder("abcd");
        sb.replace(1, 3, "");
        checkEquals("ad", sb.toString(), "replace with empty string deletes range");
    }

    static void testReturnsSameInstance() {
        StringBuilder sb = new StringBuilder("abc");
        StringBuilder r = sb.replace(0, 1, "X");
        check(r == sb, "replace must return this");
    }

    static void testChaining() {
        StringBuilder sb = new StringBuilder("abc");
        sb.replace(0, 1, "X").append("!");
        checkEquals("Xbc!", sb.toString(), "chained append after replace");
    }

    static void testNegativeStartThrows() {
        StringBuilder sb = new StringBuilder("abc");
        boolean thrown = false;
        try {
            sb.replace(-1, 2, "X");
        } catch (StringIndexOutOfBoundsException e) {
            thrown = true;
        }
        check(thrown, "negative start must throw StringIndexOutOfBoundsException");
    }

    static void testStartGreaterThanLengthThrows() {
        StringBuilder sb = new StringBuilder("abc");
        boolean thrown = false;
        try {
            sb.replace(4, 5, "X");
        } catch (StringIndexOutOfBoundsException e) {
            thrown = true;
        }
        check(thrown, "start > length must throw StringIndexOutOfBoundsException");
    }

    static void testStartGreaterThanEndThrows() {
        StringBuilder sb = new StringBuilder("abcd");
        boolean thrown = false;
        try {
            sb.replace(3, 1, "X");
        } catch (StringIndexOutOfBoundsException e) {
            thrown = true;
        }
        check(thrown, "start > end must throw StringIndexOutOfBoundsException");
    }

    static void testNullStringThrowsNPE() {
        StringBuilder sb = new StringBuilder("abc");
        boolean thrown = false;
        try {
            sb.replace(0, 1, null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null str must throw NullPointerException");
    }

    public static void main(String[] args) {
        testBasicReplacement();
        testEndClampedToLength();
        testInsertWhenStartEqualsEnd();
        testReplaceWithEmptyString();
        testReturnsSameInstance();
        testChaining();
        testNegativeStartThrows();
        testStartGreaterThanLengthThrows();
        testStartGreaterThanEndThrows();
        testNullStringThrowsNPE();
        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
