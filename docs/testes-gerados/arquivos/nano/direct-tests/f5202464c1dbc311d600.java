/* @test    @summary Test getBoolean method of UnsafeQualifiedStaticFloatFieldAccessorImpl    @run main UnsafeQualifiedStaticFloatFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticFloatFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetBooleanThrowsException();
    }

    private static void testGetBooleanThrowsException() {
        try {
            // Create an instance of the class
            Class<?> clazz = Class.forName("jdk.internal.reflect.UnsafeQualifiedStaticFloatFieldAccessorImpl");
            // Obtain the getBoolean method
            java.lang.reflect.Method method = clazz.getMethod("getBoolean", Object.class);
            // Invoke the method with null argument
            method.invoke(clazz.getDeclaredConstructor(Field.class, boolean.class).newInstance(null, false), (Object) null);
            // If no exception, test fails
            System.err.println("Expected IllegalArgumentException was not thrown.");
        } catch (java.lang.reflect.InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof IllegalArgumentException) {
                System.out.println("getBoolean() correctly threw IllegalArgumentException.");
            } else {
                System.err.println("Unexpected exception: " + cause);
            }
        } catch (Exception e) {
            System.err.println("Test setup failed: " + e);
        }
    }
}
