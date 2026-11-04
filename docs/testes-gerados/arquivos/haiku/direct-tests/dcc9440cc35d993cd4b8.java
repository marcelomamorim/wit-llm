/*
 * @test
 * @summary Verify insert(int, boolean) of AbstractStringBuilder through the public StringBuilder API
 * @run main AbstractStringBuilderWitupTest
 */

public class AbstractStringBuilderWitupTest {
    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder("ab");
        StringBuilder r = sb.insert(1, true);
        check(r == sb, "insert must return the same builder");
        check("atrueb".equals(sb.toString()), "insert true at middle: " + sb);
        check(sb.length() == 6, "length after insert true: " + sb.length());

        sb = new StringBuilder("xy");
        sb.insert(0, false);
        check("falsexy".equals(sb.toString()), "insert false at start: " + sb);

        sb = new StringBuilder("xy");
        sb.insert(2, true);
        check("xytrue".equals(sb.toString()), "insert true at end: " + sb);

        sb = new StringBuilder();
        sb.insert(0, true);
        check("true".equals(sb.toString()), "insert true into empty builder: " + sb);

        sb = new StringBuilder("abc");
        sb.insert(1, false);
        check("afalsebc".equals(sb.toString()), "insert false at middle: " + sb);
        check(sb.length() == 9, "length after insert false: " + sb.length());
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
