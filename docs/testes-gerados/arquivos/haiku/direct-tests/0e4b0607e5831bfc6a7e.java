/*
 * @test
 * @summary Behavioral checks for DoublePipeline.mapToObj(DoubleFunction) via public DoubleStream API
 * @run main DoublePipelineWitupTest
 */

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

public class DoublePipelineWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testMapsElementsInOrder() {
        List<String> result = DoubleStream.of(1.5, 2.0, -3.25)
                .mapToObj(d -> "v" + d)
                .collect(Collectors.toList());
        check(result.equals(Arrays.asList("v1.5", "v2.0", "v-3.25")),
              "unexpected mapping result: " + result);
    }

    static void testNullMapperThrowsNpe() {
        boolean thrown = false;
        try {
            DoubleStream.of(1.0).mapToObj(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null mapper");
    }

    static void testEmptyStreamProducesEmpty() {
        List<Object> result = DoubleStream.empty()
                .mapToObj(d -> (Object) Double.valueOf(d))
                .collect(Collectors.toList());
        check(result.isEmpty(), "expected empty result for empty source");
    }

    static void testMapperIsLazy() {
        AtomicInteger calls = new AtomicInteger();
        Stream<String> s = DoubleStream.of(1.0, 2.0)
                .mapToObj(d -> {
                    calls.incrementAndGet();
                    return "x";
                });
        check(calls.get() == 0, "mapper invoked before terminal operation");
        List<String> result = s.collect(Collectors.toList());
        check(calls.get() == 2, "mapper not invoked once per element: " + calls.get());
        check(result.size() == 2, "unexpected size: " + result.size());
    }

    static void testNullResultsArePreserved() {
        List<String> result = DoubleStream.of(1.0, 2.0)
                .mapToObj(d -> (String) null)
                .collect(Collectors.toList());
        check(result.size() == 2 && result.get(0) == null && result.get(1) == null,
              "null mapper results not preserved");
    }

    static void testParallelPreservesEncounterOrder() {
        List<Double> result = DoubleStream.of(1, 2, 3, 4, 5, 6, 7, 8)
                .parallel()
                .mapToObj(d -> Double.valueOf(d * 2))
                .collect(Collectors.toList());
        List<Double> expected = Arrays.asList(2.0, 4.0, 6.0, 8.0, 10.0, 12.0, 14.0, 16.0);
        check(result.equals(expected), "parallel order not preserved: " + result);
    }

    public static void main(String[] args) {
        testMapsElementsInOrder();
        testNullMapperThrowsNpe();
        testEmptyStreamProducesEmpty();
        testMapperIsLazy();
        testNullResultsArePreserved();
        testParallelPreservesEncounterOrder();
    }
}
