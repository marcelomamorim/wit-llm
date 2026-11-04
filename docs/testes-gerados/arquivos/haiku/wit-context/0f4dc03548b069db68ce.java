/*
 * @test
 * @summary Behavioral tests for EnumSet.noneOf: empty set creation and ClassCastException for non-enum types
 * @run main EnumSetWitupTest
 */

import java.util.EnumSet;

public class EnumSetWitupTest {

    private enum Color { RED, GREEN, BLUE }

    public static void main(String[] args) {
        testNoneOfReturnsEmptySet();
        testNoneOfReturnsIndependentSets();
        testNoneOfAcceptsElementsOfType();
        testNoneOfNonEnumThrowsClassCastException();
    }

    private static void testNoneOfReturnsEmptySet() {
        EnumSet<Color> s = EnumSet.noneOf(Color.class);
        if (s == null) {
            throw new AssertionError("noneOf returned null");
        }
        if (!s.isEmpty()) {
            throw new AssertionError("noneOf set should be empty");
        }
        if (s.size() != 0) {
            throw new AssertionError("expected size 0 but was " + s.size());
        }
    }

    private static void testNoneOfReturnsIndependentSets() {
        EnumSet<Color> a = EnumSet.noneOf(Color.class);
        EnumSet<Color> b = EnumSet.noneOf(Color.class);
        if (a == b) {
            throw new AssertionError("noneOf returned the same instance twice");
        }
        a.add(Color.RED);
        if (b.contains(Color.RED) || !b.isEmpty()) {
            throw new AssertionError("modifying one set affected another");
        }
    }

    private static void testNoneOfAcceptsElementsOfType() {
        EnumSet<Color> s = EnumSet.noneOf(Color.class);
        if (!s.add(Color.GREEN)) {
            throw new AssertionError("add of GREEN should return true");
        }
        if (!s.contains(Color.GREEN)) {
            throw new AssertionError("set should contain GREEN after add");
        }
        if (s.size() != 1) {
            throw new AssertionError("expected size 1 but was " + s.size());
        }
    }

    @SuppressWarnings("unchecked")
    private static void testNoneOfNonEnumThrowsClassCastException() {
        boolean thrown = false;
        try {
            EnumSet.noneOf((Class) String.class);
        } catch (ClassCastException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("expected ClassCastException for non-enum class");
        }
    }
}
