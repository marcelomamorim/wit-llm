/* @test    @summary Test TreeMap.next() method for correct key retrieval and exception handling    @run main TreeMapWitupTest */

import java.util.TreeMap;

public class TreeMapWitupTest {

    public static void main(String[] args) {
        testNextReturnsCorrectKey();
        testNextThrowsExceptionWhenEmpty();
    }

    private static void testNextReturnsCorrectKey() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        // Move iterator to the first entry
        java.util.Iterator<Integer> iterator = map.keySet().iterator();
        // Advance iterator to the first element
        if (iterator.hasNext()) {
            Integer firstKey = iterator.next();
            // Use the next() method of TreeMap to get the next key
            Integer nextKey = map.next();
            assert firstKey.equals(nextKey) : "Expected key: " + firstKey + ", but got: " + nextKey;
        } else {
            throw new AssertionError("Iterator has no elements");
        }
    }

    private static void testNextThrowsExceptionWhenEmpty() {
        TreeMap<Integer, String> emptyMap = new TreeMap<>();
        boolean exceptionThrown = false;
        try {
            emptyMap.next();
        } catch (Exception e) {
            exceptionThrown = true;
            // Expecting NoSuchElementException or similar
            assert e instanceof java.util.NoSuchElementException : "Expected NoSuchElementException, but got: " + e;
        }
        assert exceptionThrown : "Expected exception was not thrown when calling next() on empty TreeMap";
    }
}
