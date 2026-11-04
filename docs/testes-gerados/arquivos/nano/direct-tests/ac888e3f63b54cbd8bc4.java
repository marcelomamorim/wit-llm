/* @test    @summary Test getShort method of UnsafeQualifiedStaticObjectFieldAccessorImpl class    @run main UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetShortReturnsException();
    }

    private static void testGetShortReturnsException() {
        try {
            // Create an instance of the class
            Class<?> clazz = Class.forName("jdk.internal.reflect.UnsafeQualifiedStaticObjectFieldAccessorImpl");
            // Obtain the method object for getShort
            java.lang.reflect.Method method = clazz.getDeclaredMethod("getShort", Object.class);
            // Instantiate the class with dummy parameters
            Field dummyField = null; // Not used in method invocation
            Object instance = null; // No constructor, so null
            // Invoke getShort method
            Object result = method.invoke(null, (Object) null);
            // Since the method always throws, reaching here indicates failure
            System.err.println("Expected exception was not thrown.");
            System.exit(1);
        } catch (java.lang.reflect.InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof IllegalArgumentException) {
                // Expected exception
                System.out.println("getShort() threw IllegalArgumentException as expected.");
            } else {
                System.err.println("Unexpected exception: " + cause);
                System.exit(1);
            }
        } catch (NoSuchMethodException | ClassNotFoundException | IllegalAccessException e) {
            System.err.println("Test setup failed: " + e);
            System.exit(1);
        }
    }
}
