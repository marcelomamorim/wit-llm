/*
 * @test
 * @summary WITUP-derived checks for BitSet.nextClearBit behavior and negative index exception
 * @run main BitSetWitupTest
 */

public class BitSetWitupTest {

    public static void main(String[] args) {
        testNegativeIndexThrows();
        testEmptySet();
        testSetBitsAndBoundary();
        testWordBoundary();
        testBeyondWordsInUse();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNegativeIndexThrows() {
        BitSet bs = new BitSet();
        try {
            bs.nextClearBit(-1);
            throw new RuntimeException("expected IndexOutOfBoundsException for fromIndex -1");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testEmptySet() {
        BitSet bs = new BitSet();
        check(bs.nextClearBit(0) == 0, "empty set: nextClearBit(0) should be 0");
        check(bs.nextClearBit(5) == 5, "empty set: nextClearBit(5) should be 5");
    }

    static void testSetBitsAndBoundary() {
        BitSet bs = new BitSet();
        bs.set(0, 3);
        check(bs.nextClearBit(0) == 3, "nextClearBit(0) should be 3 after set(0,3)");
        check(bs.nextClearBit(1) == 3, "nextClearBit(1) should be 3 after set(0,3)");
        check(bs.nextClearBit(3) == 3, "nextClearBit(3) should be 3 after set(0,3)");
    }

    static void testWordBoundary() {
        BitSet bs = new BitSet();
        bs.set(0, 128);
        check(bs.nextClearBit(0) == 128, "nextClearBit(0) should be 128 after set(0,128)");
        check(bs.nextClearBit(10) == 128, "nextClearBit(10) should be 128 after set(0,128)");
        check(bs.nextClearBit(127) == 128, "nextClearBit(127) should be 128 after set(0,128)");
    }

    static void testBeyondWordsInUse() {
        BitSet bs = new BitSet();
        bs.set(0, 3);
        check(bs.nextClearBit(100) == 100, "nextClearBit(100) should return 100 beyond words in use");
    }
}
