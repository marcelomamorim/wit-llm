/*
 * @test
 * @summary Tests for InvalidPathException constructors and behaviors
 * @run main InvalidPathExceptionWitupTest
 */

import java.nio.file.InvalidPathException;

public class InvalidPathExceptionWitupTest {

    public static void main(String[] args) {
        testConstructorWithNullInput();
        testConstructorWithNullReason();
        testConstructorWithInvalidIndex();
        testConstructorValidInputs();
    }

    private static void testConstructorWithNullInput() {
        try {
            new InvalidPathException(null, "reason", 0);
            throw new AssertionError("Expected NullPointerException for null input");
        } catch (NullPointerException expected) {
            // Test passes
        }
    }

    private static void testConstructorWithNullReason() {
        try {
            new InvalidPathException("input", null, 0);
            throw new AssertionError("Expected NullPointerException for null reason");
        } catch (NullPointerException expected) {
            // Test passes
        }
    }

    private static void testConstructorWithInvalidIndex() {
        try {
            new InvalidPathException("input", "reason", -2);
            throw new AssertionError("Expected IllegalArgumentException for index < -1");
        } catch (IllegalArgumentException expected) {
            // Test passes
        }
    }

    private static void testConstructorValidInputs() {
        String input = "invalid:path";
        String reason = "Invalid characters";
        int index = 5;
        InvalidPathException e = new InvalidPathException(input, reason, index);
        assert e.getInput().equals(input) : "Input mismatch";
        assert e.getReason().equals(reason) : "Reason mismatch";
        assert e.getIndex() == index : "Index mismatch";
        assert e.getMessage().equals(reason) : "Message should be reason";
    }
}
