/*
 * @test
 * @summary Witup-derived checks for StringBuffer.setLength behavior
 * @run main StringBufferWitupTest
 */

public class StringBufferWitupTest {

    public static void main(String[] args) {
        testNegativeLengthThrows();
        testTruncate();
        testExtendFillsWithNulls();
        testZeroLength();
        testToStringReflectsChange();
    }

    static void testNegativeLengthThrows() {
        StringBuffer sb = new StringBuffer("abcdef");
        boolean thrown = false;
        try {
            sb.setLength(-1);
        } catch (StringIndexOutOfBoundsException e) {
            thrown = true;
        }
        check(thrown, "expected StringIndexOutOfBoundsException for negative length");
        check("abcdef".equals(sb.toString()), "content changed after failed setLength");
    }

    static void testTruncate() {
        StringBuffer sb = new StringBuffer("abcdef");
        sb.setLength(2);
        check(sb.length() == 2, "length after truncate");
        check("ab".equals(sb.toString()), "content after truncate");
    }

    static void testExtendFillsWithNulls() {
        StringBuffer sb = new StringBuffer("ab");
        sb.setLength(5);
        check(sb.length() == 5, "length after extend");
        check(sb.charAt(0) == 'a', "first char preserved");
        check(sb.charAt(1) == 'b', "second char preserved");
        for (int i = 2; i < 5; i++) {
            check(sb.charAt(i) == (char) 0, "extended position should be NUL at " + i);
        }
    }

    static void testZeroLength() {
        StringBuffer sb = new StringBuffer("xyz");
        sb.setLength(0);
        check(sb.length() == 0, "length after setLength(0)");
        check("".equals(sb.toString()), "content after setLength(0)");
    }

    static void testToStringReflectsChange() {
        StringBuffer sb = new StringBuffer("hello");
        String s1 = sb.toString();
        check("hello".equals(s1), "initial toString");
        sb.setLength(3);
        check("hel".equals(sb.toString()), "toString must reflect truncation");
        sb.setLength(4);
        check(sb.length() == 4, "length after extend");
        check(sb.charAt(3) == (char) 0, "extended char should be NUL");
        check(sb.toString().length() == 4, "toString must reflect extension");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
