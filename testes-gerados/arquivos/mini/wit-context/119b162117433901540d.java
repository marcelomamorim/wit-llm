/*
 * @test
 * @summary Test IntPipeline.anyMatch behavior including linkedOrConsumed state
 * @run main IntPipelineWitupTest
 */

import java.util.Spliterator;
import java.util.stream.IntStream;
import java.util.stream.IntPipeline;
import java.util.function.IntPredicate;
import java.util.function.Supplier;
import java.util.stream.StreamSupport;

public class IntPipelineWitupTest {

    public static void main(String[] args) {
        testAnyMatchNormal();
        testAnyMatchLinkedOrConsumed();
    }

    // Helper minimal IntPipeline subclass to test anyMatch
    static class TestIntPipeline extends IntPipeline<Integer> {
        private final IntStream stream;

        TestIntPipeline(IntStream stream) {
            super(new Supplier<Spliterator<Integer>>() {
                public Spliterator<Integer> get() {
                    return StreamSupport.intStream(stream.spliterator(), false).mapToObj(Integer::valueOf).spliterator();
                }
            }, 0, false);
            this.stream = stream;
        }

        @Override
        public boolean anyMatch(IntPredicate predicate) {
            return super.anyMatch(predicate);
        }
    }

    public static void testAnyMatchNormal() {
        IntStream baseStream = IntStream.of(1, 2, 3, 4, 5);
        TestIntPipeline pipeline = new TestIntPipeline(baseStream);

        boolean result = pipeline.anyMatch(new IntPredicate() {
            public boolean test(int value) {
                return value == 3;
            }
        });

        if (!result) {
            throw new AssertionError("Expected anyMatch to return true for value 3");
        }

        boolean resultFalse = pipeline.anyMatch(new IntPredicate() {
            public boolean test(int value) {
                return value > 10;
            }
        });

        if (resultFalse) {
            throw new AssertionError("Expected anyMatch to return false for no matching values");
        }
    }

    public static void testAnyMatchLinkedOrConsumed() {
        IntStream baseStream = IntStream.of(1, 2, 3);
        TestIntPipeline pipeline = new TestIntPipeline(baseStream);

        // Consume the stream once to simulate linkedOrConsumed state
        pipeline.anyMatch(new IntPredicate() {
            public boolean test(int value) {
                return false;
            }
        });

        try {
            // Second call should throw IllegalStateException due to linkedOrConsumed
            pipeline.anyMatch(new IntPredicate() {
                public boolean test(int value) {
                    return true;
                }
            });
            throw new AssertionError("Expected IllegalStateException on second anyMatch call due to linkedOrConsumed");
        } catch (IllegalStateException e) {
            // Expected exception
        }
    }
}
