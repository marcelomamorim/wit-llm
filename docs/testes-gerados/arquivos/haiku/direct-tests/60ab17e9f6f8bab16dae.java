/*
 * @test
 * @summary Verifies StringBuffer.insert(int, int) inserts the decimal form of an int at the given offset and returns the same buffer.
 * @run main StringBufferWitupTest
 */

public class StringBufferWitupTest {

    public static void main(String[] args) {
        StringBuffer sb = new StringBuffer("abc");
        StringBuffer result = sb.insert(1, 42);
        check(result == sb, "insert must return the same StringBuffer instance");
        check("a42bc".equals(sb.toString()), "expected a42bc but was " + sb);

        StringBuffer empty = new StringBuffer();
        empty.insert(0, -7);
        check("-7".equals(empty.toString()), "expected -7 but was " + empty);

        StringBuffer tail = new StringBuffer("xy");
        tail.insert(2, Integer.MIN_VALUE);
        check("xy-2147483648".equals(tail.toString()), "expected xy-2147483648 but was " + tail);

        tail.insert(0, 0);
        check("0xy-2147483648".equals(tail.toString()), "expected 0xy-2147483648 but was " + tail);

        StringBuffer chained = new StringBuffer();
        chained.insert(0, 1).insert(1, 2);
        check("12".equals(chained.toString()), "expected 12 but was " + chained);
        check(chained.length() == 2, "expected length 2 but was " + chained.length());
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
