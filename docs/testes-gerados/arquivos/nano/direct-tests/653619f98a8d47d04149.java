/* @test    @summary Test getChar method of UnsafeQualifiedStaticIntegerFieldAccessorImpl    @run main UnsafeQualifiedStaticIntegerFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticIntegerFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetCharThrowsException();
    }

    private static void testGetCharThrowsException() {
        try {
            // Create an instance of the class
            Class<?> clazz = Class.forName("jdk.internal.reflect.UnsafeQualifiedStaticIntegerFieldAccessorImpl");
            // Obtain the getChar method
            java.lang.reflect.Method method = clazz.getMethod("getChar", Object.class);
            // Invoke the method with null argument
            method.invoke(clazz.newInstance(), (Object) null);
            // If no exception, test fails
            throw new AssertionError("Expected IllegalArgumentException was not thrown");
        } catch (java.lang.reflect.InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (!(cause instanceof IllegalArgumentException)) {
                throw new AssertionError("Unexpected exception cause: " + cause, cause);
            }
            // Expected exception caught
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException e) {
            throw new AssertionError("Reflection error during test", e);
        }
    }
}
