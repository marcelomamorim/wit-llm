/*
 * @test
 * @summary Tests the two-argument InvalidPathException constructor: stored input and reason, default index -1, and NullPointerException on null arguments
 * @run main InvalidPathExceptionWitupTest
 */

import java.nio.file.InvalidPathException;

public class InvalidPathExceptionWitupTest {

    public static void main(String[] args) {
        InvalidPathException e = new InvalidPathException("a|b", "bad char");
        check("a|b".equals(e.getInput()), "getInput should return the input string");
        check("bad char".equals(e.getReason()), "getReason should return the reason string");
        check("bad char".equals(e.getMessage()), "getMessage should return the reason string");
        check(e.getIndex() == -1, "index should be -1 when constructed with two arguments");
        check(e instanceof IllegalArgumentException, "should be an IllegalArgumentException");

        InvalidPathException empty = new InvalidPathException("", "");
        check("".equals(empty.getInput()), "empty input should be preserved");
        check("".equals(empty.getReason()), "empty reason should be preserved");
        check(empty.getIndex() == -1, "index should be -1 for empty strings");

        expectNullPointerException(null, "reason");
        expectNullPointerException("input", null);
        expectNullPointerException(null, null);
    }

    static void expectNullPointerException(String input, String reason) {
        try {
            new InvalidPathException(input, reason);
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("Expected NullPointerException for input=" + input + ", reason=" + reason);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Failed: " + message);
        }
    }
}
