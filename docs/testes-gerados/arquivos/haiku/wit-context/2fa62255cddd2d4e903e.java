/*
 * @test
 * @summary Verify StringBuilder.insert(int, char[]) normal and exceptional behavior
 * @run main StringBuilderWitupTest
 */

public class StringBuilderWitupTest {

    public static void main(String[] args) {
        testInsertAtStart();
        testInsertAtMiddle();
        testInsertAtEnd();
        testInsertEmptyArray();
        testReturnsSameInstance();
        testNegativeOffsetThrows();
        testOffsetBeyondLengthThrows();
        testNullArrayThrowsNPE();
    }

    static void testInsertAtStart() {
        StringBuilder sb = new StringBuilder("abc");
        sb.insert(0, new char[] {'x', 'y'});
        check("xyabc".equals(sb.toString()), "insert at 0 failed: " + sb);
    }

    static void testInsertAtMiddle() {
        StringBuilder sb = new StringBuilder("abc");
        sb.insert(1, new char[] {'x', 'y'});
        check("axybc".equals(sb.toString()), "insert at middle failed: " + sb);
    }

    static void testInsertAtEnd() {
        StringBuilder sb = new StringBuilder("abc");
        sb.insert(3, new char[] {'x', 'y'});
        check("abcxy".equals(sb.toString()), "insert at end failed: " + sb);
    }

    static void testInsertEmptyArray() {
        StringBuilder sb = new StringBuilder("abc");
        sb.insert(1, new char[0]);
        check("abc".equals(sb.toString()), "insert of empty array changed content: " + sb);
    }

    static void testReturnsSameInstance() {
        StringBuilder sb = new StringBuilder("abc");
        StringBuilder result = sb.insert(0, new char[] {'z'});
        check(result == sb, "insert(int, char[]) must return this");
    }

    static void testNegativeOffsetThrows() {
        expectStringIndexOutOfBounds(-1, new char[] {'x'});
    }

    static void testOffsetBeyondLengthThrows() {
        expectStringIndexOutOfBounds(4, new char[] {'x'});
    }

    static void testNullArrayThrowsNPE() {
        try {
            new StringBuilder("abc").insert(0, (char[]) null);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null char[]");
    }

    static void expectStringIndexOutOfBounds(int offset, char[] str) {
        try {
            new StringBuilder("abc").insert(offset, str);
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
