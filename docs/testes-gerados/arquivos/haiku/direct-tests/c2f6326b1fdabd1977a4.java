/*
 * @test
 * @summary Verifies observable sorting behavior of the sorted() operations that reach SortedOps.begin(long) through the public stream API
 * @run main SortedOpsWitupTest
 */

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class SortedOpsWitupTest {

    public static void main(String[] args) {
        testRefNaturalOrder();
        testRefWithComparator();
        testRefEmpty();
        testIntSorted();
        testLongSorted();
        testDoubleSorted();
    }

    static void testRefNaturalOrder() {
        List<Integer> result = Stream.of(3, 1, 2).sorted().collect(Collectors.toList());
        check(result.equals(Arrays.asList(1, 2, 3)), "natural order failed: " + result);
    }

    static void testRefWithComparator() {
        List<Integer> result = Stream.of(3, 1, 2)
                .sorted(Collections.reverseOrder())
                .collect(Collectors.toList());
        check(result.equals(Arrays.asList(3, 2, 1)), "comparator order failed: " + result);
    }

    static void testRefEmpty() {
        long count = Stream.<String>empty().sorted().count();
        check(count == 0L, "empty sorted count expected 0 but was " + count);
    }

    static void testIntSorted() {
        int[] result = IntStream.of(5, 4, 6).sorted().toArray();
        check(Arrays.equals(result, new int[]{4, 5, 6}), "int sorted failed: " + Arrays.toString(result));
    }

    static void testLongSorted() {
        long[] result = LongStream.of(9L, -1L, 3L).sorted().toArray();
        check(Arrays.equals(result, new long[]{-1L, 3L, 9L}), "long sorted failed: " + Arrays.toString(result));
    }

    static void testDoubleSorted() {
        double[] result = DoubleStream.of(2.5, -0.5, 1.0).sorted().toArray();
        check(Arrays.equals(result, new double[]{-0.5, 1.0, 2.5}), "double sorted failed: " + Arrays.toString(result));
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
