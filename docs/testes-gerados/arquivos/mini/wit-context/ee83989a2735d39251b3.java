/*
 * @test
 * @summary Test ConcurrentHashMap.forEachRemaining(Consumer) for NPE on null action and normal iteration
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentHashMap.KeySpliterator;
import java.util.function.Consumer;

public class ConcurrentHashMapWitupTest {
    public static void main(String[] args) {
        testForEachRemainingNullAction();
        testForEachRemainingNormal();
    }

    static void testForEachRemainingNullAction() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key1", "value1");
        KeySpliterator<String, String> spliterator = map.keySpliterator();
        try {
            spliterator.forEachRemaining(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testForEachRemainingNormal() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("a", "1");
        map.put("b", "2");
        map.put("c", "3");

        final StringBuilder sb = new StringBuilder();
        KeySpliterator<String, String> spliterator = map.keySpliterator();
        spliterator.forEachRemaining(new Consumer<String>() {
            public void accept(String s) {
                sb.append(s);
            }
        });

        String result = sb.toString();
        if (!(result.contains("a") && result.contains("b") && result.contains("c"))) {
            throw new RuntimeException("forEachRemaining did not visit all keys: " + result);
        }
    }
}
