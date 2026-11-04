/* @test    @summary Test getBoolean method of UnsafeQualifiedStaticIntegerFieldAccessorImpl    @run main UnsafeQualifiedStaticIntegerFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticIntegerFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetBooleanThrowsException();
    }

    private static void testGetBooleanThrowsException() {
        try {
            // Create an instance of the class under test
            Class<?> clazz = Class.forName("jdk.internal.reflect.UnsafeQualifiedStaticIntegerFieldAccessorImpl");
            // Obtain the getBoolean method
            java.lang.reflect.Method method = clazz.getMethod("getBoolean", Object.class);
            // Invoke the method with null (since it doesn't use the parameter)
            Object instance = null; // static method, instance not needed
            method.invoke(instance, (Object) null);
            // If no exception, test fails
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
