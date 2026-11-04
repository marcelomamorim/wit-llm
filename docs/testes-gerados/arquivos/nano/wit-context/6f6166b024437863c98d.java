/*
 * @test
 * @summary Tests the map method in DoublePipeline for null input handling.
 * @run main DoublePipelineWitupTest
 */

import java.util.stream.DoublePipeline;
import java.util.stream.DoubleStream;
import java.util.function.DoubleUnaryOperator;

public class DoublePipelineWitupTest {

    public static void main(String[] args) {
        testMapWithNullMapper();
        testMapWithValidMapper();
    }

    private static void testMapWithNullMapper() {
        DoubleStream stream = DoubleStream.of(1.0, 2.0, 3.0);
        try {
            stream.map(null);
            System.err.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Throwable t) {
            System.err.println("FAIL: Unexpected exception type: " + t);
        }
    }

    private static void testMapWithValidMapper() {
        DoubleStream stream = DoubleStream.of(1.0, 2.0, 3.0);
        DoubleUnaryOperator mapper = d -> d * 2;
        DoubleStream resultStream = stream.map(mapper);
        double[] expected = {2.0, 4.0, 6.0};
        double[] actual = resultStream.toArray();
        if (java.util.Arrays.equals(expected, actual)) {
            System.out.println("PASS: map with valid mapper produces expected output");
        } else {
            System.err.println("FAIL: map with valid mapper produces unexpected output: " + java.util.Arrays.toString(actual));
        }
    }
}
