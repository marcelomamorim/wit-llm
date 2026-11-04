/*
 * @test
 * @summary Witup-derived checks for String(byte[], int, int, int) bounds validation and hibyte handling
 * @run main StringWitupTest
 */

public class StringWitupTest {

    public static void main(String[] args) {
        byte[] data = {'x', 'A', 'B'};

        check("AB", new String(data, 0, 1, 2));
        check("", new String(data, 0, 3, 0));
        check("xAB", new String(data, 0, 0, 3));

        String hi = new String(data, 1, 1, 2);
        check(String.valueOf((char) 0x141) + String.valueOf((char) 0x142), hi);

        expectStringIndexOutOfBounds(data, 0, -1, 1);
        expectStringIndexOutOfBounds(data, 0, 0, -1);
        expectStringIndexOutOfBounds(data, 0, 2, 2);
        expectStringIndexOutOfBounds(data, 0, 4, 0);
    }

    private static void check(String expected, String actual) {
        if (!expected.equals(actual)) {
            throw new RuntimeException("Expected " + expected + " but got " + actual);
        }
    }

    private static void expectStringIndexOutOfBounds(byte[] ascii, int hibyte, int offset, int count) {
        try {
            String s = new String(ascii, hibyte, offset, count);
            throw new RuntimeException("No exception for offset=" + offset + " count=" + count + " result=" + s);
        } catch (StringIndexOutOfBoundsException expected) {
            // expected
        }
    }
}
