/*
 * @test
 * @summary WITUP-derived tests for String.join null guards and basic observable behavior
 * @run main StringWitupTest
 */

public class StringWitupTest {

    public static void main(String[] args) {
        expectNpe(() -> String.join(null, "a", "b"));
        expectNpe(() -> String.join(null, (CharSequence[]) null));
        expectNpe(() -> String.join(",", (CharSequence[]) null));

        check("a,b,c", String.join(",", "a", "b", "c"));
        check("", String.join(","));
        check("solo", String.join(",", "solo"));
        check("x-y", String.join(new StringBuilder("-"), "x", "y"));
        check("a,null", String.join(",", "a", null));
    }

    static void expectNpe(Runnable r) {
        try {
            r.run();
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("Expected NullPointerException was not thrown");
    }

    static void check(String expected, String actual) {
        if (!expected.equals(actual)) {
            throw new RuntimeException("Expected [" + expected + "] but was [" + actual + "]");
        }
    }
}
