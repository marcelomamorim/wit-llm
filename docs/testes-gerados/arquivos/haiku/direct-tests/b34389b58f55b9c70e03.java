/*
 * @test
 * @summary Verifies HashSet(int initialCapacity, float loadFactor) construction behavior and observable set operations
 * @run main HashSetWitupTest
 */

import java.util.HashSet;

public class HashSetWitupTest {

    public static void main(String[] args) {
        HashSet<String> s = new HashSet<>(16, 0.75f);
        check(s.isEmpty(), "new set with valid arguments should be empty");
        check(s.add("a"), "first add should return true");
        check(!s.add("a"), "duplicate add should return false");
        check(s.add(null), "null element should be accepted");
        check(s.size() == 2, "size should be 2, was " + s.size());
        check(s.contains(null), "set should contain null");
        check(s.contains("a"), "set should contain \"a\"");
        check(!s.contains("b"), "set should not contain \"b\"");

        HashSet<String> zero = new HashSet<>(0, 0.5f);
        check(zero.isEmpty(), "zero-capacity set should start empty");
        check(zero.add("x"), "zero-capacity set should accept elements");
        check(zero.size() == 1 && zero.contains("x"), "zero-capacity set should hold added element");

        HashSet<Integer> many = new HashSet<>(1, 0.1f);
        for (int i = 0; i < 1000; i++) {
            many.add(i);
        }
        check(many.size() == 1000, "size should be 1000, was " + many.size());
        for (int i = 0; i < 1000; i++) {
            check(many.contains(i), "missing element " + i);
        }
        check(!many.contains(1000), "set should not contain 1000");

        expectIllegalArgument(() -> new HashSet<String>(-1, 0.75f), "negative initial capacity");
        expectIllegalArgument(() -> new HashSet<String>(16, 0.0f), "zero load factor");
        expectIllegalArgument(() -> new HashSet<String>(16, -0.5f), "negative load factor");
        expectIllegalArgument(() -> new HashSet<String>(16, Float.NaN), "NaN load factor");
    }

    interface Action {
        void run();
    }

    static void expectIllegalArgument(Action action, String label) {
        try {
            action.run();
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new RuntimeException("expected IllegalArgumentException for " + label);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
