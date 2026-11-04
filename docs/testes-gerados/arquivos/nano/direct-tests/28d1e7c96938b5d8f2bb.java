/* @test    @summary Test hasPrevious method of ImmutableCollections class    @run main ImmutableCollectionsWitupTest */

import java.util.ImmutableCollections;

public class ImmutableCollectionsWitupTest {

    public static void main(String[] args) {
        testHasPreviousWhenListIterator();
        testHasPreviousWhenNotListIterator();
    }

    private static void testHasPreviousWhenListIterator() {
        // Reflection to access the method
        try {
            java.lang.reflect.Method method = ImmutableCollections.class.getDeclaredMethod("hasPrevious");
            method.setAccessible(true);
            // Create an instance with isListIterator = true and cursor != 0
            Object instance = createInstance(true, 1);
            Object result = method.invoke(instance);
            if (!(result instanceof Boolean) || !((Boolean) result)) {
                throw new AssertionError("Expected true when isListIterator is true and cursor != 0");
            }
        } catch (NoSuchMethodException | IllegalAccessException | java.lang.reflect.InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private static void testHasPreviousWhenNotListIterator() {
        try {
            java.lang.reflect.Method method = ImmutableCollections.class.getDeclaredMethod("hasPrevious");
            method.setAccessible(true);
            Object instance = createInstance(false, 0);
            try {
                method.invoke(instance);
                throw new AssertionError("Expected UnsupportedOperationException when isListIterator is false");
            } catch (java.lang.reflect.InvocationTargetException e) {
                if (!(e.getCause() instanceof UnsupportedOperationException)) {
                    throw new AssertionError("Expected UnsupportedOperationException", e);
                }
            }
        } catch (NoSuchMethodException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static Object createInstance(boolean isListIterator, int cursor) {
        // Since constructor or factory is not provided, use reflection to instantiate
        // Assuming a default constructor exists and fields can be set via reflection
        try {
            Class<?> cls = Class.forName("java.util.ImmutableCollections");
            Object instance = cls.getDeclaredConstructor().newInstance();
            // Set fields via reflection if needed
            java.lang.reflect.Field fieldIsListIterator = cls.getDeclaredField("isListIterator");
            fieldIsListIterator.setAccessible(true);
            fieldIsListIterator.setBoolean(instance, isListIterator);
            java.lang.reflect.Field fieldCursor = cls.getDeclaredField("cursor");
            fieldCursor.setAccessible(true);
            fieldCursor.setInt(instance, cursor);
            return instance;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
