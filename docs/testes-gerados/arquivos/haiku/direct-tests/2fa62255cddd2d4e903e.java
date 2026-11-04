/*
 * @test
 * @summary Verifies observable behavior of StringBuilder.insert(int offset, char[] str)
 * @run main StringBuilderWitupTest
 */
public class StringBuilderWitupTest {
    public static void main(String[] args) {
        testInsertAtBeginning();
        testInsertInMiddle();
        testInsertAtEnd();
        testInsertEmptyArray();
        testInsertIntoEmptyBuilder();
        testInsertNullThrowsNPE();
        testInsertCopiesArray();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testInsertAtBeginning() {
        StringBuilder sb = new StringBuilder("world");
        StringBuilder result = sb.insert(0, new char[] {'h', 'e', 'l', 'l', 'o', ' '});
        check(result == sb, "insert must return this");
        check("hello world".equals(sb.toString()), "expected 'hello world' but was " + sb);
    }

    static void testInsertInMiddle() {
        StringBuilder sb = new StringBuilder("ac");
        StringBuilder result = sb.insert(1, new char[] {'b'});
        check(result == sb, "insert must return this");
        check("abc".equals(sb.toString()), "expected 'abc' but was " + sb);
    }

    static void testInsertAtEnd() {
        StringBuilder sb = new StringBuilder("abc");
        StringBuilder result = sb.insert(3, "def".toCharArray());
        check(result == sb, "insert must return this");
        check("abcdef".equals(sb.toString()), "expected 'abcdef' but was " + sb);
    }

    static void testInsertEmptyArray() {
        StringBuilder sb = new StringBuilder("xy");
        StringBuilder result = sb.insert(1, new char[0]);
        check(result == sb, "insert must return this");
        check("xy".equals(sb.toString()), "expected 'xy' but was " + sb);
    }

    static void testInsertIntoEmptyBuilder() {
        StringBuilder sb = new StringBuilder();
        StringBuilder result = sb.insert(0, new char[] {'a', 'b'});
        check(result == sb, "insert must return this");
        check("ab".equals(sb.toString()), "expected 'ab' but was " + sb);
    }

    static void testInsertNullThrowsNPE() {
        StringBuilder sb = new StringBuilder("abc");
        boolean thrown = false;
        try {
            sb.insert(0, (char[]) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null array");
        check("abc".equals(sb.toString()), "builder must be unchanged after NPE but was " + sb);
    }

    static void testInsertCopiesArray() {
        char[] source = {'x', 'y'};
        StringBuilder sb = new StringBuilder();
        sb.insert(0, source);
        source[0] = 'z';
        check("xy".equals(sb.toString()), "builder must not reflect later changes to source array but was " + sb);
    }
}
