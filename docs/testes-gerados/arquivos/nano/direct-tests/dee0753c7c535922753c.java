/* @test    @summary Test getBoolean method of UnsafeQualifiedStaticCharacterFieldAccessorImpl    @run main UnsafeQualifiedStaticCharacterFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticCharacterFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetBooleanThrowsException();
    }

    private static void testGetBooleanThrowsException() {
        try {
            // Create an instance of the class under test
            Class<?> clazz = Class.forName("jdk.internal.reflect.UnsafeQualifiedStaticCharacterFieldAccessorImpl");
            // Obtain the getBoolean method
            java.lang.reflect.Method method = clazz.getMethod("getBoolean", Object.class);
            // Invoke the method with null (since it does not use the parameter)
            Object instance = null; // static method, instance not needed
            method.invoke(instance, (Object) null);
            // If no exception, test fails
            System.err.println("Expected IllegalArgumentException was not thrown.");
            System.exit(1);
        } catch (java.lang.reflect.InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof IllegalArgumentException) {
                // Test passes
                System.out.println("getBoolean correctly throws IllegalArgumentException.");
            } else {
                System.err.println("Unexpected exception: " + cause);
                System.exit(1);
            }
        } catch (NoSuchMethodException | ClassNotFoundException e) {
            System.err.println("Test setup failed: " + e);
            System.exit(1);
        }
    }
}
