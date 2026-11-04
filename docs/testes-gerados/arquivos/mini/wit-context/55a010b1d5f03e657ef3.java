/*
 * @test
 * @summary Test java.util.Optional.filter(Predicate) method for null predicate and filtering behavior
 * @run main OptionalWitupTest
 */

import java.util.Optional;
import java.util.function.Predicate;

public class OptionalWitupTest {
    public static void main(String[] args) {
        testFilterWithNullPredicate();
        testFilterOnEmptyOptional();
        testFilterOnPresentValueMatchingPredicate();
        testFilterOnPresentValueNotMatchingPredicate();
    }

    private static void testFilterWithNullPredicate() {
        Optional<String> opt = Optional.of("test");
        try {
            opt.filter(null);
            throw new AssertionError("Expected NullPointerException for null predicate");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testFilterOnEmptyOptional() {
        Optional<String> emptyOpt = Optional.empty();
        Predicate<String> alwaysTrue = new Predicate<String>() {
            public boolean test(String s) {
                return true;
            }
        };
        Optional<String> result = emptyOpt.filter(alwaysTrue);
        if (result != emptyOpt) {
            throw new AssertionError("filter on empty Optional should return the same empty instance");
        }
    }

    private static void testFilterOnPresentValueMatchingPredicate() {
        Optional<String> opt = Optional.of("match");
        Predicate<String> predicate = new Predicate<String>() {
            public boolean test(String s) {
                return s.equals("match");
            }
        };
        Optional<String> result = opt.filter(predicate);
        if (!result.isPresent()) {
            throw new AssertionError("filter should return present Optional when predicate matches");
        }
        if (!result.get().equals("match")) {
            throw new AssertionError("filter returned Optional with unexpected value");
        }
    }

    private static void testFilterOnPresentValueNotMatchingPredicate() {
        Optional<String> opt = Optional.of("no-match");
        Predicate<String> predicate = new Predicate<String>() {
            public boolean test(String s) {
                return s.equals("match");
            }
        };
        Optional<String> result = opt.filter(predicate);
        if (result.isPresent()) {
            throw new AssertionError("filter should return empty Optional when predicate does not match");
        }
    }
}
