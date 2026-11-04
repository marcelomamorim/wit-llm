/*
 * @test
 * @summary Test getFloat method of UnsafeQualifiedBooleanFieldAccessorImpl
 * @run main UnsafeQualifiedBooleanFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedBooleanFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetFloatThrowsException();
    }

    private static void testGetFloatThrowsException() {
        try {
            // Create a dummy instance of the class
            Class<?> clazz = Class.forName("jdk.internal.reflect.UnsafeQualifiedBooleanFieldAccessorImpl");
            // Obtain the method object for getFloat
            java.lang.reflect.Method method = clazz.getDeclaredMethod("getFloat", Object.class);
            method.setAccessible(true);
            // Invoke the method with null or dummy object, expecting an exception
            method.invoke(null, new Object());
            // If no exception, test fails
            System.out.println("FAIL: getFloat did not throw exception as expected.");
        } catch (java.lang.reflect.InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof IllegalArgumentException) {
                System.out.println("PASS: getFloat threw IllegalArgumentException as expected.");
            } else {
                System.out.println("FAIL: getFloat threw unexpected exception: " + cause);
            }
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }
}
