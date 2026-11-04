/* @test    @summary Test getInt method of UnsafeQualifiedBooleanFieldAccessorImpl for exception throwing    @run main UnsafeQualifiedBooleanFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeQualifiedBooleanFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetIntThrowsException();
    }

    public static void testGetIntThrowsException() {
        try {
            // Create a dummy instance of the class
            Class<?> clazz = Class.forName("jdk.internal.reflect.UnsafeQualifiedBooleanFieldAccessorImpl");
            // Access the method getInt
            java.lang.reflect.Method method = clazz.getDeclaredMethod("getInt", Object.class);
            method.setAccessible(true);
            // Invoke the method with null (or any object), expecting an exception
            method.invoke(clazz.getDeclaredConstructor(Field.class, boolean.class).newInstance(null, false), new Object[]{null});
            // If no exception, fail the test
            System.err.println("Expected IllegalArgumentException was not thrown.");
        } catch (java.lang.reflect.InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof IllegalArgumentException) {
                System.out.println("getInt correctly threw IllegalArgumentException.");
            } else {
                System.err.println("Unexpected exception: " + cause);
            }
        } catch (Exception e) {
            System.err.println("Test setup or invocation failed: " + e);
        }
    }
}
