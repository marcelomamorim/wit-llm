/* @test    @summary Test for Hashtable's forEach method to ensure it applies the action to all entries without concurrent modification.    @run main HashtableWitupTest */

import java.util.Hashtable;
import java.util.function.BiConsumer;

public class HashtableWitupTest {

    public static void main(String[] args) {
        testForEachAppliesActionToAllEntries();
        testForEachThrowsOnConcurrentModification();
    }

    public static void testForEachAppliesActionToAllEntries() {
        Hashtable<String, Integer> table = new Hashtable<>();
        table.put("one", 1);
        table.put("two", 2);
        table.put("three", 3);
        StringBuilder result = new StringBuilder();
        table.forEach((k, v) -> result.append(k).append(v));
        String combined = result.toString();
        boolean hasOne = combined.contains("one1");
        boolean hasTwo = combined.contains("two2");
        boolean hasThree = combined.contains("three3");
        if (!(hasOne && hasTwo && hasThree)) {
            throw new AssertionError("forEach did not apply to all entries: " + combined);
        }
    }

    public static void testForEachThrowsOnConcurrentModification() {
        Hashtable<String, String> table = new Hashtable<>();
        table.put("a", "A");
        table.put("b", "B");
        try {
            table.forEach((k, v) -> {
                table.remove(k);
                // Trigger concurrent modification exception
                table.put("c", "C");
            });
            throw new AssertionError("Expected ConcurrentModificationException was not thrown");
        } catch (ConcurrentModificationException e) {
            // Expected exception
        }
    }
}
