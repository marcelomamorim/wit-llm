/*
 * @test
 * @summary Tests String.join null handling, empty input, and delimiter behavior
 * @run main StringWitupTest
 */

public class StringWitupTest {
    public static void main(String[] args) {
        check("a,b,c", String.join(",", "a", "b", "c"));
        check("", String.join(","));
        check("", String.join("-", ""));
        check("x", String.join("-", "x"));
        check("a--b", String.join("--", "a", "b"));
        check("ab", String.join("", new StringBuilder("a"), new StringBuilder("b")));
        check("null-z", String.join("-", (CharSequence) null, "z"));
        expectNPE(() -> String.join(null, "a"));
        expectNPE(() -> String.join(",", (CharSequence[]) null));
    }

    private static void check(String expected, String actual) {
        if (!expected.equals(actual)) {
            throw new RuntimeException("Expected [" + expected + "] but got [" + actual + "]");
        }
    }

    private static void expectNPE(Runnable r) {
        try {
            r.run();
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("Expected NullPointerException");
    }
}
