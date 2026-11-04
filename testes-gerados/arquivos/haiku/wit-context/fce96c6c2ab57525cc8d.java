/*
 * @test
 * @summary Witup-generated tests for the StringJoiner(CharSequence delimiter) constructor
 * @run main StringJoinerWitupTest
 */
public class StringJoinerWitupTest {
    public static void main(String[] args) {
        StringJoiner sj = new StringJoiner(", ");
        if (!sj.toString().equals("")) {
            throw new AssertionError("empty joiner should return empty string, got: " + sj.toString());
        }
        sj.add("a").add("b").add("c");
        if (!sj.toString().equals("a, b, c")) {
            throw new AssertionError("unexpected result: " + sj.toString());
        }
        StringJoiner empty = new StringJoiner("");
        empty.add("x").add("y");
        if (!empty.toString().equals("xy")) {
            throw new AssertionError("empty delimiter failed: " + empty.toString());
        }
        try {
            new StringJoiner(null);
            throw new AssertionError("expected NullPointerException for null delimiter");
        } catch (NullPointerException expected) {
        }
    }
}
