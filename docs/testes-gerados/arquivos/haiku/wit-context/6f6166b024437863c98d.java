/*
 * @test
 * @summary Witup-derived checks for DoublePipeline.map null handling and basic mapping behavior
 * @run main DoublePipelineWitupTest
 */
import java.util.Arrays;
import java.util.stream.DoubleStream;

public class DoublePipelineWitupTest {

    public static void main(String[] args) {
        testNullMapperThrowsNPE();
        testMapAppliesFunction();
        testMapPreservesOrder();
        testMapOnEmptyStream();
    }

    static void testNullMapperThrowsNPE() {
        try {
            DoubleStream.of(1.0).map(null);
            throw new AssertionError("expected NullPointerException for null mapper");
        } catch (NullPointerException expected) {
        }
    }

    static void testMapAppliesFunction() {
        double[] result = DoubleStream.of(1.0, 2.0, 3.0).map(d -> d * 2).toArray();
        double[] expected = {2.0, 4.0, 6.0};
        if (!Arrays.equals(expected, result)) {
            throw new AssertionError("unexpected map result: " + Arrays.toString(result));
        }
    }

    static void testMapPreservesOrder() {
        double[] result = DoubleStream.of(3.0, 1.0, 2.0).map(d -> d + 1).toArray();
        double[] expected = {4.0, 2.0, 3.0};
        if (!Arrays.equals(expected, result)) {
            throw new AssertionError("order not preserved: " + Arrays.toString(result));
        }
    }

    static void testMapOnEmptyStream() {
        double[] result = DoubleStream.empty().map(d -> d * 3).toArray();
        if (result.length != 0) {
            throw new AssertionError("expected empty result, got length " + result.length);
        }
    }
}
