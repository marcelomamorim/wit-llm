/*
 * @test
 * @summary Test EnumMap remove() method behavior
 * @run main EnumMapWitupTest
 */

import java.util.EnumMap;
import java.util.Iterator;
import java.util.Map;

public class EnumMapWitupTest {

    enum TestEnum { A, B, C }

    public static void main(String[] args) {
        testRemoveRemovesValueAndDecrementsSize();
        testRemoveWithoutValueDoesNotChangeSize();
        testRemoveResetsLastReturnedIndex();
    }

    private static void testRemoveRemovesValueAndDecrementsSize() {
        EnumMap<TestEnum, String> map = new EnumMap<>(TestEnum.class);
        map.put(TestEnum.A, "valueA");
        map.put(TestEnum.B, "valueB");

        Iterator<Map.Entry<TestEnum, String>> it = map.entrySet().iterator();
        Map.Entry<TestEnum, String> entry = it.next(); // should be A

        it.remove(); // removes A

        if (map.containsKey(TestEnum.A)) {
            throw new RuntimeException("Remove did not remove the key A");
        }
        if (map.size() != 1) {
            throw new RuntimeException("Size not decremented after remove");
        }
    }

    private static void testRemoveWithoutValueDoesNotChangeSize() {
        EnumMap<TestEnum, String> map = new EnumMap<>(TestEnum.class);
        map.put(TestEnum.A, null); // null value
        map.put(TestEnum.B, "valueB");

        Iterator<Map.Entry<TestEnum, String>> it = map.entrySet().iterator();
        Map.Entry<TestEnum, String> entry = it.next(); // should be A

        it.remove(); // removes A which has null value

        if (map.containsKey(TestEnum.A)) {
            throw new RuntimeException("Remove did not remove the key A with null value");
        }
        if (map.size() != 1) {
            throw new RuntimeException("Size not decremented after remove of null value");
        }
    }

    private static void testRemoveResetsLastReturnedIndex() {
        EnumMap<TestEnum, String> map = new EnumMap<>(TestEnum.class);
        map.put(TestEnum.A, "valueA");

        Iterator<Map.Entry<TestEnum, String>> it = map.entrySet().iterator();
        it.next();
        it.remove();

        try {
            it.remove();
            throw new RuntimeException("Expected IllegalStateException on second remove call");
        } catch (IllegalStateException expected) {
            // expected
        }
    }
}
