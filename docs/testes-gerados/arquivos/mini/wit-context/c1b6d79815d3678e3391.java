/*
 * @test
 * @summary Test EnumMap.EnumMapIterator.remove() method for expected IllegalStateException and normal removal
 * @run main EnumMapWitupTest
 */

import java.util.EnumMap;
import java.util.Iterator;

public class EnumMapWitupTest {

    enum SampleEnum { A, B, C }

    public static void main(String[] args) {
        testRemoveWithoutNextThrows();
        testRemoveAfterNextRemovesEntry();
    }

    private static void testRemoveWithoutNextThrows() {
        EnumMap<SampleEnum, String> map = new EnumMap<>(SampleEnum.class);
        map.put(SampleEnum.A, "valueA");
        Iterator<String> it = map.values().iterator();
        try {
            // Directly call remove() without next() should throw IllegalStateException
            it.remove();
            throw new RuntimeException("Expected IllegalStateException not thrown");
        } catch (IllegalStateException expected) {
            // Expected exception
        }
    }

    private static void testRemoveAfterNextRemovesEntry() {
        EnumMap<SampleEnum, String> map = new EnumMap<>(SampleEnum.class);
        map.put(SampleEnum.A, "valueA");
        map.put(SampleEnum.B, "valueB");

        Iterator<String> it = map.values().iterator();
        if (!it.hasNext()) {
            throw new RuntimeException("Iterator should have next");
        }
        String val = it.next();
        if (!"valueA".equals(val)) {
            throw new RuntimeException("Unexpected first value: " + val);
        }

        it.remove(); // should remove the entry for SampleEnum.A

        if (map.containsKey(SampleEnum.A)) {
            throw new RuntimeException("Entry for SampleEnum.A should have been removed");
        }

        if (map.size() != 1) {
            throw new RuntimeException("Map size should be 1 after removal, but was " + map.size());
        }
    }
}
