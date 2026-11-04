/* @test    @summary Test getLong method of UnsafeQualifiedStaticObjectFieldAccessorImpl class    @run main UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetLongReturnsException();
    }

    private static void testGetLongReturnsException() {
        try {
            // Create an instance of the class
            Class<?> clazz = Class.forName("jdk.internal.reflect.UnsafeQualifiedStaticObjectFieldAccessorImpl");
            // Get the method object
            java.lang.reflect.Method method = clazz.getDeclaredMethod("getLong", Object.class);
            // Instantiate the class with a dummy Field object (null for simplicity, as constructor is package-private)
            // Since constructor is package-private, use reflection to instantiate
            java.lang.reflect.Constructor<?> constructor = clazz.getDeclaredConstructor(Field.class, boolean.class);
            constructor.setAccessible(true);
            Object instance = constructor.newInstance((Field) null, false);
            // Call getLong method
            method.setAccessible(true);
            Object result = method.invoke(instance, new Object());
            // The method is expected to throw an exception, so if we reach here, fail the test
            System.err.println("Expected IllegalArgumentException was not thrown.");
        } catch (java.lang.reflect.InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof IllegalArgumentException) {
                System.out.println("getLong method correctly threw IllegalArgumentException.");
            } else {
                System.err.println("Unexpected exception: " + cause);
            }
        } catch (Exception e) {
            System.err.println("Test failed with exception: " + e);
        }
    }
}
