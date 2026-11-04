/*
 * @test
 * @summary Tests Character.codePointBefore(char[], int, int) boundary checks and surrogate pair handling
 * @run main CharacterWitupTest
 */

public class CharacterWitupTest {

    public static void main(String[] args) {
        char[] abc = {'a', 'b', 'c'};
        check(Character.codePointBefore(abc, 2, 0) == 'b', "codePointBefore before index 2 should return b");
        check(Character.codePointBefore(abc, 3, 0) == 'c', "codePointBefore at end of array should return c");

        char[] sp = {'x', (char) 0xD83D, (char) 0xDE00};
        check(Character.codePointBefore(sp, 3, 0) == 0x1F600, "surrogate pair inside start bounds should be combined");

        char[] pair = {(char) 0xD83D, (char) 0xDE00};
        check(Character.codePointBefore(pair, 2, 1) == 0xDE00, "low surrogate must not be combined when high surrogate is before start");

        expectIOOBE(abc, 1, 1);
        expectIOOBE(abc, 0, 1);
        expectIOOBE(abc, 2, -1);
        expectIOOBE(abc, 3, 3);
        expectIOOBE(abc, 4, 3);
    }

    static void expectIOOBE(char[] a, int index, int start) {
        try {
            Character.codePointBefore(a, index, start);
        } catch (IndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("expected IndexOutOfBoundsException for index=" + index + " start=" + start);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
