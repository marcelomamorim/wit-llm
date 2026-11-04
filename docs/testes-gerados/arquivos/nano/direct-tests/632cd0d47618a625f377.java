/* @test    @summary Test getInt method of UnsafeQualifiedStaticFloatFieldAccessorImpl class    @run main UnsafeQualifiedStaticFloatFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticFloatFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetIntReturnsException();
    }

    private static void testGetIntReturnsException() {
        try {
            // Create a dummy instance of the class
            Class<?> clazz = Class.forName("jdk.internal.reflect.UnsafeQualifiedStaticFloatFieldAccessorImpl");
            // Access the method getInt
            java.lang.reflect.Method method = clazz.getDeclaredMethod("getInt", Object.class);
            method.setAccessible(true);
            // Invoke the method with null (since it doesn't use the parameter)
            Object instance = null;
            method.invoke(instance, (Object) null);
            // If no exception, test fails
            System.out.println("FAIL: getInt did not throw exception as expected.");
        } catch (java.lang.reflect.InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof IllegalArgumentException) {
                System.out.println("PASS: getInt threw IllegalArgumentException as expected.");
            } else {
                System.out.println("FAIL: getInt threw unexpected exception: " + cause);
            }
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception during test: " + e);
        }
    }
}
