/*
 * @test
 * @summary Verify StringBuffer.append(char[]) appends characters, returns this, and invalidates the cached string
 * @run main StringBufferWitupTest
 */

public class StringBufferWitupTest {

    public static void main(String[] args) {
        StringBuffer sb = new StringBuffer();
        StringBuffer ret = sb.append(new char[]{'a', 'b', 'c'});
        check(ret == sb, "append(char[]) should return this");
        check("abc".equals(sb.toString()), "expected abc but got " + sb);

        sb.append(new char[0]);
        check("abc".equals(sb.toString()), "empty array must not change content, got " + sb);

        sb.append(new char[]{'d'});
        check("abcd".equals(sb.toString()), "expected abcd but got " + sb);

        char[] src = {'x', 'y'};
        sb.append(src);
        src[0] = 'z';
        check("abcdxy".equals(sb.toString()), "buffer must copy source array, got " + sb);

        StringBuffer s2 = new StringBuffer("start");
        String before = s2.toString();
        check("start".equals(before), "unexpected initial content " + before);
        s2.append(new char[]{'l', 'e'});
        check("startle".equals(s2.toString()), "cached toString not refreshed after append, got " + s2);

        StringBuffer s3 = new StringBuffer("pre");
        s3.append(new char[]{'!'});
        check("pre!".equals(s3.toString()), "expected pre! but got " + s3);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
