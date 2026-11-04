/*
 * @test
 * @summary Test ConcurrentSkipListMap next() method behavior
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testNextReturnsValuesInOrder();
        testNextThrowsNoSuchElementException();
    }

    static void testNextReturnsValuesInOrder() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");

        Iterator<String> it = map.values().iterator();

        String first = it.next();
        if (!"one".equals(first)) {
            throw new AssertionError("Expected first value 'one', got: " + first);
        }

        String second = it.next();
        if (!"two".equals(second)) {
            throw new AssertionError("Expected second value 'two', got: " + second);
        }

        String third = it.next();
        if (!"three".equals(third)) {
            throw new AssertionError("Expected third value 'three', got: " + third);
        }
    }

    static void testNextThrowsNoSuchElementException() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        Iterator<String> it = map.values().iterator();
        boolean thrown = false;
        try {
            it.next();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected NoSuchElementException when calling next() on empty iterator");
        }
    }
}
