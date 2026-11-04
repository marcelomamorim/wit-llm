/* @test    @summary Test getDouble method of UnsafeStaticObjectFieldAccessorImpl class    @run main UnsafeStaticObjectFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeStaticObjectFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetDoubleThrowsException();
    }

    private static void testGetDoubleThrowsException() {
        try {
            // Create an instance of the class
            Class<?> clazz = Class.forName("jdk.internal.reflect.UnsafeStaticObjectFieldAccessorImpl");
            // Obtain the method getDouble
            java.lang.reflect.Method method = clazz.getDeclaredMethod("getDouble", Object.class);
            // Instantiate the class with a dummy Field (null, since constructor is package-private and inaccessible)
            // As constructor is package-private, use reflection to instantiate
            java.lang.reflect.Constructor<?> constructor = clazz.getDeclaredConstructor(Field.class);
            constructor.setAccessible(true);
            Object instance = constructor.newInstance((Field) null);
            // Invoke getDouble method
            method.setAccessible(true);
            method.invoke(instance, new Object());
            // Since getDouble throws IllegalArgumentException, reaching here indicates failure
            System.err.println("Expected IllegalArgumentException was not thrown.");
        } catch (java.lang.reflect.InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof IllegalArgumentException) {
                System.out.println("getDouble correctly threw IllegalArgumentException.");
            } else {
                System.err.println("Unexpected exception: " + cause);
            }
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException e) {
            System.err.println("Reflection error during test setup: " + e);
        }
    }
}
