/* @test    @summary Test for StringUTF16.newBytesFor(int) method to verify exception paths and behavior.    @run main StringUTF16WitupTest */

import java.lang.NegativeArraySizeException;
import java.lang.OutOfMemoryError;
import java.lang.StringUTF16;

public class StringUTF16WitupTest {

    public static void main(String[] args) {
        testNegativeLen();
        testExceedMaxLength();
        testNormalLen();
    }

    private static void testNegativeLen() {
        try {
            StringUTF16.newBytesFor(-1);
            System.err.println("Failed: Expected NegativeArraySizeException for negative length");
        } catch (NegativeArraySizeException e) {
            System.out.println("Passed: NegativeArraySizeException thrown as expected for negative length");
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception for negative length: " + t);
        }
    }

    private static void testExceedMaxLength() {
        int largeLen = Integer.MAX_VALUE;
        try {
            StringUTF16.newBytesFor(largeLen);
            System.err.println("Failed: Expected OutOfMemoryError for large length");
        } catch (OutOfMemoryError e) {
            System.out.println("Passed: OutOfMemoryError thrown as expected for large length");
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception for large length: " + t);
        }
    }

    private static void testNormalLen() {
        int len = 10;
        try {
            byte[] result = StringUTF16.newBytesFor(len);
            if (result != null && result.length == (len << 1)) {
                System.out.println("Passed: Normal length test succeeded");
            } else {
                System.err.println("Failed: Result array length mismatch or null");
            }
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception for normal length: " + t);
        }
    }
}
