/*
 * @test
 * @summary Verify BitSet.nextClearBit returns the expected index and rejects negative fromIndex
 * @run main BitSetWitupTest
 */

import java.util.BitSet;

public class BitSetWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        check(new BitSet().nextClearBit(0) == 0, "empty set, fromIndex 0");
        check(new BitSet().nextClearBit(1000) == 1000, "empty set, large fromIndex");

        BitSet b = new BitSet();
        b.set(0, 3);
        check(b.nextClearBit(0) == 3, "bits 0..2 set, from 0");
        check(b.nextClearBit(1) == 3, "bits 0..2 set, from 1");
        check(b.nextClearBit(3) == 3, "bits 0..2 set, from 3");

        BitSet full128 = new BitSet();
        full128.set(0, 128);
        check(full128.nextClearBit(0) == 128, "bits 0..127 set, from 0");
        check(full128.nextClearBit(65) == 128, "bits 0..127 set, from 65");

        BitSet c = new BitSet();
        c.set(0, 70);
        check(c.nextClearBit(65) == 70, "bits 0..69 set, from 65 crosses word");

        BitSet d = BitSet.valueOf(new long[] { -1L });
        check(d.nextClearBit(0) == 64, "word of all ones, from 0");

        BitSet e = new BitSet();
        e.set(5);
        check(e.nextClearBit(5) == 6, "single bit 5, from 5");
        check(e.nextClearBit(100) == 100, "fromIndex beyond wordsInUse returns fromIndex");

        try {
            new BitSet().nextClearBit(-1);
            throw new RuntimeException("expected IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }
    }
}
