/* @test    @summary Test getBoolean method of UnsafeQualifiedStaticByteFieldAccessorImpl class    @run main UnsafeQualifiedStaticByteFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticByteFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetBooleanThrowsException();
    }

    private static void testGetBooleanThrowsException() {
        try {
            // Create an instance of the class under test
            Class<?> clazz = Class.forName("jdk.internal.reflect.UnsafeQualifiedStaticByteFieldAccessorImpl");
            // Obtain the getBoolean method
            java.lang.reflect.Method method = clazz.getDeclaredMethod("getBoolean", Object.class);
            method.setAccessible(true);
            // Invoke the method with null argument
            method.invoke(clazz.getDeclaredConstructor(Field.class, boolean.class).newInstance(null, false), (Object) null);
            // If no exception, test fails
            System.err.println("Expected IllegalArgumentException was not thrown.");
        } catch (java.lang.reflect.InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof IllegalArgumentException) {
                // Test passes
                System.out.println("getBoolean correctly throws IllegalArgumentException.");
            } else {
                System.err.println("Unexpected exception: " + cause);
            }
        } catch (Exception e) {
            System.err.println("Test setup failed: " + e);
        }
    }
}
