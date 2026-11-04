/*
 * @test
 * @summary Checks public DoubleStream.flatMap behavior: null mapper rejection, element expansion, null mapper results, and short-circuiting with limit.
 * @run main DoublePipelineWitupTest
 */

import java.util.Arrays;
import java.util.stream.DoubleStream;

public class DoublePipelineWitupTest {

    public static void main(String[] args) {
        testNullMapperThrowsNPE();
        testFlatMapExpandsElements();
        testNullResultTreatedAsEmpty();
        testEmptyResultStreams();
        testFlatMapWithLimit();
    }

    static void testNullMapperThrowsNPE() {
        boolean thrown = false;
        try {
            DoubleStream.of(1.0).flatMap(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "flatMap(null) must throw NullPointerException");
    }

    static void testFlatMapExpandsElements() {
        double[] actual = DoubleStream.of(1.0, 2.0)
                .flatMap(x -> DoubleStream.of(x, x * 10))
                .toArray();
        double[] expected = {1.0, 10.0, 2.0, 20.0};
        check(Arrays.equals(expected, actual),
              "unexpected flatMap result: " + Arrays.toString(actual));
    }

    static void testNullResultTreatedAsEmpty() {
        double[] actual = DoubleStream.of(1.0, 2.0, 3.0)
                .flatMap(x -> x == 2.0 ? null : DoubleStream.of(x))
                .toArray();
        double[] expected = {1.0, 3.0};
        check(Arrays.equals(expected, actual),
              "null mapper result should contribute no elements: " + Arrays.toString(actual));
    }

    static void testEmptyResultStreams() {
        double[] actual = DoubleStream.of(1.0, 2.0)
                .flatMap(x -> DoubleStream.empty())
                .toArray();
        check(actual.length == 0,
              "empty inner streams should produce no elements");
    }

    static void testFlatMapWithLimit() {
        double[] actual = DoubleStream.of(1.0, 2.0, 3.0)
                .flatMap(x -> DoubleStream.of(x, x))
                .limit(3)
                .toArray();
        double[] expected = {1.0, 1.0, 2.0};
        check(Arrays.equals(expected, actual),
              "unexpected limited flatMap result: " + Arrays.toString(actual));
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
