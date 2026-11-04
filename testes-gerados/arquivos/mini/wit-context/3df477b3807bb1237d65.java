/*
 * @test
 * @summary Test putCharsSB method of StringUTF16 for bounds checking and correct char copying
 * @run main StringUTF16WitupTest
 */

public class StringUTF16WitupTest {

    public static void main(String[] args) {
        testPutCharsSBValid();
        testPutCharsSBIndexNegative();
        testPutCharsSBEndLessThanOff();
        testPutCharsSBInsufficientArrayLength();
        System.out.println("All tests passed.");
    }

    private static void testPutCharsSBValid() {
        // Prepare a byte array large enough to hold chars
        byte[] val = new byte[20];
        String s = "HelloWorld";
        int off = 0;
        int end = s.length();
        int index = 0;

        // Call putCharsSB with valid parameters
        java.lang.StringUTF16.putCharsSB(val, index, s, off, end);

        // Verify that the chars were copied correctly
        for (int i = 0; i < s.length(); i++) {
            char expected = s.charAt(i);
            char actual = java.lang.StringUTF16.getChar(val, i);
            if (expected != actual) {
                throw new AssertionError("Char mismatch at position " + i + ": expected " +
                                         (int)expected + ", got " + (int)actual);
            }
        }
    }

    private static void testPutCharsSBIndexNegative() {
        byte[] val = new byte[10];
        String s = "abc";
        int off = 0;
        int end = s.length();
        int index = -1;

        try {
            java.lang.StringUTF16.putCharsSB(val, index, s, off, end);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for negative index");
        } catch (StringIndexOutOfBoundsException e) {
            // Expected
        }
    }

    private static void testPutCharsSBEndLessThanOff() {
        byte[] val = new byte[10];
        String s = "abc";
        int off = 2;
        int end = 1; // end < off
        int index = 0;

        try {
            java.lang.StringUTF16.putCharsSB(val, index, s, off, end);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for end < off");
        } catch (StringIndexOutOfBoundsException e) {
            // Expected
        }
    }

    private static void testPutCharsSBInsufficientArrayLength() {
        byte[] val = new byte[4]; // can hold only 2 chars
        String s = "abc"; // 3 chars
        int off = 0;
        int end = s.length();
        int index = 0;

        try {
            java.lang.StringUTF16.putCharsSB(val, index, s, off, end);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for insufficient array length");
        } catch (StringIndexOutOfBoundsException e) {
            // Expected
        }
    }
}
