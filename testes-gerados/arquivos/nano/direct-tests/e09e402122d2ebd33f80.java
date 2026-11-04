/* @test    @summary Test getInt method of UnsafeQualifiedStaticLongFieldAccessorImpl class    @run main UnsafeQualifiedStaticLongFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticLongFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetIntReturnsException();
    }

    private static void testGetIntReturnsException() throws Exception {
        // Reflection to access the method
        Class<?> clazz = Class.forName("jdk.internal.reflect.UnsafeQualifiedStaticLongFieldAccessorImpl");
        java.lang.reflect.Method method = clazz.getDeclaredMethod("getInt", Object.class);
        method.setAccessible(true);
        // Invoke the method with null argument, expecting an IllegalArgumentException
        try {
            method.invoke(null, (Object) null);
            // If no exception, test fails
            throw new AssertionError("Expected IllegalArgumentException was not thrown");
        } catch (java.lang.reflect.InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (!(cause instanceof IllegalArgumentException)) {
                throw new AssertionError("Unexpected exception cause: " + cause, cause);
            }
            // Test passes if IllegalArgumentException is thrown
        }
    }
}
