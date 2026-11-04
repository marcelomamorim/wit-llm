/*
 * @test
 * @summary Test String constructor String(byte[], int, int) for bounds checking and decoding
 * @run main StringWitupTest
 */

public class StringWitupTest {
    public static void main(String[] args) {
        testValidConstruction();
        testOffsetLengthBounds();
    }

    private static void testValidConstruction() {
        byte[] bytes = {65, 66, 67, 68, 69}; // ASCII for ABCDE
        int offset = 1;
        int length = 3;
        String s = new String(bytes, offset, length);
        // Expect substring "BCD"
        if (!s.equals("BCD")) {
            throw new AssertionError("Expected 'BCD' but got '" + s + "'");
        }
    }

    private static void testOffsetLengthBounds() {
        byte[] bytes = {65, 66, 67};
        // Test offset negative
        try {
            new String(bytes, -1, 2);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for negative offset");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }

        // Test length negative
        try {
            new String(bytes, 0, -1);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for negative length");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }

        // Test offset + length > bytes.length
        try {
            new String(bytes, 2, 2);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for offset + length > bytes.length");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }

        // Test offset > bytes.length
        try {
            new String(bytes, 4, 0);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for offset > bytes.length");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }
}
