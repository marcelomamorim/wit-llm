/*
 * @test
 * @summary Verifies that DoublePipeline peek actions observe each double element before it is forwarded downstream
 * @run main DoublePipelineWitupTest
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.DoubleStream;

public class DoublePipelineWitupTest {
    public static void main(String[] args) {
        testPeekRunsBeforeDownstreamPerElement();
        testPeekIsLazyUntilTerminalOperation();
        testDownstreamFilterStillSeesPeekedElements();
        testPeekReceivesExactValue();
    }

    static void testPeekRunsBeforeDownstreamPerElement() {
        List<String> log = new ArrayList<>();
        double sum = DoubleStream.of(1.5, 2.5)
                .peek(d -> log.add("peek:" + d))
                .map(d -> {
                    log.add("map:" + d);
                    return d * 2;
                })
                .sum();
        check(sum == 8.0, "unexpected sum: " + sum);
        List<String> expected = Arrays.asList("peek:1.5", "map:1.5", "peek:2.5", "map:2.5");
        check(log.equals(expected), "unexpected order: " + log);
    }

    static void testPeekIsLazyUntilTerminalOperation() {
        List<String> log = new ArrayList<>();
        DoubleStream s = DoubleStream.of(1.0).peek(d -> log.add("x"));
        check(log.isEmpty(), "peek invoked before terminal operation");
        double sum = s.sum();
        check(sum == 1.0, "unexpected sum: " + sum);
        check(log.size() == 1, "peek not invoked exactly once: " + log.size());
    }

    static void testDownstreamFilterStillSeesPeekedElements() {
        List<Double> seen = new ArrayList<>();
        long count = DoubleStream.of(-1.0, 2.0, -3.0)
                .peek(d -> seen.add(d))
                .filter(d -> d > 0)
                .count();
        check(count == 1L, "unexpected count: " + count);
        check(seen.size() == 3, "peek did not see all elements: " + seen);
    }

    static void testPeekReceivesExactValue() {
        double[] got = new double[1];
        double total = DoubleStream.of(0.1)
                .peek(d -> got[0] = d)
                .map(d -> d)
                .sum();
        check(got[0] == 0.1, "peek received wrong value: " + got[0]);
        check(total == 0.1, "downstream received wrong value: " + total);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
