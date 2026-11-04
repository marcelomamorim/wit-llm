/*
 * @test
 * @summary Checks LongIteratorSpliterator.forEachRemaining via Spliterators.spliteratorUnknownSize public API
 * @run main SpliteratorsWitupTest
 */

import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.LongConsumer;

public class SpliteratorsWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNpe();
        testNullActionDoesNotConsumeIterator();
        testNonNullActionDelegatesElements();
    }

    static PrimitiveIterator.OfLong iter(final long[] values, final boolean[] touched) {
        return new PrimitiveIterator.OfLong() {
            int i = 0;

            @Override
            public boolean hasNext() {
                touched[0] = true;
                return i < values.length;
            }

            @Override
            public long nextLong() {
                return values[i++];
            }
        };
    }

    static void testNullActionThrowsNpe() {
        boolean[] touched = new boolean[1];
        Spliterator.OfLong s = Spliterators.spliteratorUnknownSize(
                iter(new long[] {1L, 2L}, touched), 0);
        try {
            s.forEachRemaining((LongConsumer) null);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException();
    }

    static void testNullActionDoesNotConsumeIterator() {
        boolean[] touched = new boolean[1];
        Spliterator.OfLong s = Spliterators.spliteratorUnknownSize(
                iter(new long[] {1L, 2L}, touched), 0);
        try {
            s.forEachRemaining((LongConsumer) null);
        } catch (NullPointerException e) {
            if (touched[0]) {
                throw new RuntimeException();
            }
            return;
        }
        throw new RuntimeException();
    }

    static void testNonNullActionDelegatesElements() {
        boolean[] touched = new boolean[1];
        final long[] sum = new long[1];
        final int[] count = new int[1];
        Spliterator.OfLong s = Spliterators.spliteratorUnknownSize(
                iter(new long[] {1L, 2L, 3L}, touched), 0);
        LongConsumer c = v -> {
            sum[0] += v;
            count[0]++;
        };
        s.forEachRemaining(c);
        if (sum[0] != 6L || count[0] != 3) {
            throw new RuntimeException();
        }
    }
}
