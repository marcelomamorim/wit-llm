/*
 * @test
 * @summary Verify StringBuffer.insert(int, long) inserts the decimal form of the long at the given offset and returns this
 * @run main StringBufferWitupTest
 */

public class StringBufferWitupTest {

    public static void main(String[] args) {
        StringBuffer sb = new StringBuffer("abc");
        StringBuffer r = sb.insert(0, 123L);
        check(r == sb, "insert must return this");
        check("123abc".equals(sb.toString()), "insert at 0: " + sb);

        sb = new StringBuffer("abc");
        sb.insert(3, -42L);
        check("abc-42".equals(sb.toString()), "insert at end: " + sb);

        sb = new StringBuffer("ac");
        sb.insert(1, Long.MIN_VALUE);
        check("a-9223372036854775808c".equals(sb.toString()), "insert MIN_VALUE: " + sb);

        sb = new StringBuffer();
        sb.insert(0, Long.MAX_VALUE);
        check("9223372036854775807".equals(sb.toString()), "insert MAX_VALUE into empty: " + sb);

        sb = new StringBuffer("x");
        sb.insert(0, 0L);
        check("0x".equals(sb.toString()), "insert zero: " + sb);

        expectStringIndexOutOfBounds(new StringBuffer("abc"), -1, 5L);
        expectStringIndexOutOfBounds(new StringBuffer("abc"), 4, 5L);
    }

    static void expectStringIndexOutOfBounds(StringBuffer sb, int offset, long value) {
        try {
            sb.insert(offset, value);
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
