/* @test    @summary Test the next() method of WeakHashMap for correct value retrieval.    @run main WeakHashMapWitupTest */

import java.util.WeakHashMap;

public class WeakHashMapWitupTest {

    public static void main(String[] args) {
        testNextReturnsCorrectValue();
    }

    public static void testNextReturnsCorrectValue() {
        WeakHashMap<String, String> map = new WeakHashMap<>();
        String key = "testKey";
        String value = "testValue";
        map.put(key, value);
        // Simulate iteration by calling next() directly
        String result = map.next();
        if (!value.equals(result)) {
            throw new AssertionError("Expected value: " + value + ", but got: " + result);
        }
    }
}
