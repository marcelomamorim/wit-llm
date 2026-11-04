/* @test    @summary Test for InvalidPathException constructors and behavior    @run main InvalidPathExceptionWitupTest */

import java.nio.file.InvalidPathException;

public class InvalidPathExceptionWitupTest {

    public static void main(String[] args) {
        testConstructorWithAllParams();
        testConstructorWithInputAndReason();
        testGetInput();
        testGetReason();
        testGetIndex();
        testInvalidInputNull();
        testInvalidReasonNull();
        testInvalidIndexLessThanMinusOne();
    }

    static void testConstructorWithAllParams() {
        String input = "invalid/path";
        String reason = "Invalid characters";
        int index = 5;
        InvalidPathException e = new InvalidPathException(input, reason, index);
        assert e.getInput().equals(input) : "Input mismatch";
        assert e.getReason().equals(reason) : "Reason mismatch";
        assert e.getIndex() == index : "Index mismatch";
    }

    static void testConstructorWithInputAndReason() {
        String input = "another/invalid";
        String reason = "Invalid format";
        InvalidPathException e = new InvalidPathException(input, reason);
        assert e.getInput().equals(input) : "Input mismatch";
        assert e.getReason().equals(reason) : "Reason mismatch";
        assert e.getIndex() == -1 : "Default index should be -1";
    }

    static void testGetInput() {
        String input = "test/input";
        String reason = "error";
        InvalidPathException e = new InvalidPathException(input, reason, 3);
        assert e.getInput().equals(input) : "getInput() should return the input string";
    }

    static void testGetReason() {
        String input = "test/input";
        String reason = "error reason";
        InvalidPathException e = new InvalidPathException(input, reason, 2);
        assert e.getReason().equals(reason) : "getReason() should return the reason string";
    }

    static void testGetIndex() {
        String input = "test/input";
        String reason = "error";
        int index = 7;
        InvalidPathException e = new InvalidPathException(input, reason, index);
        assert e.getIndex() == index : "getIndex() should return the correct index";
    }

    static void testInvalidInputNull() {
        String reason = "reason";
        try {
            new InvalidPathException(null, reason);
            assert false : "NullPointerException expected for null input";
        } catch (NullPointerException expected) {
        }
    }

    static void testInvalidReasonNull() {
        String input = "input";
        try {
            new InvalidPathException(input, null);
            assert false : "NullPointerException expected for null reason";
        } catch (NullPointerException expected) {
        }
    }

    static void testInvalidIndexLessThanMinusOne() {
        String input = "input";
        String reason = "reason";
        try {
            new InvalidPathException(input, reason, -2);
            assert false : "IllegalArgumentException expected for index < -1";
        } catch (IllegalArgumentException expected) {
        }
    }
}
