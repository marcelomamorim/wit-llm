/*
 * @test
 * @summary Verify StringBuilder.deleteCharAt returns this and throws StringIndexOutOfBoundsException for invalid indexes
 * @run main StringBuilderWitupTest
 */

public class StringBuilderWitupTest {

    public static void main(String[] args) {
        testValidDeletion();
        testReturnsSameInstance();
        testInvalidIndexesThrow();
        testEmptyBuilderThrows();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testValidDeletion() {
        StringBuilder sb = new StringBuilder("abc");
        sb.deleteCharAt(0);
        check(sb.toString().equals("bc"), "deleteCharAt(0) on abc expected bc but was " + sb);
        check(sb.length() == 2, "length after first deletion expected 2");

        sb.deleteCharAt(sb.length() - 1);
        check(sb.toString().equals("b"), "deleteCharAt(last) expected b but was " + sb);

        sb.deleteCharAt(0);
        check(sb.toString().isEmpty(), "final deletion expected empty but was " + sb);
        check(sb.length() == 0, "final length expected 0");
    }

    static void testReturnsSameInstance() {
        StringBuilder sb = new StringBuilder("xy");
        StringBuilder result = sb.deleteCharAt(1);
        check(result == sb, "deleteCharAt must return the same instance");
        check(sb.toString().equals("x"), "expected x but was " + sb);
    }

    static void testInvalidIndexesThrow() {
        expectStringIndexOutOfBounds(new StringBuilder("abc"), 3);
        expectStringIndexOutOfBounds(new StringBuilder("abc"), 10);
        expectStringIndexOutOfBounds(new StringBuilder("abc"), -1);
    }

    static void testEmptyBuilderThrows() {
        expectStringIndexOutOfBounds(new StringBuilder(), 0);
    }

    static void expectStringIndexOutOfBounds(StringBuilder sb, int index) {
        String before = sb.toString();
        try {
            sb.deleteCharAt(index);
        } catch (StringIndexOutOfBoundsException e) {
            check(sb.toString().equals(before),
                  "builder content changed after failed deleteCharAt(" + index + ")");
            return;
        }
        throw new RuntimeException("expected StringIndexOutOfBoundsException for index " + index);
    }
}
