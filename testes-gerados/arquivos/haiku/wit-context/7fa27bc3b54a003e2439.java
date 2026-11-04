/*
 * @test
 * @summary Tests DoubleStream.generate null handling and the values it produces
 * @run main DoubleStreamWitupTest
 */

import java.util.Arrays;

public class DoubleStreamWitupTest {

    public static void main(String[] args) {
        testNullSupplierThrowsNPE();
        testConstantSupplier();
        testSequentialSupplierOrder();
        testSumOfGeneratedValues();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullSupplierThrowsNPE() {
        try {
            DoubleStream.generate(null);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null supplier");
    }

    static void testConstantSupplier() {
        double[] result = DoubleStream.generate(() -> 1.5).limit(3).toArray();
        check(Arrays.equals(result, new double[]{1.5, 1.5, 1.5}), "constant supplier values differ");
    }

    static void testSequentialSupplierOrder() {
        double[] counter = {0.0};
        double[] result = DoubleStream.generate(() -> {
            counter[0] += 1.0;
            return counter[0];
        }).limit(4).toArray();
        check(Arrays.equals(result, new double[]{1.0, 2.0, 3.0, 4.0}),
              "sequential supplier order differs");
    }

    static void testSumOfGeneratedValues() {
        double sum = DoubleStream.generate(() -> 2.0).limit(5).sum();
        check(sum == 10.0, "sum of generated values differs");
    }
}
