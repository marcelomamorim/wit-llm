/*
 * @test
 * @summary Tests for StringUTF16.putCharSB method, including boundary conditions.
 * @run main StringUTF16WitupTest
 */

import java.lang.StringUTF16;

public class StringUTF16WitupTest {

    public static void main(String[] args) {
        testPutCharSB_Normal();
        testPutCharSB_NegativeIndex();
        testPutCharSB_IndexTooLarge();
        testPutCharSB_ZeroIndex();
    }

    private static void testPutCharSB_Normal() {
        byte[] buffer = new byte[4];
        try {
            StringUTF16.putCharSB(buffer, 0, 0x0041); // 'A'
            StringUTF16.putCharSB(buffer, 2, 0x0062); // 'b'
            char c1 = StringUTF16.getChar(buffer, 0);
            char c2 = StringUTF16.getChar(buffer, 2);
            if (c1 != 'A' || c2 != 'b') {
                throw new AssertionError("Normal operation failed: " + c1 + ", " + c2);
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in normal test", e);
        }
    }

    private static void testPutCharSB_NegativeIndex() {
        byte[] buffer = new byte[4];
        try {
            StringUTF16.putCharSB(buffer, -1, 0x0041);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for negative index");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type", e);
        }
    }

    private static void testPutCharSB_IndexTooLarge() {
        byte[] buffer = new byte[4];
        try {
            StringUTF16.putCharSB(buffer, buffer.length, 0x0041);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for index equal to length");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type", e);
        }
    }

    private static void testPutCharSB_ZeroIndex() {
        byte[] buffer = new byte[4];
        try {
            StringUTF16.putCharSB(buffer, 0, 0x0041);
            char c = StringUTF16.getChar(buffer, 0);
            if (c != 0x0041) {
                throw new AssertionError("Failed to write/read at zero index: " + c);
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception at zero index", e);
        }
    }
}
