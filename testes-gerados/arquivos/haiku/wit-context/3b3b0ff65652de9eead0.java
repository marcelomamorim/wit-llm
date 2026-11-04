/*
 * @test
 * @summary Witup-derived tests for Character.codePointAt(char[], int, int)
 * @run main CharacterWitupTest
 */

public class CharacterWitupTest {

    public static void main(String[] args) {
        testIndexEqualsLimitThrows();
        testIndexBeyondLimitThrows();
        testNegativeLimitThrows();
        testLimitBeyondArrayLengthThrows();
        testBasicCodePoint();
        testLastValidIndex();
        testSurrogatePair();
        testLoneHighSurrogateAtLimit();
    }

    static void testIndexEqualsLimitThrows() {
        expectIndexOutOfBounds(new char[] {'a', 'b'}, 2, 2);
    }

    static void testIndexBeyondLimitThrows() {
        expectIndexOutOfBounds(new char[] {'a', 'b'}, 3, 2);
    }

    static void testNegativeLimitThrows() {
        expectIndexOutOfBounds(new char[] {'a'}, -2, -1);
    }

    static void testLimitBeyondArrayLengthThrows() {
        expectIndexOutOfBounds(new char[] {'a'}, 0, 2);
    }

    static void testBasicCodePoint() {
        assertEquals('a', Character.codePointAt(new char[] {'a', 'b'}, 0, 2));
    }

    static void testLastValidIndex() {
        assertEquals('b', Character.codePointAt(new char[] {'a', 'b'}, 1, 2));
    }

    static void testSurrogatePair() {
        char[] a = new char[] {(char) 0xD83D, (char) 0xDE00};
        assertEquals(0x1F600, Character.codePointAt(a, 0, 2));
    }

    static void testLoneHighSurrogateAtLimit() {
        char[] a = new char[] {(char) 0xD83D};
        assertEquals(0xD83D, Character.codePointAt(a, 0, 1));
    }

    static void expectIndexOutOfBounds(char[] a, int index, int limit) {
        try {
            Character.codePointAt(a, index, limit);
        } catch (IndexOutOfBoundsException e) {
            return;
        }
        throw new AssertionError("expected IndexOutOfBoundsException for index=" + index + " limit=" + limit);
    }

    static void assertEquals(int expected, int actual) {
        if (expected != actual) {
            throw new AssertionError("expected " + expected + " but was " + actual);
        }
    }
}
