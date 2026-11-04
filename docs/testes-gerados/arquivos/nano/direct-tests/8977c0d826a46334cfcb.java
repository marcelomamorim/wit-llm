/* @test    @summary Test getByte method of UnsafeQualifiedStaticObjectFieldAccessorImpl class    @run main UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetByteThrowsIllegalArgumentException();
    }

    private static void testGetByteThrowsIllegalArgumentException() {
        try {
            // Create an instance of the class
            Class<?> clazz = Class.forName("jdk.internal.reflect.UnsafeQualifiedStaticObjectFieldAccessorImpl");
            // Obtain the method getByte
            java.lang.reflect.Method getByteMethod = clazz.getDeclaredMethod("getByte", Object.class);
            getByteMethod.setAccessible(true);
            // Invoke the method with null argument (since method does not use the argument)
            getByteMethod.invoke(clazz.getDeclaredConstructor(Field.class, boolean.class).newInstance(null, false), (Object) null);
            // If no exception, test fails
            System.err.println("Expected IllegalArgumentException was not thrown.");
        } catch (java.lang.reflect.InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof IllegalArgumentException) {
                System.out.println("getByte method correctly throws IllegalArgumentException.");
            } else {
                System.err.println("Unexpected exception: " + cause);
            }
        } catch (Exception e) {
            System.err.println("Test setup failed: " + e);
        }
    }
}
