/*
 * @test
 * @summary Test InvalidPathException(String input, String reason) constructor
 * @run main InvalidPathExceptionWitupTest
 */

public class InvalidPathExceptionWitupTest {
    public static void main(String[] args) {
        testValidConstructor();
        testNullInput();
        testNullReason();
    }

    private static void testValidConstructor() {
        String input = "somePath";
        String reason = "invalid character";
        java.nio.file.InvalidPathException ex = new java.nio.file.InvalidPathException(input, reason);
        if (!ex.getInput().equals(input)) {
            throw new AssertionError("getInput() returned unexpected value");
        }
        if (!ex.getReason().equals(reason)) {
            throw new AssertionError("getReason() returned unexpected value");
        }
        if (ex.getIndex() != -1) {
            throw new AssertionError("getIndex() should be -1 for this constructor");
        }
    }

    private static void testNullInput() {
        try {
            new java.nio.file.InvalidPathException(null, "reason");
            throw new AssertionError("Expected NullPointerException for null input");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    private static void testNullReason() {
        try {
            new java.nio.file.InvalidPathException("input", null);
            throw new AssertionError("Expected NullPointerException for null reason");
        } catch (NullPointerException npe) {
            // expected
        }
    }
}
