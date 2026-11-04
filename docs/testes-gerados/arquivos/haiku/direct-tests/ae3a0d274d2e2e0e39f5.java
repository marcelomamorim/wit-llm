/*
 * @test
 * @summary Verifies HashSet(int initialCapacity) constructor behavior through public API
 * @run main HashSetWitupTest
 */
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class HashSetWitupTest {

    public static void main(String[] args) {
        testZeroCapacityStillAcceptsElements();
        testSmallCapacityGrowsBeyondInitialCapacity();
        testNullElementWithSmallCapacity();
        testLargeCapacityBehavesLikeDefault();
        testIterationCountMatchesSize();
    }

    static void testZeroCapacityStillAcceptsElements() {
        HashSet<String> set = new HashSet<>(0);
        check(set.isEmpty(), "new HashSet(0) should be empty");
        check(set.add("a"), "add of new element should return true");
        check(!set.add("a"), "add of duplicate element should return false");
        check(set.size() == 1, "size should be 1 after adding one distinct element");
        check(set.contains("a"), "set should contain added element");
        check(!set.contains("b"), "set should not contain absent element");
    }

    static void testSmallCapacityGrowsBeyondInitialCapacity() {
        HashSet<Integer> set = new HashSet<>(1);
        for (int i = 0; i < 1000; i++) {
            check(set.add(i), "add of distinct integer should return true: " + i);
        }
        check(set.size() == 1000, "size should be 1000, was " + set.size());
        for (int i = 0; i < 1000; i++) {
            check(set.contains(i), "set should contain " + i);
        }
        check(!set.contains(1000), "set should not contain 1000");
    }

    static void testNullElementWithSmallCapacity() {
        HashSet<String> set = new HashSet<>(4);
        check(set.add(null), "add(null) should return true");
        check(set.contains(null), "set should contain null");
        check(!set.add(null), "second add(null) should return false");
        check(set.size() == 1, "size should be 1 after adding null twice");
    }

    static void testLargeCapacityBehavesLikeDefault() {
        HashSet<Integer> large = new HashSet<>(1 << 16);
        HashSet<Integer> def = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            large.add(i * 7);
            def.add(i * 7);
        }
        check(large.size() == 10, "large-capacity set size should be 10");
        check(large.equals(def), "large-capacity set should equal default-constructed set with same elements");
        check(def.equals(large), "equality should be symmetric");
    }

    static void testIterationCountMatchesSize() {
        HashSet<String> set = new HashSet<>(2);
        set.add("x");
        set.add("y");
        set.add("z");
        Set<String> view = set;
        int count = 0;
        Iterator<String> it = view.iterator();
        while (it.hasNext()) {
            check(view.contains(it.next()), "iterated element should be contained in set");
            count++;
        }
        check(count == set.size(), "iteration count should equal size");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
