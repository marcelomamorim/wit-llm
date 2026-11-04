/*
 * @test
 * @summary Basic observable behavior of StringBuffer.insert(int, String)
 * @run main StringBufferWitupTest
 */

public class StringBufferWitupTest {

    public static void main(String[] args) {
        testInsertInMiddleReturnsSameInstance();
        testInsertAtBeginning();
        testInsertAtEnd();
        testInsertNullStringAppendsNullText();
        testInsertAfterToStringRefreshesContent();
    }

    static void testInsertInMiddleReturnsSameInstance() {
        StringBuffer sb = new StringBuffer("start");
        StringBuffer ret = sb.insert(4, "le");
        check(ret == sb, "insert must return this");
        check("starlet".equals(sb.toString()), "expected 'starlet' but was '" + sb + "'");
    }

    static void testInsertAtBeginning() {
        StringBuffer sb = new StringBuffer("abc");
        sb.insert(0, "x");
        check("xabc".equals(sb.toString()), "expected 'xabc' but was '" + sb + "'");
    }

    static void testInsertAtEnd() {
        StringBuffer sb = new StringBuffer("abc");
        sb.insert(3, "d");
        check("abcd".equals(sb.toString()), "expected 'abcd' but was '" + sb + "'");
    }

    static void testInsertNullStringAppendsNullText() {
        StringBuffer sb = new StringBuffer("ab");
        sb.insert(1, (String) null);
        check("anullb".equals(sb.toString()), "expected 'anullb' but was '" + sb + "'");
    }

    static void testInsertAfterToStringRefreshesContent() {
        StringBuffer sb = new StringBuffer("ab");
        String before = sb.toString();
        check("ab".equals(before), "unexpected initial toString: " + before);
        sb.insert(0, "x");
        check("xab".equals(sb.toString()), "stale toString after insert: '" + sb + "'");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
