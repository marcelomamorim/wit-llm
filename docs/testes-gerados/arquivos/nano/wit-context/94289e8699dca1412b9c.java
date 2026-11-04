/* @test    @summary Test for WeakHashMap.tryAdvance with null Consumer    @run main WeakHashMapWitupTest */

import java.util.WeakHashMap;
import java.util.function.Consumer;

public class WeakHashMapWitupTest {

    public static void main(String[] args) {
        testTryAdvanceWithNullConsumer();
        testTryAdvanceWithValidConsumer();
    }

    private static void testTryAdvanceWithNullConsumer() {
        WeakHashMap<Object, String> map = new WeakHashMap<>();
        Consumer<String> consumer = null;
        boolean exceptionThrown = false;
        try {
            map.tryAdvance(consumer);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when passing null Consumer");
        }
    }

    private static void testTryAdvanceWithValidConsumer() {
        WeakHashMap<Object, String> map = new WeakHashMap<>();
        // Insert an entry to ensure map is not empty
        Object key = new Object();
        String value = "test";
        map.put(key, value);
        final boolean[] called = {false};
        Consumer<String> consumer = v -> {
            if (v.equals("test")) {
                called[0] = true;
            }
        };
        boolean result = map.tryAdvance(consumer);
        if (!result) {
            throw new AssertionError("tryAdvance should return true when map has entries");
        }
        if (!called[0]) {
            throw new AssertionError("Consumer was not called with expected value");
        }
    }
}
