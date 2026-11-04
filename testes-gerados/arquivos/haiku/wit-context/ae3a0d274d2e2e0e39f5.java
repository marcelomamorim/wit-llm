/*
 * @test
 * @summary Verifies HashSet(int initialCapacity) rejects negative capacities and accepts valid and large capacities with observable set behavior
 * @run main HashSetWitupTest
 */
import java.util.HashSet;
import java.util.Set;

public class HashSetWitupTest {
    public static void main(String[] args) {
        testNegativeCapacityThrowsIllegalArgumentException();
        testMinusOneCapacityThrowsIllegalArgumentException();
        testZeroCapacityCreatesUsableSet();
        testPositiveCapacityCreatesUsableSet();
        testLargeCapacityIsAccepted();
    }

    private static void testNegativeCapacityThrowsIllegalArgumentException() {
        try {
            new HashSet<String>(-1);
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new RuntimeException("Expected IllegalArgumentException for capacity -1");
    }

    private static void testMinusOneCapacityThrowsIllegalArgumentException() {
        try {
            new HashSet<Integer>(Integer.MIN_VALUE);
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new RuntimeException("Expected IllegalArgumentException for Integer.MIN_VALUE");
    }

    private static void testZeroCapacityCreatesUsableSet() {
        Set<String> set = new HashSet<>(0);
        check(set.isEmpty(), "new HashSet(0) should be empty");
        check(set.size() == 0, "size of new HashSet(0) should be 0");
        check(set.add("a"), "add to HashSet(0) should return true");
        check(set.add("b"), "second add to HashSet(0) should return true");
        check(!set.add("a"), "duplicate add should return false");
        check(set.size() == 2, "size after two distinct adds should be 2");
        check(set.contains("a") && set.contains("b"), "elements should be contained");
    }

    private static void testPositiveCapacityCreatesUsableSet() {
        Set<Integer> set = new HashSet<>(16);
        check(set.isEmpty(), "new HashSet(16) should be empty");
        for (int i = 0; i < 100; i++) {
            check(set.add(i), "add of " + i + " should return true");
        }
        check(set.size() == 100, "size should be 100 after 100 distinct adds");
        check(set.contains(0) && set.contains(99), "boundary elements should be contained");
        check(!set.contains(100), "absent element should not be contained");
    }

    private static void testLargeCapacityIsAccepted() {
        Set<String> set = new HashSet<>(1 << 30);
        check(set.isEmpty(), "new HashSet(1 << 30) should be empty");
        check(set.add("x"), "add to large-capacity set should return true");
        check(set.contains("x"), "element should be contained in large-capacity set");
        check(set.size() == 1, "size should be 1");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
