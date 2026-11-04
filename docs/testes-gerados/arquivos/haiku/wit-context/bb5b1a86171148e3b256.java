/*
 * @test
 * @summary Verifies LongPipeline.map null-mapper rejection and mapping behavior through public LongStream API
 * @run main LongPipelineWitupTest
 */

import java.util.Arrays;
import java.util.function.LongUnaryOperator;
import java.util.stream.LongStream;

public class LongPipelineWitupTest {

    public static void main(String[] args) {
        testNullMapperThrowsNullPointerException();
        testMapAppliesMapperToEachElement();
        testMapOnEmptyStreamProducesEmptyResult();
    }

    static void testNullMapperThrowsNullPointerException() {
        LongStream source = LongStream.of(1L, 2L);
        boolean thrown = false;
        try {
            source.map(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("map(null) did not throw NullPointerException");
        }
    }

    static void testMapAppliesMapperToEachElement() {
        LongUnaryOperator doubler = x -> x * 2L;
        long[] result = LongStream.of(1L, 2L, 3L).map(doubler).toArray();
        long[] expected = {2L, 4L, 6L};
        if (!Arrays.equals(expected, result)) {
            throw new RuntimeException("unexpected mapped values: " + Arrays.toString(result));
        }
    }

    static void testMapOnEmptyStreamProducesEmptyResult() {
        LongUnaryOperator increment = x -> x + 1L;
        long[] result = LongStream.empty().map(increment).toArray();
        if (result.length != 0) {
            throw new RuntimeException("expected empty result but got length " + result.length);
        }
    }
}
