/*
 * @test
 * @summary Test Collections.UnmodifiableList.remove(int) throws UnsupportedOperationException
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        // Create a modifiable list
        List<String> modifiable = new ArrayList<>();
        modifiable.add("one");
        modifiable.add("two");

        // Create an unmodifiable view
        List<String> unmodifiable = Collections.unmodifiableList(modifiable);

        // Try to remove an element by index and expect UnsupportedOperationException
        try {
            unmodifiable.remove(0);
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
            // Expected exception
        }

        // Confirm original list is unchanged
        if (modifiable.size() != 2 || !"one".equals(modifiable.get(0)) || !"two".equals(modifiable.get(1))) {
            throw new RuntimeException("Original list was modified unexpectedly");
        }

        System.out.println("Test passed");
    }
}
