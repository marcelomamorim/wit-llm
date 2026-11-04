/*
 * @test
 * @summary InvalidPathException constructor validation and accessor behavior
 * @run main InvalidPathExceptionWitupTest
 */

import java.nio.file.InvalidPathException;

public class InvalidPathExceptionWitupTest {

    public static void main(String[] args) throws Exception {
        InvalidPathException e = new InvalidPathException("a:b", "bad char", 1);
        check("a:b".equals(e.getInput()), "getInput mismatch");
        check("bad char".equals(e.getReason()), "getReason mismatch");
        check(e.getIndex() == 1, "getIndex mismatch");
        check(e instanceof IllegalArgumentException, "not an IllegalArgumentException");

        InvalidPathException e2 = new InvalidPathException("x", "r");
        check("x".equals(e2.getInput()), "2-arg getInput mismatch");
        check("r".equals(e2.getReason()), "2-arg getReason mismatch");
        check(e2.getIndex() == -1, "2-arg index should be -1");

        InvalidPathException e3 = new InvalidPathException("x", "r", -1);
        check(e3.getIndex() == -1, "explicit -1 index not kept");

        InvalidPathException e4 = new InvalidPathException("", "r", 0);
        check(e4.getIndex() == 0, "index 0 not kept");
        check("".equals(e4.getInput()), "empty input not kept");

        expectNPE(null, "r", 0);
        expectNPE("x", null, 0);
        expectNPE(null, "r");
        expectNPE("x", null);
        expectIAE("x", "r", -2);
        expectIAE("x", "r", Integer.MIN_VALUE);
    }

    private static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    private static void expectNPE(String input, String reason, int index) {
        try {
            new InvalidPathException(input, reason, index);
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("expected NullPointerException");
    }

    private static void expectNPE(String input, String reason) {
        try {
            new InvalidPathException(input, reason);
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("expected NullPointerException");
    }

    private static void expectIAE(String input, String reason, int index) {
        try {
            new InvalidPathException(input, reason, index);
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new RuntimeException("expected IllegalArgumentException");
    }
}
