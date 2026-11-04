/*
 * @test
 * @summary Observable behavior of sorted() on sized streams, which reaches SortedOps SizedRefSortingSink.begin for small sizes
 * @run main SortedOpsWitupTest
 */

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SortedOpsWitupTest {

    public static void main(String[] args) {
        testNaturalOrderOnSizedStream();
        testComparatorOrderOnSizedStream();
        testEmptySizedStream();
        testUpstreamNotModified();
    }

    private static void testNaturalOrderOnSizedStream() {
        List<Integer> result = Arrays.asList(3, 1, 2).stream()
                .sorted()
                .collect(Collectors.toList());
        check(Arrays.asList(1, 2, 3).equals(result), "natural order: " + result);
    }

    private static void testComparatorOrderOnSizedStream() {
        List<Integer> result = Arrays.asList(3, 1, 2).stream()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
        check(Arrays.asList(3, 2, 1).equals(result), "reverse order: " + result);
    }

    private static void testEmptySizedStream() {
        List<Integer> result = Collections.<Integer>emptyList().stream()
                .sorted()
                .collect(Collectors.toList());
        check(result.isEmpty(), "empty stream: " + result);
    }

    private static void testUpstreamNotModified() {
        List<Integer> source = Arrays.asList(5, 4, 6);
        List<Integer> result = source.stream()
                .sorted()
                .collect(Collectors.toList());
        check(Arrays.asList(4, 5, 6).equals(result), "sorted result: " + result);
        check(Arrays.asList(5, 4, 6).equals(source), "source modified: " + source);
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Assertion failed: " + message);
        }
    }
}
