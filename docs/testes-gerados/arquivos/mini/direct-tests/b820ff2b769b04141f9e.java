/*
 * @test
 * @summary Test StringUTF16.checkOffset method behavior
 * @run main StringUTF16WitupTest
 */

public class StringUTF16WitupTest {
    public static void main(String[] args) {
        testValidOffset();
        testNegativeOffset();
        testOffsetEqualLength();
        testOffsetGreaterThanLength();
    }

    private static void testValidOffset() {
        byte[] val = new byte[4]; // length() = 2
        // offset 0 and 1 are valid
        StringUTF16.checkOffset(0, val);
        StringUTF16.checkOffset(1, val);
    }

    private static void testNegativeOffset() {
        byte[] val = new byte[4];
        try {
            StringUTF16.checkOffset(-1, val);
            throw new Error("Expected StringIndexOutOfBoundsException for negative offset");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testOffsetEqualLength() {
        byte[] val = new byte[4]; // length() = 2
        try {
            StringUTF16.checkOffset(2, val);
            throw new Error("Expected StringIndexOutOfBoundsException for offset == length");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testOffsetGreaterThanLength() {
        byte[] val = new byte[4]; // length() = 2
        try {
            StringUTF16.checkOffset(3, val);
            throw new Error("Expected StringIndexOutOfBoundsException for offset > length");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }
}

final class StringUTF16 {
    public static int length(byte[] value) {
        return value.length >> 1;
    }

    public static void checkOffset(int off, byte[] val) {
        String.checkOffset(off, length(val));
    }
}

final class String {
    public static void checkOffset(int off, int length) {
        if (off < 0 || off >= length) {
            throw new StringIndexOutOfBoundsException(off);
        }
    }
}
