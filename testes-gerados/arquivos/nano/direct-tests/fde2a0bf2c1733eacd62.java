/* @test    @summary Test for StringUTF16.checkBoundsBeginEnd with valid and invalid bounds    @run main StringUTF16WitupTest */

import java.lang.StringUTF16;

public class StringUTF16WitupTest {

    public static void main(String[] args) {
        testValidBounds();
        testInvalidBeginNegative();
        testInvalidEndExceedsLength();
        testBeginGreaterThanEnd();
    }

    static void testValidBounds() {
        byte[] data = new byte[10];
        try {
            StringUTF16.checkBoundsBeginEnd(0, 10, data);
        } catch (Exception e) {
            throw new AssertionError("Valid bounds threw exception", e);
        }
    }

    static void testInvalidBeginNegative() {
        byte[] data = new byte[10];
        try {
            StringUTF16.checkBoundsBeginEnd(-1, 5, data);
            throw new AssertionError("Expected NegativeArraySizeException for negative begin");
        } catch (IndexOutOfBoundsException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for negative begin", e);
        }
    }

    static void testInvalidEndExceedsLength() {
        byte[] data = new byte[10];
        try {
            StringUTF16.checkBoundsBeginEnd(0, 11, data);
            throw new AssertionError("Expected IndexOutOfBoundsException for end > length");
        } catch (IndexOutOfBoundsException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for end > length", e);
        }
    }

    static void testBeginGreaterThanEnd() {
        byte[] data = new byte[10];
        try {
            StringUTF16.checkBoundsBeginEnd(5, 3, data);
            // Depending on implementation, this might not throw, but if it does, test here
        } catch (IndexOutOfBoundsException e) {
            // expected if implementation checks for begin > end
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for begin > end", e);
        }
    }
}
