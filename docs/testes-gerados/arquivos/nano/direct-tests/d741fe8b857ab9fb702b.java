/* @test    @summary Test getShort method of UnsafeStaticFloatFieldAccessorImpl class    @run main UnsafeStaticFloatFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeStaticFloatFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetShortThrowsException();
    }

    private static void testGetShortThrowsException() {
        try {
            // Create an instance of the class under test
            Class<?> clazz = Class.forName("jdk.internal.reflect.UnsafeStaticFloatFieldAccessorImpl");
            // Obtain the constructor that takes a Field parameter
            java.lang.reflect.Constructor<?> constructor = clazz.getDeclaredConstructor(Field.class);
            constructor.setAccessible(true);
            // Pass null as Field to instantiate (not used in getShort, but required for constructor)
            Object instance = constructor.newInstance((Object) null);
            // Call getShort method
            java.lang.reflect.Method method = clazz.getMethod("getShort", Object.class);
            method.invoke(instance, new Object());
            // If no exception, the test fails
            throw new AssertionError("Expected IllegalArgumentException was not thrown");
        } catch (java.lang.reflect.InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (!(cause instanceof IllegalArgumentException)) {
                throw new AssertionError("Unexpected exception cause: " + cause, cause);
            }
            // Test passes if IllegalArgumentException is thrown
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException e) {
            throw new AssertionError("Reflection setup failed", e);
        } catch (ClassNotFoundException e) {
            throw new AssertionError("Class not found", e);
        }
    }
}
