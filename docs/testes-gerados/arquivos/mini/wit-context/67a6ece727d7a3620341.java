/*
 * @test
 * @summary Test AbstractMap.setValue throws UnsupportedOperationException
 * @run main AbstractMapWitupTest
 */

import java.util.AbstractMap;

public class AbstractMapWitupTest {
    public static void main(String[] args) {
        AbstractMap.SimpleImmutableEntry<String, String> entry =
            new AbstractMap.SimpleImmutableEntry<>("key", "value");

        try {
            entry.setValue("newValue");
            throw new Error("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
            // Expected exception
        }
    }
}
