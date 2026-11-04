/* @test    @summary Test getShort method of UnsafeStaticBooleanFieldAccessorImpl class    @run main UnsafeStaticBooleanFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeStaticBooleanFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetShortThrowsException();
    }

    private static void testGetShortThrowsException() {
        try {
            // Create an instance of the class under test
            Class<?> clazz = Class.forName("jdk.internal.reflect.UnsafeStaticBooleanFieldAccessorImpl");
            // Obtain the constructor that takes a Field parameter
            java.lang.reflect.Constructor<?> constructor = clazz.getDeclaredConstructor(Field.class);
            constructor.setAccessible(true);
            // Pass null as Field to instantiate (simulate invalid state)
            Object instance = constructor.newInstance((Field) null);
            // Call getShort method
            java.lang.reflect.Method getShortMethod = clazz.getMethod("getShort", Object.class);
            getShortMethod.invoke(instance, new Object());
            // If no exception, the test fails
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
