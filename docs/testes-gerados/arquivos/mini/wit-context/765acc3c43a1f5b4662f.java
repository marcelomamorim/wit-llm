/*
 * @test
 * @summary Test Collections.synchronizedCollection(Collection) for null input and basic synchronization behavior
 * @run main CollectionsWitupTest
 */

import java.util.Collection;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Iterator;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testNullInput();
        testSynchronizedCollectionBasic();
    }

    private static void testNullInput() {
        try {
            Collections.synchronizedCollection(null);
            throw new RuntimeException("Expected NullPointerException for null input");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testSynchronizedCollectionBasic() {
        Collection<String> base = new ArrayList<>();
        Collection<String> syncCol = Collections.synchronizedCollection(base);

        // Add elements via synchronized collection
        syncCol.add("one");
        syncCol.add("two");

        // Check that base collection reflects additions
        if (!base.contains("one") || !base.contains("two")) {
            throw new RuntimeException("Underlying collection does not contain added elements");
        }

        // Check that iterator is synchronized on the syncCol's mutex
        // We cannot access mutex directly, but we can check iterator behavior
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
            throw new RuntimeException("Iterator did not iterate over all elements");
        }
    }
}
