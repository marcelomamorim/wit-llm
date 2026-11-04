/*
 * @test
 * @summary Test java.util.Collections.synchronizedCollection method
 * @run main CollectionsWitupTest
 */

import java.util.Collection;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Iterator;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testSynchronizedCollectionBasic();
        testSynchronizedCollectionNull();
    }

    private static void testSynchronizedCollectionBasic() {
        Collection<String> list = new ArrayList<>();
        Collection<String> syncCol = Collections.synchronizedCollection(list);

        // The returned collection should not be the same instance
        if (syncCol == list) {
            throw new RuntimeException("synchronizedCollection returned the same instance");
        }

        // Add elements via synchronized collection
        syncCol.add("one");
        syncCol.add("two");

        // The underlying collection should reflect the changes
        if (!list.contains("one") || !list.contains("two")) {
            throw new RuntimeException("Underlying collection not updated correctly");
        }

        // Iteration should be possible and consistent
        synchronized(syncCol) {
            Iterator<String> it = syncCol.iterator();
            int count = 0;
            while (it.hasNext()) {
                String s = it.next();
                if (!"one".equals(s) && !"two".equals(s)) {
                    throw new RuntimeException("Unexpected element in synchronized collection: " + s);
                }
                count++;
            }
            if (count != 2) {
                throw new RuntimeException("Expected 2 elements in synchronized collection, found " + count);
            }
        }
    }

    private static void testSynchronizedCollectionNull() {
        try {
            Collections.synchronizedCollection(null);
            throw new RuntimeException("Expected NullPointerException for null argument");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }
}
