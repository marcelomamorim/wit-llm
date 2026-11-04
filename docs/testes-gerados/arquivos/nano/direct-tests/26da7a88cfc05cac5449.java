/* @test    @summary Test TreeMap.next() method for correct value return and exception handling    @run main TreeMapWitupTest */

import java.util.TreeMap;

public class TreeMapWitupTest {

    public static void main(String[] args) {
        testNextReturnsValue();
        testNextThrowsExceptionWhenEmpty();
    }

    private static void testNextReturnsValue() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        map.pollFirstEntry(); // remove smallest key to test next()
        try {
            // Move iterator to the first entry
            java.util.Iterator<java.util.Map.Entry<Integer, String>> iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                iterator.next();
            }
            // Call next() method
            String value = map.next();
            assert value != null : "next() should return a non-null value";
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testNextReturnsValue", e);
        }
    }

    private static void testNextThrowsExceptionWhenEmpty() {
        TreeMap<Integer, String> emptyMap = new TreeMap<>();
        try {
            emptyMap.next();
            throw new AssertionError("Expected NoSuchElementException when calling next() on empty map");
        } catch (java.util.NoSuchElementException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type", e);
        }
    }
}
