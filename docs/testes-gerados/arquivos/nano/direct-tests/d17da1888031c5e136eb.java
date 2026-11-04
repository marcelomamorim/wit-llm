/* @test    @summary Test getFloat method throws IllegalArgumentException as expected    @run main UnsafeStaticBooleanFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeStaticBooleanFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetFloatThrowsException();
    }

    private static void testGetFloatThrowsException() {
        try {
            // Create an instance of the class with the method to test
            Class<?> clazz = Class.forName("jdk.internal.reflect.UnsafeStaticBooleanFieldAccessorImpl");
            // Obtain a Field object for the class (simulate with null or dummy, as actual field access is not needed)
            Field dummyField = null;
            // Instantiate the class (simulate with null, as constructor is package-private and inaccessible)
            // Instead, directly instantiate via reflection if accessible, or skip instantiation and test method directly
            // Since constructor is package-private, and no public constructor, we simulate by subclassing or reflection
            // For simplicity, assume we can instantiate via reflection (if accessible), else skip
            // But as per constraints, focus on method behavior, so invoke getFloat with null
            // Create an anonymous subclass or dummy class if needed, but here, directly call method via reflection
            // Alternatively, create a dummy subclass in test code to access protected method
            // For simplicity, use reflection to invoke getFloat
            java.lang.reflect.Method getFloatMethod = clazz.getDeclaredMethod("getFloat", Object.class);
            getFloatMethod.setAccessible(true);
            Object instance = null; // Since constructor is package-private, assume null for static context
            // Call method and expect IllegalArgumentException
            getFloatMethod.invoke(instance, new Object[]{null});
            // If no exception, test fails
            System.err.println("Expected IllegalArgumentException was not thrown.");
        } catch (java.lang.reflect.InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof IllegalArgumentException) {
                System.out.println("Test passed: getFloat throws IllegalArgumentException as expected.");
            } else {
                System.err.println("Test failed: Unexpected exception cause: " + cause);
            }
        } catch (NoSuchMethodException | IllegalAccessException e) {
            System.err.println("Test setup failed: " + e);
        }
    }
}
