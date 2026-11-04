/*
 * @test
 * @summary Tests the tryAdvance method of WeakHashMap.
 * @run main WeakHashMapWitupTest
 */

import java.util.WeakHashMap;
import java.util.function.Consumer;

public class WeakHashMapWitupTest {

    public static void main(String[] args) {
        testTryAdvanceWithNullConsumer();
        testTryAdvanceWithEmptyMap();
        testTryAdvanceWithSingleEntry();
    }

    static void testTryAdvanceWithNullConsumer() {
        WeakHashMap<String, String> map = new WeakHashMap<>();
        try {
            map.tryAdvance(null);
            System.out.println("testTryAdvanceWithNullConsumer: Failed - no exception");
        } catch (NullPointerException e) {
            System.out.println("testTryAdvanceWithNullConsumer: Passed");
        } catch (Throwable t) {
            System.out.println("testTryAdvanceWithNullConsumer: Failed - wrong exception");
        }
    }

    static void testTryAdvanceWithEmptyMap() {
        WeakHashMap<String, String> map = new WeakHashMap<>();
        boolean result = map.tryAdvance(k -> {});
        if (!result) {
            System.out.println("testTryAdvanceWithEmptyMap: Passed");
        } else {
            System.out.println("testTryAdvanceWithEmptyMap: Failed - should return false");
        }
    }

    static void testTryAdvanceWithSingleEntry() {
        WeakHashMap<String, String> map = new WeakHashMap<>();
        String key = "key";
        String value = "value";
        map.put(key, value);
        final boolean[] called = {false};
        boolean result = map.tryAdvance(k -> {
            if (k.equals("key")) {
                called[0] = true;
            }
        });
        if (result && called[0]) {
            System.out.println("testTryAdvanceWithSingleEntry: Passed");
        } else {
            System.out.println("testTryAdvanceWithSingleEntry: Failed");
        }
    }
}
