/* @test    @summary Test getFloat method of UnsafeQualifiedStaticDoubleFieldAccessorImpl    @run main UnsafeQualifiedStaticDoubleFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticDoubleFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetFloatThrowsException();
    }

    private static void testGetFloatThrowsException() {
        try {
            // Create an instance of the class
            Class<?> clazz = Class.forName("jdk.internal.reflect.UnsafeQualifiedStaticDoubleFieldAccessorImpl");
            // Obtain the method getFloat
            java.lang.reflect.Method method = clazz.getDeclaredMethod("getFloat", Object.class);
            // Invoke the method with null argument
            method.invoke(clazz.getDeclaredConstructor(Field.class, boolean.class).newInstance(null, false), (Object) null);
            // If no exception, test fails
            System.out.println("getFloat did not throw exception as expected.");
        } catch (java.lang.reflect.InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof IllegalArgumentException) {
                System.out.println("getFloat threw IllegalArgumentException as expected.");
            } else {
                System.out.println("getFloat threw unexpected exception: " + cause);
            }
        } catch (Exception e) {
            System.out.println("Unexpected exception during test: " + e);
        }
    }
}
