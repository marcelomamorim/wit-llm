/*
 * @test
 * @summary Test StringLatin1.inflate(byte[], int, int) for expected exceptions and normal behavior
 * @run main StringLatin1WitupTest
 */

public class StringLatin1WitupTest {
    public static void main(String[] args) {
        testInflateNormal();
        testInflateNegativeLen();
        testInflateLargeLen();
    }

    private static void testInflateNormal() {
        byte[] input = {1, 2, 3, 4, 5};
        int off = 0;
        int len = input.length;
        byte[] result = java.lang.StringLatin1.inflate(input, off, len);
        if (result == null) {
            throw new RuntimeException("inflate returned null for valid input");
        }
        if (result.length < len) {
            throw new RuntimeException("inflate returned array smaller than requested length");
        }
    }

    private static void testInflateNegativeLen() {
        byte[] input = {1, 2, 3};
        int off = 0;
        int len = -1;
        try {
            java.lang.StringLatin1.inflate(input, off, len);
            throw new RuntimeException("Expected NegativeArraySizeException for negative len but none thrown");
        } catch (NegativeArraySizeException e) {
            // Expected exception
        } catch (Throwable t) {
            throw new RuntimeException("Unexpected exception type for negative len", t);
        }
    }

    private static void testInflateLargeLen() {
        byte[] input = {1, 2, 3};
        int off = 0;
        int len = Integer.MAX_VALUE >>> 1; // large length to try to trigger OutOfMemoryError
        try {
            java.lang.StringLatin1.inflate(input, off, len);
        } catch (OutOfMemoryError e) {
            // Expected possible OutOfMemoryError for very large len
        } catch (Throwable t) {
            throw new RuntimeException("Unexpected exception type for large len", t);
        }
    }
}
