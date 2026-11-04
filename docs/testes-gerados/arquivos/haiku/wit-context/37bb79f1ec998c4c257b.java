/*
 * @test
 * @summary Witup-derived checks for the InfiniteSupplyingSpliterator.OfLong tryAdvance null-action path, reached through LongStream.generate(...).spliterator()
 * @run main StreamSpliteratorsWitupTest
 */

import java.util.Spliterator;
import java.util.function.LongConsumer;
import java.util.stream.LongStream;

public class StreamSpliteratorsWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNpe();
        testNonNullActionAdvancesWithSuppliedValue();
    }

    static void testNullActionThrowsNpe() {
        Spliterator.OfLong sp = LongStream.generate(() -> 1L).spliterator();
        try {
            sp.tryAdvance((LongConsumer) null);
            throw new RuntimeException("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testNonNullActionAdvancesWithSuppliedValue() {
        Spliterator.OfLong sp = LongStream.generate(() -> 7L).spliterator();
        long[] box = {0L};
        boolean advanced = sp.tryAdvance((LongConsumer) v -> box[0] = v);
        if (!advanced) {
            throw new RuntimeException("tryAdvance should return true for infinite supplier");
        }
        if (box[0] != 7L) {
            throw new RuntimeException("Expected consumer to receive 7 but got " + box[0]);
        }
    }
}
