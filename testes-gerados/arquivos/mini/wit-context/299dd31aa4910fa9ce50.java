/*
 * @test
 * @summary Test StringBuffer.codePointCount(int, int) for valid and invalid indices
 * @run main java.lang.StringBufferWitupTest
 */

public class StringBufferWitupTest {
    public static void main(String[] args) {
        testValidRange();
        testBeginIndexGreaterThanEndIndex();
        testBeginIndexNegative();
        testEndIndexGreaterThanLength();
    }

    private static void testValidRange() {
        StringBuffer sb = new StringBuffer("Hello, world!");
        int length = sb.length();
        // codePointCount should return a non-negative count within valid range
        int count = sb.codePointCount(0, length);
        if (count != length) {
            throw new Error("Expected codePointCount(0, length) to be " + length + " but got " + count);
        }
        // test a subrange
        count = sb.codePointCount(7, 12);
        if (count != 5) {
            throw new Error("Expected codePointCount(7, 12) to be 5 but got " + count);
        }
    }

    private static void testBeginIndexGreaterThanEndIndex() {
        StringBuffer sb = new StringBuffer("abc");
        int beginIndex = 2;
        int endIndex = 1;
        try {
            sb.codePointCount(beginIndex, endIndex);
            throw new Error("Expected IndexOutOfBoundsException for beginIndex > endIndex");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }
    }

    private static void testBeginIndexNegative() {
        StringBuffer sb = new StringBuffer("abc");
        int beginIndex = -1;
        int endIndex = 2;
        try {
            sb.codePointCount(beginIndex, endIndex);
            throw new Error("Expected IndexOutOfBoundsException for negative beginIndex");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }
    }

    private static void testEndIndexGreaterThanLength() {
        StringBuffer sb = new StringBuffer("abc");
        int beginIndex = 0;
        int endIndex = sb.length() + 1;
        try {
            sb.codePointCount(beginIndex, endIndex);
            throw new Error("Expected IndexOutOfBoundsException for endIndex > length");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }
    }
}
