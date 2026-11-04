/*
 * @test
 * @summary Test java.util.Optional filter(Predicate) method behavior
 * @run main OptionalWitupTest
 */

import java.util.Optional;
import java.util.Objects;
import java.util.function.Predicate;

public class OptionalWitupTest {
    public static void main(String[] args) {
        testFilterWithNullPredicate();
        testFilterOnEmptyOptionalReturnsSameInstance();
        testFilterOnPresentValuePredicateTrueReturnsSameInstance();
        testFilterOnPresentValuePredicateFalseReturnsEmpty();
    }

    private static void testFilterWithNullPredicate() {
        Optional<String> opt = Optional.of("test");
        try {
            opt.filter(null);
            throw new AssertionError("Expected NullPointerException for null predicate");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testFilterOnEmptyOptionalReturnsSameInstance() {
        Optional<String> emptyOpt = Optional.empty();
        Optional<String> filtered = emptyOpt.filter(s -> false);
        if (filtered != emptyOpt) {
            throw new AssertionError("filter on empty Optional should return same instance");
        }
    }

    private static void testFilterOnPresentValuePredicateTrueReturnsSameInstance() {
        Optional<String> opt = Optional.of("value");
        Optional<String> filtered = opt.filter(new Predicate<String>() {
            public boolean test(String s) {
                return s.equals("value");
            }
        });
        if (filtered != opt) {
            throw new AssertionError("filter with predicate true should return same instance");
        }
    }

    private static void testFilterOnPresentValuePredicateFalseReturnsEmpty() {
        Optional<String> opt = Optional.of("value");
        Optional<String> filtered = opt.filter(new Predicate<String>() {
            public boolean test(String s) {
                return false;
            }
        });
        if (filtered.isPresent()) {
            throw new AssertionError("filter with predicate false should return empty Optional");
        }
    }
}
