/*
 * @test
 * @summary Witup-derived checks for FindOps find-first/find-any null handling via public Stream API
 * @run main FindOpsWitupTest
 */

import java.util.Optional;
import java.util.stream.Stream;

public class FindOpsWitupTest {

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    private static void testNullElementFindFirstThrowsNpe() {
        boolean thrown = false;
        try {
            Stream.of((String) null).findFirst();
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "findFirst on null element must throw NullPointerException");
    }

    private static void testNullElementFindAnyThrowsNpe() {
        boolean thrown = false;
        try {
            Stream.of((String) null).findAny();
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "findAny on null element must throw NullPointerException");
    }

    private static void testNonNullFindFirstReturnsFirst() {
        Optional<String> result = Stream.of("a", "b").findFirst();
        check(result.isPresent(), "findFirst on non-empty stream must be present");
        check("a".equals(result.get()), "findFirst must return first element in encounter order");
    }

    private static void testEmptyStreamFindFirstIsEmpty() {
        Optional<String> result = Stream.<String>empty().findFirst();
        check(!result.isPresent(), "findFirst on empty stream must be empty");
    }

    private static void testEmptyStreamFindAnyIsEmpty() {
        Optional<String> result = Stream.<String>empty().findAny();
        check(!result.isPresent(), "findAny on empty stream must be empty");
    }

    public static void main(String[] args) {
        testNullElementFindFirstThrowsNpe();
        testNullElementFindAnyThrowsNpe();
        testNonNullFindFirstReturnsFirst();
        testEmptyStreamFindFirstIsEmpty();
        testEmptyStreamFindAnyIsEmpty();
    }
}
