/* @test    @summary Test String(byte[], int, int) constructor for correct decoding and bounds checking    @run main StringWitupTest */

import java.lang.String;

public class StringWitupTest {

    public static void main(String[] args) {
        testValidBytes();
        testEmptyBytes();
        testBoundsCheck();
    }

    static void testValidBytes() {
        byte[] bytes = {65, 66, 67, 68}; // ASCII for 'ABCD'
        String s = new String(bytes, 0, 4);
        if (!"ABCD".equals(s)) {
            throw new AssertionError("Expected 'ABCD', but got: " + s);
        }
    }

    static void testEmptyBytes() {
        byte[] bytes = {};
        String s = new String(bytes, 0, 0);
        if (!"".equals(s)) {
            throw new AssertionError("Expected empty string, but got: " + s);
        }
    }

    static void testBoundsCheck() {
        byte[] bytes = {1, 2, 3};
        try {
            new String(bytes, 2, 2);
            throw new AssertionError("Expected IndexOutOfBoundsException for offset + length > bytes.length");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
        try {
            new String(bytes, -1, 2);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative offset");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
        try {
            new String(bytes, 1, -1);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative length");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }
}
