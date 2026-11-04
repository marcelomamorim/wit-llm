/*
 * @test
 * @summary Test EnumSet.allOf method for null argument and normal enum class
 * @run main EnumSetWitupTest
 */

import java.util.EnumSet;

public class EnumSetWitupTest {

    enum SampleEnum { A, B, C }

    public static void main(String[] args) {
        testAllOfWithValidEnum();
        testAllOfWithNull();
    }

    private static void testAllOfWithValidEnum() {
        EnumSet<SampleEnum> set = EnumSet.allOf(SampleEnum.class);
        // The set should contain all enum constants
        if (set.size() != SampleEnum.values().length) {
            throw new AssertionError("EnumSet.allOf did not include all enum constants");
        }
        for (SampleEnum e : SampleEnum.values()) {
            if (!set.contains(e)) {
                throw new AssertionError("EnumSet.allOf missing element: " + e);
            }
        }
    }

    private static void testAllOfWithNull() {
        try {
            EnumSet.allOf(null);
            throw new AssertionError("EnumSet.allOf(null) did not throw NullPointerException");
        } catch (NullPointerException expected) {
            // Expected exception
        } catch (ClassCastException cce) {
            // The WIT expath suggested ClassCastException if universe is null,
            // but current code throws NullPointerException on null argument.
            // Acceptable alternative behavior.
        }
    }
}
