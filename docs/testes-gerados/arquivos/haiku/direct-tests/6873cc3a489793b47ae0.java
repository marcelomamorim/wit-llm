/*
 * @test
 * @summary Verifies public Stream.map behavior backed by ReferencePipeline.map: element transformation, null mapper rejection, laziness, and null results.
 * @run main ReferencePipelineWitupTest
 */

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReferencePipelineWitupTest {

    public static void main(String[] args) {
        testMapTransformsElements();
        testNullMapperThrowsNpe();
        testMapIsLazyUntilTerminalOperation();
        testMapAllowsNullResults();
    }

    static void testMapTransformsElements() {
        List<String> result = Stream.of(1, 2, 3)
                .map(i -> "v" + i)
                .collect(Collectors.toList());
        check(result.equals(Arrays.asList("v1", "v2", "v3")),
              "unexpected mapped result: " + result);
    }

    static void testNullMapperThrowsNpe() {
        Function<Integer, Integer> nullMapper = null;
        boolean thrown = false;
        try {
            Stream.of(1).map(nullMapper);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null mapper");
    }

    static void testMapIsLazyUntilTerminalOperation() {
        AtomicInteger calls = new AtomicInteger();
        Stream<Integer> mapped = Stream.of(1, 2).map(i -> {
            calls.incrementAndGet();
            return i * 2;
        });
        check(calls.get() == 0, "mapper invoked before terminal operation");
        List<Integer> out = mapped.collect(Collectors.toList());
        check(calls.get() == 2, "mapper call count was " + calls.get());
        check(out.equals(Arrays.asList(2, 4)), "unexpected output: " + out);
    }

    static void testMapAllowsNullResults() {
        List<String> result = Stream.of("a")
                .map(x -> (String) null)
                .collect(Collectors.toList());
        check(result.size() == 1 && result.get(0) == null,
              "null mapper result not preserved: " + result);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
