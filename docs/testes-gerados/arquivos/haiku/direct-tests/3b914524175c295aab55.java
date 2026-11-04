/*
 * @test
 * @summary Verify StringBuffer.substring(int,int) returns correct substrings and rejects invalid ranges
 * @run main StringBufferWitupTest
 */

public class StringBufferWitupTest {

    public static void main(String[] args) {
        testBasicSubstring();
        testEmptyAndBoundarySubstrings();
        testSubstringAfterModification();
        testSubstringReturnsIndependentString();
        testInvalidRangesThrow();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testBasicSubstring() {
        StringBuffer sb = new StringBuffer("hello");
        check("el".equals(sb.substring(1, 3)), "substring(1,3) should be 'el'");
        check("hello".equals(sb.substring(0, 5)), "substring(0,5) should be 'hello'");
        check("h".equals(sb.substring(0, 1)), "substring(0,1) should be 'h'");
        check("o".equals(sb.substring(4, 5)), "substring(4,5) should be 'o'");
    }

    static void testEmptyAndBoundarySubstrings() {
        StringBuffer sb = new StringBuffer("hello");
        check("".equals(sb.substring(2, 2)), "substring(2,2) should be empty");
        check("".equals(sb.substring(0, 0)), "substring(0,0) should be empty");
        check("".equals(sb.substring(5, 5)), "substring(5,5) should be empty");
        StringBuffer empty = new StringBuffer();
        check("".equals(empty.substring(0, 0)), "empty buffer substring(0,0) should be empty");
    }

    static void testSubstringAfterModification() {
        StringBuffer sb = new StringBuffer("hello");
        sb.append("world");
        check("world".equals(sb.substring(5, 10)), "substring after append should be 'world'");
        check("helloworld".equals(sb.substring(0, 10)), "full substring after append mismatch");
        sb.insert(0, "X");
        check("Xhe".equals(sb.substring(0, 3)), "substring after insert mismatch");
    }

    static void testSubstringReturnsIndependentString() {
        StringBuffer sb = new StringBuffer("abcdef");
        String part = sb.substring(1, 4);
        check("bcd".equals(part), "substring(1,4) should be 'bcd'");
        sb.setCharAt(1, 'Z');
        check("bcd".equals(part), "returned substring must not reflect later buffer changes");
        check("Zcd".equals(sb.substring(1, 4)), "new substring should reflect buffer change");
    }

    static void testInvalidRangesThrow() {
        StringBuffer sb = new StringBuffer("hello");
        expectIndexException(sb, -1, 2, "negative start");
        expectIndexException(sb, 0, 6, "end greater than length");
        expectIndexException(sb, 3, 2, "start greater than end");
        expectIndexException(sb, 6, 6, "start greater than length");
    }

    static void expectIndexException(StringBuffer sb, int start, int end, String label) {
        try {
            String result = sb.substring(start, end);
            throw new RuntimeException("Expected IndexOutOfBoundsException for " + label + " but got '" + result + "'");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }
    }
}
