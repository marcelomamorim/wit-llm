/*
 * @test
 * @summary Tests the map method of DoublePipeline for correct behavior.
 * @run main DoublePipelineWitupTest
 */
import java.util.stream.DoubleStream;
import java.util.stream.DoublePipeline;
import java.util.function.DoubleUnaryOperator;

public class DoublePipelineWitupTest {
    public static void main(String[] args) {
        testMapWithIdentity();
        testMapWithSquare();
        testMapNullMapper();
    }

    static void testMapWithIdentity() {
        DoubleStream stream = DoubleStream.of(1.0, 2.0, 3.0);
        DoubleStream result = stream.map(x -> x);
        double[] expected = {1.0, 2.0, 3.0};
        double[] actual = result.toArray();
        assertArrayEquals(expected, actual);
    }

    static void testMapWithSquare() {
        DoubleStream stream = DoubleStream.of(1.0, 2.0, 3.0);
        DoubleStream result = stream.map(x -> x * x);
        double[] expected = {1.0, 4.0, 9.0};
        double[] actual = result.toArray();
        assertArrayEquals(expected, actual);
    }

    static void testMapNullMapper() {
        DoubleStream stream = DoubleStream.of(1.0, 2.0);
        try {
            stream.map(null);
            throw new AssertionError("Expected NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void assertArrayEquals(double[] expected, double[] actual) {
        if (expected.length != actual.length) {
            throw new AssertionError("Array lengths differ: expected " + expected.length + " but was " + actual.length);
        }
        for (int i = 0; i < expected.length; i++) {
            if (Double.compare(expected[i], actual[i]) != 0) {
                throw new AssertionError("Arrays differ at index " + i + ": expected " + expected[i] + " but was " + actual[i]);
            }
        }
    }
}
