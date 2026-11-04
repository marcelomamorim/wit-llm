/*
 * @test
 * @summary Test StringBuffer.offsetByCodePoints for valid and invalid indices
 * @run main StringBufferWitupTest
 */

public class StringBufferWitupTest {
    public static void main(String[] args) {
        testValidOffsetByCodePoints();
        testInvalidOffsetByCodePoints();
    }

    private static void testValidOffsetByCodePoints() {
        StringBuffer sb = new StringBuffer("abcde");
        // offsetByCodePoints should return a valid index within bounds
        int result = sb.offsetByCodePoints(1, 2); // from index 1, offset by 2 code points
        if (result != 3) {
            throw new Error("Expected offsetByCodePoints(1,2) to return 3 but got " + result);
        }
        // offset 0 from index 0 should return 0
        if (sb.offsetByCodePoints(0, 0) != 0) {
            throw new Error("Expected offsetByCodePoints(0,0) to return 0");
        }
        // offset negative codePointOffset moving backwards
        int backward = sb.offsetByCodePoints(3, -2);
        if (backward != 1) {
            throw new Error("Expected offsetByCodePoints(3,-2) to return 1 but got " + backward);
        }
    }

    private static void testInvalidOffsetByCodePoints() {
        StringBuffer sb = new StringBuffer("abc");
        // According to WIT, index < 0 or index > count throws IndexOutOfBoundsException
        // count is length() here
        int length = sb.length();

        // Test index < 0
        try {
            sb.offsetByCodePoints(-1, 0);
            throw new Error("Expected IndexOutOfBoundsException for index < 0");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }

        // Test index > length
        try {
            sb.offsetByCodePoints(length + 1, 0);
            throw new Error("Expected IndexOutOfBoundsException for index > length");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }

        // Test offset that would go out of bounds
        try {
            sb.offsetByCodePoints(1, 10); // offset too large
            throw new Error("Expected IndexOutOfBoundsException for offset out of bounds");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }

        try {
            sb.offsetByCodePoints(1, -10); // offset too negative
            throw new Error("Expected IndexOutOfBoundsException for offset out of bounds");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }
    }
}
