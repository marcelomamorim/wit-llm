/*
 * @test
 * @summary Tests observable behavior of StringBuilder.insert(int, double)
 * @run main StringBuilderWitupTest
 */
public class StringBuilderWitupTest {
    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder("ab");
        StringBuilder result = sb.insert(1, 1.5);
        check(result == sb, "insert must return the same builder instance");
        check("a1.5b".equals(sb.toString()), "middle insert produced: " + sb);

        StringBuilder empty = new StringBuilder();
        empty.insert(0, 2.0);
        check("2.0".equals(empty.toString()), "insert into empty builder produced: " + empty);

        StringBuilder atEnd = new StringBuilder("x");
        atEnd.insert(atEnd.length(), 3.25);
        check("x3.25".equals(atEnd.toString()), "insert at end produced: " + atEnd);

        StringBuilder atStart = new StringBuilder("end");
        atStart.insert(0, -0.0);
        check("-0.0end".equals(atStart.toString()), "insert negative zero produced: " + atStart);

        StringBuilder nan = new StringBuilder();
        nan.insert(0, Double.NaN);
        check("NaN".equals(nan.toString()), "NaN insert produced: " + nan);

        StringBuilder inf = new StringBuilder();
        inf.insert(0, Double.POSITIVE_INFINITY);
        check("Infinity".equals(inf.toString()), "infinity insert produced: " + inf);

        StringBuilder ninf = new StringBuilder();
        ninf.insert(0, Double.NEGATIVE_INFINITY);
        check("-Infinity".equals(ninf.toString()), "negative infinity insert produced: " + ninf);

        double value = 123.456;
        StringBuilder consistent = new StringBuilder(">");
        consistent.insert(1, value);
        check((">" + Double.toString(value)).equals(consistent.toString()),
              "insert must match Double.toString: " + consistent);
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
