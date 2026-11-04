/*
 * @test
 * @summary Verifies IntPipeline mapToDouble stage (downstream accept with applyAsDouble) through public API
 * @run main IntPipelineWitupTest
 */

import java.util.Arrays;
import java.util.stream.IntStream;

public class IntPipelineWitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testMapperAppliedInOrder() {
        double[] r = IntStream.of(1, 2, 3).mapToDouble(i -> i * 1.5).toArray();
        check(Arrays.equals(r, new double[]{1.5, 3.0, 4.5}),
              "unexpected values: " + Arrays.toString(r));
    }

    static void testMapperInvokedOncePerElement() {
        int[] calls = new int[1];
        double[] r = IntStream.of(10, 20, 30, 40).mapToDouble(i -> {
            calls[0]++;
            return i / 10.0;
        }).toArray();
        check(calls[0] == 4, "mapper calls: " + calls[0]);
        check(Arrays.equals(r, new double[]{1.0, 2.0, 3.0, 4.0}),
              "unexpected values: " + Arrays.toString(r));
    }

    static void testEmptyStream() {
        double[] r = IntStream.empty().mapToDouble(i -> i * 2.0).toArray();
        check(r.length == 0, "expected empty array, got length " + r.length);
    }

    static void testBoundaryValues() {
        double[] r = IntStream.of(Integer.MIN_VALUE, -1, 0, Integer.MAX_VALUE)
                .mapToDouble(i -> (double) i).toArray();
        check(r[0] == -2147483648.0, "min value: " + r[0]);
        check(r[1] == -1.0, "neg one: " + r[1]);
        check(r[2] == 0.0, "zero: " + r[2]);
        check(r[3] == 2147483647.0, "max value: " + r[3]);
    }

    static void testDownstreamSum() {
        double sum = IntStream.rangeClosed(1, 4).mapToDouble(i -> i * 0.5).sum();
        check(sum == 5.0, "sum: " + sum);
    }

    public static void main(String[] args) {
        testMapperAppliedInOrder();
        testMapperInvokedOncePerElement();
        testEmptyStream();
        testBoundaryValues();
        testDownstreamSum();
    }
}
