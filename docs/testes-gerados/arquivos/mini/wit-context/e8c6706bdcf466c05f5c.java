/*
 * @test
 * @summary Test LinkedList(Collection<? extends E>) constructor for basic and edge cases
 * @run main LinkedListWitupTest
 */

import java.util.LinkedList;
import java.util.Collection;
import java.util.ArrayList;
import java.util.List;

public class LinkedListWitupTest {
    public static void main(String[] args) {
        testConstructorWithNullCollection();
        testConstructorWithEmptyCollection();
        testConstructorWithNonEmptyCollection();
    }

    private static void testConstructorWithNullCollection() {
        try {
            new LinkedList<Object>(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testConstructorWithEmptyCollection() {
        Collection<String> empty = new ArrayList<>();
        LinkedList<String> list = new LinkedList<>(empty);
        if (list.size() != 0) {
            throw new RuntimeException("LinkedList constructed from empty collection should be empty");
        }
    }

    private static void testConstructorWithNonEmptyCollection() {
        List<String> source = new ArrayList<>();
        source.add("a");
        source.add("b");
        source.add("c");
        LinkedList<String> list = new LinkedList<>(source);
        if (list.size() != source.size()) {
            throw new RuntimeException("LinkedList size mismatch after construction from collection");
        }
        int i = 0;
        for (String s : list) {
            if (!s.equals(source.get(i++))) {
                throw new RuntimeException("LinkedList elements differ from source collection");
            }
        }
    }
}
