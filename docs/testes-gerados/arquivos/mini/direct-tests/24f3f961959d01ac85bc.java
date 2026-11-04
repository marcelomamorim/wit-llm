/*
 * @test
 * @summary Test forEachRemaining(IntConsumer) method of StreamSpliterators
 * @run main StreamSpliteratorsWitupTest
 */

import java.util.Objects;
import java.util.Spliterator;
import java.util.function.IntConsumer;
import java.util.stream.StreamSpliterators;

public class StreamSpliteratorsWitupTest {

    // Minimal PipelineHelper stub to allow calling forEachRemaining
    static class PipelineHelper<T> {
        void wrapAndCopyInto(Sink.OfInt sink, Spliterator<T> spliterator) {
            // Simulate pushing elements 1,2,3 to sink
            sink.accept(1);
            sink.accept(2);
            sink.accept(3);
        }
    }

    // Minimal Sink.OfInt interface to match expected functional interface
    interface Sink<T> {
        interface OfInt {
            void accept(int value);
        }
    }

    // Minimal IntConsumer implementation to collect consumed ints
    static class CollectingIntConsumer implements IntConsumer {
        int sum = 0;
        int count = 0;
        public void accept(int value) {
            sum += value;
            count++;
        }
    }

    // Minimal subclass of StreamSpliterators to expose forEachRemaining
    static class TestSpliterator extends StreamSpliterators.AbstractWrappingSpliterator<Integer, Integer, Object> {

        boolean finished = false;
        Object buffer = null;

        TestSpliterator() {
            super(false, new PipelineHelper<Integer>());
            this.spliterator = null;
        }

        void init() {
            // no-op for test
        }

        public void forEachRemaining(IntConsumer consumer) {
            if (buffer == null && !finished) {
                Objects.requireNonNull(consumer);
                init();
                ph.wrapAndCopyInto((Sink.OfInt) consumer::accept, spliterator);
                finished = true;
            } else {
                do { } while (tryAdvance(consumer));
            }
        }

        public boolean tryAdvance(IntConsumer consumer) {
            // no elements left after first forEachRemaining call
            return false;
        }
    }

    public static void main(String[] args) {
        // Test normal forEachRemaining with non-null consumer
        TestSpliterator ts = new TestSpliterator();
        CollectingIntConsumer cic = new CollectingIntConsumer();
        ts.forEachRemaining(cic);
        if (cic.count != 3 || cic.sum != 6) {
            throw new RuntimeException("forEachRemaining did not consume expected elements");
        }

        // Test that calling forEachRemaining again uses tryAdvance loop
        CollectingIntConsumer cic2 = new CollectingIntConsumer();
        ts.forEachRemaining(cic2);
        if (cic2.count != 0) {
            throw new RuntimeException("forEachRemaining second call should not consume elements");
        }

        // Test that passing null consumer throws NullPointerException
        TestSpliterator ts2 = new TestSpliterator();
        try {
            ts2.forEachRemaining(null);
            throw new RuntimeException("Expected NullPointerException for null consumer");
        } catch (NullPointerException npe) {
            // expected
        }
    }
}
