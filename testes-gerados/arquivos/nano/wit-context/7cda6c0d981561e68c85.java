/* @test    @summary Test for StringBuffer.offsetByCodePoints with valid and invalid inputs    @run main StringBufferWitupTest */

import java.lang.StringBuffer;

public class StringBufferWitupTest {

    public static void main(String[] args) {
        testOffsetByCodePointsValid();
        testOffsetByCodePointsOutOfBounds();
    }

    private static void testOffsetByCodePointsValid() {
        StringBuffer sb = new StringBuffer("Hello World");
        int index = 6;
        int codePointOffset = 2;
        int result = sb.offsetByCodePoints(index, codePointOffset);
        System.out.println("Valid offset: " + result);
        // Expect a valid integer result within bounds
    }

    private static void testOffsetByCodePointsOutOfBounds() {
        StringBuffer sb = new StringBuffer("Hello");
        int index = -1; // invalid, should throw
        int codePointOffset = 1;
        try {
            sb.offsetByCodePoints(index, codePointOffset);
            System.out.println("Error: Expected IndexOutOfBoundsException for index = -1");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Caught expected IndexOutOfBoundsException for index = -1");
        }

        index = 10; // invalid, beyond length
        try {
            sb.offsetByCodePoints(index, codePointOffset);
            System.out.println("Error: Expected IndexOutOfBoundsException for index = 10");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Caught expected IndexOutOfBoundsException for index = 10");
        }
    }
}
