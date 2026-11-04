/*
 * @test
 * @summary Test for Collections.removeAll to verify behavior and exception throwing.
 * @run main CollectionsWitupTest
 */

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testRemoveAllThrowsException();
        testRemoveAllWithEmptyCollection();
        testRemoveAllWithNonEmptyCollection();
    }

    private static void testRemoveAllThrowsException() {
        Collection<Object> coll = Collections.unmodifiableCollection(Arrays.asList(1, 2, 3));
        try {
            coll.removeAll(Arrays.asList(1));
            System.out.println("FAIL: Expected UnsupportedOperationException not thrown.");
        } catch (UnsupportedOperationException e) {
            System.out.println("PASS: Caught expected UnsupportedOperationException.");
        } catch (Exception e) {
            System.out.println("FAIL: Caught unexpected exception: " + e);
        }
    }

    private static void testRemoveAllWithEmptyCollection() {
        Collection<Integer> coll = Arrays.asList(1, 2, 3);
        boolean result = coll.removeAll(Collections.emptyList());
        if (!result) {
            System.out.println("PASS: removeAll with empty collection returned false as expected.");
        } else {
            System.out.println("FAIL: removeAll with empty collection returned true unexpectedly.");
        }
    }

    private static void testRemoveAllWithNonEmptyCollection() {
        Collection<Integer> coll = Arrays.asList(1, 2, 3);
        boolean result = coll.removeAll(Arrays.asList(2, 3));
        if (result) {
            System.out.println("PASS: removeAll with matching elements returned true.");
        } else {
            System.out.println("FAIL: removeAll with matching elements returned false.");
        }
    }
}
