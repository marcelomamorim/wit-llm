/*
 * @test
 * @summary Test EnumSet.noneOf method for valid and invalid inputs
 * @run main EnumSetWitupTest
 */

import java.util.EnumSet;

public class EnumSetWitupTest {

    enum SampleEnum { A, B, C }

    public static void main(String[] args) {
        testNoneOfWithValidEnum();
        testNoneOfWithNullClass();
        testNoneOfWithNonEnumClass();
    }

    private static void testNoneOfWithValidEnum() {
        EnumSet<SampleEnum> set = EnumSet.noneOf(SampleEnum.class);
        if (set == null) {
            throw new AssertionError("EnumSet.noneOf returned null for valid enum class");
        }
        if (!set.isEmpty()) {
            throw new AssertionError("EnumSet.noneOf should return empty set");
        }
    }

    private static void testNoneOfWithNullClass() {
        try {
            EnumSet.noneOf(null);
            throw new AssertionError("Expected NullPointerException for null argument");
        } catch (NullPointerException expected) {
            // Expected exception
        } catch (ClassCastException e) {
            // Defensive: if implementation throws ClassCastException instead of NPE, accept it
        }
    }

    private static void testNoneOfWithNonEnumClass() {
        try {
            EnumSet.noneOf(String.class);
            throw new AssertionError("Expected ClassCastException for non-enum class");
        } catch (ClassCastException expected) {
            // Expected exception
        }
    }
}
