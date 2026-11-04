/*
 * @test
 * @summary Test getChar method of UnsafeStaticShortFieldAccessorImpl
 * @run main UnsafeStaticShortFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeStaticShortFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetCharThrowsException();
    }

    public static void testGetCharThrowsException() {
        try {
            // Create an instance of the class
            Class<?> clazz = Class.forName("jdk.internal.reflect.UnsafeStaticShortFieldAccessorImpl");
            // Get the method getChar
            java.lang.reflect.Method method = clazz.getDeclaredMethod("getChar", Object.class);
            method.setAccessible(true);
            // Invoke the method with null (since it doesn't use the parameter)
            Object instance = null;
            method.invoke(instance, (Object) null);
            // If no exception, fail the test
            throw new AssertionError("Expected IllegalArgumentException was not thrown.");
        } catch (java.lang.reflect.InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (!(cause instanceof IllegalArgumentException)) {
                throw new AssertionError("Unexpected exception type: " + cause, cause);
            }
            // Test passes if IllegalArgumentException is thrown
        } catch (NoSuchMethodException | ClassNotFoundException | IllegalAccessException e) {
            throw new AssertionError("Reflection error during test execution", e);
        }
    }
}
