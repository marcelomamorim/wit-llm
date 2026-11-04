/* @test    @summary Test getChar method of UnsafeIntegerFieldAccessorImpl class    @run main UnsafeIntegerFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeIntegerFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetCharThrowsException();
    }

    private static void testGetCharThrowsException() {
        try {
            // Reflection to access the method
            Class<?> clazz = Class.forName("jdk.internal.reflect.UnsafeIntegerFieldAccessorImpl");
            Field field = clazz.getDeclaredField("field"); // Dummy, not used
            // Instantiate with dummy field (simulate constructor)
            Object instance = clazz.getConstructor(Field.class).newInstance((Field) null);
            // Invoke getChar method
            clazz.getMethod("getChar", Object.class).invoke(instance, new Object());
            // If no exception, test fails
            throw new AssertionError("Expected IllegalArgumentException was not thrown.");
        } catch (java.lang.reflect.InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (!(cause instanceof IllegalArgumentException)) {
                throw new AssertionError("Unexpected exception type: " + cause, cause);
            }
            // Test passes if IllegalArgumentException is thrown
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception during test execution", e);
        }
    }
}
