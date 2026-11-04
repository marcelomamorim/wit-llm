/* @test    @summary Test lastIndexOf method in ImmutableCollections for correct index return or -1 if not found    @run main ImmutableCollectionsWitupTest */

import java.util.*;

public class ImmutableCollectionsWitupTest {

    public static void main(String[] args) {
        testLastIndexOfFound();
        testLastIndexOfNotFound();
        testLastIndexOfNull();
    }

    static void testLastIndexOfFound() {
        List<String> list = Arrays.asList("a", "b", "c", "b", "a");
        // Using reflection to access the method since it's package-private
        try {
            java.lang.reflect.Method method = java.util.ImmutableCollections.class.getDeclaredMethod("lastIndexOf", Object.class);
            method.setAccessible(true);
            int index = (int) method.invoke(list, "b");
            if (index != 3) {
                throw new AssertionError("Expected index 3, got " + index);
            }
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    static void testLastIndexOfNotFound() {
        List<String> list = Arrays.asList("a", "b", "c");
        try {
            java.lang.reflect.Method method = java.util.ImmutableCollections.class.getDeclaredMethod("lastIndexOf", Object.class);
            method.setAccessible(true);
            int index = (int) method.invoke(list, "d");
            if (index != -1) {
                throw new AssertionError("Expected -1, got " + index);
            }
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    static void testLastIndexOfNull() {
        List<String> list = Arrays.asList("a", null, "c");
        try {
            java.lang.reflect.Method method = java.util.ImmutableCollections.class.getDeclaredMethod("lastIndexOf", Object.class);
            method.setAccessible(true);
            int index = (int) method.invoke(list, null);
            if (index != 1) {
                throw new AssertionError("Expected index 1, got " + index);
            }
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }
}
