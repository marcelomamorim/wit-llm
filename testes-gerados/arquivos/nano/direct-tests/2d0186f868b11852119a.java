/* @test    @summary Test String(byte[], int) constructor for correct behavior and edge cases    @run main StringWitupTest */

import java.lang.String;

public class StringWitupTest {

    public static void main(String[] args) {
        testValidAsciiAndHibyte();
        testEmptyAsciiArray();
        testNullAsciiArray();
        testHibyteBoundaryValues();
    }

    static void testValidAsciiAndHibyte() {
        byte[] ascii = {65, 66, 67}; // 'A', 'B', 'C'
        int hibyte = 0x7F; // 127
        String s = new String(ascii, hibyte);
        assert s.length() == ascii.length : "Length mismatch";
        for (int i = 0; i < ascii.length; i++) {
            assert s.charAt(i) == (char) (ascii[i] & 0xFF) : "Char mismatch at index " + i;
        }
    }

    static void testEmptyAsciiArray() {
        byte[] ascii = {};
        int hibyte = 0x00;
        String s = new String(ascii, hibyte);
        assert s.length() == 0 : "Expected empty string";
    }

    static void testNullAsciiArray() {
        try {
            new String((byte[])null, 0);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testHibyteBoundaryValues() {
        byte[] ascii = {0, -1, 127, -128};
        int[] hibytes = {0, 127, -128, 127};
        for (int hibyte : hibytes) {
            String s = new String(ascii, hibyte);
            assert s.length() == ascii.length : "Length mismatch for hibyte " + hibyte;
            for (int i = 0; i < ascii.length; i++) {
                assert s.charAt(i) == (char) (ascii[i] & 0xFF) : "Char mismatch at index " + i + " for hibyte " + hibyte;
            }
        }
    }
}
