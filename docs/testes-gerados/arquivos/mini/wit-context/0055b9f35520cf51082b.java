/*
 * @test
 * @summary Deterministic tests for LongPipeline.forEach(LongConsumer)
 * @run main LongPipelineWitupTest
 */

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.LongConsumer;
import java.util.stream.LongStream;
import java.util.stream.AbstractPipeline;
import java.util.stream.LongPipeline;
import java.util.function.Supplier;

public class LongPipelineWitupTest {

    public static void main(String[] args) {
        testForEachSequential();
        testForEachParallelDelegation();
    }

    // Test that forEach on a sequential LongPipeline invokes the action on all elements
    private static void testForEachSequential() {
        final long[] data = {1L, 2L, 3L};
        // Create a LongPipeline head with a Spliterator over data, sequential
        LongPipeline<Long> pipeline = new LongPipeline<Long>(
            new Supplier<Spliterator<Long>>() {
                public Spliterator<Long> get() {
                    return Spliterators.spliterator(data, 0);
                }
            },
            0,
            false
        ) {
            // Implement abstract methods minimally
            @Override
            public void forEach(LongConsumer action) {
                if (!isParallel()) {
                    adapt(sourceStageSpliterator()).forEachRemaining(action);
                } else {
                    super.forEach(action);
                }
            }

            private LongConsumer adapt(Spliterator<Long> spliterator) {
                // Not used here
                return null;
            }
        };

        final StringBuilder sb = new StringBuilder();
        LongConsumer action = new LongConsumer() {
            public void accept(long value) {
                sb.append(value).append(",");
            }
        };

        pipeline.forEach(action);

        String result = sb.toString();
        if (!result.equals("1,2,3,")) {
            throw new AssertionError("Expected '1,2,3,' but got: " + result);
        }
    }

    // Test that forEach on a parallel LongPipeline delegates to super.forEach
    private static void testForEachParallelDelegation() {
        // Create a subclass to detect super.forEach call
        class TestPipeline extends LongPipeline<Long> {
            boolean superCalled = false;

            TestPipeline() {
                super(new Supplier<Spliterator<Long>>() {
                    public Spliterator<Long> get() {
                        return Spliterators.emptySpliterator();
                    }
                }, 0, true);
            }

            @Override
            public void forEach(LongConsumer action) {
                if (!isParallel()) {
                    adapt(sourceStageSpliterator()).forEachRemaining(action);
                } else {
                    superCalled = true;
                    super.forEach(action);
                }
            }

            private LongConsumer adapt(Spliterator<Long> spliterator) {
                // Not used here
                return null;
            }
        }

        TestPipeline pipeline = new TestPipeline();
        pipeline.forEach(new LongConsumer() {
            public void accept(long value) {
                // no-op
            }
        });

        if (!pipeline.superCalled) {
            throw new AssertionError("Expected super.forEach to be called for parallel pipeline");
        }
    }
}
