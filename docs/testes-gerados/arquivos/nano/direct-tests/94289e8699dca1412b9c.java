/* @test    @summary Test tryAdvance method of WeakHashMap for basic behavior    @run main WeakHashMapWitupTest */

import java.util.WeakHashMap;
import java.util.function.Consumer;

public class WeakHashMapWitupTest {

    public static void main(String[] args) {
        testTryAdvanceWithNullAction();
        testTryAdvanceWithEmptyMap();
        testTryAdvanceWithMapWithEntries();
    }

    static void testTryAdvanceWithNullAction() {
        WeakHashMap<Object, String> map = new WeakHashMap<>();
        try {
            map.tryAdvance(null);
            System.out.println("testTryAdvanceWithNullAction: FAILED (expected NullPointerException)");
        } catch (NullPointerException e) {
            System.out.println("testTryAdvanceWithNullAction: PASSED");
        }
    }

    static void testTryAdvanceWithEmptyMap() {
        WeakHashMap<Object, String> map = new WeakHashMap<>();
        Consumer<String> action = s -> {
            throw new AssertionError("Should not be called on empty map");
        };
        boolean result = map.tryAdvance(action);
        if (!result) {
            System.out.println("testTryAdvanceWithEmptyMap: PASSED");
        } else {
            System.out.println("testTryAdvanceWithEmptyMap: FAILED (unexpected true)");
        }
    }

    static void testTryAdvanceWithMapWithEntries() {
        WeakHashMap<Object, String> map = new WeakHashMap<>();
        Object key1 = new Object();
        Object key2 = new Object();
        map.put(key1, "value1");
        map.put(key2, "value2");
        StringBuilder sb = new StringBuilder();
        Consumer<String> action = s -> sb.append(s).append(",");
        boolean result = map.tryAdvance(action);
        if (result && sb.toString().contains("value1") || sb.toString().contains("value2")) {
            System.out.println("testTryAdvanceWithMapWithEntries: PASSED");
        } else {
            System.out.println("testTryAdvanceWithMapWithEntries: FAILED");
        }
    }
}
