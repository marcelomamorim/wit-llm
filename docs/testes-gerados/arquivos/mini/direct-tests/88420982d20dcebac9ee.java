/*
 * @test
 * @summary Test StringBuffer.insert(int, float) method behavior
 * @run main StringBufferWitupTest
 */

public class StringBufferWitupTest {
    public static void main(String[] args) {
        testInsertFloatAtStart();
        testInsertFloatAtMiddle();
        testInsertFloatAtEnd();
        testInsertFloatWithNegativeOffset();
        testInsertFloatWithOffsetGreaterThanLength();
    }

    private static void testInsertFloatAtStart() {
        StringBuffer sb = new StringBuffer("abc");
        sb.insert(0, 1.5f);
        String result = sb.toString();
        if (!result.startsWith("1.5")) {
            throw new Error("testInsertFloatAtStart failed: expected prefix '1.5', got '" + result + "'");
        }
    }

    private static void testInsertFloatAtMiddle() {
        StringBuffer sb = new StringBuffer("abc");
        sb.insert(1, 2.5f);
        String result = sb.toString();
        if (!result.startsWith("a2.5")) {
            throw new Error("testInsertFloatAtMiddle failed: expected prefix 'a2.5', got '" + result + "'");
        }
        if (!result.endsWith("bc")) {
            throw new Error("testInsertFloatAtMiddle failed: expected suffix 'bc', got '" + result + "'");
        }
    }

    private static void testInsertFloatAtEnd() {
        StringBuffer sb = new StringBuffer("abc");
        sb.insert(sb.length(), 3.5f);
        String result = sb.toString();
        if (!result.endsWith("3.5")) {
            throw new Error("testInsertFloatAtEnd failed: expected suffix '3.5', got '" + result + "'");
        }
        if (!result.startsWith("abc")) {
            throw new Error("testInsertFloatAtEnd failed: expected prefix 'abc', got '" + result + "'");
        }
    }

    private static void testInsertFloatWithNegativeOffset() {
        StringBuffer sb = new StringBuffer("abc");
        try {
            sb.insert(-1, 1.0f);
            throw new Error("testInsertFloatWithNegativeOffset failed: expected StringIndexOutOfBoundsException");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testInsertFloatWithOffsetGreaterThanLength() {
        StringBuffer sb = new StringBuffer("abc");
        try {
            sb.insert(sb.length() + 1, 1.0f);
            throw new Error("testInsertFloatWithOffsetGreaterThanLength failed: expected StringIndexOutOfBoundsException");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }
}
