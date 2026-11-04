/* @test    @summary Test getChar method of UnsafeQualifiedStaticDoubleFieldAccessorImpl class    @run main UnsafeQualifiedStaticDoubleFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticDoubleFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetCharThrowsException();
    }

    private static void testGetCharThrowsException() {
        try {
            // Create an instance of the class
            Class<?> clazz = Class.forName("jdk.internal.reflect.UnsafeQualifiedStaticDoubleFieldAccessorImpl");
            // Obtain the getChar method
            java.lang.reflect.Method method = clazz.getDeclaredMethod("getChar", Object.class);
            // Make it accessible
            method.setAccessible(true);
            // Invoke the method with null (since it doesn't use the argument)
            Object instance = null;
            method.invoke(instance, (Object) null);
            // If no exception, test fails
            throw new AssertionError("Expected IllegalArgumentException was not thrown.");
        } catch (java.lang.reflect.InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof IllegalArgumentException) {
                // Test passes
                System.out.println("getChar() correctly throws IllegalArgumentException.");
            } else {
                throw new AssertionError("Unexpected exception cause: " + cause, cause);
            }
        } catch (NoSuchMethodException | ClassNotFoundException | IllegalAccessException e) {
            throw new AssertionError("Reflection setup failed: " + e, e);
        }
    }
}
