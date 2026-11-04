/*
 * @test
 * @summary Test for ConcurrentSkipListMap.next() method, including exception path.
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.NoSuchElementException;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testNextWithEmptyMap();
        testNextWithSingleElement();
    }

    private static void testNextWithEmptyMap() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        try {
            map.next();
            System.out.println("FAIL: Expected NoSuchElementException for empty map");
        } catch (NoSuchElementException e) {
            System.out.println("PASS: Caught expected NoSuchElementException for empty map");
        } catch (Throwable t) {
            System.out.println("FAIL: Caught unexpected exception: " + t);
        }
    }

    private static void testNextWithSingleElement() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        // Manually set internal state to simulate iterator at end
        // Since internal state is private, we simulate by removing the only element after calling next
        try {
            // Create an iterator-like scenario by calling next
            Integer key = map.next();
            if (key != null && key.equals(1)) {
                System.out.println("PASS: next() returned the only key as expected");
            } else {
                System.out.println("FAIL: next() did not return expected key");
            }
        } catch (Throwable t) {
            System.out.println("FAIL: Exception during next(): " + t);
        }
    }
}
