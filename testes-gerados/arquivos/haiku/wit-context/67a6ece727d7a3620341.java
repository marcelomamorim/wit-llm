/*
 * @test
 * @summary Verifies that AbstractMap.SimpleImmutableEntry.setValue always throws UnsupportedOperationException and leaves the entry unchanged
 * @run main AbstractMapWitupTest
 */

public class AbstractMapWitupTest {
    public static void main(String[] args) {
        AbstractMap.SimpleImmutableEntry<String, String> entry =
            new AbstractMap.SimpleImmutableEntry<>("k", "v");

        try {
            entry.setValue("x");
            throw new RuntimeException("Expected UnsupportedOperationException for non-null value");
        } catch (UnsupportedOperationException expected) {
        }

        if (!"v".equals(entry.getValue())) {
            throw new RuntimeException("Value changed after failed setValue: " + entry.getValue());
        }

        try {
            entry.setValue(null);
            throw new RuntimeException("Expected UnsupportedOperationException for null value");
        } catch (UnsupportedOperationException expected) {
        }

        if (!"v".equals(entry.getValue())) {
            throw new RuntimeException("Value changed after failed setValue(null): " + entry.getValue());
        }

        if (!"k".equals(entry.getKey())) {
            throw new RuntimeException("Key changed: " + entry.getKey());
        }
    }
}
