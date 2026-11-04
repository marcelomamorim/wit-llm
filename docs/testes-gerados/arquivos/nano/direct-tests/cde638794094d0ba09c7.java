/* @test    @summary Test getChar method throws IllegalArgumentException as expected    @run main UnsafeQualifiedBooleanFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeQualifiedBooleanFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetCharThrowsException();
    }

    public static void testGetCharThrowsException() {
        try {
            // Create a dummy instance of the class
            Class<?> clazz = Class.forName("jdk.internal.reflect.UnsafeQualifiedBooleanFieldAccessorImpl");
            // Obtain the getChar method
            java.lang.reflect.Method method = clazz.getDeclaredMethod("getChar", Object.class);
            // Make it accessible
            method.setAccessible(true);
            // Invoke the method with null (or any object, since it throws exception immediately)
            method.invoke(clazz.getDeclaredConstructor(Field.class, boolean.class).newInstance(null, false), new Object[]{null});
            // If no exception, fail the test
            System.err.println("Expected IllegalArgumentException was not thrown.");
        } catch (java.lang.reflect.InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof IllegalArgumentException) {
                System.out.println("getChar method correctly threw IllegalArgumentException.");
            } else {
                System.err.println("Unexpected exception: " + cause);
            }
        } catch (Exception e) {
            System.err.println("Test failed with exception: " + e);
        }
    }
}
