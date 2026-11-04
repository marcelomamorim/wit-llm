/*
 * @test
 * @summary Test StringBuffer.insert(int, boolean) method
 * @run main StringBufferWitupTest
 */

public class StringBufferWitupTest {
    public static void main(String[] args) {
        testInsertBooleanAtStart();
        testInsertBooleanAtEnd();
        testInsertBooleanInMiddle();
        testInsertBooleanWithNegativeOffset();
        testInsertBooleanWithOffsetGreaterThanLength();
    }

    private static void testInsertBooleanAtStart() {
        StringBuffer sb = new StringBuffer("abc");
        sb.insert(0, true);
        String result = sb.toString();
        if (!result.equals("trueabc")) {
            throw new AssertionError("Expected 'trueabc' but got '" + result + "'");
        }
    }

    private static void testInsertBooleanAtEnd() {
        StringBuffer sb = new StringBuffer("abc");
        sb.insert(sb.length(), false);
        String result = sb.toString();
        if (!result.equals("abcfalse")) {
            throw new AssertionError("Expected 'abcfalse' but got '" + result + "'");
        }
    }

    private static void testInsertBooleanInMiddle() {
        StringBuffer sb = new StringBuffer("abc");
        sb.insert(1, true);
        String result = sb.toString();
        if (!result.equals("atruebc")) {
            throw new AssertionError("Expected 'atruebc' but got '" + result + "'");
        }
    }

    private static void testInsertBooleanWithNegativeOffset() {
        StringBuffer sb = new StringBuffer("abc");
        try {
            sb.insert(-1, true);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for negative offset");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testInsertBooleanWithOffsetGreaterThanLength() {
        StringBuffer sb = new StringBuffer("abc");
        try {
            sb.insert(sb.length() + 1, false);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for offset > length");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }
}
