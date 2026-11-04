/*
 * @test
 * @summary Test StringBuffer.insert(int, double) method behavior
 * @run main StringBufferWitupTest
 */

public class StringBufferWitupTest {
    public static void main(String[] args) {
        testInsertDoubleAtZero();
        testInsertDoubleAtMiddle();
        testInsertDoubleAtEnd();
        testInsertDoubleNegativeOffset();
        testInsertDoubleOffsetBeyondLength();
    }

    private static void testInsertDoubleAtZero() {
        StringBuffer sb = new StringBuffer("abc");
        sb.insert(0, 1.23d);
        String result = sb.toString();
        if (!result.startsWith("1.23")) {
            throw new Error("Failed insert double at offset 0: " + result);
        }
    }

    private static void testInsertDoubleAtMiddle() {
        StringBuffer sb = new StringBuffer("abcdef");
        sb.insert(3, 4.56d);
        String result = sb.toString();
        if (!result.startsWith("abc4.56") || !result.endsWith("def")) {
            throw new Error("Failed insert double at middle offset: " + result);
        }
    }

    private static void testInsertDoubleAtEnd() {
        StringBuffer sb = new StringBuffer("xyz");
        sb.insert(sb.length(), 7.89d);
        String result = sb.toString();
        if (!result.equals("xyz7.89")) {
            throw new Error("Failed insert double at end offset: " + result);
        }
    }

    private static void testInsertDoubleNegativeOffset() {
        StringBuffer sb = new StringBuffer("test");
        try {
            sb.insert(-1, 0.1d);
            throw new Error("Expected StringIndexOutOfBoundsException for negative offset");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testInsertDoubleOffsetBeyondLength() {
        StringBuffer sb = new StringBuffer("test");
        try {
            sb.insert(sb.length() + 1, 0.1d);
            throw new Error("Expected StringIndexOutOfBoundsException for offset beyond length");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }
}
