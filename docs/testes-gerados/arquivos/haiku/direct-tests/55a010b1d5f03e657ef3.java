/*
 * @test
 * @summary Witup-style tests for Optional.filter behavior
 * @run main OptionalWitupTest
 */
import java.util.Optional;
import java.util.function.Predicate;

public class OptionalWitupTest {
    public static void main(String[] args) {
        testNullPredicateOnEmptyThrowsNpe();
        testNullPredicateOnPresentThrowsNpe();
        testFilterOnEmptyReturnsSameInstance();
        testFilterOnEmptyDoesNotInvokePredicate();
        testPredicateTrueReturnsSameInstance();
        testPredicateFalseReturnsEmpty();
        testPredicateReceivesContainedValue();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullPredicateOnEmptyThrowsNpe() {
        try {
            Optional.<String>empty().filter(null);
            throw new RuntimeException("expected NullPointerException for null predicate on empty");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testNullPredicateOnPresentThrowsNpe() {
        try {
            Optional.of("x").filter(null);
            throw new RuntimeException("expected NullPointerException for null predicate on present");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testFilterOnEmptyReturnsSameInstance() {
        Optional<String> empty = Optional.empty();
        Predicate<String> p = s -> true;
        Optional<String> result = empty.filter(p);
        check(result == empty, "filter on empty should return this");
        check(!result.isPresent(), "result of filter on empty should be empty");
    }

    static void testFilterOnEmptyDoesNotInvokePredicate() {
        final boolean[] invoked = new boolean[1];
        Predicate<String> p = s -> {
            invoked[0] = true;
            return true;
        };
        Optional<String> result = Optional.<String>empty().filter(p);
        check(!invoked[0], "predicate must not be invoked for empty Optional");
        check(!result.isPresent(), "result should be empty");
    }

    static void testPredicateTrueReturnsSameInstance() {
        Optional<String> opt = Optional.of("abc");
        Optional<String> result = opt.filter(s -> true);
        check(result == opt, "filter with true predicate should return this");
        check(result.isPresent(), "value should remain present");
        check(result.get().equals("abc"), "value should be unchanged");
    }

    static void testPredicateFalseReturnsEmpty() {
        Optional<String> opt = Optional.of("abc");
        Optional<String> result = opt.filter(s -> false);
        check(!result.isPresent(), "filter with false predicate should return empty");
    }

    static void testPredicateReceivesContainedValue() {
        final String[] seen = new String[1];
        Optional<String> opt = Optional.of("value");
        opt.filter(s -> {
            seen[0] = s;
            return true;
        });
        check("value".equals(seen[0]), "predicate should receive the contained value");
    }
}
