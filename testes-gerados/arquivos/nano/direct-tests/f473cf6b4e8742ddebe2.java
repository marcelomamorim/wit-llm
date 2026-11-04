/* @test    @summary Test getBoolean method of UnsafeStaticObjectFieldAccessorImpl for exception throwing    @run main UnsafeStaticObjectFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeStaticObjectFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetBooleanThrowsException();
    }

    private static void testGetBooleanThrowsException() {
        try {
            // Create an instance of the class under test
            Class<?> clazz = Class.forName("jdk.internal.reflect.UnsafeStaticObjectFieldAccessorImpl");
            Field field = clazz.getDeclaredField("field"); // Dummy, not used in method
            Object instance = clazz.getConstructor(Field.class).newInstance((Field) null); // Dummy, not used
            // Call getBoolean, expecting an IllegalArgumentException
            ((java.lang.reflect.Method) clazz.getMethod("getBoolean", Object.class)).invoke(instance, new Object());
            // Since the method throws exception directly, the above line should throw
            // If no exception, fail the test
            System.err.println("Expected IllegalArgumentException was not thrown.");
            System.exit(1);
        } catch (java.lang.reflect.InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof IllegalArgumentException) {
                // Test passes
                System.out.println("getBoolean correctly threw IllegalArgumentException.");
            } else {
                System.err.println("Unexpected exception: " + cause);
                System.exit(1);
            }
        } catch (Exception e) {
            System.err.println("Test failed with exception: " + e);
            System.exit(1);
        }
    }
}
