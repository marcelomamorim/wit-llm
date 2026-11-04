/*
 * @test
 * @summary Verify DoubleStream.reduce(DoubleBinaryOperator) observable results and IllegalStateException for linked or consumed streams
 * @run main DoublePipelineWitupTest
 */

import java.util.OptionalDouble;
import java.util.stream.DoubleStream;

public class DoublePipelineWitupTest {

    public static void main(String[] args) {
        OptionalDouble sum = DoubleStream.of(1.0, 2.0, 3.0).reduce((a, b) -> a + b);
        check(sum.isPresent() && sum.getAsDouble() == 6.0, 1);

        OptionalDouble empty = DoubleStream.empty().reduce((a, b) -> a + b);
        check(!empty.isPresent(), 2);

        OptionalDouble single = DoubleStream.of(4.5).reduce((a, b) -> a * b);
        check(single.isPresent() && single.getAsDouble() == 4.5, 3);

        OptionalDouble max = DoubleStream.of(-1.0, 7.5, 3.0).reduce((a, b) -> a > b ? a : b);
        check(max.isPresent() && max.getAsDouble() == 7.5, 4);

        DoubleStream linked = DoubleStream.of(1.0, 2.0);
        linked.map(x -> x);
        boolean thrown = false;
        try {
            linked.reduce((a, b) -> a + b);
        } catch (IllegalStateException e) {
            thrown = true;
        }
        check(thrown, 5);

        DoubleStream consumed = DoubleStream.of(1.0, 2.0);
        consumed.reduce((a, b) -> a + b);
        thrown = false;
        try {
            consumed.reduce((a, b) -> a + b);
        } catch (IllegalStateException e) {
            thrown = true;
        }
        check(thrown, 6);
    }

    private static void check(boolean condition, int id) {
        if (!condition) {
            throw new RuntimeException("DoublePipelineWitupTest failed check " + id);
        }
    }
}
