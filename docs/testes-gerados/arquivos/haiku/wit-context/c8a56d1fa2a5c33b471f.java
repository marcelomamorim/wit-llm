/*
 * @test
 * @summary Verifies ReferencePipeline.reduce(identity, accumulator, combiner) results and reuse-after-link/consume IllegalStateException through the public Stream API
 * @run main ReferencePipelineWitupTest
 */

import java.util.List;
import java.util.stream.Stream;

public class ReferencePipelineWitupTest {

    public static void main(String[] args) {
        testSequentialReduce();
        testEmptyStreamReturnsIdentity();
        testParallelReduceWithAssociativeOps();
        testReuseAfterIntermediateOpThrows();
        testReuseAfterTerminalOpThrows();
    }

    static void testSequentialReduce() {
        Integer sum = Stream.of(1, 2, 3)
                .reduce(0, (Integer a, Integer b) -> a + b, Integer::sum);
        check(sum == 6, "expected 6 but was " + sum);

        String joined = Stream.of("a", "b", "c")
                .reduce("", (String acc, String s) -> acc + s, (String x, String y) -> x + y);
        check("abc".equals(joined), "expected abc but was " + joined);
    }

    static void testEmptyStreamReturnsIdentity() {
        Integer r = Stream.<Integer>empty()
                .reduce(42, (Integer a, Integer b) -> a + b, Integer::sum);
        check(r == 42, "expected identity 42 but was " + r);
    }

    static void testParallelReduceWithAssociativeOps() {
        List<Integer> data = List.of(1, 2, 3, 4, 5, 6, 7, 8);
        Integer r = data.parallelStream()
                .reduce(0, (Integer a, Integer b) -> a + b, Integer::sum);
        check(r == 36, "expected 36 but was " + r);
    }

    static void testReuseAfterIntermediateOpThrows() {
        final Stream<String> s = Stream.of("x", "y");
        s.map(x -> x + "!");
        expectIllegalState(() -> s.reduce("", (String a, String b) -> a + b,
                (String a, String b) -> a + b));
    }

    static void testReuseAfterTerminalOpThrows() {
        final Stream<String> s = Stream.of("x", "y");
        s.forEach(x -> { });
        expectIllegalState(() -> s.reduce("", (String a, String b) -> a + b,
                (String a, String b) -> a + b));
    }

    static void expectIllegalState(Runnable r) {
        try {
            r.run();
        } catch (IllegalStateException e) {
            return;
        }
        throw new AssertionError("expected IllegalStateException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
