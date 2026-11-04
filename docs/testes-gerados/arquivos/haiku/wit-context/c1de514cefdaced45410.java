/*
 * @test
 * @summary White-box-free behavioral tests for Character.codePointCount(char[], int, int) argument validation and counting
 * @run main CharacterWitupTest
 */
public class CharacterWitupTest {
    public static void main(String[] args) {
        check(3, Character.codePointCount(new char[]{'a', 'b', 'c'}, 0, 3));
        check(0, Character.codePointCount(new char[]{'a', 'b', 'c'}, 1, 0));
        check(0, Character.codePointCount(new char[]{'a', 'b', 'c'}, 3, 0));
        check(2, Character.codePointCount(new char[]{'a', 'b', 'c'}, 1, 2));
        check(1, Character.codePointCount(new char[]{'\uD83D', '\uDE00'}, 0, 2));
        check(2, Character.codePointCount(new char[]{'\uD800', 'a'}, 0, 2));
        check(1, Character.codePointCount(new char[]{'x', '\uD83D', '\uDE00'}, 1, 2));

        expectIOOBE(new char[]{'a', 'b', 'c'}, -1, 1);
        expectIOOBE(new char[]{'a', 'b', 'c'}, 0, -1);
        expectIOOBE(new char[]{'a', 'b', 'c'}, 0, 4);
        expectIOOBE(new char[]{'a', 'b', 'c'}, 2, 2);
        expectIOOBE(new char[]{'a', 'b', 'c'}, 1, Integer.MAX_VALUE);
        expectIOOBE(new char[]{'a', 'b', 'c'}, 4, 0);
    }

    static void check(int expected, int actual) {
        if (expected != actual) {
            throw new RuntimeException("unexpected result: expected " + expected + " but was " + actual);
        }
    }

    static void expectIOOBE(char[] a, int offset, int count) {
        try {
            Character.codePointCount(a, offset, count);
        } catch (IndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("expected IndexOutOfBoundsException for offset=" + offset + " count=" + count);
    }
}
