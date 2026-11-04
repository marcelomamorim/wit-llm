/*
 * @test
 * @summary Test String.getBytes() method for correctness and consistency
 * @run main StringWitupTest
 */

public class StringWitupTest {
    public static void main(String[] args) throws Exception {
        testEmptyString();
        testAsciiString();
        testUnicodeString();
        testConsistency();
    }

    private static void testEmptyString() {
        String s = "";
        byte[] bytes = s.getBytes();
        if (bytes == null) {
            throw new RuntimeException("getBytes() returned null for empty string");
        }
        if (bytes.length != 0) {
            throw new RuntimeException("getBytes() for empty string should return zero-length array");
        }
    }

    private static void testAsciiString() {
        String s = "Hello, World!";
        byte[] bytes = s.getBytes();
        if (bytes == null) {
            throw new RuntimeException("getBytes() returned null for ASCII string");
        }
        if (bytes.length != s.length()) {
            throw new RuntimeException("getBytes() length mismatch for ASCII string");
        }
        for (int i = 0; i < bytes.length; i++) {
            if ((byte) s.charAt(i) != bytes[i]) {
                throw new RuntimeException("getBytes() content mismatch at index " + i);
            }
        }
    }

    private static void testUnicodeString() {
        String s = "\u00E9\u20AC\uD83D\uDE00"; // é, €, 😀
        byte[] bytes = s.getBytes();
        if (bytes == null) {
            throw new RuntimeException("getBytes() returned null for Unicode string");
        }
        if (bytes.length == 0) {
            throw new RuntimeException("getBytes() returned empty array for non-empty Unicode string");
        }
    }

    private static void testConsistency() {
        String s = "ConsistencyTest123";
        byte[] bytes1 = s.getBytes();
        byte[] bytes2 = s.getBytes();
        if (bytes1.length != bytes2.length) {
            throw new RuntimeException("getBytes() returned arrays of different lengths on repeated calls");
        }
        for (int i = 0; i < bytes1.length; i++) {
            if (bytes1[i] != bytes2[i]) {
                throw new RuntimeException("getBytes() returned different content on repeated calls at index " + i);
            }
        }
    }
}
