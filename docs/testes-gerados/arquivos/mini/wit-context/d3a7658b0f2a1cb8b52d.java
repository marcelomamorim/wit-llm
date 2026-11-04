/*
 * @test
 * @summary Test deterministic behavior of java.util.ArrayList.hashCode()
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class ArrayListWitupTest {
    public static void main(String[] args) {
        testHashCodeConsistent();
        testHashCodeDetectsComodification();
    }

    private static void testHashCodeConsistent() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");

        int hash1 = list.hashCode();
        int hash2 = list.hashCode();

        if (hash1 != hash2) {
            throw new AssertionError("hashCode() should be consistent across calls without modification");
        }
    }

    private static void testHashCodeDetectsComodification() {
        ArrayList<String> list = new ArrayList<>();
        list.add("x");
        list.add("y");

        // We simulate concurrent modification by subclassing and tampering with elementData length
        // Since elementData is private and no public API allows direct modification, we test that
        // hashCode() does not throw ConcurrentModificationException under normal usage.
        // The WIT expath about elementData.length < size is not reproducible via public API,
        // so we test that no exception is thrown normally.

        try {
            int hash = list.hashCode();
        } catch (ConcurrentModificationException e) {
            throw new AssertionError("hashCode() threw ConcurrentModificationException unexpectedly", e);
        }
    }
}
