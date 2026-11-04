/*
 * @test
 * @summary Behavioral tests for Character.codePointCount(char[], int, int)
 * @run main CharacterWitupTest
 */

public class CharacterWitupTest {

    public static void main(String[] args) {
        char[] abc = {'a', 'b', 'c'};
        check(Character.codePointCount(abc, 0, 3) == 3, "plain chars");
        check(Character.codePointCount(abc, 0, 0) == 0, "zero count");
        check(Character.codePointCount(abc, 3, 0) == 0, "zero count at end");

        char[] pair = {'\uD83D', '\uDE00'};
        check(Character.codePointCount(pair, 0, 2) == 1, "surrogate pair counts as one");

        char[] highThenA = {'\uD83D', 'a'};
        check(Character.codePointCount(highThenA, 0, 2) == 2, "high surrogate followed by non-low");

        char[] lone = {'\uDE00'};
        check(Character.codePointCount(lone, 0, 1) == 1, "lone low surrogate");

        char[] highOnly = {'\uD83D'};
        check(Character.codePointCount(highOnly, 0, 1) == 1, "lone high surrogate at end");

        char[] mixed = {'x', '\uD83D', '\uDE00', 'y'};
        check(Character.codePointCount(mixed, 0, 4) == 3, "mixed full range");
        check(Character.codePointCount(mixed, 1, 2) == 1, "pair subrange");
        check(Character.codePointCount(mixed, 1, 1) == 1, "high surrogate truncated by range end");
        check(Character.codePointCount(mixed, 1, 3) == 2, "pair plus trailing char");
        check(Character.codePointCount(mixed, 3, 1) == 1, "trailing char only");

        expectIndexOutOfBounds(abc, -1, 1);
        expectIndexOutOfBounds(abc, 0, -1);
        expectIndexOutOfBounds(abc, 0, 4);
        expectIndexOutOfBounds(abc, 2, 2);
        expectIndexOutOfBounds(abc, 4, 0);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void expectIndexOutOfBounds(char[] a, int offset, int count) {
        try {
            Character.codePointCount(a, offset, count);
        } catch (IndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("expected IndexOutOfBoundsException for offset=" + offset + " count=" + count);
    }
}
