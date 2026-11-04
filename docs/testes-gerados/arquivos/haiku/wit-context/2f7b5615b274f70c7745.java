/*
 * @test
 * @summary Tests observable behavior of StringBuilder.insert(int, float) and offset validation
 * @run main StringBuilderWitupTest
 */

public class StringBuilderWitupTest {

    public static void main(String[] args) {
        testInsertInMiddle();
        testInsertAtStart();
        testInsertAtEnd();
        testInsertIntoEmptyBuilder();
        testNegativeFloatAndNaN();
        testReturnsSameInstance();
        testNegativeOffsetThrows();
        testOffsetBeyondLengthThrows();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testInsertInMiddle() {
        StringBuilder sb = new StringBuilder("ab");
        sb.insert(1, 1.5f);
        check("a1.5b".equals(sb.toString()), "expected a1.5b but was " + sb);
    }

    static void testInsertAtStart() {
        StringBuilder sb = new StringBuilder("ab");
        sb.insert(0, 2.0f);
        check("2.0ab".equals(sb.toString()), "expected 2.0ab but was " + sb);
    }

    static void testInsertAtEnd() {
        StringBuilder sb = new StringBuilder("ab");
        sb.insert(2, 0.5f);
        check("ab0.5".equals(sb.toString()), "expected ab0.5 but was " + sb);
    }

    static void testInsertIntoEmptyBuilder() {
        StringBuilder sb = new StringBuilder();
        sb.insert(0, 2.0f);
        check("2.0".equals(sb.toString()), "expected 2.0 but was " + sb);
    }

    static void testNegativeFloatAndNaN() {
        StringBuilder sb = new StringBuilder();
        sb.insert(0, -3.25f);
        check("-3.25".equals(sb.toString()), "expected -3.25 but was " + sb);

        StringBuilder nan = new StringBuilder();
        nan.insert(0, Float.NaN);
        check("NaN".equals(nan.toString()), "expected NaN but was " + nan);
    }

    static void testReturnsSameInstance() {
        StringBuilder sb = new StringBuilder("ab");
        StringBuilder result = sb.insert(1, 1.5f);
        check(result == sb, "insert(int, float) must return this");
    }

    static void testNegativeOffsetThrows() {
        expectStringIndexOutOfBounds(-1);
    }

    static void testOffsetBeyondLengthThrows() {
        expectStringIndexOutOfBounds(3);
    }

    static void expectStringIndexOutOfBounds(int offset) {
        StringBuilder sb = new StringBuilder("ab");
        try {
            sb.insert(offset, 1.0f);
        } catch (StringIndexOutOfBoundsException e) {
            check("ab".equals(sb.toString()),
                  "builder must be unchanged after failed insert, was " + sb);
            return;
        }
        throw new RuntimeException("expected StringIndexOutOfBoundsException for offset " + offset);
    }
}
