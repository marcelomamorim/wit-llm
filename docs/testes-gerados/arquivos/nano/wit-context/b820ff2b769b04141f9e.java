/* @test    @summary Test for StringUTF16.checkOffset to verify exception on invalid offset values    @run main StringUTF16WitupTest */

import java.lang.StringUTF16;

public class StringUTF16WitupTest {

    public static void main(String[] args) {
        testCheckOffsetWithInvalidNegativeOffset();
        testCheckOffsetWithOffsetGreaterThanLength();
        testCheckOffsetWithValidOffset();
    }

    private static void testCheckOffsetWithInvalidNegativeOffset() {
        byte[] data = new byte[10];
        try {
            StringUTF16.checkOffset(-1, data);
            System.out.println("FAIL: Expected StringIndexOutOfBoundsException for negative offset");
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("PASS: Caught expected exception for negative offset");
        }
    }

    private static void testCheckOffsetWithOffsetGreaterThanLength() {
        byte[] data = new byte[10];
        try {
            StringUTF16.checkOffset(11, data);
            System.out.println("FAIL: Expected StringIndexOutOfBoundsException for offset greater than length");
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("PASS: Caught expected exception for offset greater than length");
        }
    }

    private static void testCheckOffsetWithValidOffset() {
        byte[] data = new byte[10];
        try {
            StringUTF16.checkOffset(5, data);
            System.out.println("PASS: No exception for valid offset");
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("FAIL: Unexpected exception for valid offset");
        }
    }
}
