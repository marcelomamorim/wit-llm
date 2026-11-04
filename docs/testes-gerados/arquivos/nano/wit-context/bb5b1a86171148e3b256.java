/*
 * @test
 * @summary Tests the map method of LongPipeline for null input handling.
 * @run main LongPipelineWitupTest
 */

import java.util.stream.LongStream;
import java.util.function.LongUnaryOperator;

public class LongPipelineWitupTest {

    public static void main(String[] args) {
        testMapWithNullMapper();
    }

    public static void testMapWithNullMapper() {
        LongStream stream = LongStream.of(1L, 2L, 3L);
        try {
            stream.map(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected exception, test passes
        }
    }
}
