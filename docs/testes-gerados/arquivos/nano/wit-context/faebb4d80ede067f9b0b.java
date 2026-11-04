/*
 * @test
 * @summary Tests the flatMap method in DoublePipeline for null input.
 * @run main DoublePipelineWitupTest
 */

import java.util.stream.DoubleStream;
import java.util.function.DoubleFunction;

public class DoublePipelineWitupTest {

    public static void main(String[] args) {
        testFlatMapWithNullMapper();
        testFlatMapWithValidMapper();
    }

    private static void testFlatMapWithNullMapper() {
        DoubleStream stream = DoubleStream.of(1.0, 2.0, 3.0);
        try {
            stream.flatMap(null);
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    private static void testFlatMapWithValidMapper() {
        DoubleStream stream = DoubleStream.of(1.0, 2.0);
        DoubleFunction<? extends DoubleStream> mapper = d -> DoubleStream.of(d, d * 10);
        DoubleStream resultStream = stream.flatMap(mapper);
        double[] result = resultStream.toArray();
        if (result.length == 4 && result[0] == 1.0 && result[1] == 10.0 && result[2] == 2.0 && result[3] == 20.0) {
            System.out.println("PASS: flatMap with valid mapper produces expected output");
        } else {
            System.out.println("FAIL: flatMap with valid mapper produces unexpected output");
        }
    }
}
