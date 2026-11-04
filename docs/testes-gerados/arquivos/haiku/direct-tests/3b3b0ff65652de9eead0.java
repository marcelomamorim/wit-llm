/*
 * @test
 * @summary Behavioral tests for Character.codePointAt(char[], int, int) bounds checks and surrogate handling
 * @run main CharacterWitupTest
 */

public class CharacterWitupTest {

    static final char HI = (char) 0xD83D;
    static final char LO = (char) 0xDE00;

    public static void main(String[] args) {
        checkIOOBE(new char[]{'a'}, 0, 0);
        checkIOOBE(new char[]{'a', 'b'}, 1, 1);
        checkIOOBE(new char[]{'a'}, 0, -1);
        checkIOOBE(new char[]{'a'}, 0, 2);
        checkIOOBE(new char[]{'a'}, 2, 1);

        check((int) 'A', new char[]{'A'}, 0, 1);
        check((int) LO, new char[]{LO}, 0, 1);
        check(0x1F600, new char[]{HI, LO}, 0, 2);
        check(0x1F600, new char[]{HI, LO, 'z'}, 0, 3);
        check((int) HI, new char[]{HI, LO}, 0, 1);
        check((int) HI, new char[]{HI, 'a'}, 0, 2);
        check(0x1F600, new char[]{'x', HI, LO}, 1, 3);
    }

    static void checkIOOBE(char[] a, int index, int limit) {
        try {
            Character.codePointAt(a, index, limit);
        } catch (IndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("Expected IndexOutOfBoundsException: index=" + index + " limit=" + limit);
    }

    static void check(int expected, char[] a, int index, int limit) {
        int actual = Character.codePointAt(a, index, limit);
        if (actual != expected) {
            throw new RuntimeException("Expected " + Integer.toHexString(expected)
                    + " but got " + Integer.toHexString(actual)
                    + " for index=" + index + " limit=" + limit);
        }
    }
}
