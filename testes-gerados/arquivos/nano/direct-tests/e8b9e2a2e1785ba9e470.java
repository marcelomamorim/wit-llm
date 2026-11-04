/*
 * @test
 * @summary Tests the filter method of DoublePipeline for various predicates.
 * @run main DoublePipelineWitupTest
 */

import java.util.stream.DoubleStream;
import java.util.stream.DoublePipeline;
import java.util.function.DoublePredicate;

public class DoublePipelineWitupTest {

    public static void main(String[] args) {
        testFilterWithPositivePredicate();
        testFilterWithNegativePredicate();
        testFilterWithAlwaysTruePredicate();
        testFilterWithAlwaysFalsePredicate();
        testFilterWithNullPredicate();
    }

    static void testFilterWithPositivePredicate() {
        DoubleStream stream = DoubleStream.of(1.0, 2.0, 3.0, 4.0);
        DoublePredicate predicate = d -> d > 2.0;
        DoubleStream filtered = stream.filter(predicate);
        double[] result = filtered.toArray();
        assert result.length == 2 : "Expected 2 elements";
        assert result[0] == 3.0 : "First element should be 3.0";
        assert result[1] == 4.0 : "Second element should be 4.0";
    }

    static void testFilterWithNegativePredicate() {
        DoubleStream stream = DoubleStream.of(1.0, 2.0, 3.0, 4.0);
        DoublePredicate predicate = d -> d < 3.0;
        DoubleStream filtered = stream.filter(predicate);
        double[] result = filtered.toArray();
        assert result.length == 2 : "Expected 2 elements";
        assert result[0] == 1.0 : "First element should be 1.0";
        assert result[1] == 2.0 : "Second element should be 2.0";
    }

    static void testFilterWithAlwaysTruePredicate() {
        DoubleStream stream = DoubleStream.of(5.0, 6.0, 7.0);
        DoublePredicate predicate = d -> true;
        DoubleStream filtered = stream.filter(predicate);
        double[] result = filtered.toArray();
        assert result.length == 3 : "Expected 3 elements";
        assert result[0] == 5.0 : "First element should be 5.0";
        assert result[1] == 6.0 : "Second element should be 6.0";
        assert result[2] == 7.0 : "Third element should be 7.0";
    }

    static void testFilterWithAlwaysFalsePredicate() {
        DoubleStream stream = DoubleStream.of(8.0, 9.0, 10.0);
        DoublePredicate predicate = d -> false;
        DoubleStream filtered = stream.filter(predicate);
        double[] result = filtered.toArray();
        assert result.length == 0 : "Expected 0 elements";
    }

    static void testFilterWithNullPredicate() {
        DoubleStream stream = DoubleStream.of(1.0, 2.0);
        try {
            stream.filter(null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
