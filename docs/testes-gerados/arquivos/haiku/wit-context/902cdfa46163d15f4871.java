/*
 * @test
 * @summary Witup-derived tests for StringBuffer.substring(int)
 * @run main StringBufferWitupTest
 */
public class StringBufferWitupTest {
    public static void main(String[] args) {
        check("hello".equals(new StringBuffer("hello").substring(0)), "substring(0) should return whole content");
        check("llo".equals(new StringBuffer("hello").substring(2)), "substring(2) should return suffix");
        check("".equals(new StringBuffer("hello").substring(5)), "substring(length) should return empty string");
        check("".equals(new StringBuffer().substring(0)), "substring(0) on empty buffer should return empty string");

        StringBuffer sb = new StringBuffer("start");
        sb.append("le");
        check("le".equals(sb.substring(5)), "substring should reflect appended content");

        expectSIOOBE(new StringBuffer("hello"), -1);
        expectSIOOBE(new StringBuffer("hello"), 6);
        expectSIOOBE(new StringBuffer(), 1);
    }

    static void expectSIOOBE(StringBuffer sb, int start) {
        try {
            sb.substring(start);
        } catch (StringIndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("expected StringIndexOutOfBoundsException for start=" + start);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
