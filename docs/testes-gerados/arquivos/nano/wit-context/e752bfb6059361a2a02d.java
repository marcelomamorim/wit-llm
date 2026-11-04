/* @test    @summary Test for StringUTF16.inflate to verify behavior with valid and invalid inputs    @run main StringUTF16WitupTest */

import java.lang.StringUTF16;

public class StringUTF16WitupTest {

    public static void main(String[] args) {
        testValidInflate();
        testInflateWithDstTooSmall();
        testInflateWithNegativeLength();
        testInflateWithNegativeDstOff();
    }

    private static void testValidInflate() {
        byte[] src = {0, 65, 0, 66, 0, 67}; // represents 'ABC'
        byte[] dst = new byte[6];
        try {
            StringUTF16.inflate(src, 0, dst, 0, 3);
            boolean success = (dst[0] == 0 && dst[1] == 65 && dst[2] == 0 && dst[3] == 66 && dst[4] == 0 && dst[5] == 67);
            if (!success) {
                throw new AssertionError("Valid inflate did not produce expected output");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in valid inflate", e);
        }
    }

    private static void testInflateWithDstTooSmall() {
        byte[] src = {0, 65, 0, 66, 0, 67};
        byte[] dst = new byte[4]; // too small for 3 chars
        try {
            StringUTF16.inflate(src, 0, dst, 0, 3);
            throw new AssertionError("Expected StringIndexOutOfBoundsException not thrown");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type", e);
        }
    }

    private static void testInflateWithNegativeLength() {
        byte[] src = {0, 65};
        byte[] dst = new byte[2];
        try {
            StringUTF16.inflate(src, 0, dst, 0, -1);
            throw new AssertionError("Expected NegativeArraySizeException or similar not thrown");
        } catch (NegativeArraySizeException e) {
            // expected
        } catch (ArrayIndexOutOfBoundsException e) {
            // acceptable as per bounds check
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type", e);
        }
    }

    private static void testInflateWithNegativeDstOff() {
        byte[] src = {0, 65};
        byte[] dst = new byte[2];
        try {
            StringUTF16.inflate(src, 0, dst, -1, 1);
            throw new AssertionError("Expected StringIndexOutOfBoundsException not thrown");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        } catch (ArrayIndexOutOfBoundsException e) {
            // acceptable
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type", e);
        }
    }
}
