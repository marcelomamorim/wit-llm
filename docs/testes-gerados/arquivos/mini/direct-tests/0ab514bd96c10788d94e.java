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
        testNextReturnsKeysInOrder();
        testNextThrowsNoSuchElementException();
    }

    static void testNextReturnsKeysInOrder() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");

        Iterator<Integer> it = map.keySet().iterator();

        int expected = 1;
        while (it.hasNext()) {
            Integer key = it.next();
            if (key != expected) {
                throw new AssertionError("Expected key " + expected + " but got " + key);
            }
            expected++;
        }
        if (expected != 4) {
            throw new AssertionError("Iterator did not return all keys");
        }
    }

    static void testNextThrowsNoSuchElementException() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        Iterator<Integer> it = map.keySet().iterator();
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
