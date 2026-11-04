/* @test    @summary Test for InvalidPathException constructor with input and reason.    @run main InvalidPathExceptionWitupTest */

import java.nio.file.InvalidPathException;

public class InvalidPathExceptionWitupTest {

    public static void main(String[] args) {
        testConstructorWithInputAndReason();
        testConstructorWithNullInput();
        testConstructorWithNullReason();
        testConstructorWithEmptyStrings();
    }

    static void testConstructorWithInputAndReason() {
        String input = "invalid/path";
        String reason = "Invalid characters";
        InvalidPathException e = new InvalidPathException(input, reason);
        assert e.getInput().equals(input) : "Input mismatch";
        assert e.getReason().equals(reason) : "Reason mismatch";
        assert e.getIndex() == -1 : "Index should be -1";
    }

    static void testConstructorWithNullInput() {
        try {
            new InvalidPathException(null, "reason");
            assert false : "Expected NullPointerException";
        } catch (NullPointerException expected) {
        }
    }

    static void testConstructorWithNullReason() {
        try {
            new InvalidPathException("input", null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException expected) {
        }
    }

    static void testConstructorWithEmptyStrings() {
        String input = "";
        String reason = "";
        InvalidPathException e = new InvalidPathException(input, reason);
        assert e.getInput().equals(input) : "Empty input mismatch";
        assert e.getReason().equals(reason) : "Empty reason mismatch";
        assert e.getIndex() == -1 : "Index should be -1";
    }
}
