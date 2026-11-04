/*
 * @test
 * @summary Verifies StringBuilder.insert(int, float), which reaches AbstractStringBuilder.insert(int, float) and delegates to insert(int, String)
 * @run main AbstractStringBuilderWitupTest
 */
public class AbstractStringBuilderWitupTest {
    public static void main(String[] args) {
        check("a1.5b", new StringBuilder("ab").insert(1, 1.5f).toString());
        check("NaN", new StringBuilder().insert(0, Float.NaN).toString());
        check("-0.0", new StringBuilder().insert(0, -0.0f).toString());
        check("Infinity", new StringBuilder().insert(0, Float.POSITIVE_INFINITY).toString());
        check("xy3.0", new StringBuilder("xy").insert(2, 3.0f).toString());
        check("3.0xy", new StringBuilder("xy").insert(0, 3.0f).toString());

        StringBuilder sb = new StringBuilder("abc");
        StringBuilder result = sb.insert(1, 2.5f);
        check("a2.5bc", sb.toString());
        if (result != sb) {
            throw new RuntimeException("insert must return the receiver");
        }

        expectOutOfBounds(new StringBuilder("ab"), -1, 1.0f);
        expectOutOfBounds(new StringBuilder("ab"), 3, 1.0f);
    }

    static void check(String expected, String actual) {
        if (!expected.equals(actual)) {
            throw new RuntimeException("Expected \"" + expected + "\" but got \"" + actual + "\"");
        }
    }

    static void expectOutOfBounds(StringBuilder sb, int offset, float f) {
        try {
            sb.insert(offset, f);
        } catch (StringIndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("Expected StringIndexOutOfBoundsException for offset " + offset);
    }
}
