/*
 * @test
 * @summary Black-box checks of BitSet.get(int) observable behavior
 * @run main BitSetWitupTest
 */
public class BitSetWitupTest {
    static void check(boolean c, String m) {
        if (!c) throw new RuntimeException(m);
    }

    static void expectIOOBE(int idx) {
        try {
            new BitSet().get(idx);
        } catch (IndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("no IndexOutOfBoundsException for " + idx);
    }

    public static void main(String[] args) {
        BitSet empty = new BitSet();
        check(!empty.get(0), "empty bit 0 should be false");

        BitSet b = new BitSet(1000);
        check(!b.get(999), "preallocated unset bit should be false");
        b.set(5);
        check(b.get(5), "bit 5 should be set");
        check(!b.get(4) && !b.get(6), "neighbors of bit 5 should be false");
        b.clear(5);
        check(!b.get(5), "bit 5 should be cleared");

        BitSet v = BitSet.valueOf(new long[]{0x5L});
        check(v.get(0) && !v.get(1) && v.get(2), "valueOf bit pattern mismatch");

        BitSet hi = BitSet.valueOf(new long[]{1L << 63});
        check(hi.get(63) && !hi.get(62), "bit 63 mismatch");
        check(!hi.get(64), "bit beyond word should be false");

        BitSet ones = BitSet.valueOf(new long[]{-1L});
        check(ones.get(0) && ones.get(63) && !ones.get(64), "all-ones word mismatch");

        check(!BitSet.valueOf(new long[]{0L, 0L}).get(100), "zero words should yield false");
        check(!BitSet.valueOf(new long[]{1L}).get(128), "index beyond words in use should be false");

        expectIOOBE(-1);
        expectIOOBE(Integer.MIN_VALUE);
    }
}
