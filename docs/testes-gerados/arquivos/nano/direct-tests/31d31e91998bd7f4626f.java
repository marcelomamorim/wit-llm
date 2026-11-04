/* @test    @summary Test getChar method of UnsafeQualifiedStaticLongFieldAccessorImpl class    @run main UnsafeQualifiedStaticLongFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticLongFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetCharThrowsException();
    }

    private static void testGetCharThrowsException() throws Exception {
        // Create a dummy instance of the class
        Class<?> clazz = Class.forName("jdk.internal.reflect.UnsafeQualifiedStaticLongFieldAccessorImpl");
        // Obtain the getChar method
        java.lang.reflect.Method getCharMethod = clazz.getDeclaredMethod("getChar", Object.class);
        getCharMethod.setAccessible(true);
        // Invoke the method with null (since it doesn't use the parameter)
        Object instance = null;
        try {
            getCharMethod.invoke(instance, (Object) null);
            // If no exception, test fails
            System.err.println("Expected IllegalArgumentException was not thrown.");
            System.exit(1);
        } catch (java.lang.reflect.InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof IllegalArgumentException) {
                // Test passes
                System.out.println("getChar() correctly throws IllegalArgumentException.");
            } else {
                System.err.println("Unexpected exception: " + cause);
                System.exit(1);
            }
        }
    }
}
