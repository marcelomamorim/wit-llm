/*
 * @test
 * @summary Test Collections$UnmodifiableList set(E) throws UnsupportedOperationException
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        // Create a modifiable list
        List<String> modifiable = new ArrayList<>();
        modifiable.add("a");
        modifiable.add("b");

        // Wrap it with unmodifiableList
        List<String> unmodifiable = Collections.unmodifiableList(modifiable);

        // Verify that set throws UnsupportedOperationException
        boolean threw = false;
        try {
            unmodifiable.set(0, "z");
        } catch (UnsupportedOperationException e) {
            threw = true;
        }

        if (!threw) {
            throw new RuntimeException("Expected UnsupportedOperationException from unmodifiableList.set");
        }

        // Verify that original list can be modified
        modifiable.set(0, "x");
        if (!"x".equals(modifiable.get(0))) {
            throw new RuntimeException("Modifiable list set failed");
        }

        // Verify that unmodifiable list reflects changes in underlying list
        if (!"x".equals(unmodifiable.get(0))) {
            throw new RuntimeException("Unmodifiable list did not reflect underlying list changes");
        }

        System.out.println("Test passed.");
    }
}
