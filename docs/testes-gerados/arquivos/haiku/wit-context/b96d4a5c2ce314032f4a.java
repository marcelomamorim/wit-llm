/*
 * @test
 * @summary WITUP-generated checks for URISyntaxException(String, String) null guards and observable state
 * @run main URISyntaxExceptionWitupTest
 */

public class URISyntaxExceptionWitupTest {

    public static void main(String[] args) {
        testNullInputThrowsNPE();
        testNullReasonThrowsNPE();
        testBothNullThrowsNPE();
        testValidArgumentsPreserveState();
    }

    static void testNullInputThrowsNPE() {
        try {
            new URISyntaxException(null, "bad input");
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("Expected NullPointerException when input is null");
    }

    static void testNullReasonThrowsNPE() {
        try {
            new URISyntaxException("http://x", null);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("Expected NullPointerException when reason is null");
    }

    static void testBothNullThrowsNPE() {
        try {
            new URISyntaxException(null, null);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("Expected NullPointerException when input and reason are null");
    }

    static void testValidArgumentsPreserveState() {
        String input = "http://[bad";
        String reason = "Malformed IPv6 address";
        URISyntaxException e = new URISyntaxException(input, reason);
        if (!input.equals(e.getInput())) {
            throw new RuntimeException("getInput mismatch");
        }
        if (!reason.equals(e.getReason())) {
            throw new RuntimeException("getReason mismatch");
        }
        if (e.getIndex() != -1) {
            throw new RuntimeException("Expected index -1 but was " + e.getIndex());
        }
        String expected = reason + ": " + input;
        if (!expected.equals(e.getMessage())) {
            throw new RuntimeException("Unexpected message: " + e.getMessage());
        }
    }
}
