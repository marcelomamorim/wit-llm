/* @test    @summary Test StringUTF16.checkIndex for valid and invalid inputs    @run main StringUTF16WitupTest */

import java.lang.StringUTF16;

public class StringUTF16WitupTest {

    public static void main(String[] args) {
        testCheckIndexValid();
        testCheckIndexNegative();
        testCheckIndexExceedsLength();
    }

    static void testCheckIndexValid() {
        byte[] data = new byte[10];
        try {
            StringUTF16.checkIndex(0, data);
            StringUTF16.checkIndex(5, data);
            StringUTF16.checkIndex(9, data);
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid indices", e);
        }
    }

    static void testCheckIndexNegative() {
        byte[] data = new byte[10];
        boolean thrown = false;
        try {
            StringUTF16.checkIndex(-1, data);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected IndexOutOfBoundsException for negative index");
        }
    }

    static void testCheckIndexExceedsLength() {
        byte[] data = new byte[10];
        boolean thrown = false;
        try {
            StringUTF16.checkIndex(10, data);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected IndexOutOfBoundsException for index equal to length");
        }
    }
}
