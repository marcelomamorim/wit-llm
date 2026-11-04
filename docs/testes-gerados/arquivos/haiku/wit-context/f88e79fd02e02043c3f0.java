/*
 * @test
 * @summary Verifies SetN.contains(Object) via public Set.of() API: NPE on null, membership results otherwise
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.Set;

public class ImmutableCollectionsWitupTest {

    public static void main(String[] args) {
        testNullArgumentThrowsNPE();
        testEmptySetNullThrowsNPE();
        testMembershipPresentAndAbsent();
        testLargerSetMembership();
        testDifferentTypeIsAbsent();
    }

    static void testNullArgumentThrowsNPE() {
        Set<String> s = Set.of("a", "b", "c");
        boolean thrown = false;
        try {
            s.contains(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("contains(null) did not throw NullPointerException");
        }
    }

    static void testEmptySetNullThrowsNPE() {
        Set<String> empty = Set.of();
        if (empty.contains("x")) {
            throw new RuntimeException("empty set reported element present");
        }
        boolean thrown = false;
        try {
            empty.contains(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("empty set contains(null) did not throw NullPointerException");
        }
    }

    static void testMembershipPresentAndAbsent() {
        Set<String> s = Set.of("a", "b", "c");
        if (!s.contains("a") || !s.contains("b") || !s.contains("c")) {
            throw new RuntimeException("present element not found");
        }
        if (s.contains("z")) {
            throw new RuntimeException("absent element reported present");
        }
    }

    static void testLargerSetMembership() {
        Set<Integer> s = Set.of(1, 2, 3, 4, 5);
        for (int i = 1; i <= 5; i++) {
            if (!s.contains(i)) {
                throw new RuntimeException("element " + i + " not found");
            }
        }
        if (s.contains(9) || s.contains(0)) {
            throw new RuntimeException("absent integer reported present");
        }
    }

    static void testDifferentTypeIsAbsent() {
        Set<Object> s = Set.of("a", "b", "c");
        if (s.contains(new Object())) {
            throw new RuntimeException("unrelated object reported present");
        }
        if (s.contains(Integer.valueOf(1))) {
            throw new RuntimeException("integer reported present in string set");
        }
    }
}
