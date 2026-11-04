/*
 * @test
 * @summary StringBuffer.replace(int,int,String) returns this, replaces the range, clamps end, rejects invalid ranges and refreshes the cached toString
 * @run main StringBufferWitupTest
 */

public class StringBufferWitupTest {
    public static void main(String[] args) {
        StringBuffer sb = new StringBuffer("hello");
        StringBuffer r = sb.replace(1, 3, "XYZ");
        check(r == sb, "replace must return this");
        check("hXYZlo".equals(sb.toString()), "simple range replacement");

        sb = new StringBuffer("hello");
        sb.replace(0, 100, "ab");
        check("ab".equals(sb.toString()), "end beyond length must be clamped");

        sb = new StringBuffer("abc");
        sb.replace(3, 3, "d");
        check("abcd".equals(sb.toString()), "empty range at end must append");

        sb = new StringBuffer("abc");
        sb.replace(1, 1, "");
        check("abc".equals(sb.toString()), "empty replacement over empty range must not change content");

        StringBuffer cached = new StringBuffer("abc");
        String before = cached.toString();
        check("abc".equals(before), "initial toString");
        cached.replace(0, 1, "Z");
        check("Zbc".equals(cached.toString()), "toString must reflect replace after cache was populated");

        expectSIOOBE(new StringBuffer("hello"), 2, 1);
        expectSIOOBE(new StringBuffer("hello"), 10, 20);
        expectSIOOBE(new StringBuffer("hello"), -1, 2);
    }

    static void expectSIOOBE(StringBuffer sb, int start, int end) {
        try {
            sb.replace(start, end, "x");
        } catch (StringIndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("expected StringIndexOutOfBoundsException for " + start + "," + end);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
