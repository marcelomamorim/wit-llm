/* @test    @summary Test HashMap forEachRemaining method for correct iteration and exception handling    @run main HashMapWitupTest */

import java.util.HashMap;
import java.util.function.Consumer;

public class HashMapWitupTest {

    public static void main(String[] args) {
        testForEachRemainingNormal();
        testForEachRemainingNullAction();
        testForEachRemainingConcurrentModification();
    }

    static void testForEachRemainingNormal() {
        HashMap<Integer, String> map = new HashMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        StringBuilder sb = new StringBuilder();
        Consumer<Integer> consumer = key -> sb.append(key).append(",");
        map.forEachRemaining(consumer);
        String result = sb.toString();
        boolean condition = result.contains("1,") && result.contains("2,") && result.contains("3,");
        if (!condition) {
            throw new AssertionError("Failed normal iteration: " + result);
        }
    }

    static void testForEachRemainingNullAction() {
        HashMap<Integer, String> map = new HashMap<>();
        map.put(1, "one");
        try {
            map.forEachRemaining(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testForEachRemainingConcurrentModification() {
        HashMap<Integer, String> map = new HashMap<>();
        map.put(1, "one");
        map.put(2, "two");
        java.util.Iterator<Integer> iterator = map.keySet().iterator();
        // Advance iterator to set internal state
        if (iterator.hasNext()) {
            iterator.next();
        }
        // Modify map to cause concurrent modification
        map.put(3, "three");
        try {
            // Use reflection to access forEachRemaining (not possible directly, so simulate by calling it via reflection)
            java.lang.reflect.Method method = HashMap.class.getMethod("forEachRemaining", java.util.function.Consumer.class);
            method.invoke(map, (Consumer<Integer>) key -> {});
            throw new AssertionError("Expected ConcurrentModificationException not thrown");
        } catch (java.lang.reflect.InvocationTargetException e) {
            if (e.getCause() instanceof java.util.ConcurrentModificationException) {
                // expected
            } else {
                throw new AssertionError("Unexpected exception", e);
            }
        } catch (Exception e) {
            throw new AssertionError("Reflection invocation failed", e);
        }
    }
}
