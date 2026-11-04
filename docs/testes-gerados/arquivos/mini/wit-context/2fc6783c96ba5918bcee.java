/*
 * @test
 * @summary Test String.getBytes() method for basic correctness and regression
 * @run main StringWitupTest
 */

public class StringWitupTest {
    public static void main(String[] args) {
        testGetBytesBasic();
    }

    private static void testGetBytesBasic() {
        // Basic test: getBytes() returns bytes representing the string in platform default charset
        String s = "abc";
        byte[] bytes = s.getBytes();
        // The bytes should correspond to the characters 'a', 'b', 'c' in the default charset
        // We check length and that bytes are not null
        if (bytes == null) {
            throw new AssertionError("getBytes() returned null");
        }
        if (bytes.length == 0) {
            throw new AssertionError("getBytes() returned empty array");
        }
        // Check that the bytes contain the ASCII codes for 'a', 'b', 'c' in order
        // Since default charset may vary, we check that decoding back yields the original string
        String decoded = new String(bytes);
        if (!s.equals(decoded)) {
            throw new AssertionError("Decoded bytes do not match original string: expected '" + s + "' but got '" + decoded + "'");
        }
    }
}
