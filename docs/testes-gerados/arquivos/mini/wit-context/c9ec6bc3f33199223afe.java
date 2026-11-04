/*
 * @test
 * @summary Test String constructor String(byte[], int, int, int) for bounds and encoding
 * @run main StringWitupTest
 */

public class StringWitupTest {
    public static void main(String[] args) {
        testValidLatin1Construction();
        testValidUTF16Construction();
        testEmptyCount();
        testBoundsCheck();
    }

    private static void testValidLatin1Construction() {
        byte[] ascii = {65, 66, 67, 68, 69}; // ABCDE
        int hibyte = 0; // triggers LATIN1 branch
        int offset = 1;
        int count = 3;
        String s = new String(ascii, hibyte, offset, count);
        // Expect substring "BCD"
        if (!s.equals("BCD")) {
            throw new AssertionError("Expected 'BCD' but got '" + s + "'");
        }
    }

    private static void testValidUTF16Construction() {
        byte[] ascii = {0x01, 0x02, 0x03, 0x04};
        int hibyte = 1; // non-zero hibyte triggers UTF16 branch
        int offset = 0;
        int count = 4;
        String s = new String(ascii, hibyte, offset, count);
        // The string length should be count
        if (s.length() != count) {
            throw new AssertionError("Expected length " + count + " but got " + s.length());
        }
    }

    private static void testEmptyCount() {
        byte[] ascii = {1, 2, 3};
        int hibyte = 0;
        int offset = 0;
        int count = 0;
        String s = new String(ascii, hibyte, offset, count);
        if (!s.equals("")) {
            throw new AssertionError("Expected empty string but got '" + s + "'");
        }
    }

    private static void testBoundsCheck() {
        byte[] ascii = {1, 2, 3};
        int hibyte = 0;
        // offset + count > ascii.length
        try {
            new String(ascii, hibyte, 2, 2);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for offset+count > length");
        } catch (StringIndexOutOfBoundsException expected) {
        }
        // negative count
        try {
            new String(ascii, hibyte, 0, -1);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for negative count");
        } catch (StringIndexOutOfBoundsException expected) {
        }
        // negative offset
        try {
            new String(ascii, hibyte, -1, 1);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for negative offset");
        } catch (StringIndexOutOfBoundsException expected) {
        }
    }
}
