/*
 * @test
 * @summary Checks observable behavior of insert(int, char[]) through the public StringBuilder API
 * @run main AbstractStringBuilderWitupTest
 */

public class AbstractStringBuilderWitupTest {

    public static void main(String[] args) {
        testInsertMiddle();
        testInsertAtStart();
        testInsertAtEnd();
        testInsertEmptyArray();
        testReturnsSameBuilder();
        testInsertNonLatin1();
        testNegativeOffset();
        testOffsetBeyondLength();
        testNullArray();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testInsertMiddle() {
        StringBuilder sb = new StringBuilder("abc");
        sb.insert(1, new char[] {'x', 'y'});
        check(sb.toString().equals("axybc"), "expected axybc but was " + sb);
        check(sb.length() == 5, "unexpected length " + sb.length());
    }

    static void testInsertAtStart() {
        StringBuilder sb = new StringBuilder("abc");
        sb.insert(0, new char[] {'x'});
        check(sb.toString().equals("xabc"), "expected xabc but was " + sb);
    }

    static void testInsertAtEnd() {
        StringBuilder sb = new StringBuilder("abc");
        sb.insert(3, new char[] {'z'});
        check(sb.toString().equals("abcz"), "expected abcz but was " + sb);
    }

    static void testInsertEmptyArray() {
        StringBuilder sb = new StringBuilder("abc");
        sb.insert(1, new char[0]);
        check(sb.toString().equals("abc"), "expected abc but was " + sb);

        StringBuilder empty = new StringBuilder();
        empty.insert(0, new char[0]);
        check(empty.length() == 0, "expected empty builder");
    }

    static void testReturnsSameBuilder() {
        StringBuilder sb = new StringBuilder("ab");
        StringBuilder result = sb.insert(1, new char[] {'q'});
        check(result == sb, "insert must return this builder");
    }

    static void testInsertNonLatin1() {
        StringBuilder sb = new StringBuilder("ab");
        sb.insert(1, new char[] {'\u4e2d'});
        check(sb.toString().equals("a\u4e2db"), "unexpected content for non-Latin1 insert");
        check(sb.length() == 3, "unexpected length " + sb.length());
    }

    static void testNegativeOffset() {
        StringBuilder sb = new StringBuilder("abc");
        try {
            sb.insert(-1, new char[] {'x'});
            throw new RuntimeException("expected StringIndexOutOfBoundsException");
        } catch (StringIndexOutOfBoundsException expected) {
            // expected
        }
        check(sb.toString().equals("abc"), "builder must be unchanged");
    }

    static void testOffsetBeyondLength() {
        StringBuilder sb = new StringBuilder("abc");
        try {
            sb.insert(4, new char[] {'x'});
            throw new RuntimeException("expected StringIndexOutOfBoundsException");
        } catch (StringIndexOutOfBoundsException expected) {
            // expected
        }
        check(sb.toString().equals("abc"), "builder must be unchanged");
    }

    static void testNullArray() {
        StringBuilder sb = new StringBuilder("abc");
        try {
            sb.insert(1, (char[]) null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
