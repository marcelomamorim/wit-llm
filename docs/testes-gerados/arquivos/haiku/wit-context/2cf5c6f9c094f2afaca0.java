/*
 * @test
 * @summary Witup-derived tests for BitSet.clear(int, int) argument checks and range clearing behavior
 * @run main BitSetWitupTest
 */

public class BitSetWitupTest {

    public static void main(String[] args) {
        testNegativeFromIndexThrows();
        testNegativeToIndexThrows();
        testFromGreaterThanToThrows();
        testEmptyRangeIsNoOp();
        testClearSubRangeKeepsOutsideBits();
        testClearRangeAcrossWords();
        testClearBeyondLogicalSizeIsNoOp();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void expectIOOBE(int from, int to) {
        BitSet bs = new BitSet();
        bs.set(0, 10);
        try {
            bs.clear(from, to);
        } catch (IndexOutOfBoundsException expected) {
            check(bs.cardinality() == 10, "state changed after rejected clear(" + from + "," + to + ")");
            return;
        }
        throw new RuntimeException("no IndexOutOfBoundsException for clear(" + from + "," + to + ")");
    }

    static void testNegativeFromIndexThrows() {
        expectIOOBE(-1, 5);
        expectIOOBE(-1, -1);
    }

    static void testNegativeToIndexThrows() {
        expectIOOBE(0, -1);
        expectIOOBE(3, -1);
    }

    static void testFromGreaterThanToThrows() {
        expectIOOBE(5, 3);
        expectIOOBE(1, 0);
    }

    static void testEmptyRangeIsNoOp() {
        BitSet bs = new BitSet();
        bs.set(4);
        bs.set(7);
        bs.clear(5, 5);
        check(bs.get(4), "bit 4 cleared by empty range");
        check(bs.get(7), "bit 7 cleared by empty range");
        check(bs.cardinality() == 2, "cardinality changed by empty range");
    }

    static void testClearSubRangeKeepsOutsideBits() {
        BitSet bs = new BitSet();
        bs.set(0, 8);
        bs.clear(2, 5);
        check(bs.get(0), "bit 0 should remain set");
        check(bs.get(1), "bit 1 should remain set");
        check(!bs.get(2), "bit 2 should be cleared");
        check(!bs.get(3), "bit 3 should be cleared");
        check(!bs.get(4), "bit 4 should be cleared");
        check(bs.get(5), "bit 5 should remain set (toIndex exclusive)");
        check(bs.get(7), "bit 7 should remain set");
        check(bs.cardinality() == 5, "unexpected cardinality " + bs.cardinality());
    }

    static void testClearRangeAcrossWords() {
        BitSet bs = new BitSet();
        bs.set(0, 200);
        bs.clear(60, 130);
        check(bs.get(59), "bit 59 should remain set");
        check(!bs.get(60), "bit 60 should be cleared");
        check(!bs.get(129), "bit 129 should be cleared");
        check(bs.get(130), "bit 130 should remain set");
        check(bs.cardinality() == 200 - 70, "unexpected cardinality " + bs.cardinality());
    }

    static void testClearBeyondLogicalSizeIsNoOp() {
        BitSet bs = new BitSet();
        bs.set(1);
        bs.clear(100, 200);
        check(bs.get(1), "bit 1 should remain set");
        check(bs.cardinality() == 1, "unexpected cardinality " + bs.cardinality());
    }
}
