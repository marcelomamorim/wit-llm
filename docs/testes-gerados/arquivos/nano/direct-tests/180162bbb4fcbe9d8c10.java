/* @test    @summary Test for Vector's forEachRemaining method to ensure it correctly applies a Consumer to remaining elements.    @run main VectorWitupTest */

import java.util.Vector;
import java.util.function.Consumer;

public class VectorWitupTest {

    public static void main(String[] args) {
        testForEachRemainingAppliesConsumer();
        testForEachRemainingWithEmptyVector();
        testForEachRemainingThrowsOnNullConsumer();
    }

    static void testForEachRemainingAppliesConsumer() {
        Vector<Integer> vector = new Vector<>();
        vector.add(1);
        vector.add(2);
        vector.add(3);
        StringBuilder sb = new StringBuilder();
        Consumer<Integer> consumer = (Integer i) -> sb.append(i);
        // Initialize iterator state
        vector.elementAt(0); // dummy to ensure vector is initialized
        // Use reflection to invoke forEachRemaining, as it is package-private
        try {
            java.lang.reflect.Method method = vector.getClass().getDeclaredMethod("forEachRemaining", java.util.function.Consumer.class);
            method.setAccessible(true);
            // Set cursor to 1 to simulate partial iteration
            java.lang.reflect.Field cursorField = vector.getClass().getDeclaredField("cursor");
            cursorField.setAccessible(true);
            cursorField.setInt(vector, 1);
            method.invoke(vector, consumer);
            assert sb.toString().equals("23") : "Expected '23', got: " + sb.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static void testForEachRemainingWithEmptyVector() {
        Vector<Integer> vector = new Vector<>();
        StringBuilder sb = new StringBuilder();
        Consumer<Integer> consumer = (Integer i) -> sb.append(i);
        try {
            java.lang.reflect.Method method = vector.getClass().getDeclaredMethod("forEachRemaining", java.util.function.Consumer.class);
            method.setAccessible(true);
            java.lang.reflect.Field cursorField = vector.getClass().getDeclaredField("cursor");
            cursorField.setAccessible(true);
            cursorField.setInt(vector, 0);
            method.invoke(vector, consumer);
            assert sb.toString().isEmpty() : "Expected empty string, got: " + sb.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static void testForEachRemainingThrowsOnNullConsumer() {
        Vector<Integer> vector = new Vector<>();
        try {
            java.lang.reflect.Method method = vector.getClass().getDeclaredMethod("forEachRemaining", java.util.function.Consumer.class);
            method.setAccessible(true);
            java.lang.reflect.Field cursorField = vector.getClass().getDeclaredField("cursor");
            cursorField.setAccessible(true);
            cursorField.setInt(vector, 0);
            method.invoke(vector, (Object) null);
            assert false : "Expected NullPointerException";
        } catch (java.lang.reflect.InvocationTargetException e) {
            assert e.getCause() instanceof NullPointerException : "Expected NullPointerException, got: " + e.getCause();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
