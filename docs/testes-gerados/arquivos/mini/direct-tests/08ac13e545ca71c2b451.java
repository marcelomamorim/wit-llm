/*
 * @test
 * @summary Test String constructor String(byte[], int, int) for correct decoding and bounds checking
 * @run main StringWitupTest
 */

public class StringWitupTest {
    public static void main(String[] args) {
        testValidAsciiBytes();
        testValidUtf8Bytes();
        testOffsetLengthBounds();
    }

    private static void testValidAsciiBytes() {
        byte[] asciiBytes = {65, 66, 67, 68, 69}; // ABCDE
        String s = new String(asciiBytes, 1, 3); // BCD
        if (!s.equals("BCD")) {
            throw new Error("Expected 'BCD' but got '" + s + "'");
        }
    }

    private static void testValidUtf8Bytes() {
        // UTF-8 bytes for "éà"
        byte[] utf8Bytes = {(byte)0xC3, (byte)0xA9, (byte)0xC3, (byte)0xA0};
        String s = new String(utf8Bytes, 0, utf8Bytes.length);
        // The constructor decodes bytes using the platform default charset, which may not be UTF-8
        // So we check that the string length is 2 and chars are as expected
        if (s.length() != 2 || s.charAt(0) != '\u00E9' || s.charAt(1) != '\u00E0') {
            throw new Error("Expected string with chars '\u00E9\u00E0' but got '" + s + "'");
        }
    }

    private static void testOffsetLengthBounds() {
        byte[] bytes = {1, 2, 3, 4, 5};
        boolean npeThrown = false;
        boolean ioobeThrown = false;

        try {
            new String(null, 0, 1);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new Error("Expected NullPointerException for null bytes array");
        }

        try {
            new String(bytes, -1, 1);
        } catch (IndexOutOfBoundsException e) {
            ioobeThrown = true;
        }
        if (!ioobeThrown) {
            throw new Error("Expected IndexOutOfBoundsException for negative offset");
        }

        ioobeThrown = false;
        try {
            new String(bytes, 0, -1);
        } catch (IndexOutOfBoundsException e) {
            ioobeThrown = true;
        }
        if (!ioobeThrown) {
            throw new Error("Expected IndexOutOfBoundsException for negative length");
        }

        ioobeThrown = false;
        try {
            new String(bytes, 4, 2);
        } catch (IndexOutOfBoundsException e) {
            ioobeThrown = true;
        }
        if (!ioobeThrown) {
            throw new Error("Expected IndexOutOfBoundsException for offset+length > bytes.length");
        }
    }
}
