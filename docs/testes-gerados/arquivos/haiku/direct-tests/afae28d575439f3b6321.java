/*
 * @test
 * @summary Tests String.offsetByCodePoints for valid offsets, surrogate pairs and out-of-range indices
 * @run main StringWitupTest
 */

public class StringWitupTest {

    public static void main(String[] args) {
        testValidOffsets();
        testSurrogatePairs();
        testIndexAtBoundaries();
        testInvalidIndexThrows();
        testCodePointOffsetOutOfRangeThrows();
    }

    static void testValidOffsets() {
        check("abc".offsetByCodePoints(0, 2) == 2, "abc 0,+2");
        check("abc".offsetByCodePoints(1, 1) == 2, "abc 1,+1");
        check("abc".offsetByCodePoints(1, -1) == 0, "abc 1,-1");
        check("abc".offsetByCodePoints(0, 0) == 0, "abc 0,0");
        check("".offsetByCodePoints(0, 0) == 0, "empty 0,0");
    }

    static void testSurrogatePairs() {
        String s = "\uD83D\uDE00a";
        check(s.length() == 3, "length of surrogate string");
        check(s.offsetByCodePoints(0, 1) == 2, "surrogate 0,+1");
        check(s.offsetByCodePoints(0, 2) == 3, "surrogate 0,+2");
        check(s.offsetByCodePoints(3, -1) == 2, "surrogate 3,-1");
        check(s.offsetByCodePoints(3, -2) == 0, "surrogate 3,-2");
    }

    static void testIndexAtBoundaries() {
        check("abc".offsetByCodePoints(3, 0) == 3, "index at length, offset 0");
        check("abc".offsetByCodePoints(0, 3) == 3, "full advance");
    }

    static void testInvalidIndexThrows() {
        expectIOOBE("abc", -1, 0);
        expectIOOBE("abc", 4, 0);
        expectIOOBE("", 1, 0);
    }

    static void testCodePointOffsetOutOfRangeThrows() {
        expectIOOBE("abc", 0, 4);
        expectIOOBE("abc", 3, 1);
        expectIOOBE("abc", 0, -1);
    }

    static void expectIOOBE(String s, int index, int offset) {
        try {
            s.offsetByCodePoints(index, offset);
        } catch (IndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("Expected IndexOutOfBoundsException for index=" + index + ", offset=" + offset);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Check failed: " + message);
        }
    }
}
