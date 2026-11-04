/*
 * @test
 * @summary Verifies observable results of sorted() operations that exercise SortedOps.begin(long) through the public stream API
 * @run main SortedOpsWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class SortedOpsWitupTest {

    static int failures = 0;

    static void check(boolean cond, String msg) {
        if (!cond) {
            failures++;
            System.err.println("FAILED: " + msg);
        }
    }

    public static void main(String[] args) {
        testNaturalOrderSizedStream();
        testReverseComparator();
        testEmptyStream();
        testUnsizedStream();
        testStabilityWithComparator();
        testPrimitiveStreams();
        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }

    static void testNaturalOrderSizedStream() {
        List<Integer> result = Arrays.asList(3, 1, 2).stream()
                .sorted()
                .collect(java.util.stream.Collectors.toList());
        check(result.equals(Arrays.asList(1, 2, 3)), "natural order sized stream: " + result);
    }

    static void testReverseComparator() {
        List<String> result = Stream.of("b", "c", "a")
                .sorted(Comparator.reverseOrder())
                .collect(java.util.stream.Collectors.toList());
        check(result.equals(Arrays.asList("c", "b", "a")), "reverse comparator: " + result);
    }

    static void testEmptyStream() {
        List<Integer> result = Collections.<Integer>emptyList().stream()
                .sorted()
                .collect(java.util.stream.Collectors.toList());
        check(result.isEmpty(), "empty stream sorted should be empty: " + result);
    }

    static void testUnsizedStream() {
        List<Integer> result = Stream.of(9, 7, 8)
                .filter(x -> x > 0)
                .sorted()
                .collect(java.util.stream.Collectors.toList());
        check(result.equals(Arrays.asList(7, 8, 9)), "unsized stream sorted: " + result);
    }

    static void testStabilityWithComparator() {
        List<String> result = Stream.of("bb", "a", "cc", "d")
                .sorted(Comparator.comparingInt(String::length))
                .collect(java.util.stream.Collectors.toList());
        check(result.equals(Arrays.asList("a", "d", "bb", "cc")),
              "stable comparator sort: " + result);
    }

    static void testPrimitiveStreams() {
        int[] ints = IntStream.of(5, 4, 6).sorted().toArray();
        check(Arrays.equals(ints, new int[] {4, 5, 6}), "IntStream sorted: " + Arrays.toString(ints));

        long[] longs = LongStream.of(3L, 1L, 2L).sorted().toArray();
        check(Arrays.equals(longs, new long[] {1L, 2L, 3L}), "LongStream sorted: " + Arrays.toString(longs));

        double[] doubles = DoubleStream.of(2.5, -1.0, 0.5).sorted().toArray();
        check(Arrays.equals(doubles, new double[] {-1.0, 0.5, 2.5}),
              "DoubleStream sorted: " + Arrays.toString(doubles));

        int[] emptyInts = IntStream.empty().sorted().toArray();
        check(emptyInts.length == 0, "empty IntStream sorted should be empty");
    }
}
