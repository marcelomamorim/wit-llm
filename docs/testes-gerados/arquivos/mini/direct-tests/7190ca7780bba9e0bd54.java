/*
 * @test
 * @summary Test LongPipeline.forEach(LongConsumer) with simple and edge cases
 * @run main LongPipelineWitupTest
 */

import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.LongStream;
import java.util.function.LongConsumer;

public class LongPipelineWitupTest {

    public static void main(String[] args) {
        testForEachSum();
        testForEachEmpty();
        testForEachSideEffect();
    }

    private static void testForEachSum() {
        final AtomicLong sum = new AtomicLong();
        LongStream stream = LongStream.of(1L, 2L, 3L, 4L, 5L);
        stream.forEach(new LongConsumer() {
            public void accept(long value) {
                sum.addAndGet(value);
            }
        });
        if (sum.get() != 15L) {
            throw new AssertionError("Sum should be 15 but was " + sum.get());
        }
    }

    private static void testForEachEmpty() {
        final AtomicLong count = new AtomicLong();
        LongStream emptyStream = LongStream.empty();
        emptyStream.forEach(new LongConsumer() {
            public void accept(long value) {
                count.incrementAndGet();
            }
        });
        if (count.get() != 0L) {
            throw new AssertionError("Count should be 0 for empty stream but was " + count.get());
        }
    }

    private static void testForEachSideEffect() {
        final StringBuilder sb = new StringBuilder();
        LongStream stream = LongStream.of(10L, 20L);
        stream.forEach(new LongConsumer() {
            public void accept(long value) {
                sb.append(value).append(",");
            }
        });
        String result = sb.toString();
        if (!result.equals("10,20,")) {
            throw new AssertionError("Expected '10,20,' but got '" + result + "'");
        }
    }
}
