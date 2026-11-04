/*
 * @test
 * @summary Verify public subSequence behavior of StringBuilder, which delegates to AbstractStringBuilder.subSequence
 * @run main AbstractStringBuilderWitupTest
 */

public class AbstractStringBuilderWitupTest {
    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder("hello world");

        CharSequence cs = sb.subSequence(0, 5);
        check("hello".equals(cs.toString()), "subSequence(0,5) should return hello");
        check(cs.length() == 5, "length of subSequence(0,5) should be 5");

        check("".equals(sb.subSequence(3, 3).toString()), "empty range should return empty sequence");
        check("hello world".equals(sb.subSequence(0, sb.length()).toString()), "full range should return whole content");

        check(sb.substring(6, 11).equals(sb.subSequence(6, 11).toString()), "subSequence should match substring for same range");

        CharSequence before = sb.subSequence(0, 5);
        sb.setLength(0);
        check("hello".equals(before.toString()), "returned subSequence must not change when builder is modified");

        StringBuilder unicode = new StringBuilder("h\u00e9llo\u4e2d");
        check("o\u4e2d".equals(unicode.subSequence(4, 6).toString()), "subSequence should handle non-Latin1 characters");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
