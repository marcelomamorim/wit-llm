/*
 * @test
 * @summary Test removeAll method in Collections class.
 * @run main CollectionsWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testRemoveAllWithEmptyCollection();
        testRemoveAllWithNonEmptyCollection();
        testRemoveAllWithNull();
    }

    static void testRemoveAllWithEmptyCollection() {
        Collection<String> list = new ArrayList<>(Arrays.asList("a", "b", "c"));
        Collection<String> toRemove = new ArrayList<>();
        boolean result = false;
        try {
            result = Collections.removeAll(list, toRemove);
        } catch (UnsupportedOperationException e) {
            // Expected, as removeAll throws UnsupportedOperationException
            System.out.println("removeAll with empty collection: UnsupportedOperationException as expected");
            return;
        }
        // Since removeAll throws UnsupportedOperationException, code should not reach here
        assert false : "Expected UnsupportedOperationException";
    }

    static void testRemoveAllWithNonEmptyCollection() {
        Collection<String> list = new ArrayList<>(Arrays.asList("a", "b", "c"));
        Collection<String> toRemove = new ArrayList<>(Arrays.asList("b", "c"));
        boolean result = false;
        try {
            result = Collections.removeAll(list, toRemove);
        } catch (UnsupportedOperationException e) {
            // Expected, as removeAll throws UnsupportedOperationException
            System.out.println("removeAll with non-empty collection: UnsupportedOperationException as expected");
            return;
        }
        // Since removeAll throws UnsupportedOperationException, code should not reach here
        assert false : "Expected UnsupportedOperationException";
    }

    static void testRemoveAllWithNull() {
        Collection<String> list = new ArrayList<>(Arrays.asList("a", "b", "c"));
        try {
            Collections.removeAll(list, null);
        } catch (NullPointerException e) {
            System.out.println("removeAll with null collection: NullPointerException as expected");
            return;
        } catch (UnsupportedOperationException e) {
            // Expected, as removeAll throws UnsupportedOperationException
            System.out.println("removeAll with null collection: UnsupportedOperationException as expected");
            return;
        }
        // Since removeAll throws UnsupportedOperationException, code should not reach here
        assert false : "Expected UnsupportedOperationException or NullPointerException";
    }
}
