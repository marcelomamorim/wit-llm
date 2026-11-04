/*
 * @test
 * @summary Witup-generated checks for StringUTF16.charAt reached through public String.charAt on UTF16 strings
 * @run main StringUTF16WitupTest
 */

public class StringUTF16WitupTest {

    public static void main(String[] args) {
        String s = "" + (char) 0x20AC + (char) 0x4E2D;
        if (s.charAt(0) != (char) 0x20AC) {
            throw new RuntimeException("Unexpected char at 0: " + (int) s.charAt(0));
        }
        if (s.charAt(1) != (char) 0x4E2D) {
            throw new RuntimeException("Unexpected char at 1: " + (int) s.charAt(1));
        }
        expectStringIndexOutOfBounds(s, -1);
        expectStringIndexOutOfBounds(s, 2);
        expectStringIndexOutOfBounds(s, Integer.MAX_VALUE);
    }

    static void expectStringIndexOutOfBounds(String s, int index) {
        try {
            char c = s.charAt(index);
            throw new RuntimeException("No exception for index " + index + ", got " + (int) c);
        } catch (StringIndexOutOfBoundsException expected) {
            // expected
        }
    }
}
