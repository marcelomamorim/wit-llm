/*
 * @test
 * @summary Test the forEach method of LongPipeline for sequential and parallel execution.
 * @run main LongPipelineWitupTest
 */

import java.util.stream.LongStream;
import java.util.stream.LongPipeline;
import java.util.function.LongConsumer;

public class LongPipelineWitupTest {

    public static void main(String[] args) {
        testForEachSequential();
        testForEachParallel();
    }

    static void testForEachSequential() {
        StringBuilder sb = new StringBuilder();
        LongStream stream = LongStream.of(1L, 2L, 3L);
        stream.sequential().forEach(new LongConsumer() {
            @Override
            public void accept(long value) {
                sb.append(value);
            }
        });
        assert sb.toString().equals("123") : "Sequential forEach did not process all elements";
    }

    static void testForEachParallel() {
        StringBuilder sb = new StringBuilder();
        LongStream stream = LongStream.of(4L, 5L, 6L);
        stream.parallel().forEach(new LongConsumer() {
            @Override
            public void accept(long value) {
                synchronized (sb) {
                    sb.append(value);
                }
            }
        });
        String result = sb.toString();
        // Since parallel forEach order is not guaranteed, check for all elements regardless of order
        assert (result.contains("4") && result.contains("5") && result.contains("6")) : "Parallel forEach did not process all elements";
    }
}
