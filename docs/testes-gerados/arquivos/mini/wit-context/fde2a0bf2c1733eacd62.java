/*
 * @test
 * @summary Test StringUTF16.checkBoundsBeginEnd for bounds validation
 * @run main java.lang.StringUTF16WitupTest
 */

public class StringUTF16WitupTest {
    public static void main(String[] args) {
        testValidBounds();
        testBeginNegative();
        testBeginGreaterThanEnd();
        testEndGreaterThanLength();
    }

    private static void testValidBounds() {
        byte[] val = new byte[10 << 1]; // length = 10 chars
        // Should not throw
        java.lang.StringUTF16.checkBoundsBeginEnd(0, 10, val);
        java.lang.StringUTF16.checkBoundsBeginEnd(5, 5, val);
        java.lang.StringUTF16.checkBoundsBeginEnd(3, 7, val);
    }

    private static void testBeginNegative() {
        byte[] val = new byte[4 << 1]; // length = 4 chars
        try {
            java.lang.StringUTF16.checkBoundsBeginEnd(-1, 2, val);
            throw new Error("Expected StringIndexOutOfBoundsException for begin < 0");
        } catch (StringIndexOutOfBoundsException expected) {
            // expected
        }
    }

    private static void testBeginGreaterThanEnd() {
        byte[] val = new byte[6 << 1]; // length = 6 chars
        try {
            java.lang.StringUTF16.checkBoundsBeginEnd(5, 3, val);
            throw new Error("Expected StringIndexOutOfBoundsException for begin > end");
        } catch (StringIndexOutOfBoundsException expected) {
            // expected
        }
    }

    private static void testEndGreaterThanLength() {
        byte[] val = new byte[8 << 1]; // length = 8 chars
        try {
            java.lang.StringUTF16.checkBoundsBeginEnd(2, 9, val);
            throw new Error("Expected StringIndexOutOfBoundsException for end > length(val)");
        } catch (StringIndexOutOfBoundsException expected) {
            // expected
        }
    }
}
