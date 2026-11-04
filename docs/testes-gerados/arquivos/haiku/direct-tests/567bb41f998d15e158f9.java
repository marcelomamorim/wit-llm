/*
 * @test
 * @summary Behavioral checks for BitSet.previousClearBit(int)
 * @run main BitSetWitupTest
 */

import java.util.BitSet;

public class BitSetWitupTest {
    static int failures = 0;

    static void check(boolean cond, String msg) {
        if (!cond) {
            failures++;
            System.err.println("FAIL: " + msg);
        }
    }

    public static void main(String[] args) {
        BitSet empty = new BitSet();
        check(empty.previousClearBit(-1) == -1, "empty, fromIndex -1");
        check(empty.previousClearBit(0) == 0, "empty, fromIndex 0");
        check(empty.previousClearBit(5) == 5, "empty, fromIndex 5");
        check(empty.previousClearBit(Integer.MAX_VALUE) == Integer.MAX_VALUE, "empty, fromIndex MAX_VALUE");

        BitSet b = new BitSet();
        b.set(0, 10);
        check(b.previousClearBit(9) == -1, "bits 0-9 set, from 9 should be -1");
        check(b.previousClearBit(10) == 10, "beyond set bits, from 10 should be 10");

        BitSet c = new BitSet();
        c.set(0, 5);
        check(c.previousClearBit(4) == -1, "bits 0-4 set, from 4 should be -1");
        check(c.previousClearBit(7) == 7, "bit 7 clear, from 7 should be 7");

        BitSet d = new BitSet();
        d.set(0, 128);
        d.clear(63);
        check(d.previousClearBit(127) == 63, "cross-word search from 127 should find 63");
        check(d.previousClearBit(64) == 63, "cross-word search from 64 should find 63");
        check(d.previousClearBit(63) == 63, "clear bit 63 itself should return 63");

        BitSet e = new BitSet();
        e.set(0, 128);
        e.clear(0);
        check(e.previousClearBit(127) == 0, "only bit 0 clear, from 127 should be 0");
        check(e.previousClearBit(0) == 0, "bit 0 clear, from 0 should be 0");

        boolean thrown = false;
        try {
            empty.previousClearBit(-2);
        } catch (IndexOutOfBoundsException ex) {
            thrown = true;
        }
        check(thrown, "fromIndex -2 should throw IndexOutOfBoundsException");

        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
