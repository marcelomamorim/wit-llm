/*
 * @test
 * @summary Test BitSet.forEachRemaining with null and normal IntConsumer
 * @run main BitSetWitupTest
 */

import java.util.BitSet;
import java.util.function.IntConsumer;
import java.util.Objects;

public class BitSetWitupTest {
    public static void main(String[] args) {
        testForEachRemainingNullAction();
        testForEachRemainingNormal();
    }

    private static void testForEachRemainingNullAction() {
        BitSet bs = new BitSet();
        BitSet.BitSetSpliterator spliterator = bs.spliterator();
        try {
            spliterator.forEachRemaining(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    private static void testForEachRemainingNormal() {
        BitSet bs = new BitSet();
        bs.set(0);
        bs.set(1);
        bs.set(63);
        bs.set(Integer.MAX_VALUE);

        BitSet.BitSetSpliterator spliterator = bs.spliterator();

        final StringBuilder sb = new StringBuilder();
        IntConsumer consumer = new IntConsumer() {
            public void accept(int value) {
                if (sb.length() > 0) sb.append(",");
                sb.append(value);
            }
        };

        spliterator.forEachRemaining(consumer);

        String result = sb.toString();
        // The bits set are 0,1,63 and Integer.MAX_VALUE
        // They should be reported in ascending order
        if (!result.equals("0,1,63," + Integer.MAX_VALUE)) {
            throw new RuntimeException("Unexpected forEachRemaining output: " + result);
        }
    }
}
