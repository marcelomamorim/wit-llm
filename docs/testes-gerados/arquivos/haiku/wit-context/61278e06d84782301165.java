/*
 * @test
 * @summary Witup-derived tests for StringBuilder.append(CharSequence, int, int)
 * @run main StringBuilderWitupTest
 */

public class StringBuilderWitupTest {

    public static void main(String[] args) {
        check("hello", 1, 3, "el");
        check("hello", 0, 5, "hello");
        check("hello", 2, 2, "");
        check(null, 0, 4, "null");
        check(null, 1, 3, "ul");

        expectIOOBE("abc", -1, 2);
        expectIOOBE("abc", 0, 4);
        expectIOOBE("abc", 2, 1);
        expectIOOBE(null, 0, 5);
        expectIOOBE(null, 3, 2);
    }

    static void check(CharSequence s, int start, int end, String expected) {
        StringBuilder sb = new StringBuilder("x");
        StringBuilder r = sb.append(s, start, end);
        if (r != sb) {
            throw new RuntimeException("append did not return this builder");
        }
        String actual = sb.toString();
        if (!actual.equals("x" + expected)) {
            throw new RuntimeException("expected x" + expected + " but was " + actual);
        }
    }

    static void expectIOOBE(CharSequence s, int start, int end) {
        try {
            new StringBuilder().append(s, start, end);
        } catch (IndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("expected IndexOutOfBoundsException for start="
                + start + ", end=" + end);
    }
}
