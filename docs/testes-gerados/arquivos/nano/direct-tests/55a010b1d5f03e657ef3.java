/* @test    @summary Test Optional.filter method for various predicates    @run main OptionalWitupTest */

import java.util.Optional;
import java.util.function.Predicate;

public class OptionalWitupTest {

    public static void main(String[] args) {
        testFilterWithMatchingPredicate();
        testFilterWithNonMatchingPredicate();
        testFilterOnEmptyOptional();
    }

    static void testFilterWithMatchingPredicate() {
        Optional<Integer> opt = Optional.of(10);
        Optional<Integer> result = opt.filter(x -> x > 5);
        if (!result.isPresent() || result.get() != 10) {
            throw new AssertionError("Expected Optional containing 10");
        }
    }

    static void testFilterWithNonMatchingPredicate() {
        Optional<Integer> opt = Optional.of(10);
        Optional<Integer> result = opt.filter(x -> x < 5);
        if (result.isPresent()) {
            throw new AssertionError("Expected empty Optional");
        }
    }

    static void testFilterOnEmptyOptional() {
        Optional<Integer> emptyOpt = Optional.empty();
        Optional<Integer> result = emptyOpt.filter(x -> true);
        if (result.isPresent()) {
            throw new AssertionError("Expected empty Optional");
        }
    }
}
