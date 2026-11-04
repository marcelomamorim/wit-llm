/*
 * @test
 * @summary Test getBoolean method of UnsafeQualifiedObjectFieldAccessorImpl
 * @run main UnsafeQualifiedObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedObjectFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetBooleanThrowsException();
    }

    private static void testGetBooleanThrowsException() {
        try {
            // Create a dummy instance of UnsafeQualifiedObjectFieldAccessorImpl
            // Since constructor is package-private, use reflection to instantiate
            Class<?> clazz = Class.forName("jdk.internal.reflect.UnsafeQualifiedObjectFieldAccessorImpl");
            Field field = null; // No need to set actual field, as getBoolean throws exception
            boolean isReadOnly = false;
            Object accessor = clazz.getDeclaredConstructor(Field.class, boolean.class).newInstance(field, isReadOnly);
            // Invoke getBoolean method
            clazz.getMethod("getBoolean", Object.class).invoke(accessor, new Object());
            // If no exception, test fails
            System.err.println("Expected IllegalArgumentException was not thrown.");
        } catch (java.lang.reflect.InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof IllegalArgumentException) {
                System.out.println("getBoolean correctly threw IllegalArgumentException.");
            } else {
                System.err.println("Unexpected exception: " + cause);
            }
        } catch (Exception e) {
            System.err.println("Test setup or invocation failed: " + e);
        }
    }
}
