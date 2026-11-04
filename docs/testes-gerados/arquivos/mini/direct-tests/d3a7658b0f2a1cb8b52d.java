/*
 * @test
 * @summary Test java.util.ArrayList.hashCode() method for correctness and fail-fast behavior
 * @run main java.util.ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class ArrayListWitupTest {
    public static void main(String[] args) {
        testHashCodeConsistency();
        testHashCodeFailFast();
    }

    private static void testHashCodeConsistency() {
        ArrayList<String> list = new ArrayList<>();
        // Empty list hashCode
        int emptyHash = list.hashCode();

        list.add("a");
        list.add("b");
        list.add("c");

        int hash1 = list.hashCode();
        int hash2 = list.hashCode();

        if (hash1 != hash2) {
            throw new AssertionError("hashCode() should be consistent across multiple calls");
        }

        // Modify list and check hashCode changes
        list.add("d");
        int hash3 = list.hashCode();
        if (hash3 == hash1) {
            throw new AssertionError("hashCode() should change after list modification");
        }
    }

    private static void testHashCodeFailFast() {
        ArrayList<String> list = new ArrayList<>();
        list.add("x");
        list.add("y");

        // We simulate concurrent modification by modifying the list during hashCode call
        // Since hashCode caches modCount at start and checks after computing hash,
        // we try to modify list during hashCode call indirectly by subclassing or thread.
        // But no public hook to do that, so we test that if modCount changes during hashCode,
        // ConcurrentModificationException is thrown.

        // To simulate, we create a subclass overriding hashCodeRange to modify list mid-call
        ArrayList<String> modList = new ArrayList<String>() {
            @Override
            protected int hashCodeRange(int fromIndex, int toIndex) {
                // Modify list during hashCodeRange
                add("z");
                return super.hashCodeRange(fromIndex, toIndex);
            }
        };

        modList.add("x");
        modList.add("y");

        try {
            modList.hashCode();
            throw new AssertionError("Expected ConcurrentModificationException not thrown");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }
}
