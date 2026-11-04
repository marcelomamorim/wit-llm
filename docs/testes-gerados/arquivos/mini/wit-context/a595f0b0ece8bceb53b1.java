/*
 * @test
 * @summary Test InvalidPathException constructors for expected exceptions and state
 * @run main InvalidPathExceptionWitupTest
 */

public class InvalidPathExceptionWitupTest {
    public static void main(String[] args) {
        testNullInputThrowsNPE();
        testNullReasonThrowsNPE();
        testIndexLessThanMinusOneThrowsIAE();
        testValidConstructorSetsFields();
    }

    private static void testNullInputThrowsNPE() {
        try {
            new java.nio.file.InvalidPathException(null, "reason", 0);
            throw new RuntimeException("Expected NullPointerException for null input");
        } catch (NullPointerException expected) {
        }
    }

    private static void testNullReasonThrowsNPE() {
        try {
            new java.nio.file.InvalidPathException("input", null, 0);
            throw new RuntimeException("Expected NullPointerException for null reason");
        } catch (NullPointerException expected) {
        }
    }

    private static void testIndexLessThanMinusOneThrowsIAE() {
        try {
            new java.nio.file.InvalidPathException("input", "reason", -2);
            throw new RuntimeException("Expected IllegalArgumentException for index < -1");
        } catch (IllegalArgumentException expected) {
        }
    }

    private static void testValidConstructorSetsFields() {
        String input = "validInput";
        String reason = "validReason";
        int index = 5;
        java.nio.file.InvalidPathException ex = new java.nio.file.InvalidPathException(input, reason, index);
        if (!input.equals(ex.getInput())) {
            throw new RuntimeException("getInput() returned unexpected value");
        }
        if (!reason.equals(ex.getReason())) {
            throw new RuntimeException("getReason() returned unexpected value");
        }
        if (index != ex.getIndex()) {
            throw new RuntimeException("getIndex() returned unexpected value");
        }
    }
}
