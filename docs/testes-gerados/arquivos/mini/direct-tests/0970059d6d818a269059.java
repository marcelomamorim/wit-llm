/*
 * @test
 * @summary Test StringUTF16.newBytesFor method for valid and invalid inputs
 * @run main StringUTF16WitupTest
 */

public class StringUTF16WitupTest {
    public static void main(String[] args) {
        testNewBytesForZeroLength();
        testNewBytesForPositiveLength();
        testNewBytesForNegativeLength();
        testNewBytesForExceedMaxLength();
    }

    private static void testNewBytesForZeroLength() {
        byte[] result = java.lang.StringUTF16.newBytesFor(0);
        if (result == null || result.length != 0) {
            throw new AssertionError("Expected byte array of length 0 for input 0");
        }
    }

    private static void testNewBytesForPositiveLength() {
        int len = 10;
        byte[] result = java.lang.StringUTF16.newBytesFor(len);
        if (result == null || result.length != (len << 1)) {
            throw new AssertionError("Expected byte array of length " + (len << 1) + " for input " + len);
        }
    }

    private static void testNewBytesForNegativeLength() {
        try {
            java.lang.StringUTF16.newBytesFor(-1);
            throw new AssertionError("Expected NegativeArraySizeException for negative length");
        } catch (NegativeArraySizeException e) {
            // expected
        }
    }

    private static void testNewBytesForExceedMaxLength() {
        // MAX_LENGTH is private, so we test with a large value that should exceed it
        // The exact MAX_LENGTH is not accessible, but we can test a large value to trigger OutOfMemoryError
        int largeLen = Integer.MAX_VALUE / 2 + 1; // large enough to exceed MAX_LENGTH
        try {
            java.lang.StringUTF16.newBytesFor(largeLen);
            throw new AssertionError("Expected OutOfMemoryError for length exceeding MAX_LENGTH");
        } catch (OutOfMemoryError e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("UTF16 String size is")) {
                throw new AssertionError("OutOfMemoryError message unexpected: " + msg);
            }
            // expected
        }
    }
}
