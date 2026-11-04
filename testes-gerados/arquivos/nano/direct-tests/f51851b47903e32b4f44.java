/*
 * @test
 * @summary Test the min() method of LongPipeline for correct behavior.
 * @run main LongPipelineWitupTest
 */

import java.util.OptionalLong;
import java.util.stream.LongStream;

public class LongPipelineWitupTest {

    public static void main(String[] args) {
        testMinWithValues();
        testMinWithEmptyStream();
    }

    static void testMinWithValues() {
        LongStream stream = LongStream.of(5L, 3L, 8L, 2L, 7L);
        OptionalLong result = stream.min();
        if (!result.isPresent()) {
            throw new AssertionError("Expected present OptionalLong");
        }
        if (result.getAsLong() != 2L) {
            throw new AssertionError("Expected min to be 2, but was " + result.getAsLong());
        }
    }

    static void testMinWithEmptyStream() {
        LongStream emptyStream = LongStream.empty();
        OptionalLong result = emptyStream.min();
        if (result.isPresent()) {
            throw new AssertionError("Expected empty OptionalLong");
        }
    }
}
