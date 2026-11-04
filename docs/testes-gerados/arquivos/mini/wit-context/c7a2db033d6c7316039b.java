/*
 * @test
 * @summary Test URISyntaxException constructors for expected exceptions and state
 * @run main URISyntaxExceptionWitupTest
 */

public class URISyntaxExceptionWitupTest {
    public static void main(String[] args) {
        testNullInputThrows();
        testNullReasonThrows();
        testIndexLessThanMinusOneThrows();
        testValidConstructorSetsFields();
        testTwoArgConstructorDefaultsIndex();
        System.out.println("All tests passed.");
    }

    private static void testNullInputThrows() {
        try {
            new java.net.URISyntaxException(null, "reason", 0);
            throw new RuntimeException("Expected NullPointerException for null input");
        } catch (NullPointerException expected) {
        }
    }

    private static void testNullReasonThrows() {
        try {
            new java.net.URISyntaxException("input", null, 0);
            throw new RuntimeException("Expected NullPointerException for null reason");
        } catch (NullPointerException expected) {
        }
    }

    private static void testIndexLessThanMinusOneThrows() {
        try {
            new java.net.URISyntaxException("input", "reason", -2);
            throw new RuntimeException("Expected IllegalArgumentException for index < -1");
        } catch (IllegalArgumentException expected) {
        }
    }

    private static void testValidConstructorSetsFields() {
        String input = "inputString";
        String reason = "bad syntax";
        int index = 5;
        java.net.URISyntaxException ex = new java.net.URISyntaxException(input, reason, index);
        if (!ex.getInput().equals(input)) {
            throw new RuntimeException("getInput() did not return expected input");
        }
        if (!ex.getReason().equals(reason)) {
            throw new RuntimeException("getReason() did not return expected reason");
        }
        if (ex.getIndex() != index) {
            throw new RuntimeException("getIndex() did not return expected index");
        }
    }

    private static void testTwoArgConstructorDefaultsIndex() {
        String input = "input2";
        String reason = "reason2";
        java.net.URISyntaxException ex = new java.net.URISyntaxException(input, reason);
        if (!ex.getInput().equals(input)) {
            throw new RuntimeException("getInput() did not return expected input for two-arg constructor");
        }
        if (!ex.getReason().equals(reason)) {
            throw new RuntimeException("getReason() did not return expected reason for two-arg constructor");
        }
        if (ex.getIndex() != -1) {
            throw new RuntimeException("getIndex() did not default to -1 for two-arg constructor");
        }
    }
}
