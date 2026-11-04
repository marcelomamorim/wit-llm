/*
 * @test
 * @summary Verifies StringBuilder.insert(int, boolean) inserts "true"/"false" at the given offset and returns the same builder
 * @run main StringBuilderWitupTest
 */

public class StringBuilderWitupTest {

    public static void main(String[] args) {
        testInsertTrueInMiddle();
        testInsertFalseAtStart();
        testInsertAtEnd();
        testInsertIntoEmptyBuilder();
        testInsertIntoZeroCapacityBuilder();
        testChainedInsertions();
        testReturnsSameInstance();
    }

    static void testInsertTrueInMiddle() {
        StringBuilder sb = new StringBuilder("ab");
        sb.insert(1, true);
        check("atrueb".equals(sb.toString()), "expected atrueb but was " + sb);
    }

    static void testInsertFalseAtStart() {
        StringBuilder sb = new StringBuilder("xyz");
        sb.insert(0, false);
        check("falsexyz".equals(sb.toString()), "expected falsexyz but was " + sb);
    }

    static void testInsertAtEnd() {
        StringBuilder sb = new StringBuilder("x");
        sb.insert(1, true);
        check("xtrue".equals(sb.toString()), "expected xtrue but was " + sb);
    }

    static void testInsertIntoEmptyBuilder() {
        StringBuilder sb = new StringBuilder();
        sb.insert(0, false);
        check("false".equals(sb.toString()), "expected false but was " + sb);
        check(sb.length() == 5, "expected length 5 but was " + sb.length());
    }

    static void testInsertIntoZeroCapacityBuilder() {
        StringBuilder sb = new StringBuilder(0);
        sb.insert(0, true);
        check("true".equals(sb.toString()), "expected true but was " + sb);
    }

    static void testChainedInsertions() {
        StringBuilder sb = new StringBuilder();
        sb.insert(0, true).insert(4, false);
        check("truefalse".equals(sb.toString()), "expected truefalse but was " + sb);
    }

    static void testReturnsSameInstance() {
        StringBuilder sb = new StringBuilder("q");
        StringBuilder result = sb.insert(0, true);
        check(result == sb, "insert must return this builder");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
