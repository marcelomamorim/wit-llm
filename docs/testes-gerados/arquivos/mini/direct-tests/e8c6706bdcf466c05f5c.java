/*
 * @test
 * @summary Test LinkedList(Collection<? extends E>) constructor behavior
 * @run main LinkedListWitupTest
 */

import java.util.LinkedList;
import java.util.Collection;
import java.util.ArrayList;
import java.util.List;

public class LinkedListWitupTest {
    public static void main(String[] args) {
        testNullCollection();
        testEmptyCollection();
        testNonEmptyCollection();
    }

    private static void testNullCollection() {
        try {
            new LinkedList<Object>(null);
            throw new RuntimeException("Expected NullPointerException for null collection");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testEmptyCollection() {
        Collection<String> empty = new ArrayList<>();
        LinkedList<String> list = new LinkedList<>(empty);
        if (!list.isEmpty()) {
            throw new RuntimeException("LinkedList constructed from empty collection should be empty");
        }
    }

    private static void testNonEmptyCollection() {
        List<String> source = new ArrayList<>();
        source.add("a");
        source.add("b");
        source.add("c");
        LinkedList<String> list = new LinkedList<>(source);
        if (list.size() != source.size()) {
            throw new RuntimeException("LinkedList size should match source collection size");
        }
        int i = 0;
        for (String s : list) {
            if (!s.equals(source.get(i++))) {
                throw new RuntimeException("LinkedList elements should match source collection elements in order");
            }
        }
    }
}
