/*
 * @test
 * @summary Behavioral tests for ReferencePipeline.flatMap through the public Stream API
 * @run main ReferencePipelineWitupTest
 */

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReferencePipelineWitupTest {

    public static void main(String[] args) {
        testNullMapperThrowsNPE();
        testBasicFlatMap();
        testNullInnerStreamIsSkipped();
        testShortCircuitWithInfiniteInnerStream();
        testInnerStreamIsClosed();
        System.out.println("PASS");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullMapperThrowsNPE() {
        try {
            Stream.of(1, 2).flatMap(null);
            throw new RuntimeException("expected NullPointerException for null mapper");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testBasicFlatMap() {
        List<Integer> result = Stream.of(1, 2)
                .flatMap(i -> Stream.of(i, i * 10))
                .collect(Collectors.toList());
        check(result.equals(Arrays.asList(1, 10, 2, 20)),
              "unexpected flatMap result: " + result);
    }

    static void testNullInnerStreamIsSkipped() {
        List<Integer> result = Stream.of(1, 2, 3)
                .flatMap(i -> i == 2 ? null : Stream.of(i))
                .collect(Collectors.toList());
        check(result.equals(Arrays.asList(1, 3)),
              "null inner stream should contribute no elements: " + result);
    }

    static void testShortCircuitWithInfiniteInnerStream() {
        List<Integer> result = Stream.of(1)
                .flatMap(i -> Stream.iterate(i, x -> x + 1))
                .limit(3)
                .collect(Collectors.toList());
        check(result.equals(Arrays.asList(1, 2, 3)),
              "unexpected short-circuit result: " + result);
    }

    static void testInnerStreamIsClosed() {
        boolean[] closed = {false};
        Stream<Integer> inner = Stream.of(1, 2).onClose(() -> closed[0] = true);
        long count = Stream.of(0)
                .flatMap(i -> inner)
                .count();
        check(count == 2, "unexpected element count: " + count);
        check(closed[0], "inner stream returned by mapper was not closed");
    }
}
