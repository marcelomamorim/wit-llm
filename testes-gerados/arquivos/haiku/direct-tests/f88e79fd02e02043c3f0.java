/*
 * @test
 * @summary Verifies observable contains() behavior of immutable sets created by Set.of, including null rejection
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.Set;

public class ImmutableCollectionsWitupTest {
    static int failures = 0;

    static void check(boolean condition, String message) {
        if (!condition) {
            failures++;
            System.err.println("FAIL: " + message);
        }
    }

    static void checkNpe(Set<?> set, Object arg, String message) {
        try {
            set.contains(arg);
            failures++;
            System.err.println("FAIL: expected NullPointerException: " + message);
        } catch (NullPointerException expected) {
        }
    }

    public static void main(String[] args) {
        Set<String> empty = Set.of();
        check(!empty.contains("a"), "empty set must not contain \"a\"");
        checkNpe(empty, null, "empty set contains(null)");

        Set<Integer> one = Set.of(1);
        check(one.contains(1), "single-element set must contain 1");
        check(!one.contains(2), "single-element set must not contain 2");
        checkNpe(one, null, "single-element set contains(null)");

        Set<Integer> many = Set.of(1, 2, 3, 4, 5);
        for (int i = 1; i <= 5; i++) {
            check(many.contains(i), "multi-element set must contain " + i);
        }
        check(!many.contains(0), "multi-element set must not contain 0");
        check(!many.contains(6), "multi-element set must not contain 6");
        check(!many.contains("1"), "multi-element set must not contain String \"1\"");
        checkNpe(many, null, "multi-element set contains(null)");

        Set<String> strings = Set.of("a", "b", "c");
        check(strings.contains("b"), "string set must contain \"b\"");
        check(strings.contains(new String("a")), "string set must match equal but distinct String");
        check(!strings.contains("d"), "string set must not contain \"d\"");

        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
