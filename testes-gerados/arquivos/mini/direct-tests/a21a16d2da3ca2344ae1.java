/*
 * @test
 * @summary Test codePointAtSB method of StringUTF16 for correctness and bounds checking
 * @run main StringUTF16WitupTest
 */

public class StringUTF16WitupTest {

    public static void main(String[] args) {
        testBasicBMP();
        testSurrogatePair();
        testIndexOutOfBounds();
    }

    private static void testBasicBMP() {
        // Prepare a byte array representing chars: 'A' (U+0041), 'B' (U+0042)
        byte[] val = new byte[4];
        // 'A' = 0x0041
        val[0] = 0x00;
        val[1] = 0x41;
        // 'B' = 0x0042
        val[2] = 0x00;
        val[3] = 0x42;

        int cp0 = java.lang.StringUTF16.codePointAtSB(val, 0, 2);
        if (cp0 != 0x0041) {
            throw new Error("Expected code point 0x0041 at index 0, got " + cp0);
        }

        int cp1 = java.lang.StringUTF16.codePointAtSB(val, 1, 2);
        if (cp1 != 0x0042) {
            throw new Error("Expected code point 0x0042 at index 1, got " + cp1);
        }
    }

    private static void testSurrogatePair() {
        // Prepare a byte array representing a surrogate pair for U+1D11E (MUSICAL SYMBOL G CLEF)
        // High surrogate: 0xD834, Low surrogate: 0xDD1E
        byte[] val = new byte[4];
        // High surrogate 0xD834
        val[0] = (byte)0xD8;
        val[1] = (byte)0x34;
        // Low surrogate 0xDD1E
        val[2] = (byte)0xDD;
        val[3] = (byte)0x1E;

        int cp = java.lang.StringUTF16.codePointAtSB(val, 0, 4);
        if (cp != 0x1D11E) {
            throw new Error("Expected code point 0x1D11E at index 0, got " + Integer.toHexString(cp));
        }
    }

    private static void testIndexOutOfBounds() {
        byte[] val = new byte[2];
        val[0] = 0x00;
        val[1] = 0x41; // 'A'

        boolean caught = false;
        try {
            // index == end, should fail assertion or throw
            java.lang.StringUTF16.codePointAtSB(val, 2, 2);
        } catch (AssertionError e) {
            caught = true;
        } catch (IndexOutOfBoundsException e) {
            caught = true;
        } catch (Exception e) {
            // Other exceptions not expected
        }
        if (!caught) {
            throw new Error("Expected exception for index >= end not thrown");
        }

        caught = false;
        try {
            // index < 0, should fail
            java.lang.StringUTF16.codePointAtSB(val, -1, 2);
        } catch (AssertionError e) {
            caught = true;
        } catch (IndexOutOfBoundsException e) {
            caught = true;
        } catch (Exception e) {
        }
        if (!caught) {
            throw new Error("Expected exception for negative index not thrown");
        }
    }
}
