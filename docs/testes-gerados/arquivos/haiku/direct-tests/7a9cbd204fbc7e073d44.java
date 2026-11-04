/*
 * @test
 * @summary Verifies StringBuilder.insert(int, Object) inserts the String.valueOf result at the given offset and returns this builder
 * @run main StringBuilderWitupTest
 */

public class StringBuilderWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static final class Named {
        private final String name;

        Named(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    static void testInsertAtMiddle() {
        StringBuilder sb = new StringBuilder("ab");
        StringBuilder result = sb.insert(1, (Object) "X");
        check("aXb".equals(sb.toString()), "expected aXb but was " + sb);
        check(result == sb, "insert(int, Object) must return this builder");
    }

    static void testInsertAtStart() {
        StringBuilder sb = new StringBuilder("abc");
        sb.insert(0, (Object) "Z");
        check("Zabc".equals(sb.toString()), "expected Zabc but was " + sb);
    }

    static void testInsertAtEnd() {
        StringBuilder sb = new StringBuilder("abc");
        sb.insert(3, (Object) "Z");
        check("abcZ".equals(sb.toString()), "expected abcZ but was " + sb);
    }

    static void testInsertNullObjectAddsNullText() {
        StringBuilder sb = new StringBuilder("ab");
        sb.insert(1, (Object) null);
        check("anullb".equals(sb.toString()), "expected anullb but was " + sb);
    }

    static void testUsesToStringOfObject() {
        StringBuilder sb = new StringBuilder("ab");
        sb.insert(2, (Object) new Named("123"));
        check("ab123".equals(sb.toString()), "expected ab123 but was " + sb);
    }

    static void testInsertIntoEmptyBuilder() {
        StringBuilder sb = new StringBuilder();
        sb.insert(0, (Object) new Named("xy"));
        check("xy".equals(sb.toString()), "expected xy but was " + sb);
        check(sb.length() == 2, "expected length 2 but was " + sb.length());
    }

    static void testChainedInsertReturnsSameBuilder() {
        StringBuilder sb = new StringBuilder("a");
        StringBuilder r = sb.insert(0, (Object) "1").insert(2, (Object) "2");
        check(r == sb, "chained insert must return same builder");
        check("1a2".equals(sb.toString()), "expected 1a2 but was " + sb);
    }

    public static void main(String[] args) {
        testInsertAtMiddle();
        testInsertAtStart();
        testInsertAtEnd();
        testInsertNullObjectAddsNullText();
        testUsesToStringOfObject();
        testInsertIntoEmptyBuilder();
        testChainedInsertReturnsSameBuilder();
    }
}
