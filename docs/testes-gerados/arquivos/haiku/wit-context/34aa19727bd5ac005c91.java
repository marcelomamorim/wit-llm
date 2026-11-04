/*
 * @test
 * @summary Witup-derived checks for StringBuffer.charAt(int) valid and out-of-range indexes
 * @run main StringBufferWitupTest
 */

public class StringBufferWitupTest {

    public static void main(String[] args) {
        StringBuffer sb = new StringBuffer("abc");
        check(sb.charAt(0) == 'a');
        check(sb.charAt(1) == 'b');
        check(sb.charAt(2) == 'c');

        expectStringIndexOutOfBounds(sb, 3);
        expectStringIndexOutOfBounds(sb, -1);
        expectStringIndexOutOfBounds(sb, 100);

        StringBuffer empty = new StringBuffer();
        expectStringIndexOutOfBounds(empty, 0);

        StringBuffer appended = new StringBuffer("ab");
        appended.append("d");
        check(appended.charAt(2) == 'd');
        expectStringIndexOutOfBounds(appended, 3);
    }

    static void check(boolean condition) {
        if (!condition) {
            throw new RuntimeException("Check failed");
        }
    }

    static void expectStringIndexOutOfBounds(StringBuffer sb, int index) {
        try {
            char c = sb.charAt(index);
            throw new RuntimeException("No exception for index " + index + ", returned " + c);
        } catch (StringIndexOutOfBoundsException expected) {
            // expected
        }
    }
}
