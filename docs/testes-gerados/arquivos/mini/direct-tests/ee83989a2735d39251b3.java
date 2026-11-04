/*
 * @test
 * @summary Test ConcurrentHashMap forEachRemaining method with Consumer
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ConcurrentHashMapWitupTest {

    public static void main(String[] args) {
        testForEachRemainingWithValidConsumer();
        testForEachRemainingWithNullConsumer();
    }

    static void testForEachRemainingWithValidConsumer() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("one", 1);
        map.put("two", 2);
        map.put("three", 3);

        Iterator<String> keyIterator = map.keySet().iterator();

        final StringBuilder sb = new StringBuilder();
        Consumer<String> consumer = new Consumer<String>() {
            public void accept(String s) {
                sb.append(s).append(",");
            }
        };

        // Use forEachRemaining on the iterator to consume remaining keys
        keyIterator.forEachRemaining(consumer);

        String result = sb.toString();

        // Check that all keys were consumed
        if (!result.contains("one") || !result.contains("two") || !result.contains("three")) {
            throw new AssertionError("forEachRemaining did not consume all keys: " + result);
        }

        // Check that no extra keys were added
        int count = result.split(",").length - 1; // last split is empty
        if (count != 3) {
            throw new AssertionError("forEachRemaining consumed wrong number of keys: " + count);
        }
    }

    static void testForEachRemainingWithNullConsumer() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("key", 42);

        Iterator<String> keyIterator = map.keySet().iterator();

        try {
            keyIterator.forEachRemaining(null);
            throw new AssertionError("Expected NullPointerException for null Consumer");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }
}
