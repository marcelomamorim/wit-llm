/*
 * @test
 * @summary Witup-generated checks for StringBuilder.insert(int, double) offset handling and output
 * @run main StringBuilderWitupTest
 */
public class StringBuilderWitupTest {
    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder("ab");
        sb.insert(0, 1.5);
        check("1.5ab", sb.toString());
        sb.insert(2, -0.25);
        check("1.5-0.25ab", sb.toString());
        sb.insert(sb.length(), 2.0);
        check("1.5-0.25ab2.0", sb.toString());
        expectStringIndexOutOfBounds(new StringBuilder("ab"), -1, 1.0);
        expectStringIndexOutOfBounds(new StringBuilder("ab"), 3, 1.0);
    }

    static void check(String expected, String actual) {
        if (!expected.equals(actual)) {
            throw new RuntimeException("expected " + expected + " but was " + actual);
        }
    }

    static void expectStringIndexOutOfBounds(StringBuilder sb, int offset, double d) {
        try {
            sb.insert(offset, d);
        } catch (StringIndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("no StringIndexOutOfBoundsException for offset " + offset);
    }
}
