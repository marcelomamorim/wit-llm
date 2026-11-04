/*
 * @test
 * @summary Witup-generated tests for URISyntaxException(String input, String reason, int index) constructor
 * @run main URISyntaxExceptionWitupTest
 */

public class URISyntaxExceptionWitupTest {

    public static void main(String[] args) throws Exception {
        testValidArgumentsAreStored();
        testMinusOneIndexIsAccepted();
        testZeroIndexIsAccepted();
        testNullInputThrowsNPE();
        testNullReasonThrowsNPE();
        testIndexBelowMinusOneThrowsIAE();
        testTwoArgConstructorUsesMinusOne();
        testMessageIsReason();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testValidArgumentsAreStored() {
        URISyntaxException e = new URISyntaxException("a b", "Illegal character", 1);
        check("a b".equals(e.getInput()), "input not stored");
        check("Illegal character".equals(e.getReason()), "reason not returned");
        check(e.getIndex() == 1, "index not stored");
    }

    static void testMinusOneIndexIsAccepted() {
        URISyntaxException e = new URISyntaxException("x", "bad", -1);
        check(e.getIndex() == -1, "index -1 not preserved");
    }

    static void testZeroIndexIsAccepted() {
        URISyntaxException e = new URISyntaxException("", "empty", 0);
        check("".equals(e.getInput()), "empty input not stored");
        check(e.getIndex() == 0, "index 0 not preserved");
    }

    static void testNullInputThrowsNPE() {
        try {
            new URISyntaxException(null, "reason", 0);
            throw new RuntimeException("expected NullPointerException for null input");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testNullReasonThrowsNPE() {
        try {
            new URISyntaxException("input", null, 0);
            throw new RuntimeException("expected NullPointerException for null reason");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testIndexBelowMinusOneThrowsIAE() {
        try {
            new URISyntaxException("input", "reason", -2);
            throw new RuntimeException("expected IllegalArgumentException for index -2");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    static void testTwoArgConstructorUsesMinusOne() {
        URISyntaxException e = new URISyntaxException("abc", "reason");
        check("abc".equals(e.getInput()), "input not stored in two-arg constructor");
        check("reason".equals(e.getReason()), "reason not stored in two-arg constructor");
        check(e.getIndex() == -1, "two-arg constructor must use index -1");
    }

    static void testMessageIsReason() {
        URISyntaxException e = new URISyntaxException("abc", "my reason", 2);
        check("my reason".equals(e.getMessage()), "getMessage() must return the reason");
    }
}
