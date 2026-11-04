/*
 * @test
 * @summary Tests observable behavior of Character.codePointBefore(char[], int, int)
 * @run main CharacterWitupTest
 */
public class CharacterWitupTest {
    static int failures = 0;

    static void check(boolean cond, String msg) {
        if (!cond) {
            failures++;
            System.err.println("FAIL: " + msg);
        }
    }

    static void expectIOOBE(char[] a, int index, int start, String msg) {
        try {
            Character.codePointBefore(a, index, start);
            check(false, msg + ": no exception thrown");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    public static void main(String[] args) {
        char[] ascii = {'a', 'b', 'c'};
        check(Character.codePointBefore(ascii, 2, 0) == 'b', "ascii index 2");
        check(Character.codePointBefore(ascii, 1, 0) == 'a', "ascii index 1");

        char[] pair = {'x', (char) 0xD83D, (char) 0xDE00};
        check(Character.codePointBefore(pair, 3, 0) == 0x1F600, "surrogate pair fully inside range");

        char[] pair2 = {(char) 0xD83D, (char) 0xDE00};
        check(Character.codePointBefore(pair2, 2, 0) == 0x1F600, "surrogate pair at start 0");
        check(Character.codePointBefore(pair2, 2, 1) == 0xDE00, "low surrogate cut off by start");

        expectIOOBE(ascii, 1, 1, "index equal to start");
        expectIOOBE(ascii, 0, 1, "index less than start");
        expectIOOBE(ascii, 2, -1, "negative start");
        expectIOOBE(ascii, 4, 3, "start not less than array length");

        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
