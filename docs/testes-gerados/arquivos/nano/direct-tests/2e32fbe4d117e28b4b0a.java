/*
 * @test
 * @summary Test StringUTF16.putCharSB for valid and invalid inputs
 * @run main StringUTF16WitupTest
 */

import java.lang.StringUTF16;

public class StringUTF16WitupTest {

    public static void main(String[] args) {
        testValidPutCharSB();
        testInvalidPutCharSB();
    }

    private static void testValidPutCharSB() {
        byte[] buffer = new byte[4];
        int index = 0;
        char c = 'A';
        StringUTF16.putCharSB(buffer, index, c);
        char result = StringUTF16.getChar(buffer, index);
        if (result != c) {
            throw new AssertionError("Expected: " + c + ", but got: " + result);
        }
    }

    private static void testInvalidPutCharSB() {
        byte[] buffer = new byte[2];
        int index = 2; // intentionally out of bounds
        char c = 'B';
        boolean threw = false;
        try {
            StringUTF16.putCharSB(buffer, index, c);
        } catch (IndexOutOfBoundsException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected IndexOutOfBoundsException for index: " + index);
        }
    }
}
