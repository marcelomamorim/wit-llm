/*
 * @test
 * @summary Test IdentityHashMap.EntryIterator.Entry.getValue() throws IllegalStateException if index < 0
 * @run main IdentityHashMapWitupTest
 */

import java.util.IdentityHashMap;

public class IdentityHashMapWitupTest {
    public static void main(String[] args) {
        testGetValueThrowsIfIndexNegative();
    }

    private static void testGetValueThrowsIfIndexNegative() {
        IdentityHashMap<Object, Object> map = new IdentityHashMap<>();
        // Use the public constructor to create an empty map
        // Create an EntryIterator.Entry with invalid index by using the iterator and advancing it fully
        IdentityHashMap.EntryIterator it = new IdentityHashMap.EntryIterator(map);
        IdentityHashMap.EntryIterator.Entry entry = it.new Entry();

        // Manually set index to -1 via reflection is not allowed (no public API), so simulate by calling getValue before next()
        // The EntryIterator.Entry.getValue() calls checkIndexForEntryUse(), which throws IllegalStateException if index < 0

        boolean thrown = false;
        try {
            entry.getValue();
        } catch (IllegalStateException e) {
            thrown = true;
        }

        if (!thrown) {
            throw new AssertionError("Expected IllegalStateException when calling getValue() with index < 0");
        }
    }
}
