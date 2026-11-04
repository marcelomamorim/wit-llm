/*
 * @test
 * @summary Basic observable behavior of StringBuffer.subSequence(int, int)
 * @run main StringBufferWitupTest
 */

public class StringBufferWitupTest {

    public static void main(String[] args) {
        StringBuffer sb = new StringBuffer("abcdef");

        check("bcd".equals(sb.subSequence(1, 4).toString()), "subSequence(1,4) should be bcd");
        check("abcdef".equals(sb.subSequence(0, sb.length()).toString()), "full range should return whole content");
        check("".equals(sb.subSequence(2, 2).toString()), "empty range should return empty string");
        check("".equals(sb.subSequence(6, 6).toString()), "empty range at end should return empty string");
        check("a".equals(sb.subSequence(0, 1).toString()), "single char range should return a");

        CharSequence cs = sb.subSequence(0, 3);
        check(cs instanceof String, "subSequence result should be a String instance");

        sb.append("zzz");
        check("abc".equals(cs.toString()), "returned sequence must not change after buffer append");

        sb.insert(0, "XX");
        check("abc".equals(cs.toString()), "returned sequence must not change after buffer insert");

        StringBuffer sb2 = new StringBuffer("hello");
        CharSequence part = sb2.subSequence(1, 3);
        check("el".equals(part.toString()), "subSequence(1,3) of hello should be el");
        sb2.setLength(0);
        check("el".equals(part.toString()), "snapshot should survive setLength on buffer");

        StringBuffer sb3 = new StringBuffer();
        sb3.append("0123456789");
        check("456".equals(sb3.subSequence(4, 7).toString()), "subSequence on appended content should be 456");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
