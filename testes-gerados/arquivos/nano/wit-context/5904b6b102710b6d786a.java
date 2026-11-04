/*
 * @test
 * @summary Test for LongPipeline.max() method behavior.
 * @run main LongPipelineWitupTest
 */

import java.util.stream.LongPipeline;
import java.util.OptionalLong;

public class LongPipelineWitupTest {

    public static void main(String[] args) {
        testMaxWithEmptyStream();
        testMaxWithValues();
    }

    private static void testMaxWithEmptyStream() {
        LongPipeline pipeline = new LongPipeline() {
            @Override
            public java.util.Spliterator.OfLong spliterator() {
                return java.util.Spliterators.emptyLongSpliterator();
            }
        };
        OptionalLong result = pipeline.max();
        if (result.isPresent()) {
            System.err.println("Failed: Expected empty OptionalLong for empty stream.");
            System.exit(1);
        }
    }

    private static void testMaxWithValues() {
        LongPipeline pipeline = new LongPipeline() {
            @Override
            public java.util.Spliterator.OfLong spliterator() {
                return java.util.Spliterators.spliterator(new long[]{1L, 3L, 2L}, 0);
            }
        };
        OptionalLong result = pipeline.max();
        if (!result.isPresent() || result.getAsLong() != 3L) {
            System.err.println("Failed: Expected max value 3L.");
            System.exit(1);
        }
    }
}
