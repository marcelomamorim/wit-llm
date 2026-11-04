/*
 * @test
 * @summary Test EnumMap getValue method behavior
 * @run main EnumMapWitupTest
 */

import java.util.EnumMap;
import java.util.Map;

public class EnumMapWitupTest {

    enum SampleEnum { A, B }

    // Minimal subclass to expose getValue() for testing
    static class TestEntry extends EnumMap<SampleEnum, String>.Entry {
        TestEntry(int idx) {
            // Use reflection to call protected constructor of Entry
            super(idx);
        }

        // Expose getValue() publicly
        public String testGetValue() {
            return getValue();
        }

        // Expose setValue publicly
        public String testSetValue(String val) {
            return setValue(val);
        }
    }

    public static void main(String[] args) throws Exception {
        EnumMap<SampleEnum, String> map = new EnumMap<>(SampleEnum.class);

        // Put some values
        map.put(SampleEnum.A, "valueA");
        map.put(SampleEnum.B, null);

        // Access Entry via entrySet iterator
        Map.Entry<SampleEnum, String> entryA = null;
        Map.Entry<SampleEnum, String> entryB = null;
        for (Map.Entry<SampleEnum, String> e : map.entrySet()) {
            if (e.getKey() == SampleEnum.A) entryA = e;
            if (e.getKey() == SampleEnum.B) entryB = e;
        }

        // Test getValue returns correct value for non-null
        if (!"valueA".equals(entryA.getValue())) {
            throw new AssertionError("getValue() did not return expected value for A");
        }

        // Test getValue returns null for null value
        if (entryB.getValue() != null) {
            throw new AssertionError("getValue() did not return null for B");
        }

        // Test setValue updates value and returns old value
        String oldVal = entryA.setValue("newValueA");
        if (!"valueA".equals(oldVal)) {
            throw new AssertionError("setValue() did not return old value");
        }
        if (!"newValueA".equals(entryA.getValue())) {
            throw new AssertionError("setValue() did not update value");
        }

        // Test that getValue reflects updated value
        if (!"newValueA".equals(entryA.getValue())) {
            throw new AssertionError("getValue() did not reflect updated value");
        }

        // Test that getValue does not throw for valid entries
        try {
            entryA.getValue();
            entryB.getValue();
        } catch (Exception e) {
            throw new AssertionError("getValue() threw unexpected exception: " + e);
        }

        System.out.println("All tests passed.");
    }
}
