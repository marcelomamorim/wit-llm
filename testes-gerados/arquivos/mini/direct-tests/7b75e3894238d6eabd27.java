/*
 * @test
 * @summary Test StringUTF16.checkIndex method behavior
 * @run main StringUTF16WitupTest
 */

public class StringUTF16WitupTest {
    public static void main(String[] args) {
        testValidIndex();
        testNegativeIndex();
        testIndexEqualLength();
        testIndexGreaterThanLength();
    }

    private static void testValidIndex() {
        byte[] val = new byte[4]; // length(val) = 2
        // Valid indices: 0 and 1
        try {
            java.lang.StringUTF16.checkIndex(0, val);
            java.lang.StringUTF16.checkIndex(1, val);
        } catch (Exception e) {
            throw new RuntimeException("checkIndex threw unexpectedly for valid indices", e);
        }
    }

    private static void testNegativeIndex() {
        byte[] val = new byte[4];
        try {
            java.lang.StringUTF16.checkIndex(-1, val);
            throw new RuntimeException("checkIndex did not throw for negative index");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }
    }

    private static void testIndexEqualLength() {
        byte[] val = new byte[4]; // length(val) = 2
        int len = java.lang.StringUTF16.length(val);
        try {
            java.lang.StringUTF16.checkIndex(len, val);
            throw new RuntimeException("checkIndex did not throw for index equal to length");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }
    }

    private static void testIndexGreaterThanLength() {
        byte[] val = new byte[4]; // length(val) = 2
        int len = java.lang.StringUTF16.length(val);
        try {
            java.lang.StringUTF16.checkIndex(len + 1, val);
            throw new RuntimeException("checkIndex did not throw for index greater than length");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }
    }
}
