/*
 * @test
 * @summary Witup-generated checks for String(byte[], int, int) bounds validation and decoding
 * @run main StringWitupTest
 */
public class StringWitupTest {
    public static void main(String[] args) {
        byte[] data = {'h', 'e', 'l', 'l', 'o'};

        check("ell", new String(data, 1, 3));
        check("hello", new String(data, 0, 5));
        check("", new String(data, 5, 0));
        check("o", new String(data, 4, 1));

        expectSioobe(data, -1, 2);
        expectSioobe(data, 0, -1);
        expectSioobe(data, 3, 3);
        expectSioobe(data, 6, 0);
        expectSioobe(data, 0, 6);

        try {
            new String((byte[]) null, 0, 0);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null bytes");
    }

    static void check(String expected, String actual) {
        if (!expected.equals(actual)) {
            throw new RuntimeException("expected \"" + expected + "\" but was \"" + actual + "\"");
        }
    }

    static void expectSioobe(byte[] b, int offset, int length) {
        try {
            new String(b, offset, length);
        } catch (StringIndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("expected StringIndexOutOfBoundsException for offset="
                + offset + " length=" + length);
    }
}
