/*
 * @test
 * @summary Test LongPipeline.filter(LongPredicate) for null argument and basic filtering
 * @run main LongPipelineWitupTest
 */

import java.util.Objects;
import java.util.stream.LongStream;
import java.util.function.LongPredicate;

public class LongPipelineWitupTest {

    // Minimal subclass of LongPipeline to allow testing filter method
    static class TestLongPipeline extends LongPipeline<Object> {
        TestLongPipeline() {
            super((Spliterator<Long>)null, 0, false);
        }
    }

    public static void main(String[] args) {
        testFilterNullPredicate();
        testFilterBasicBehavior();
    }

    private static void testFilterNullPredicate() {
        LongPipeline<?> pipeline = new TestLongPipeline();
        try {
            pipeline.filter(null);
            throw new AssertionError("Expected NullPointerException for null predicate");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testFilterBasicBehavior() {
        LongStream stream = LongStream.of(1L, 2L, 3L, 4L, 5L);
        LongStream filtered = stream.filter(new LongPredicate() {
            public boolean test(long value) {
                return value % 2 == 0; // keep even numbers
            }
        });
        long[] result = filtered.toArray();
        if (result.length != 2 || result[0] != 2L || result[1] != 4L) {
            throw new AssertionError("Filter did not produce expected even numbers");
        }
    }
}
