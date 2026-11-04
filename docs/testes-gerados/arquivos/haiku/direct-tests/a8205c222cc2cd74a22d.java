/*
 * @test
 * @summary Witup-generated tests for String.valueOf(char[], int, int)
 * @run main StringWitupTest
 */

public class StringWitupTest {

    public static void main(String[] args) {
        char[] data = {'a', 'b', 'c', 'd', 'e'};

        check("bcd", String.valueOf(data, 1, 3));
        check("", String.valueOf(data, 0, 0));
        check("", String.valueOf(data, data.length, 0));
        check("abcde", String.valueOf(data, 0, data.length));
        check("e", String.valueOf(data, 4, 1));

        String s = String.valueOf(data, 0, 3);
        data[0] = 'z';
        check("abc", s);
        check("zbcde", String.valueOf(data, 0, data.length));

        expectStringIndexOutOfBounds(data, -1, 2);
        expectStringIndexOutOfBounds(data, 0, -1);
        expectStringIndexOutOfBounds(data, 3, 3);
        expectStringIndexOutOfBounds(data, 6, 0);

        char[] nullData = null;
        boolean npe = false;
        try {
            String.valueOf(nullData, 0, 0);
        } catch (NullPointerException e) {
            npe = true;
        }
        if (!npe) {
            throw new RuntimeException("expected NullPointerException for null array");
        }
    }

    private static void check(String expected, String actual) {
        if (!expected.equals(actual)) {
            throw new RuntimeException("expected [" + expected + "] but was [" + actual + "]");
        }
    }

    private static void expectStringIndexOutOfBounds(char[] data, int offset, int count) {
        try {
            String.valueOf(data, offset, count);
        } catch (StringIndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("expected StringIndexOutOfBoundsException for offset="
                + offset + ", count=" + count);
    }
}
