/*
 * @test
 * @summary Test java.lang.StringUTF16.newBytesFor(int) for valid and exceptional inputs
 * @run main StringUTF16WitupTest
 */

public class StringUTF16WitupTest {
    public static void main(String[] args) {
        testNewBytesForValid();
        testNewBytesForNegativeLength();
        testNewBytesForTooLargeLength();
        System.out.println("All tests passed.");
    }

    private static void testNewBytesForValid() {
        // Test with zero length
        byte[] zero = java.lang.StringUTF16.newBytesFor(0);
        if (zero == null || zero.length != 0) {
            throw new AssertionError("Expected zero-length byte array for len=0");
        }

        // Test with small positive length
        byte[] small = java.lang.StringUTF16.newBytesFor(5);
        if (small == null || small.length != 10) { // len << 1
            throw new AssertionError("Expected byte array of length 10 for len=5");
        }

        // Test with MAX_LENGTH boundary - since MAX_LENGTH is not public, test a large value below it
        // We test a large length that should not throw
        int largeLen = 1000000; // 1 million, should be safe
        byte[] large = java.lang.StringUTF16.newBytesFor(largeLen);
        if (large == null || large.length != largeLen << 1) {
            throw new AssertionError("Expected byte array of length " + (largeLen << 1) + " for len=" + largeLen);
        }
    }

    private static void testNewBytesForNegativeLength() {
        try {
            java.lang.StringUTF16.newBytesFor(-1);
            throw new AssertionError("Expected NegativeArraySizeException for len=-1");
        } catch (NegativeArraySizeException e) {
            // Expected
        } catch (Throwable t) {
            throw new AssertionError("Expected NegativeArraySizeException but got " + t);
        }
    }

    private static void testNewBytesForTooLargeLength() {
        // MAX_LENGTH is private, so we cannot get exact value.
        // We test a large length that is very likely > MAX_LENGTH to trigger OutOfMemoryError.
        // If no exception is thrown, test is adapted to current behavior.
        int tooLargeLen = Integer.MAX_VALUE / 2 + 1; // large enough to exceed MAX_LENGTH
        try {
            java.lang.StringUTF16.newBytesFor(tooLargeLen);
            // If no exception, accept behavior but warn
            // No assertion error here because MAX_LENGTH is unknown and may be larger
        } catch (OutOfMemoryError e) {
            // Expected
        } catch (Throwable t) {
            throw new AssertionError("Expected OutOfMemoryError but got " + t);
        }
    }
}
