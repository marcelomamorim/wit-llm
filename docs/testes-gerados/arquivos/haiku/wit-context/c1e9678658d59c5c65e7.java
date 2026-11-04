/*
 * @test
 * @summary Checks CheckedCollection iterator forEachRemaining delegation and null action handling
 * @run main CollectionsWitupTest
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testRemainingElementsDelivered();
        testNullActionThrowsNPE();
    }

    static void testRemainingElementsDelivered() {
        List<String> src = new ArrayList<>(List.of("a", "b", "c"));
        Collection<String> c = Collections.checkedCollection(src, String.class);
        Iterator<String> it = c.iterator();
        it.next();
        List<String> seen = new ArrayList<>();
        it.forEachRemaining(seen::add);
        if (!seen.equals(List.of("b", "c"))) {
            throw new RuntimeException("Unexpected remaining elements: " + seen);
        }
    }

    static void testNullActionThrowsNPE() {
        Collection<String> c = Collections.checkedCollection(
                new ArrayList<>(List.of("x")), String.class);
        Iterator<String> it = c.iterator();
        try {
            it.forEachRemaining(null);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("Expected NullPointerException for null action");
    }
}
