/* @test    @summary Test getByte method of UnsafeQualifiedStaticCharacterFieldAccessorImpl    @run main UnsafeQualifiedStaticCharacterFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticCharacterFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetByteThrowsException();
    }

    private static void testGetByteThrowsException() {
        try {
            // Create an instance of the class under test
            Class<?> clazz = Class.forName("jdk.internal.reflect.UnsafeQualifiedStaticCharacterFieldAccessorImpl");
            // Obtain the getByte method
            java.lang.reflect.Method getByteMethod = clazz.getDeclaredMethod("getByte", Object.class);
            // Instantiate the class with dummy parameters
            Field dummyField = null; // Not used in this test, so can be null
            Object instance = null; // Not used in static context, but constructor may require
            // Since constructor is package-private, use reflection to instantiate
            java.lang.reflect.Constructor<?> constructor = clazz.getDeclaredConstructor(Field.class, boolean.class);
            constructor.setAccessible(true);
            Object accessor = constructor.newInstance(dummyField, true);
            // Call getByte method, expecting it to throw an exception
            getByteMethod.setAccessible(true);
            getByteMethod.invoke(accessor, new Object());
            // If no exception, test fails
            throw new AssertionError("Expected IllegalArgumentException was not thrown.");
        } catch (java.lang.reflect.InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (!(cause instanceof IllegalArgumentException)) {
                throw new AssertionError("Unexpected exception type: " + cause, cause);
            }
            // Test passes if IllegalArgumentException is thrown
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException e) {
            throw new AssertionError("Reflection setup failed", e);
        }
    }
}
