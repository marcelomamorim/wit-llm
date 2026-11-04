/*
 * @test
 * @summary Test URISyntaxException(String input, String reason) constructor for null inputs
 * @run main URISyntaxExceptionWitupTest
 */

public class URISyntaxExceptionWitupTest {
    public static void main(String[] args) {
        testNullInputThrows();
        testNullReasonThrows();
        testValidInputs();
    }

    private static void testNullInputThrows() {
        try {
            new java.net.URISyntaxException(null, "reason");
            throw new RuntimeException("Expected NullPointerException for null input");
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void testNullReasonThrows() {
        try {
            new java.net.URISyntaxException("input", null);
            throw new RuntimeException("Expected NullPointerException for null reason");
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void testValidInputs() {
        java.net.URISyntaxException ex = new java.net.URISyntaxException("input", "reason");
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
