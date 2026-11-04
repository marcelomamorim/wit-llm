/* @test    @summary Test for TreeMap.next() method handling normal and exceptional cases    @run main TreeMapWitupTest */

import java.util.TreeMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class TreeMapWitupTest {

    public static void main(String[] args) {
        testNextNormal();
        testNextThrowsNoSuchElementException();
    }

    private static void testNextNormal() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        TreeMap.Entry<Integer, String> entry = map.firstEntry();
        TreeMap.Entry<Integer, String> savedEntry = entry;
        try {
            // Simulate iterator's next() call
            Map.Entry<Integer, String> result = map.next();
            if (result == null) {
                throw new AssertionError("Expected non-null result from next()");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testNextNormal: " + e);
        }
    }

    private static void testNextThrowsNoSuchElementException() {
        TreeMap<Integer, String> map = new TreeMap<>();
        // No entries added, so next() should throw
        try {
            map.next();
            throw new AssertionError("Expected NoSuchElementException to be thrown");
        } catch (NoSuchElementException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }
}
