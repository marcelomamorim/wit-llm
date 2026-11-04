/*
 * @test
 * @summary Test LinkedList.addAll(Collection<? extends E>) method behavior
 * @run main LinkedListWitupTest
 */

import java.util.LinkedList;
import java.util.Collection;
import java.util.ArrayList;
import java.util.List;

public class LinkedListWitupTest {
    public static void main(String[] args) {
        testAddAllWithValidCollection();
        testAddAllWithEmptyCollection();
        testAddAllWithNullCollection();
    }

    private static void testAddAllWithValidCollection() {
        LinkedList<String> list = new LinkedList<>();
        List<String> toAdd = new ArrayList<>();
        toAdd.add("a");
        toAdd.add("b");

        boolean result = list.addAll(toAdd);

        if (!result) {
            throw new RuntimeException("addAll should return true when adding non-empty collection");
        }
        if (list.size() != 2) {
            throw new RuntimeException("List size should be 2 after addAll");
        }
        if (!"a".equals(list.get(0)) || !"b".equals(list.get(1))) {
            throw new RuntimeException("Elements not added correctly by addAll");
        }
    }

    private static void testAddAllWithEmptyCollection() {
        LinkedList<String> list = new LinkedList<>();
        List<String> empty = new ArrayList<>();

        boolean result = list.addAll(empty);

        if (result) {
            throw new RuntimeException("addAll should return false when adding empty collection");
        }
        if (list.size() != 0) {
            throw new RuntimeException("List size should remain 0 after addAll with empty collection");
        }
    }

    private static void testAddAllWithNullCollection() {
        LinkedList<String> list = new LinkedList<>();
        try {
            list.addAll(null);
            throw new RuntimeException("addAll should throw NullPointerException when collection is null");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }
}
