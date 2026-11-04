/*
 * @test
 * @summary Test EnumMap.setValue method behavior
 * @run main EnumMapWitupTest
 */

import java.util.EnumMap;
import java.util.Map;

public class EnumMapWitupTest {

    enum SampleEnum { A, B, C }

    public static void main(String[] args) {
        testSetValueReturnsOldValue();
        testSetValueAllowsNullValue();
    }

    private static void testSetValueReturnsOldValue() {
        EnumMap<SampleEnum, String> map = new EnumMap<>(SampleEnum.class);
        map.put(SampleEnum.A, "first");

        Map.Entry<SampleEnum, String> entry = map.entrySet().iterator().next();

        String oldValue = entry.setValue("second");

        if (!"first".equals(oldValue)) {
            throw new RuntimeException("Expected old value 'first', got: " + oldValue);
        }
        if (!"second".equals(map.get(SampleEnum.A))) {
            throw new RuntimeException("Expected new value 'second' in map, got: " + map.get(SampleEnum.A));
        }
    }

    private static void testSetValueAllowsNullValue() {
        EnumMap<SampleEnum, String> map = new EnumMap<>(SampleEnum.class);
        map.put(SampleEnum.B, "value");

        Map.Entry<SampleEnum, String> entry = map.entrySet().iterator().next();

        String oldValue = entry.setValue(null);

        if (!"value".equals(oldValue)) {
            throw new RuntimeException("Expected old value 'value', got: " + oldValue);
        }
        if (map.get(SampleEnum.B) != null) {
            throw new RuntimeException("Expected new value null in map, got: " + map.get(SampleEnum.B));
        }
    }
}
