/*
 * @test
 * @summary Verifies that IntPipeline's asDoubleStream sink converts each int to double exactly once per element
 * @run main IntPipelineWitupTest
 */

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class IntPipelineWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testConversionOfSmallValues() {
        int[] inputs = {0, 1, -1, 42, -42};
        List<Double> out = new ArrayList<>();
        IntStream.of(inputs).asDoubleStream().forEach(d -> out.add(d));
        check(out.size() == inputs.length, "unexpected element count: " + out.size());
        for (int i = 0; i < inputs.length; i++) {
            double expected = (double) inputs[i];
            check(Double.compare(out.get(i), expected) == 0,
                  "expected " + expected + " but got " + out.get(i));
        }
    }

    static void testBoundaryValues() {
        int[] inputs = {Integer.MAX_VALUE, Integer.MIN_VALUE};
        List<Double> out = new ArrayList<>();
        IntStream.of(inputs).asDoubleStream().forEach(d -> out.add(d));
        check(out.size() == 2, "unexpected element count: " + out.size());
        check(out.get(0) == 2147483647.0, "MAX_VALUE converted incorrectly: " + out.get(0));
        check(out.get(1) == -2147483648.0, "MIN_VALUE converted incorrectly: " + out.get(1));
    }

    static void testEmptyStream() {
        List<Double> out = new ArrayList<>();
        IntStream.empty().asDoubleStream().forEach(d -> out.add(d));
        check(out.isEmpty(), "empty stream produced elements");
    }

    static void testOrderPreserved() {
        List<Double> out = new ArrayList<>();
        IntStream.range(0, 5).asDoubleStream().forEach(d -> out.add(d));
        check(out.size() == 5, "unexpected element count: " + out.size());
        for (int i = 0; i < 5; i++) {
            check(out.get(i) == (double) i, "order mismatch at " + i + ": " + out.get(i));
        }
    }

    public static void main(String[] args) {
        testConversionOfSmallValues();
        testBoundaryValues();
        testEmptyStream();
        testOrderPreserved();
    }
}
