/*
 * @test
 * @summary WITUP generated checks for StringJoiner.setEmptyValue null handling and empty value behavior
 * @run main StringJoinerWitupTest
 */

import java.util.StringJoiner;

public class StringJoinerWitupTest {

    public static void main(String[] args) {
        testNullEmptyValueThrowsNPE();
        testEmptyValueReturnedWhenEmpty();
        testEmptyValueIgnoredAfterAdd();
        testReturnsSameInstance();
        testEmptyStringAllowed();
        testEmptyValueSnapshotOfCharSequence();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullEmptyValueThrowsNPE() {
        StringJoiner sj = new StringJoiner(",", "[", "]");
        boolean thrown = false;
        try {
            sj.setEmptyValue(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "setEmptyValue(null) must throw NullPointerException");
        check(sj.toString().equals("[]"),
              "failed setEmptyValue must not change empty value, got: " + sj.toString());
    }

    static void testEmptyValueReturnedWhenEmpty() {
        StringJoiner sj = new StringJoiner(",", "[", "]");
        sj.setEmptyValue("{}");
        check(sj.toString().equals("{}"),
              "empty value must be returned when no elements added, got: " + sj.toString());
    }

    static void testEmptyValueIgnoredAfterAdd() {
        StringJoiner sj = new StringJoiner(",", "[", "]");
        sj.setEmptyValue("{}");
        sj.add("a");
        check(sj.toString().equals("[a]"),
              "empty value must not be used after add, got: " + sj.toString());
    }

    static void testReturnsSameInstance() {
        StringJoiner sj = new StringJoiner(",");
        check(sj.setEmptyValue("x") == sj, "setEmptyValue must return this");
    }

    static void testEmptyStringAllowed() {
        StringJoiner sj = new StringJoiner(",", "[", "]");
        sj.setEmptyValue("");
        check(sj.toString().equals(""),
              "empty string must be a valid empty value, got: " + sj.toString());
    }

    static void testEmptyValueSnapshotOfCharSequence() {
        StringBuilder sb = new StringBuilder("ab");
        StringJoiner sj = new StringJoiner(",");
        sj.setEmptyValue(sb);
        sb.append("c");
        check(sj.toString().equals("ab"),
              "empty value must reflect CharSequence content at set time, got: " + sj.toString());
    }
}
