/* @test    @summary Test getByte method of UnsafeQualifiedStaticFloatFieldAccessorImpl class.    @run main UnsafeQualifiedStaticFloatFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticFloatFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetByteThrowsException();
    }

    public static void testGetByteThrowsException() {
        try {
            // Create an instance of the class under test
            Class<?> clazz = Class.forName("jdk.internal.reflect.UnsafeQualifiedStaticFloatFieldAccessorImpl");
            // Obtain the getByte method
            java.lang.reflect.Method method = clazz.getDeclaredMethod("getByte", Object.class);
            // Instantiate the class with null parameters (simulate constructor call)
            java.lang.reflect.Constructor<?> constructor = clazz.getDeclaredConstructor(Field.class, boolean.class);
            constructor.setAccessible(true);
            Object instance = constructor.newInstance((Field) null, false);
            // Invoke getByte with null argument
            method.invoke(instance, (Object) null);
            // If no exception, test fails
            System.err.println("Expected IllegalArgumentException was not thrown.");
        } catch (java.lang.reflect.InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof IllegalArgumentException) {
                System.out.println("getByte() threw IllegalArgumentException as expected.");
            } else {
                System.err.println("Unexpected exception: " + cause);
            }
        } catch (Exception e) {
            System.err.println("Test setup failed: " + e);
        }
    }
}
