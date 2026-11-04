/*
 * @test
 * @summary Verifies StringBuffer.insert(int, boolean) inserts the string form of a boolean at the given offset and returns this buffer
 * @run main StringBufferWitupTest
 */

public class StringBufferWitupTest {
    public static void main(String[] args) {
        StringBuffer sb = new StringBuffer();
        StringBuffer r = sb.insert(0, true);
        check(r == sb, "insert must return the same StringBuffer instance");
        check("true".equals(sb.toString()), "insert true into empty buffer");

        sb = new StringBuffer("abc");
        sb.insert(0, false);
        check("falseabc".equals(sb.toString()), "insert false at offset 0");

        sb = new StringBuffer("abc");
        sb.insert(3, true);
        check("abctrue".equals(sb.toString()), "insert true at end offset");

        sb = new StringBuffer("ac");
        sb.insert(1, true);
        check("atruec".equals(sb.toString()), "insert true in middle");

        sb = new StringBuffer("x");
        sb.insert(1, false).insert(0, true);
        check("truexfalse".equals(sb.toString()), "chained inserts must preserve order");

        sb = new StringBuffer();
        sb.insert(0, true);
        sb.insert(4, false);
        check("truefalse".equals(sb.toString()), "insert false after existing content");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
