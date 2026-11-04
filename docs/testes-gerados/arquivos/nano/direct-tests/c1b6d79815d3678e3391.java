/* @test    @summary Test EnumMap.remove() method for correct removal behavior    @run main EnumMapWitupTest */

import java.util.EnumMap;
import java.util.Map;

public class EnumMapWitupTest {

    public static void main(String[] args) {
        testRemoveMethodRemovesValue();
        testRemoveMethodWithEmptyMap();
    }

    private static void testRemoveMethodRemovesValue() {
        EnumMap<TestEnum, String> map = new EnumMap<>(TestEnum.class);
        map.put(TestEnum.A, "Alpha");
        map.put(TestEnum.B, "Beta");
        map.put(TestEnum.C, "Gamma");

        // Remove an existing key
        map.remove(TestEnum.B);

        // Verify that the key is removed
        if (map.containsKey(TestEnum.B)) {
            throw new AssertionError("Key B should have been removed");
        }
        if (map.size() != 2) {
            throw new AssertionError("Map size should be 2 after removal");
        }
        if (map.get(TestEnum.B) != null) {
            throw new AssertionError("Value for key B should be null after removal");
        }
    }

    private static void testRemoveMethodWithEmptyMap() {
        EnumMap<TestEnum, String> map = new EnumMap<>(TestEnum.class);
        // Remove from empty map should not throw and should keep map empty
        map.remove(TestEnum.A);
        if (!map.isEmpty()) {
            throw new AssertionError("Map should be empty after removal from empty map");
        }
    }

    private enum TestEnum {
        A, B, C
    }
}
