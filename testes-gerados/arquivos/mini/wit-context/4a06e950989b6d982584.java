/*
 * @test
 * @summary Test InvalidPathException(String input, String reason) constructor for null inputs
 * @run main InvalidPathExceptionWitupTest
 */

public class InvalidPathExceptionWitupTest {
    public static void main(String[] args) {
        testNullInputThrows();
        testNullReasonThrows();
        testValidInputReason();
    }

    private static void testNullInputThrows() {
        try {
            new java.nio.file.InvalidPathException(null, "reason");
            throw new RuntimeException("Expected NullPointerException for null input");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testNullReasonThrows() {
        try {
            new java.nio.file.InvalidPathException("input", null);
            throw new RuntimeException("Expected NullPointerException for null reason");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testValidInputReason() {
        java.nio.file.InvalidPathException ex = new java.nio.file.InvalidPathException("input", "reason");
        if (!"input".equals(ex.getInput())) {
            throw new RuntimeException("getInput() returned unexpected value");
        }
        if (!"reason".equals(ex.getReason())) {
            throw new RuntimeException("getReason() returned unexpected value");
        }
        if (ex.getIndex() != -1) {
            throw new RuntimeException("getIndex() should be -1 for this constructor");
        }
    }
}
