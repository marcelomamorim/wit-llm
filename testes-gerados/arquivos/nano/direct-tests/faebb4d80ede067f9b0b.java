/*
 * @test
 * @summary Tests the flatMap method of DoublePipeline for correct behavior and regressions
 * @run main DoublePipelineWitupTest
 */

import java.util.stream.DoubleStream;
import java.util.stream.DoubleFunction;
import java.util.stream.Stream;

public class DoublePipelineWitupTest {

    public static void main(String[] args) {
        testFlatMapWithNullMapper();
        testFlatMapWithEmptyStream();
        testFlatMapWithSingleElement();
        testFlatMapWithMultipleElements();
    }

    static void testFlatMapWithNullMapper() {
        DoubleStream stream = DoubleStream.of(1.0, 2.0);
        try {
            stream.flatMap(null);
            throw new AssertionError("Expected NullPointerException for null mapper");
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testFlatMapWithEmptyStream() {
        DoubleStream emptyStream = DoubleStream.empty();
        DoubleFunction<? extends DoubleStream> mapper = t -> DoubleStream.of(t, t + 1);
        DoubleStream result = emptyStream.flatMap(mapper);
        if (result.count() != 0) {
            throw new AssertionError("Expected empty stream from flatMap on empty input");
        }
    }

    static void testFlatMapWithSingleElement() {
        DoubleStream stream = DoubleStream.of(5.0);
        DoubleFunction<? extends DoubleStream> mapper = t -> DoubleStream.of(t, t + 10);
        DoubleStream result = stream.flatMap(mapper);
        double[] expected = {5.0, 15.0};
        double[] actual = result.toArray();
        if (actual.length != expected.length) {
            throw new AssertionError("Unexpected length: " + actual.length);
        }
        for (int i = 0; i < expected.length; i++) {
            if (actual[i] != expected[i]) {
                throw new AssertionError("Mismatch at index " + i + ": expected " + expected[i] + " but got " + actual[i]);
            }
        }
    }

    static void testFlatMapWithMultipleElements() {
        DoubleStream stream = DoubleStream.of(1.0, 2.0);
        DoubleFunction<? extends DoubleStream> mapper = t -> DoubleStream.of(t, t + 10);
        DoubleStream result = stream.flatMap(mapper);
        double[] expected = {1.0, 11.0, 2.0, 12.0};
        double[] actual = result.toArray();
        if (actual.length != expected.length) {
            throw new AssertionError("Unexpected length: " + actual.length);
        }
        for (int i = 0; i < expected.length; i++) {
            if (actual[i] != expected[i]) {
                throw new AssertionError("Mismatch at index " + i + ": expected " + expected[i] + " but got " + actual[i]);
            }
        }
    }
}
