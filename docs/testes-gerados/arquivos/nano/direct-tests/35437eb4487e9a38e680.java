/*
 * @test
 * @summary Tests the filter method of ReferencePipeline for various predicates.
 * @run main ReferencePipelineWitupTest
 */

import java.util.stream.ReferencePipeline;
import java.util.stream.Stream;
import java.util.function.Predicate;

public class ReferencePipelineWitupTest {

    public static void main(String[] args) {
        testFilterWithAlwaysTruePredicate();
        testFilterWithAlwaysFalsePredicate();
        testFilterWithNullPredicate();
        testFilterWithPredicateFilteringOddNumbers();
    }

    static void testFilterWithAlwaysTruePredicate() {
        Stream<Integer> stream = Stream.of(1, 2, 3);
        Stream<Integer> filtered = stream.filter(x -> true);
        Object[] result = filtered.toArray();
        assert result.length == 3 : "Expected 3 elements";
        assert result[0].equals(1) : "First element should be 1";
        assert result[1].equals(2) : "Second element should be 2";
        assert result[2].equals(3) : "Third element should be 3";
    }

    static void testFilterWithAlwaysFalsePredicate() {
        Stream<Integer> stream = Stream.of(1, 2, 3);
        Stream<Integer> filtered = stream.filter(x -> false);
        Object[] result = filtered.toArray();
        assert result.length == 0 : "Expected 0 elements";
    }

    static void testFilterWithNullPredicate() {
        boolean exceptionThrown = false;
        try {
            Stream<Integer> stream = Stream.of(1, 2, 3);
            stream.filter(null).toArray();
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "filter(null) should throw NullPointerException";
    }

    static void testFilterWithPredicateFilteringOddNumbers() {
        Stream<Integer> stream = Stream.of(1, 2, 3, 4, 5);
        Stream<Integer> filtered = stream.filter(x -> x % 2 != 0);
        Object[] result = filtered.toArray();
        assert result.length == 3 : "Expected 3 elements";
        assert result[0].equals(1) : "First element should be 1";
        assert result[1].equals(3) : "Second element should be 3";
        assert result[2].equals(5) : "Third element should be 5";
    }
}
