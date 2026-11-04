/*
 * @test
 * @summary Tests DoubleStream.reduce, which reaches ReduceOps.makeDouble through public API
 * @run main ReduceOpsWitupTest
 */

import java.util.stream.DoubleStream;

public class ReduceOpsWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        // Empty stream must return the identity element
        double empty = DoubleStream.empty().reduce(7.5, (a, b) -> a + b);
        check(empty == 7.5, "empty reduce should return identity, got " + empty);

        // Simple sum of sequential elements
        double sum = DoubleStream.of(1.0, 2.0, 3.5).reduce(0.0, (a, b) -> a + b);
        check(sum == 6.5, "sum expected 6.5, got " + sum);

        // Order of application must be left-to-right in sequential streams
        double ordered = DoubleStream.of(2.0, 3.0).reduce(1.0, (a, b) -> a * 10 + b);
        check(ordered == 123.0, "ordered reduce expected 123.0, got " + ordered);

        // Identity is used even with a single element
        double single = DoubleStream.of(4.0).reduce(1.0, (a, b) -> a * b);
        check(single == 4.0, "single element reduce expected 4.0, got " + single);

        // Parallel reduction with exact integer-valued sums is deterministic
        double par = DoubleStream.of(1.0, 2.0, 3.0, 4.0).parallel().reduce(0.0, (a, b) -> a + b);
        check(par == 10.0, "parallel sum expected 10.0, got " + par);

        // Null operator must be rejected (Objects.requireNonNull in makeDouble)
        boolean npe = false;
        try {
            DoubleStream.of(1.0).reduce(0.0, null);
        } catch (NullPointerException e) {
            npe = true;
        }
        check(npe, "null operator should throw NullPointerException");
    }
}
