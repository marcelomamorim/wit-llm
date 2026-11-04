/*
 * @test
 * @summary Test java.net.URISyntaxException constructor with input, reason and index
 * @run main URISyntaxExceptionWitupTest
 */

public class URISyntaxExceptionWitupTest {
    public static void main(String[] args) {
        testValidConstructor();
        testNullInput();
        testNullReason();
        testInvalidIndex();
    }

    private static void testValidConstructor() {
        String input = "http://example.com";
        String reason = "Invalid URI";
        int index = 5;
        java.net.URISyntaxException ex = new java.net.URISyntaxException(input, reason, index);
        if (!ex.getInput().equals(input)) {
            throw new RuntimeException("getInput() returned unexpected value");
        }
        if (!ex.getReason().equals(reason)) {
            throw new RuntimeException("getReason() returned unexpected value");
        }
        if (ex.getIndex() != index) {
            throw new RuntimeException("getIndex() returned unexpected value");
        }
    }

    private static void testNullInput() {
        try {
            new java.net.URISyntaxException(null, "reason", 0);
            throw new RuntimeException("Expected NullPointerException for null input");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    private static void testNullReason() {
        try {
            new java.net.URISyntaxException("input", null, 0);
            throw new RuntimeException("Expected NullPointerException for null reason");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    private static void testInvalidIndex() {
        try {
            new java.net.URISyntaxException("input", "reason", -2);
            throw new RuntimeException("Expected IllegalArgumentException for index < -1");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }
}
