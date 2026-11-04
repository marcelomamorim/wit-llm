/*
 * @test
 * @summary Test EnumSet.noneOf method behavior
 * @run main EnumSetWitupTest
 */

import java.util.EnumSet;

public class EnumSetWitupTest {

    enum SmallEnum { A, B, C }

    enum LargeEnum {
        E0, E1, E2, E3, E4, E5, E6, E7, E8, E9,
        E10, E11, E12, E13, E14, E15, E16, E17, E18, E19,
        E20, E21, E22, E23, E24, E25, E26, E27, E28, E29,
        E30, E31, E32, E33, E34, E35, E36, E37, E38, E39,
        E40, E41, E42, E43, E44, E45, E46, E47, E48, E49,
        E50, E51, E52, E53, E54, E55, E56, E57, E58, E59,
        E60, E61, E62, E63, E64, E65
    }

    public static void main(String[] args) {
        testNoneOfWithSmallEnum();
        testNoneOfWithLargeEnum();
        testNoneOfWithNonEnumClass();
    }

    static void testNoneOfWithSmallEnum() {
        EnumSet<SmallEnum> set = EnumSet.noneOf(SmallEnum.class);
        if (set == null) {
            throw new AssertionError("EnumSet.noneOf returned null for SmallEnum");
        }
        if (!set.isEmpty()) {
            throw new AssertionError("EnumSet.noneOf should return empty set");
        }
        // Check that the set is of type RegularEnumSet by behavior: add and contains
        set.add(SmallEnum.A);
        if (!set.contains(SmallEnum.A)) {
            throw new AssertionError("EnumSet should contain added element");
        }
    }

    static void testNoneOfWithLargeEnum() {
        EnumSet<LargeEnum> set = EnumSet.noneOf(LargeEnum.class);
        if (set == null) {
            throw new AssertionError("EnumSet.noneOf returned null for LargeEnum");
        }
        if (!set.isEmpty()) {
            throw new AssertionError("EnumSet.noneOf should return empty set");
        }
        // Check that the set can add and contain elements
        set.add(LargeEnum.E0);
        set.add(LargeEnum.E65);
        if (!set.contains(LargeEnum.E0) || !set.contains(LargeEnum.E65)) {
            throw new AssertionError("EnumSet should contain added elements");
        }
    }

    static void testNoneOfWithNonEnumClass() {
        try {
            EnumSet.noneOf(String.class);
            throw new AssertionError("Expected ClassCastException for non-enum class");
        } catch (ClassCastException e) {
            // Expected
            String msg = e.getMessage();
            if (msg == null || !msg.contains("not an enum")) {
                throw new AssertionError("ClassCastException message unexpected: " + msg);
            }
        }
    }
}
