/*
 * @test
 * @summary Verifies observable element-wise ordering of LongPipeline peek stage (the anonymous Sink accept chain)
 * @run main LongPipelineWitupTest
 */

import java.util.ArrayList;
import java.util.List;
import java.util.stream.LongStream;

public class LongPipelineWitupTest {

    public static void main(String[] args) {
        testPeekRunsBeforeDownstreamPerElement();
        testPeekPreservesSumOfElements();
        testPeekShortCircuitsWithFindFirst();
        testPeekNullActionRejected();
    }

    static void testPeekRunsBeforeDownstreamPerElement() {
        List<String> log = new ArrayList<>();
        LongStream.of(1L, 2L)
                .peek(v -> log.add("p" + v))
                .forEach(v -> log.add("f" + v));
        check(log.toString().equals("[p1, f1, p2, f2]"), "unexpected order: " + log);
    }

    static void testPeekPreservesSumOfElements() {
        List<Long> seen = new ArrayList<>();
        long sum = LongStream.of(1L, 2L, 3L)
                .peek(v -> seen.add(v))
                .sum();
        check(sum == 6L, "unexpected sum: " + sum);
        check(seen.size() == 3, "peek not invoked for all elements: " + seen);
    }

    static void testPeekShortCircuitsWithFindFirst() {
        List<String> log = new ArrayList<>();
        long first = LongStream.of(10L, 20L, 30L)
                .peek(v -> log.add("p" + v))
                .findFirst()
                .getAsLong();
        check(first == 10L, "unexpected first element: " + first);
        check(log.toString().equals("[p10]"), "unexpected peek calls: " + log);
    }

    static void testPeekNullActionRejected() {
        try {
            LongStream.of(1L).peek(null);
            throw new AssertionError("expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
