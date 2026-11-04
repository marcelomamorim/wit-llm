/* @test    @summary Test HashMap.tryAdvance with a non-null Consumer    @run main HashMapWitupTest */

import java.util.HashMap;
import java.util.function.Consumer;

public class HashMapWitupTest {

    public static void main(String[] args) {
        testTryAdvanceWithValidConsumer();
        testTryAdvanceWithNullConsumer();
        testTryAdvanceWithEmptyMap();
    }

    static void testTryAdvanceWithValidConsumer() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("one", 1);
        map.put("two", 2);
        map.put("three", 3);
        StringBuilder sb = new StringBuilder();
        Consumer<String> consumer = k -> sb.append(k).append(",");
        boolean result = map.tryAdvance(consumer);
        assert result : "Expected tryAdvance to return true";
        String resultString = sb.toString();
        assert resultString.contains("one") || resultString.contains("two") || resultString.contains("three") : "Consumer should have been applied to at least one key";
    }

    static void testTryAdvanceWithNullConsumer() {
        HashMap<String, Integer> map = new HashMap<>();
        try {
            map.tryAdvance(null);
            assert false : "Expected NullPointerException when passing null consumer";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testTryAdvanceWithEmptyMap() {
        HashMap<String, Integer> map = new HashMap<>();
        Consumer<String> consumer = k -> {};
        boolean result = map.tryAdvance(consumer);
        assert !result : "Expected tryAdvance to return false on empty map";
    }
}
