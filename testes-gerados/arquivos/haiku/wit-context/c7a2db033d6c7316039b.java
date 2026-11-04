/*
 * @test
 * @summary Exercises the public URISyntaxException(String, String, int) constructor guards and observable accessors
 * @run main URISyntaxExceptionWitupTest
 */

public class URISyntaxExceptionWitupTest {

    public static void main(String[] args) throws Exception {
        testValidConstructionExposesValues();
        testNullInputThrowsNPE();
        testNullReasonThrowsNPE();
        testNullInputAndReasonThrowsNPE();
        testIndexBelowMinusOneThrowsIAE();
        testIndexMinusOneIsAccepted();
        testTwoArgConstructorUsesMinusOneIndex();
    }

    static void testValidConstructionExposesValues() {
        String input = "http://exa mple";
        String reason = "Illegal character in host";
        URISyntaxException e = new URISyntaxException(input, reason, 4);
        check(input.equals(e.getInput()), "getInput mismatch");
        check(reason.equals(e.getReason()), "getReason mismatch");
        check(e.getIndex() == 4, "getIndex mismatch: " + e.getIndex());
    }

    static void testNullInputThrowsNPE() {
        expectException(NullPointerException.class, () ->
                new URISyntaxException(null, "reason", 0));
    }

    static void testNullReasonThrowsNPE() {
        expectException(NullPointerException.class, () ->
                new URISyntaxException("input", null, 0));
    }

    static void testNullInputAndReasonThrowsNPE() {
        expectException(NullPointerException.class, () ->
                new URISyntaxException(null, null, 0));
    }

    static void testIndexBelowMinusOneThrowsIAE() {
        expectException(IllegalArgumentException.class, () ->
                new URISyntaxException("input", "reason", -2));
    }

    static void testIndexMinusOneIsAccepted() {
        URISyntaxException e = new URISyntaxException("input", "reason", -1);
        check(e.getIndex() == -1, "index -1 not preserved: " + e.getIndex());
    }

    static void testTwoArgConstructorUsesMinusOneIndex() {
        URISyntaxException e = new URISyntaxException("input", "reason");
        check(e.getIndex() == -1, "default index should be -1: " + e.getIndex());
        check("input".equals(e.getInput()), "getInput mismatch");
        check("reason".equals(e.getReason()), "getReason mismatch");
    }

    interface Action {
        void run() throws Exception;
    }

    static void expectException(Class<? extends Throwable> expected, Action action) {
        try {
            action.run();
        } catch (Throwable t) {
            if (expected.isInstance(t)) {
                return;
            }
            throw new RuntimeException("Unexpected exception: " + t, t);
        }
        throw new RuntimeException("Expected " + expected.getName() + " was not thrown");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
