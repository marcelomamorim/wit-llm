/*
 * @test
 * @summary Verifies that index checks reached through String.charAt on UTF16-coded strings reject out-of-range indexes and accept valid ones
 * @run main StringUTF16WitupTest
 */
public class StringUTF16WitupTest {

    public static void main(String[] args) {
        String s = "\u4e2d\u6587";

        if (s.charAt(0) != '\u4e2d') {
            throw new AssertionError("charAt(0) returned unexpected char");
        }
        if (s.charAt(1) != '\u6587') {
            throw new AssertionError("charAt(1) returned unexpected char");
        }

        expectStringIndexOutOfBounds(s, -1);
        expectStringIndexOutOfBounds(s, 2);
        expectStringIndexOutOfBounds(s, Integer.MAX_VALUE);
        expectStringIndexOutOfBounds(s, Integer.MIN_VALUE);
    }

    static void expectStringIndexOutOfBounds(String s, int index) {
        try {
            s.charAt(index);
        } catch (StringIndexOutOfBoundsException e) {
            return;
        }
        throw new AssertionError("expected StringIndexOutOfBoundsException for index " + index);
    }
}
