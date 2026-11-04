/* @test    @summary Test getBoolean method of UnsafeQualifiedStaticObjectFieldAccessorImpl    @run main UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetBooleanThrowsException();
    }

    private static void testGetBooleanThrowsException() {
        try {
            // Create an instance of the class
            Class<?> clazz = Class.forName("jdk.internal.reflect.UnsafeQualifiedStaticObjectFieldAccessorImpl");
            // Obtain the method object for getBoolean
            java.lang.reflect.Method method = clazz.getDeclaredMethod("getBoolean", Object.class);
            // Instantiate the class with null parameters (simulate constructor)
            java.lang.reflect.Constructor<?> constructor = clazz.getDeclaredConstructor(Field.class, boolean.class);
            constructor.setAccessible(true);
            Object instance = constructor.newInstance(null, false);
            // Invoke getBoolean method
            boolean result = (Boolean) method.invoke(instance, new Object[]{null});
            // Since the method always throws, reaching this line indicates failure
            System.err.println("Expected exception was not thrown, result: " + result);
        } catch (java.lang.reflect.InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof IllegalArgumentException) {
                // Expected exception
                System.out.println("getBoolean threw IllegalArgumentException as expected.");
            } else {
                System.err.println("Unexpected exception cause: " + cause);
            }
        } catch (Exception e) {
            System.err.println("Test failed with exception: " + e);
        }
    }
}
