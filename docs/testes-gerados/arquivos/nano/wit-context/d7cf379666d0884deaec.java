/*
 * @test
 * @summary Test for ArrayList.retainAll(Collection<?> c) method.
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.Collection;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testRetainAllWithNull();
        testRetainAllWithEmptyCollection();
        testRetainAllWithNonEmptyCollection();
    }

    private static void testRetainAllWithNull() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        boolean exceptionThrown = false;
        try {
            list.retainAll(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when retainAll(null)");
        }
    }

    private static void testRetainAllWithEmptyCollection() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        Collection<String> empty = new ArrayList<>();
        boolean changed = list.retainAll(empty);
        if (!list.isEmpty() || !changed) {
            throw new AssertionError("retainAll with empty collection should clear list and return true");
        }
    }

    private static void testRetainAllWithNonEmptyCollection() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        Collection<String> retain = new ArrayList<>();
        retain.add("b");
        retain.add("c");
        boolean changed = list.retainAll(retain);
        if (list.size() != 2 || !list.contains("b") || !list.contains("c") || !changed) {
            throw new AssertionError("retainAll should retain only specified elements and return true");
        }
    }
}
