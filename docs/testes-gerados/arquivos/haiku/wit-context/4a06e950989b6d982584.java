/*
 * @test
 * @summary Verifies the two-argument InvalidPathException constructor delegates with index -1 and rejects null input or reason
 * @run main InvalidPathExceptionWitupTest
 */
public class InvalidPathExceptionWitupTest {
    public static void main(String[] args) {
        InvalidPathException e = new InvalidPathException("a/b", "bad");
        check(e.getIndex() == -1, "index should be -1");
        check("a/b".equals(e.getInput()), "input should be preserved");
        check("bad".equals(e.getReason()), "reason should be preserved");
        check("bad".equals(e.getMessage()), "message should equal reason");

        expectNpe(null, "reason");
        expectNpe("x", null);
        expectNpe(null, null);
    }

    static void expectNpe(String input, String reason) {
        try {
            new InvalidPathException(input, reason);
        } catch (NullPointerException npe) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for input=" + input + " reason=" + reason);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
