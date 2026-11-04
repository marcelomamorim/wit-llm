/*
 * @test
 * @summary Verifies StringBuilder.insert(int offset, char c) returns this, inserts the char at the given offset, and rejects invalid offsets
 * @run main StringBuilderWitupTest
 */

public class StringBuilderWitupTest {

    public static void main(String[] args) {
        testInsertInMiddleReturnsSameInstance();
        testInsertAtStart();
        testInsertAtEnd();
        testInsertIntoEmptyBuilder();
        testInvalidOffsetsThrow();
    }

    static void testInsertInMiddleReturnsSameInstance() {
        StringBuilder sb = new StringBuilder("start");
        StringBuilder result = sb.insert(4, 'l');
        check(result == sb, "insert must return this");
        check("starlt".equals(sb.toString()), "expected starlt but was " + sb);
    }

    static void testInsertAtStart() {
        StringBuilder sb = new StringBuilder("starlt");
        sb.insert(0, 'X');
        check("Xstarlt".equals(sb.toString()), "expected Xstarlt but was " + sb);
    }

    static void testInsertAtEnd() {
        StringBuilder sb = new StringBuilder("abc");
        sb.insert(sb.length(), 'Z');
        check("abcZ".equals(sb.toString()), "expected abcZ but was " + sb);
    }

    static void testInsertIntoEmptyBuilder() {
        StringBuilder sb = new StringBuilder();
        StringBuilder result = sb.insert(0, 'a');
        check(result == sb, "insert must return this on empty builder");
        check("a".equals(sb.toString()), "expected a but was " + sb);
    }

    static void testInvalidOffsetsThrow() {
        StringBuilder sb = new StringBuilder("ab");
        expectStringIndexOutOfBounds(sb, -1);
        expectStringIndexOutOfBounds(sb, sb.length() + 1);
        check("ab".equals(sb.toString()), "builder must be unchanged after failed insert");
    }

    static void expectStringIndexOutOfBounds(StringBuilder sb, int offset) {
        try {
            sb.insert(offset, 'x');
        } catch (StringIndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("expected StringIndexOutOfBoundsException for offset " + offset);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
