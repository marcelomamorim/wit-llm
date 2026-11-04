/*
 * @test
 * @summary Test for StringUTF16.newBytesFor(int len)
 * @run main StringUTF16WitupTest
 */

import java.lang.NegativeArraySizeException;
import java.lang.OutOfMemoryError;
import java.util.Arrays;

public class StringUTF16WitupTest {

    public static void main(String[] args) {
        testNegativeLen();
        testExceedMaxLength();
        testValidLen();
    }

    static void testNegativeLen() {
        try {
            StringUTF16.newBytesFor(-1);
            throw new AssertionError("Expected NegativeArraySizeException for negative length");
        } catch (NegativeArraySizeException expected) {
            // expected
        }
    }

    static void testExceedMaxLength() {
        long maxLength = getMaxLength();
        long tooLarge = maxLength + 1;
        try {
            StringUTF16.newBytesFor((int) tooLarge);
            throw new AssertionError("Expected OutOfMemoryError for length exceeding MAX_LENGTH");
        } catch (OutOfMemoryError expected) {
            // expected
        } catch (Throwable t) {
            if (!(t instanceof OutOfMemoryError)) {
                throw new AssertionError("Unexpected exception type", t);
            }
        }
    }

    static void testValidLen() {
        int validLen = 10;
        byte[] result = StringUTF16.newBytesFor(validLen);
        if (result == null) {
            throw new AssertionError("Result should not be null");
        }
        if (result.length != (validLen < 0 ? 0 : validLen) << 1) {
            throw new AssertionError("Unexpected array length: " + result.length);
        }
    }

    static long getMaxLength() {
        // As MAX_LENGTH is not directly accessible, assume a safe large value for testing
        // or derive from the source code if accessible. Here, we assume a large value.
        return Integer.MAX_VALUE;
    }
}
