/*
 * @test
 * @summary Witup-generated checks for String.valueOf(char[], int, int) bounds handling
 * @run main StringWitupTest
 */

public class StringWitupTest {

    public static void main(String[] args) {
        char[] data = {'a', 'b', 'c'};

        check("abc".equals(String.valueOf(data, 0, 3)), "full range");
        check("bc".equals(String.valueOf(data, 1, 2)), "middle range");
        check("".equals(String.valueOf(data, 3, 0)), "empty at end boundary");
        check("".equals(String.valueOf(data, 0, 0)), "empty at start");
        check("c".equals(String.valueOf(data, 2, 1)), "last character");

        expectSIOOBE(data, -1, 1);
        expectSIOOBE(data, 0, -1);
        expectSIOOBE(data, 0, 4);
        expectSIOOBE(data, 2, 2);
        expectSIOOBE(data, 4, 0);
        expectSIOOBE(data, Integer.MAX_VALUE, 1);

        expectNPE(null, 0, 0);
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Check failed: " + message);
        }
    }

    private static void expectSIOOBE(char[] data, int offset, int count) {
        try {
            String result = String.valueOf(data, offset, count);
            throw new RuntimeException("Expected StringIndexOutOfBoundsException for offset="
                    + offset + ", count=" + count + " but got \"" + result + "\"");
        } catch (StringIndexOutOfBoundsException expected) {
            // expected
        }
    }

    private static void expectNPE(char[] data, int offset, int count) {
        try {
            String.valueOf(data, offset, count);
            throw new RuntimeException("Expected NullPointerException for null data");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
