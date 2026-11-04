/*
 * @test
 * @summary Witup-derived behavioral tests for Optional.filter(Predicate) null-check and predicate semantics
 * @run main OptionalWitupTest
 */

import java.util.Optional;

public class OptionalWitupTest {
    public static void main(String[] args) {
        testNullPredicateOnPresentThrowsNPE();
        testNullPredicateOnEmptyThrowsNPE();
        testPresentMatchingReturnsSameInstance();
        testPresentNonMatchingReturnsEmpty();
        testEmptyReturnsThisWithoutCallingPredicate();
        testPredicateReceivesContainedValue();
    }

    static void testNullPredicateOnPresentThrowsNPE() {
        Optional<String> opt = Optional.of("x");
        try {
            opt.filter(null);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null predicate on present value");
    }

    static void testNullPredicateOnEmptyThrowsNPE() {
        Optional<String> opt = Optional.empty();
        try {
            opt.filter(null);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null predicate on empty");
    }

    static void testPresentMatchingReturnsSameInstance() {
        Optional<String> opt = Optional.of("x");
        Optional<String> r = opt.filter(s -> true);
        if (r != opt) {
            throw new RuntimeException("expected same instance when predicate matches");
        }
    }

    static void testPresentNonMatchingReturnsEmpty() {
        Optional<String> opt = Optional.of("x");
        Optional<String> r = opt.filter(s -> false);
        if (r.isPresent()) {
            throw new RuntimeException("expected empty result when predicate does not match");
        }
    }

    static void testEmptyReturnsThisWithoutCallingPredicate() {
        Optional<String> opt = Optional.empty();
        int[] calls = {0};
        Optional<String> r = opt.filter(s -> {
            calls[0]++;
            return true;
        });
        if (r != opt) {
            throw new RuntimeException("expected this for empty Optional");
        }
        if (calls[0] != 0) {
            throw new RuntimeException("predicate must not be called for empty Optional");
        }
    }

    static void testPredicateReceivesContainedValue() {
        Optional<String> opt = Optional.of("abc");
        if (!opt.filter(s -> "abc".equals(s)).isPresent()) {
            throw new RuntimeException("predicate should receive the contained value");
        }
        if (opt.filter(s -> "other".equals(s)).isPresent()) {
            throw new RuntimeException("predicate result should control presence");
        }
    }
}
