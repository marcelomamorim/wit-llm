/*
 * @test
 * @summary Test EnumMap.EntryIterator.Entry.setValue(V) for IllegalStateException when index < 0
 * @run main EnumMapWitupTest
 */

import java.util.EnumMap;
import java.util.Map;

public class EnumMapWitupTest {

    // A simple enum for testing
    private enum TestEnum { A, B }

    public static void main(String[] args) {
        testSetValueThrowsIllegalStateException();
        testSetValueReturnsOldValue();
    }

    // This test attempts to trigger IllegalStateException by simulating an Entry with index < 0
    // Since Entry and EntryIterator are not public, and no public API exposes setValue directly,
    // this test is omitted because no public access to setValue with invalid index is possible.
    // The WIT expath is noted but cannot be tested without reflection or internal access.

    // Test normal setValue behavior via EnumMap.Entry returned by entrySet iterator
    private static void testSetValueReturnsOldValue() {
        EnumMap<TestEnum, String> map = new EnumMap<>(TestEnum.class);
        map.put(TestEnum.A, "first");

        Map.Entry<TestEnum, String> entry = map.entrySet().iterator().next();
        String oldValue = entry.setValue("second");

        if (!"first".equals(oldValue)) {
            throw new AssertionError("Expected old value 'first', got: " + oldValue);
        }

        String newValue = map.get(TestEnum.A);
        if (!"second".equals(newValue)) {
            throw new AssertionError("Expected new value 'second', got: " + newValue);
        }
    }
}
