/* @test    @summary Test getBoolean method of UnsafeStaticLongFieldAccessorImpl class    @run main UnsafeStaticLongFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeStaticLongFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetBooleanThrowsException();
    }

    public static void testGetBooleanThrowsException() {
        try {
            // Reflection to obtain the method
            Class<?> clazz = Class.forName("jdk.internal.reflect.UnsafeStaticLongFieldAccessorImpl");
            Field field = clazz.getDeclaredField("field"); // Dummy, as method does not use it
            // Instantiate the class with a dummy Field object
            // Since constructor is package-private, simulate via reflection
            // But constructor is package-private, so we need to access it reflectively
            java.lang.reflect.Constructor<?> constructor = clazz.getDeclaredConstructor(Field.class);
            constructor.setAccessible(true);
            Object instance = constructor.newInstance((Field) null); // null as dummy
            // Call getBoolean method
            java.lang.reflect.Method getBooleanMethod = clazz.getMethod("getBoolean", Object.class);
            // Expecting an IllegalArgumentException to be thrown
            getBooleanMethod.invoke(instance, new Object());
            // If no exception, test fails
            System.err.println("Expected IllegalArgumentException was not thrown.");
        } catch (java.lang.reflect.InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof IllegalArgumentException) {
                System.out.println("Test passed: getBoolean threw IllegalArgumentException as expected.");
            } else {
                System.err.println("Test failed: Unexpected exception cause: " + cause);
            }
        } catch (NoSuchMethodException | NoSuchFieldException | InstantiationException | IllegalAccessException e) {
            System.err.println("Test setup failed: " + e);
        }
    }
}
