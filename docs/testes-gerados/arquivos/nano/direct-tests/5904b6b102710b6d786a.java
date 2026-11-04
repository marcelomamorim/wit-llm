/*
 * @test
 * @summary Test the max() method of LongPipeline for correct behavior.
 * @run main LongPipelineWitupTest
 */

import java.util.OptionalLong;
import java.util.stream.LongStream;

public class LongPipelineWitupTest {

    public static void main(String[] args) {
        testMaxWithValues();
        testMaxWithEmptyStream();
    }

    static void testMaxWithValues() {
        LongStream stream = LongStream.of(1L, 3L, 2L);
        OptionalLong result = stream.max();
        if (!result.isPresent()) {
            throw new AssertionError("Expected present OptionalLong");
        }
        if (result.getAsLong() != 3L) {
            throw new AssertionError("Expected max to be 3, but was " + result.getAsLong());
        }
    }

    static void testMaxWithEmptyStream() {
        LongStream emptyStream = LongStream.empty();
        OptionalLong result = emptyStream.max();
        if (result.isPresent()) {
            throw new AssertionError("Expected empty OptionalLong for empty stream");
        }
    }
}
