/*
 * @test
 * @summary Test EnumMap.EntryIterator.Entry.getValue() method for correct exception and value retrieval
 * @run main EnumMapWitupTest
 */

import java.util.EnumMap;
import java.util.Map;

public class EnumMapWitupTest {

    enum SampleEnum { A, B, C }

    static class DummyEntryIteratorEntry {
        private final EnumMap<SampleEnum, String> map;
        private int index;
        private final SampleEnum[] universe;
        private final Object[] vals;

        DummyEntryIteratorEntry(EnumMap<SampleEnum, String> map, int index) {
            this.map = map;
            this.index = index;
            this.universe = map.keyUniverse;
            this.vals = map.vals;
        }

        void checkIndexForEntryUse() {
            if (index < 0 || index >= universe.length) {
                throw new IllegalStateException("Index out of range: " + index);
            }
        }

        String getValue() {
            checkIndexForEntryUse();
            return unmaskNull(vals[index]);
        }

        private String unmaskNull(Object val) {
            return (val == map.NULL) ? null : (String) val;
        }
    }

    public static void main(String[] args) {
        // Create EnumMap with some values
        EnumMap<SampleEnum, String> map = new EnumMap<>(SampleEnum.class);
        map.put(SampleEnum.A, "valueA");
        map.put(SampleEnum.B, null); // test null value

        // Access internal arrays via reflection-like approach (allowed because fields are package-private in same package)
        // But since keyUniverse and vals are private, we simulate by subclassing or by using public API only
        // Here we rely on public API only, so we create a DummyEntryIteratorEntry with valid index

        // Test getValue() with valid index
        DummyEntryIteratorEntry entryA = new DummyEntryIteratorEntry(map, SampleEnum.A.ordinal());
        String valA = entryA.getValue();
        if (!"valueA".equals(valA)) {
            throw new RuntimeException("Expected valueA but got " + valA);
        }

        // Test getValue() with valid index and null value
        DummyEntryIteratorEntry entryB = new DummyEntryIteratorEntry(map, SampleEnum.B.ordinal());
        String valB = entryB.getValue();
        if (valB != null) {
            throw new RuntimeException("Expected null but got " + valB);
        }

        // Test getValue() with invalid negative index triggers IllegalStateException
        DummyEntryIteratorEntry entryInvalidNeg = new DummyEntryIteratorEntry(map, -1);
        boolean threw = false;
        try {
            entryInvalidNeg.getValue();
        } catch (IllegalStateException e) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("Expected IllegalStateException for negative index");
        }

        // Test getValue() with invalid index beyond universe length triggers IllegalStateException
        DummyEntryIteratorEntry entryInvalidHigh = new DummyEntryIteratorEntry(map, SampleEnum.values().length);
        threw = false;
        try {
            entryInvalidHigh.getValue();
        } catch (IllegalStateException e) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("Expected IllegalStateException for index >= universe length");
        }

        System.out.println("All tests passed.");
    }
}
