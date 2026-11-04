/*
 * @test
 * @summary Verifies StringJoiner.setEmptyValue behavior: empty value returned when empty, ignored after add, null rejection, chaining, and copy semantics.
 * @run main StringJoinerWitupTest
 */

public class StringJoinerWitupTest {

    public static void main(String[] args) {
        testEmptyValueReturnedWhenEmpty();
        testEmptyValueIgnoredAfterAdd();
        testNullRejected();
        testReturnsSameInstance();
        testCharSequenceIsCopied();
        testLastSetWins();
        testEmptyValueOverridesPrefixSuffix();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testEmptyValueReturnedWhenEmpty() {
        StringJoiner sj = new StringJoiner(",", "[", "]").setEmptyValue("EMPTY");
        check("EMPTY".equals(sj.toString()),
              "expected EMPTY but was " + sj.toString());
    }

    static void testEmptyValueIgnoredAfterAdd() {
        StringJoiner sj = new StringJoiner(",", "[", "]").setEmptyValue("EMPTY");
        sj.add("a");
        check("[a]".equals(sj.toString()),
              "expected [a] but was " + sj.toString());
    }

    static void testNullRejected() {
        boolean thrown = false;
        try {
            new StringJoiner(",").setEmptyValue(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "setEmptyValue(null) must throw NullPointerException");
    }

    static void testReturnsSameInstance() {
        StringJoiner sj = new StringJoiner(",");
        StringJoiner returned = sj.setEmptyValue("X");
        check(returned == sj, "setEmptyValue must return this");
    }

    static void testCharSequenceIsCopied() {
        StringBuilder sb = new StringBuilder("none");
        StringJoiner sj = new StringJoiner(",", "[", "]").setEmptyValue(sb);
        sb.setLength(0);
        sb.append("changed");
        check("none".equals(sj.toString()),
              "emptyValue must be a copy taken at set time, but was " + sj.toString());
    }

    static void testLastSetWins() {
        StringJoiner sj = new StringJoiner(",", "[", "]")
                .setEmptyValue("FIRST")
                .setEmptyValue("SECOND");
        check("SECOND".equals(sj.toString()),
              "expected SECOND but was " + sj.toString());
    }

    static void testEmptyValueOverridesPrefixSuffix() {
        StringJoiner sj = new StringJoiner(",", "{", "}").setEmptyValue("{}");
        check("{}".equals(sj.toString()),
              "expected {} but was " + sj.toString());
        StringJoiner sj2 = new StringJoiner(",", "<", ">").setEmptyValue("NONE");
        check("NONE".equals(sj2.toString()),
              "expected NONE but was " + sj2.toString());
    }
}
