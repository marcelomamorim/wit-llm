/*
 * @test
 * @summary Test StringUTF16.checkBoundsBeginEnd method behavior
 * @run main StringUTF16WitupTest
 */

public class StringUTF16WitupTest {
    public static void main(String[] args) {
        testValidBounds();
        testInvalidBounds();
        testEmptyArrayBounds();
    }

    private static void testValidBounds() {
        byte[] val = new byte[4]; // length(val) = 4, length(val)>>1 = 2
        // Valid range: begin=0, end=2
        StringUTF16.checkBoundsBeginEnd(0, 2, val);
        StringUTF16.checkBoundsBeginEnd(1, 2, val);
        StringUTF16.checkBoundsBeginEnd(0, 1, val);
    }

    private static void testInvalidBounds() {
        byte[] val = new byte[4]; // length(val)>>1 = 2
        boolean caught;

        caught = false;
        try {
            StringUTF16.checkBoundsBeginEnd(-1, 1, val);
        } catch (IndexOutOfBoundsException e) {
            caught = true;
        }
        if (!caught) throw new AssertionError("Expected IndexOutOfBoundsException for begin < 0");

        caught = false;
        try {
            StringUTF16.checkBoundsBeginEnd(0, 3, val); // end > length(val)>>1
        } catch (IndexOutOfBoundsException e) {
            caught = true;
        }
        if (!caught) throw new AssertionError("Expected IndexOutOfBoundsException for end > length(val)>>1");

        caught = false;
        try {
            StringUTF16.checkBoundsBeginEnd(2, 1, val); // begin > end
        } catch (IndexOutOfBoundsException e) {
            caught = true;
        }
        if (!caught) throw new AssertionError("Expected IndexOutOfBoundsException for begin > end");
    }

    private static void testEmptyArrayBounds() {
        byte[] val = new byte[0]; // length(val)>>1 = 0
        boolean caught;

        caught = false;
        try {
            StringUTF16.checkBoundsBeginEnd(0, 0, val); // begin == end == 0 is valid
        } catch (IndexOutOfBoundsException e) {
            throw new AssertionError("Did not expect exception for empty array with begin==end==0");
        }

        caught = false;
        try {
            StringUTF16.checkBoundsBeginEnd(0, 1, val); // end > 0
        } catch (IndexOutOfBoundsException e) {
            caught = true;
        }
        if (!caught) throw new AssertionError("Expected IndexOutOfBoundsException for end > length(val)>>1 on empty array");
    }
}

final class StringUTF16 {
    public static void checkBoundsBeginEnd(int begin, int end, byte[] val) {
        String.checkBoundsBeginEnd(begin, end, length(val));
    }

    public static int length(byte[] value) {
        return value.length >> 1;
    }
}

final class String {
    public static void checkBoundsBeginEnd(int begin, int end, int length) {
        if (begin < 0 || end > length || begin > end) {
            throw new IndexOutOfBoundsException();
        }
    }
}
