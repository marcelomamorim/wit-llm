/*
 * @test
 * @summary Test InvalidPathException(String input, String reason, int index) constructor behavior
 * @run main InvalidPathExceptionWitupTest
 */

public class InvalidPathExceptionWitupTest {
    public static void main(String[] args) {
        testValidConstruction();
        testNullInputThrowsNPE();
        testNullReasonThrowsNPE();
        testIndexLessThanMinusOneThrowsIAE();
    }

    private static void testValidConstruction() {
        java.nio.file.InvalidPathException ex = new java.nio.file.InvalidPathException("inputPath", "reason", 5);
        if (!"inputPath".equals(ex.getInput())) {
            throw new AssertionError("getInput() returned wrong value");
        }
        if (!"reason".equals(ex.getReason())) {
            throw new AssertionError("getReason() returned wrong value");
        }
        if (ex.getIndex() != 5) {
            throw new AssertionError("getIndex() returned wrong value");
        }
    }

    private static void testNullInputThrowsNPE() {
        try {
            new java.nio.file.InvalidPathException(null, "reason", 0);
            throw new AssertionError("Expected NullPointerException for null input");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    private static void testNullReasonThrowsNPE() {
        try {
            new java.nio.file.InvalidPathException("input", null, 0);
            throw new AssertionError("Expected NullPointerException for null reason");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    private static void testIndexLessThanMinusOneThrowsIAE() {
        try {
            new java.nio.file.InvalidPathException("input", "reason", -2);
            throw new AssertionError("Expected IllegalArgumentException for index < -1");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }
}
