/*
 * @test
 * @summary Behavioral tests for ReferencePipeline.mapToInt via public Stream API
 * @run main ReferencePipelineWitupTest
 */

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ReferencePipelineWitupTest {

    public static void main(String[] args) {
        testMapsElements();
        testNullMapperThrowsNPE();
        testLazyUntilTerminalOperation();
        testMapperCalledOncePerElement();
        testEmptyStream();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void testMapsElements() {
        int[] result = Stream.of("a", "bb", "ccc")
                             .mapToInt(String::length)
                             .toArray();
        check(Arrays.equals(result, new int[]{1, 2, 3}),
              "mapToInt did not map elements correctly: " + Arrays.toString(result));
    }

    static void testNullMapperThrowsNPE() {
        boolean thrown = false;
        try {
            Stream.of("a").mapToInt(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "mapToInt(null) should throw NullPointerException");
    }

    static void testLazyUntilTerminalOperation() {
        AtomicInteger calls = new AtomicInteger();
        IntStream s = Stream.of(1, 2, 3).mapToInt(x -> {
            calls.incrementAndGet();
            return x;
        });
        check(calls.get() == 0, "mapper should not run before terminal operation");
        int sum = s.sum();
        check(sum == 6, "unexpected sum: " + sum);
        check(calls.get() == 3, "mapper should run once per element, ran " + calls.get());
    }

    static void testMapperCalledOncePerElement() {
        AtomicInteger calls = new AtomicInteger();
        long count = Stream.of("x", "y", "z", "w")
                           .mapToInt(s -> {
                               calls.incrementAndGet();
                               return s.charAt(0);
                           })
                           .count();
        check(count == 4, "unexpected count: " + count);
        check(calls.get() == 4, "mapper call count should be 4, was " + calls.get());
    }

    static void testEmptyStream() {
        long count = Stream.<String>empty()
                           .mapToInt(String::length)
                           .count();
        check(count == 0, "empty stream mapped to non-empty result: " + count);
    }
}
