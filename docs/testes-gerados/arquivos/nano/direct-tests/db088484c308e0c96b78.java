/* @test    @summary Test getShort method of UnsafeQualifiedCharacterFieldAccessorImpl class    @run main UnsafeQualifiedCharacterFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeQualifiedCharacterFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetShortThrowsException();
    }

    private static void testGetShortThrowsException() {
        try {
            // Create an instance of the class
            Class<?> clazz = Class.forName("jdk.internal.reflect.UnsafeQualifiedCharacterFieldAccessorImpl");
            // Get the method object
            java.lang.reflect.Method method = clazz.getDeclaredMethod("getShort", Object.class);
            // Since the method throws an exception, invoke it with null to trigger
            method.invoke(null, new Object());
            // If no exception, fail the test
            throw new AssertionError("Expected IllegalArgumentException was not thrown");
        } catch (java.lang.reflect.InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (!(cause instanceof IllegalArgumentException)) {
                throw new AssertionError("Unexpected exception cause: " + cause, cause);
            }
            // Test passes if IllegalArgumentException is thrown
        } catch (NoSuchMethodException | ClassNotFoundException e) {
            throw new AssertionError("Reflection error during test setup", e);
        }
    }
}
