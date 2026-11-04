/*
 * @test
 * @summary Test ConcurrentSkipListMap.next() method for expected behavior and exception
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.Iterator;

public class ConcurrentSkipListMapWitupTest {
    public static void main(String[] args) {
        testNextReturnsKeysInOrder();
        testNextThrowsNoSuchElementException();
        System.out.println("All tests passed.");
    }

    private static void testNextReturnsKeysInOrder() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(3, "three");
        map.put(2, "two");

        Iterator<Integer> it = map.keySet().iterator();

        int prev = 0;
        int count = 0;
        while (it.hasNext()) {
            Integer key = it.next();
            if (count > 0) {
                if (key <= prev) {
                    throw new AssertionError("Keys not in ascending order");
                }
            }
            prev = key;
            count++;
        }
        if (count != 3) {
            throw new AssertionError("Iterator did not return all keys");
        }
    }

    private static void testNextThrowsNoSuchElementException() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        Iterator<Integer> it = map.keySet().iterator();
        if (it.hasNext()) {
            throw new AssertionError("Iterator should have no elements");
        }
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
