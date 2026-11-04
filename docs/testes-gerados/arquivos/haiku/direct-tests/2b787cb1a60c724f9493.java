/*
 * @test
 * @summary Verifies contains() on immutable List and Set instances created by List.of and Set.of
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.List;
import java.util.Set;

public class ImmutableCollectionsWitupTest {
    public static void main(String[] args) {
        List<Integer> one = List.of(1);
        check(one.contains(1), "single-element list should contain 1");
        check(!one.contains(2), "single-element list should not contain 2");

        List<Integer> many = List.of(1, 2, 3, 4, 5);
        for (int i = 1; i <= 5; i++) {
            check(many.contains(i), "list should contain " + i);
        }
        check(!many.contains(0), "list should not contain 0");
        check(!many.contains(6), "list should not contain 6");

        List<String> strs = List.of("a", "b", "c");
        check(strs.contains(new String("b")), "contains must use equals semantics");
        check(!strs.contains("d"), "list should not contain d");

        check(!List.of(1).contains("1"), "contains must not match elements of a different type");

        Set<String> set = Set.of("x", "y", "z");
        check(set.contains("x"), "set should contain x");
        check(set.contains("z"), "set should contain z");
        check(!set.contains("w"), "set should not contain w");

        Set<Integer> single = Set.of(42);
        check(single.contains(42), "single-element set should contain 42");
        check(!single.contains(41), "single-element set should not contain 41");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
