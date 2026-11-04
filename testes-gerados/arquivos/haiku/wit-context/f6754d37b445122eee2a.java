/*
 * @test
 * @summary Witup-derived checks for StringBuffer.insert(int, double) offset validation
 * @run main StringBufferWitupTest
 */

public class StringBufferWitupTest {
    public static void main(String[] args) {
        StringBuffer sb = new StringBuffer("ab");
        StringBuffer result = sb.insert(1, 2.5);
        check(result == sb, "insert must return this");
        check("a2.5b".equals(sb.toString()), "unexpected content: " + sb);

        check("1.0".equals(new StringBuffer().insert(0, 1.0).toString()), "insert at 0 on empty buffer failed");
        check("ab1.0".equals(new StringBuffer("ab").insert(2, 1.0).toString()), "insert at length failed");

        expectStringIndexOutOfBounds(new StringBuffer("ab"), -1, 1.0);
        expectStringIndexOutOfBounds(new StringBuffer("ab"), 3, 1.0);
        expectStringIndexOutOfBounds(new StringBuffer(), 1, 1.0);
    }

    static void expectStringIndexOutOfBounds(StringBuffer sb, int offset, double d) {
        try {
            sb.insert(offset, d);
        } catch (StringIndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("expected StringIndexOutOfBoundsException for offset " + offset);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
