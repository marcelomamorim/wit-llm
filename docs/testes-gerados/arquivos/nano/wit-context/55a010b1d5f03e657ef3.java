/*
 * @test
 * @summary Test for Optional.filter() method, focusing on null predicate handling.
 * @run main OptionalWitupTest
 */

import java.util.Optional;
import java.util.function.Predicate;

public class OptionalWitupTest {

    public static void main(String[] args) {
        testFilterWithNullPredicate();
        testFilterWithNonNullPredicateAndValue();
        testFilterWithNonNullPredicateAndEmpty();
    }

    private static void testFilterWithNullPredicate() {
        Optional<String> opt = Optional.of("test");
        try {
            opt.filter(null);
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    private static void testFilterWithNonNullPredicateAndValue() {
        Optional<String> opt = Optional.of("hello");
        Predicate<String> predicate = s -> s.startsWith("h");
        Optional<String> result = opt.filter(predicate);
        if (result.isPresent() && result.get().equals("hello")) {
            System.out.println("PASS: filter with predicate true returns same Optional");
        } else {
            System.out.println("FAIL: filter with predicate true did not return expected Optional");
        }
    }

    private static void testFilterWithNonNullPredicateAndEmpty() {
        Optional<String> emptyOpt = Optional.empty();
        Predicate<String> predicate = s -> s.startsWith("h");
        Optional<String> result = emptyOpt.filter(predicate);
        if (!result.isPresent()) {
            System.out.println("PASS: filter on empty Optional returns empty");
        } else {
            System.out.println("FAIL: filter on empty Optional did not return empty");
        }
    }
}
