/*
 * @test
 * @summary Witup-generated behavioral tests for InvalidPathException constructors
 * @run main InvalidPathExceptionWitupTest
 */

import java.nio.file.InvalidPathException;

public class InvalidPathExceptionWitupTest {

    public static void main(String[] args) {
        testValidConstructionExposesValues();
        testNullInputThrowsNullPointerException();
        testNullReasonThrowsNullPointerException();
        testNullInputAndReasonThrowsNullPointerException();
        testIndexBelowMinusOneThrowsIllegalArgumentException();
        testIndexMinusOneIsAccepted();
        testTwoArgConstructorUsesMinusOneIndex();
    }

    static void testValidConstructionExposesValues() {
        InvalidPathException e = new InvalidPathException("a:b", "bad char", 1);
        check("a:b".equals(e.getInput()), "getInput mismatch");
        check("bad char".equals(e.getReason()), "getReason mismatch");
        check(e.getIndex() == 1, "getIndex mismatch");
        check(e.getMessage() != null && e.getMessage().contains("bad char"), "message should contain reason");
    }

    static void testNullInputThrowsNullPointerException() {
        expectNPE(() -> new InvalidPathException(null, "reason", 0));
    }

    static void testNullReasonThrowsNullPointerException() {
        expectNPE(() -> new InvalidPathException("input", null, 0));
    }

    static void testNullInputAndReasonThrowsNullPointerException() {
        expectNPE(() -> new InvalidPathException(null, null, -1));
    }

    static void testIndexBelowMinusOneThrowsIllegalArgumentException() {
        expectIAE(() -> new InvalidPathException("input", "reason", -2));
    }

    static void testIndexMinusOneIsAccepted() {
        InvalidPathException e = new InvalidPathException("input", "reason", -1);
        check(e.getIndex() == -1, "index -1 should be stored");
        check("input".equals(e.getInput()), "input mismatch for index -1");
    }

    static void testTwoArgConstructorUsesMinusOneIndex() {
        InvalidPathException e = new InvalidPathException("input", "reason");
        check(e.getIndex() == -1, "two-arg constructor should use index -1");
        check("input".equals(e.getInput()), "input mismatch in two-arg constructor");
        check("reason".equals(e.getReason()), "reason mismatch in two-arg constructor");
    }

    interface Action {
        void run();
    }

    static void expectNPE(Action a) {
        try {
            a.run();
        } catch (NullPointerException ex) {
            return;
        }
        throw new RuntimeException("expected NullPointerException");
    }

    static void expectIAE(Action a) {
        try {
            a.run();
        } catch (IllegalArgumentException ex) {
            return;
        }
        throw new RuntimeException("expected IllegalArgumentException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
