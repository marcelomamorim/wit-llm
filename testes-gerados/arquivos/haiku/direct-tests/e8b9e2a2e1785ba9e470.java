/*
 * @test
 * @summary Behavioral tests for DoublePipeline.filter via the public DoubleStream API
 * @run main DoublePipelineWitupTest
 */

import java.util.Arrays;
import java.util.stream.DoubleStream;

public class DoublePipelineWitupTest {

    public static void main(String[] args) {
        testKeepsMatchingElementsInOrder();
        testNoMatchesYieldsEmptyArray();
        testEmptySourceYieldsEmptyArray();
        testNullPredicateThrowsNullPointerException();
        testChainedFilters();
        testPredicateInvokedOncePerElement();
        testNaNIsFilteredByPredicate();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testKeepsMatchingElementsInOrder() {
        double[] result = DoubleStream.of(1.0, 2.0, 3.0, 4.0, 5.0, 6.0)
                .filter(d -> d % 2 == 0)
                .toArray();
        check(Arrays.equals(result, new double[]{2.0, 4.0, 6.0}),
              "expected [2.0, 4.0, 6.0] but got " + Arrays.toString(result));
    }

    static void testNoMatchesYieldsEmptyArray() {
        double[] result = DoubleStream.of(1.0, 3.0, 5.0)
                .filter(d -> d < 0)
                .toArray();
        check(result.length == 0,
              "expected empty array but got " + Arrays.toString(result));
    }

    static void testEmptySourceYieldsEmptyArray() {
        double[] result = DoubleStream.empty()
                .filter(d -> true)
                .toArray();
        check(result.length == 0,
              "expected empty array but got " + Arrays.toString(result));
    }

    static void testNullPredicateThrowsNullPointerException() {
        boolean thrown = false;
        try {
            DoubleStream.of(1.0).filter(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null predicate");
    }

    static void testChainedFilters() {
        double[] result = DoubleStream.of(1.0, 2.0, 3.0, 4.0)
                .filter(d -> d > 1.0)
                .filter(d -> d < 4.0)
                .toArray();
        check(Arrays.equals(result, new double[]{2.0, 3.0}),
              "expected [2.0, 3.0] but got " + Arrays.toString(result));
    }

    static void testPredicateInvokedOncePerElement() {
        final int[] calls = {0};
        double[] result = DoubleStream.of(1.0, 2.0, 3.0)
                .filter(d -> {
                    calls[0]++;
                    return true;
                })
                .toArray();
        check(calls[0] == 3, "expected 3 predicate calls but got " + calls[0]);
        check(result.length == 3, "expected 3 elements but got " + result.length);
    }

    static void testNaNIsFilteredByPredicate() {
        double[] result = DoubleStream.of(1.0, Double.NaN, 2.0)
                .filter(Double::isNaN)
                .toArray();
        check(result.length == 1 && Double.isNaN(result[0]),
              "expected single NaN but got " + Arrays.toString(result));
    }
}
