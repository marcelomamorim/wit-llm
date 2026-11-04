/*
 * @test
 * @summary Test EnumSet.allOf method behavior
 * @run main EnumSetWitupTest
 */

import java.util.EnumSet;

public class EnumSetWitupTest {

    enum SampleEnum {
        A, B, C
    }

    public static void main(String[] args) {
        // Create an EnumSet with all elements of SampleEnum
        EnumSet<SampleEnum> all = EnumSet.allOf(SampleEnum.class);

        // Check that all enum constants are present
        if (!all.contains(SampleEnum.A)) throw new RuntimeException("Missing A");
        if (!all.contains(SampleEnum.B)) throw new RuntimeException("Missing B");
        if (!all.contains(SampleEnum.C)) throw new RuntimeException("Missing C");

        // Check size matches enum constants count
        if (all.size() != SampleEnum.values().length) {
            throw new RuntimeException("Size mismatch: expected " + SampleEnum.values().length + " but got " + all.size());
        }

        // Check that no extra elements are present
        for (SampleEnum e : SampleEnum.values()) {
            if (!all.contains(e)) {
                throw new RuntimeException("EnumSet missing element: " + e);
            }
        }

        // Check that the EnumSet is mutable (addAll should have added all elements)
        // Add an element already present and check size remains the same
        boolean changed = all.add(SampleEnum.A);
        if (changed) {
            throw new RuntimeException("Adding existing element should not change the set");
        }

        System.out.println("Test passed.");
    }
}
