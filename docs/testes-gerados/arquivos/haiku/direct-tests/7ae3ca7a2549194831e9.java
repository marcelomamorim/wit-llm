/*
 * @test
 * @summary Verifies LongPipeline.mapToObj through the public LongStream API: element mapping order, empty streams, null results, and NullPointerException for a null mapper.
 * @run main LongPipelineWitupTest
 */

import java.util.Arrays;
import java.util.List;
import java.util.function.LongFunction;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class LongPipelineWitupTest {

    public static void main(String[] args) {
        testMapsElementsInOrder();
        testEmptyStreamProducesEmptyResult();
        testNullResultsArePreserved();
        testNullMapperThrowsNullPointerException();
    }

    static void testMapsElementsInOrder() {
        List<String> result = LongStream.of(1L, 2L, 3L)
                .mapToObj(x -> "v" + x)
                .collect(Collectors.toList());
        check(Arrays.asList("v1", "v2", "v3").equals(result),
              "expected [v1, v2, v3] but got " + result);
    }

    static void testEmptyStreamProducesEmptyResult() {
        List<String> result = LongStream.empty()
                .mapToObj(x -> "v" + x)
                .collect(Collectors.toList());
        check(result.isEmpty(), "expected empty result but got " + result);
    }

    static void testNullResultsArePreserved() {
        List<String> result = LongStream.of(1L, 2L, 3L)
                .mapToObj(x -> x == 2L ? null : "v" + x)
                .collect(Collectors.toList());
        check(Arrays.asList("v1", null, "v3").equals(result),
              "expected [v1, null, v3] but got " + result);
    }

    static void testNullMapperThrowsNullPointerException() {
        LongFunction<String> nullMapper = null;
        boolean thrown = false;
        try {
            LongStream.of(1L).mapToObj(nullMapper);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null mapper");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
