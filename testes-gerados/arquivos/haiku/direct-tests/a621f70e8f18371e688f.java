/*
 * @test
 * @summary Tests BitSet spliterator forEachRemaining through the public stream() API
 * @run main BitSetWitupTest
 */

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Spliterator;

public class BitSetWitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static List<Integer> collect(BitSet bs) {
        final List<Integer> out = new ArrayList<>();
        Spliterator.OfInt sp = bs.stream().spliterator();
        sp.forEachRemaining((int i) -> out.add(i));
        return out;
    }

    static void testEmpty() {
        check(collect(new BitSet()).isEmpty(), "empty bitset must produce no elements");
        check(collect(new BitSet(128)).isEmpty(), "preallocated empty bitset must produce no elements");
    }

    static void testSingleBit() {
        BitSet bs = new BitSet();
        bs.set(0);
        List<Integer> r = collect(bs);
        check(r.size() == 1 && r.get(0) == 0, "expected [0] but got " + r);
    }

    static void testOrderAcrossWordBoundaries() {
        BitSet bs = new BitSet();
        bs.set(1);
        bs.set(5);
        bs.set(63);
        bs.set(64);
        bs.set(127);
        bs.set(130);
        List<Integer> expected = new ArrayList<>();
        expected.add(1);
        expected.add(5);
        expected.add(63);
        expected.add(64);
        expected.add(127);
        expected.add(130);
        List<Integer> r = collect(bs);
        check(r.equals(expected), "expected " + expected + " but got " + r);
    }

    static void testMatchesBitSetContents() {
        BitSet bs = new BitSet();
        for (int i = 0; i < 300; i += 7) {
            bs.set(i);
        }
        List<Integer> r = collect(bs);
        int idx = 0;
        for (int i = bs.nextSetBit(0); i >= 0; i = bs.nextSetBit(i + 1)) {
            check(idx < r.size() && r.get(idx) == i,
                  "mismatch at position " + idx + " expected " + i);
            idx++;
        }
        check(idx == r.size(), "element count mismatch: " + r.size() + " vs " + idx);
    }

    static void testNullActionThrows() {
        BitSet bs = new BitSet();
        bs.set(3);
        Spliterator.OfInt sp = bs.stream().spliterator();
        boolean thrown = false;
        try {
            sp.forEachRemaining(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null action must throw NullPointerException");
    }

    public static void main(String[] args) {
        testEmpty();
        testSingleBit();
        testOrderAcrossWordBoundaries();
        testMatchesBitSetContents();
        testNullActionThrows();
    }
}
