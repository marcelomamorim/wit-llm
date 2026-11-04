/*
 * @test
 * @summary Test StringBuffer.replace(int, int, String) for valid and invalid ranges
 * @run main StringBufferWitupTest
 */

public class StringBufferWitupTest {
    public static void main(String[] args) {
        testReplaceValid();
        testReplaceInvalidStartNegative();
        testReplaceInvalidEndLessThanStart();
        testReplaceInvalidEndGreaterThanCount();
    }

    private static void testReplaceValid() {
        StringBuffer sb = new StringBuffer("HelloWorld");
        StringBuffer result = sb.replace(5, 7, "_X_");
        if (!"Hello_X_rld".equals(sb.toString())) {
            throw new Error("replace did not produce expected result: " + sb.toString());
        }
        if (result != sb) {
            throw new Error("replace should return this instance");
        }
    }

    private static void testReplaceInvalidStartNegative() {
        StringBuffer sb = new StringBuffer("Hello");
        try {
            sb.replace(-1, 3, "X");
            throw new Error("Expected StringIndexOutOfBoundsException for negative start");
        } catch (StringIndexOutOfBoundsException expected) {
            // expected
        }
    }

    private static void testReplaceInvalidEndLessThanStart() {
        StringBuffer sb = new StringBuffer("Hello");
        try {
            sb.replace(3, 2, "X");
            throw new Error("Expected StringIndexOutOfBoundsException for end < start");
        } catch (StringIndexOutOfBoundsException expected) {
            // expected
        }
    }

    private static void testReplaceInvalidEndGreaterThanCount() {
        StringBuffer sb = new StringBuffer("Hello");
        try {
            sb.replace(1, 10, "X");
            throw new Error("Expected StringIndexOutOfBoundsException for end > length");
        } catch (StringIndexOutOfBoundsException expected) {
            // expected
        }
    }
}
