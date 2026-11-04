/*
 * @test
 * @summary Test for LongPipeline.min() method behavior.
 * @run main LongPipelineWitupTest
 */

import java.util.stream.LongPipeline;
import java.util.OptionalLong;

public class LongPipelineWitupTest {

    public static void main(String[] args) {
        testMinWithEmptyStream();
        testMinWithValues();
        testMinWithNull();
    }

    private static void testMinWithEmptyStream() {
        try {
            LongPipeline stream = LongPipeline.empty();
            OptionalLong result = stream.min();
            if (result.isPresent()) {
                throw new AssertionError("Expected empty OptionalLong for empty stream, but got: " + result);
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for empty stream: " + e);
        }
    }

    private static void testMinWithValues() {
        try {
            LongPipeline stream = LongPipeline.of(new long[]{3, 1, 4, 1, 5});
            OptionalLong result = stream.min();
            if (!result.isPresent() || result.getAsLong() != 1L) {
                throw new AssertionError("Expected min to be 1, but got: " + result);
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for stream with values: " + e);
        }
    }

    private static void testMinWithNull() {
        try {
            LongPipeline stream = null;
            stream.min();
            throw new AssertionError("Expected NullPointerException for null stream");
        } catch (NullPointerException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for null stream: " + e);
        }
    }
}
