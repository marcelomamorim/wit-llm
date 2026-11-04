/* @test    @summary Test TreeMap forEachRemaining method for correct iteration and exception handling    @run main TreeMapWitupTest */

import java.util.TreeMap;
import java.util.Map;
import java.util.function.Consumer;

public class TreeMapWitupTest {

    public static void main(String[] args) {
        testForEachRemainingNormal();
        testForEachRemainingNullConsumer();
        testForEachRemainingConcurrentModification();
    }

    static void testForEachRemainingNormal() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        StringBuilder sb = new StringBuilder();
        Consumer<Map.Entry<Integer, String>> consumer = entry -> sb.append(entry.getKey()).append(":").append(entry.getValue()).append(",");
        map.forEachRemaining(consumer);
        String result = sb.toString();
        boolean containsAll = result.contains("1:one") && result.contains("2:two") && result.contains("3:three");
        if (!containsAll) {
            throw new AssertionError("forEachRemaining did not process all entries: " + result);
        }
    }

    static void testForEachRemainingNullConsumer() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        try {
            map.forEachRemaining(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testForEachRemainingConcurrentModification() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(2, "two");
        java.util.Iterator<Map.Entry<Integer, String>> iterator = map.entrySet().iterator();
        // Advance iterator to trigger internal state
        if (iterator.hasNext()) {
            iterator.next();
        }
        // Modify map structurally
        map.put(3, "three");
        try {
            // Use reflection to access forEachRemaining, as it's package-private
            java.lang.reflect.Method method = map.getClass().getDeclaredMethod("forEachRemaining", java.util.function.Consumer.class);
            method.setAccessible(true);
            method.invoke(iterator, (Consumer<Map.Entry<Integer, String>>) entry -> {});
            throw new AssertionError("Expected ConcurrentModificationException not thrown");
        } catch (java.lang.reflect.InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (!(cause instanceof java.util.ConcurrentModificationException)) {
                throw new AssertionError("Unexpected exception: " + cause, cause);
            }
            // expected
        } catch (Exception e) {
            throw new AssertionError("Reflection error: " + e, e);
        }
    }
}
