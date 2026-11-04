/*
 * @test
 * @summary Test ReferencePipeline.filter method behavior
 * @run main ReferencePipelineWitupTest
 */

import java.util.stream.Stream;
import java.util.function.Predicate;
import java.util.Objects;

public class ReferencePipelineWitupTest {
    public static void main(String[] args) {
        testFilterWithNullPredicate();
        testFilterWithAlwaysTruePredicate();
        testFilterWithAlwaysFalsePredicate();
        testFilterWithSelectivePredicate();
    }

    static void testFilterWithNullPredicate() {
        Stream<String> stream = Stream.of("a", "b");
        try {
            stream.filter(null);
            throw new AssertionError("Expected NullPointerException for null predicate");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testFilterWithAlwaysTruePredicate() {
        Stream<String> stream = Stream.of("a", "b", "c");
        Predicate<String> alwaysTrue = new Predicate<String>() {
            public boolean test(String s) { return true; }
        };
        Object[] result = stream.filter(alwaysTrue).toArray();
        if (result.length != 3 || !"a".equals(result[0]) || !"b".equals(result[1]) || !"c".equals(result[2])) {
            throw new AssertionError("Filter with always true predicate should return all elements");
        }
    }

    static void testFilterWithAlwaysFalsePredicate() {
        Stream<String> stream = Stream.of("a", "b", "c");
        Predicate<String> alwaysFalse = new Predicate<String>() {
            public boolean test(String s) { return false; }
        };
        Object[] result = stream.filter(alwaysFalse).toArray();
        if (result.length != 0) {
            throw new AssertionError("Filter with always false predicate should return no elements");
        }
    }

    static void testFilterWithSelectivePredicate() {
        Stream<String> stream = Stream.of("a", "b", "c", "d");
        Predicate<String> selectBAndD = new Predicate<String>() {
            public boolean test(String s) { return "b".equals(s) || "d".equals(s); }
        };
        Object[] result = stream.filter(selectBAndD).toArray();
        if (result.length != 2 || !"b".equals(result[0]) || !"d".equals(result[1])) {
            throw new AssertionError("Filter should return only elements matching predicate");
        }
    }
}
