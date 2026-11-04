/*
 * @test
 * @summary Test URISyntaxException(String input, String reason) constructor
 * @run main URISyntaxExceptionWitupTest
 */

public class URISyntaxExceptionWitupTest {
    public static void main(String[] args) {
        testValidConstructor();
        testNullInput();
        testNullReason();
    }

    private static void testValidConstructor() {
        String input = "http://example.com";
        String reason = "Invalid URI";
        java.net.URISyntaxException ex = new java.net.URISyntaxException(input, reason);
        if (!ex.getInput().equals(input)) {
            throw new RuntimeException("getInput() returned unexpected value");
        }
        if (!ex.getReason().equals(reason)) {
            throw new RuntimeException("getReason() returned unexpected value");
        }
        if (ex.getIndex() != -1) {
            throw new RuntimeException("getIndex() should be -1 for this constructor");
        }
    }

    private static void testNullInput() {
        try {
            new java.net.URISyntaxException(null, "reason");
            throw new RuntimeException("Expected NullPointerException for null input");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    private static void testNullReason() {
        try {
            new java.net.URISyntaxException("input", null);
            throw new RuntimeException("Expected NullPointerException for null reason");
        } catch (NullPointerException npe) {
            // expected
        }
    }
}
