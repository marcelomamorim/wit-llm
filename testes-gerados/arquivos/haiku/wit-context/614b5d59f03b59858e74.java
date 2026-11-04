/*
 * @test
 * @summary Witup-derived checks for SortedOps DoubleSortingSink.begin(long) reached through the public DoubleStream.sorted() API
 * @run main SortedOpsWitupTest
 */

import java.util.Arrays;
import java.util.Spliterator;
import java.util.function.DoubleConsumer;
import java.util.stream.DoubleStream;
import java.util.stream.StreamSupport;

public class SortedOpsWitupTest {

    public static void main(String[] args) {
        testSmallSizedSortProducesOrderedResult();
        testHugeExactSizeIsRejectedByBegin();
    }

    static void testSmallSizedSortProducesOrderedResult() {
        double[] expected = {1.0, 2.0, 3.0};
        double[] actual = DoubleStream.of(3.0, 1.0, 2.0).sorted().toArray();
        if (!Arrays.equals(expected, actual)) {
            throw new RuntimeException("Unexpected sorted result: " + Arrays.toString(actual));
        }
    }

    static void testHugeExactSizeIsRejectedByBegin() {
        Spliterator.OfDouble spliterator = new HugeSizedSpliterator();
        boolean thrown = false;
        try {
            StreamSupport.doubleStream(spliterator, false)
                    .sorted()
                    .forEach(x -> { });
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException for exact size Long.MAX_VALUE");
        }
    }

    static final class HugeSizedSpliterator implements Spliterator.OfDouble {
        @Override
        public boolean tryAdvance(DoubleConsumer action) {
            return false;
        }

        @Override
        public Spliterator.OfDouble trySplit() {
            return null;
        }

        @Override
        public long estimateSize() {
            return Long.MAX_VALUE;
        }

        @Override
        public int characteristics() {
            return Spliterator.SIZED | Spliterator.SUBSIZED;
        }
    }
}
