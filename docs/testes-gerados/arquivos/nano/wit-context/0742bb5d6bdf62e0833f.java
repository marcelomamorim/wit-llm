/* @test    @summary Test subSequence with invalid indices to trigger StringIndexOutOfBoundsException    @run main StringBufferWitupTest */

import java.lang.StringBuffer;

public class StringBufferWitupTest {

    public static void main(String[] args) {
        testSubSequenceInvalidIndices();
    }

    public static void testSubSequenceInvalidIndices() {
        StringBuffer sb = new StringBuffer("example");
        try {
            sb.subSequence(-1, 3);
            System.err.println("Failed: Expected StringIndexOutOfBoundsException for start = -1");
        } catch (StringIndexOutOfBoundsException e) {
            // Expected exception
        }
        try {
            sb.subSequence(2, 10);
            System.err.println("Failed: Expected StringIndexOutOfBoundsException for end = 10");
        } catch (StringIndexOutOfBoundsException e) {
            // Expected exception
        }
        try {
            sb.subSequence(4, 2);
            System.err.println("Failed: Expected StringIndexOutOfBoundsException for start > end");
        } catch (StringIndexOutOfBoundsException e) {
            // Expected exception
        }
        try {
            sb.subSequence(0, 7); // valid case, should not throw
        } catch (StringIndexOutOfBoundsException e) {
            System.err.println("Failed: Unexpected exception for valid indices");
        }
    }
}
