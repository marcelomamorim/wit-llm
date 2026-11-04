/* @test    @summary Test getInt method of UnsafeQualifiedStaticObjectFieldAccessorImpl class    @run main UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetIntThrowsException();
    }

    private static void testGetIntThrowsException() {
        try {
            // Create a dummy instance of the class
            Class<?> clazz = Class.forName("jdk.internal.reflect.UnsafeQualifiedStaticObjectFieldAccessorImpl");
            // Obtain the method getInt
            java.lang.reflect.Method method = clazz.getDeclaredMethod("getInt", Object.class);
            // Instantiate the class with dummy parameters
            Field dummyField = null; // Not used in getInt, so can be null
            Object instance = clazz.getConstructor(Field.class, boolean.class).newInstance(dummyField, false);
            // Call getInt and expect an exception
            int result = (int) method.invoke(instance, new Object());
            // If no exception, the test fails
            System.err.println("Expected IllegalArgumentException was not thrown.");
            System.exit(1);
        } catch (java.lang.reflect.InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof IllegalArgumentException) {
                System.out.println("getInt() threw IllegalArgumentException as expected.");
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
