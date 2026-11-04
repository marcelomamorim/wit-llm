/* @test    @summary Test for StringUTF16.putCharsSB method to ensure correct character copying.    @run main StringUTF16WitupTest */

import java.lang.StringUTF16;

public class StringUTF16WitupTest {

    public static void main(String[] args) {
        testPutCharsSB_NormalCase();
        testPutCharsSB_EmptySequence();
        testPutCharsSB_SmallBuffer();
        testPutCharsSB_NullSequence();
    }

    static void testPutCharsSB_NormalCase() {
        byte[] buffer = new byte[20];
        String sequence = "abc";
        StringUTF16.putCharsSB(buffer, 0, sequence, 0, sequence.length());
        // Verify that buffer contains the UTF-16 encoding of "abc"
        int expectedLength = sequence.length() * 2;
        boolean match = true;
        for (int i = 0; i < expectedLength; i += 2) {
            char c = StringUTF16.getChar(buffer, i);
            if (c != sequence.charAt(i / 2)) {
                match = false;
                break;
            }
        }
        if (!match) {
            throw new AssertionError("Normal case failed: buffer contents do not match expected characters.");
        }
    }

    static void testPutCharsSB_EmptySequence() {
        byte[] buffer = new byte[10];
        String sequence = "";
        StringUTF16.putCharsSB(buffer, 0, sequence, 0, sequence.length());
        // Buffer should remain unchanged; verify first two bytes are zero
        if (buffer[0] != 0 || buffer[1] != 0) {
            throw new AssertionError("Empty sequence test failed: buffer was modified.");
        }
    }

    static void testPutCharsSB_SmallBuffer() {
        byte[] buffer = new byte[4];
        String sequence = "ab";
        StringUTF16.putCharsSB(buffer, 0, sequence, 0, sequence.length());
        // Verify buffer contains 'a' and 'b'
        if (StringUTF16.getChar(buffer, 0) != 'a' || StringUTF16.getChar(buffer, 2) != 'b') {
            throw new AssertionError("Small buffer test failed: characters not correctly written.");
        }
    }

    static void testPutCharsSB_NullSequence() {
        byte[] buffer = new byte[10];
        try {
            StringUTF16.putCharsSB(buffer, 0, null, 0, 0);
            throw new AssertionError("Null sequence did not throw NullPointerException.");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }
}
