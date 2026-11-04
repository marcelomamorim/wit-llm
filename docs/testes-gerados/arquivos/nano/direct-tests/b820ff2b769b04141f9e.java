/* @test    @summary Test StringUTF16.checkOffset with valid and invalid inputs    @run main StringUTF16WitupTest */

import java.lang.StringUTF16;

public class StringUTF16WitupTest {

    public static void main(String[] args) {
        testCheckOffsetValid();
        testCheckOffsetNegative();
        testCheckOffsetExceedsLength();
    }

    static void testCheckOffsetValid() {
        byte[] data = new byte[10];
        try {
            StringUTF16.checkOffset(0, data);
            StringUTF16.checkOffset(5, data);
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid offsets", e);
        }
    }

    static void testCheckOffsetNegative() {
        byte[] data = new byte[10];
        boolean threw = false;
        try {
            StringUTF16.checkOffset(-1, data);
        } catch (IndexOutOfBoundsException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected IndexOutOfBoundsException for negative offset");
        }
    }

    static void testCheckOffsetExceedsLength() {
        byte[] data = new byte[10];
        boolean threw = false;
        try {
            StringUTF16.checkOffset(11, data);
        } catch (IndexOutOfBoundsException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected IndexOutOfBoundsException for offset exceeding length");
        }
    }
}
