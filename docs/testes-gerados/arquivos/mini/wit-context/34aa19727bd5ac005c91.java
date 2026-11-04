/*
 * @test
 * @summary Test StringBuffer.charAt(int) for valid and invalid indices
 * @run main StringBufferWitupTest
 */

public class StringBufferWitupTest {
    public static void main(String[] args) {
        testCharAtValidIndex();
        testCharAtInvalidIndex();
    }

    private static void testCharAtValidIndex() {
        StringBuffer sb = new StringBuffer("hello");
        // Test valid indices
        for (int i = 0; i < sb.length(); i++) {
            char c = sb.charAt(i);
            if (c != "hello".charAt(i)) {
                throw new AssertionError("charAt returned wrong character at index " + i);
            }
        }
    }

    private static void testCharAtInvalidIndex() {
        StringBuffer sb = new StringBuffer("test");
        // Test negative index returns StringIndexOutOfBoundsException
        try {
            sb.charAt(-1);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for index -1");
        } catch (StringIndexOutOfBoundsException expected) {
        }

        // Test index equal to length returns StringIndexOutOfBoundsException
        try {
            sb.charAt(sb.length());
            throw new AssertionError("Expected StringIndexOutOfBoundsException for index equal to length");
        } catch (StringIndexOutOfBoundsException expected) {
        }

        // Test index greater than length returns StringIndexOutOfBoundsException
        try {
            sb.charAt(sb.length() + 1);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for index greater than length");
        } catch (StringIndexOutOfBoundsException expected) {
        }
    }
}
