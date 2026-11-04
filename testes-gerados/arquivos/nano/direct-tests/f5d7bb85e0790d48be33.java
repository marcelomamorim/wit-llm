/* @test    @summary Test getByte method of UnsafeQualifiedStaticLongFieldAccessorImpl class    @run main UnsafeQualifiedStaticLongFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticLongFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetByteThrowsException();
    }

    private static void testGetByteThrowsException() throws Exception {
        // Obtain the class
        Class<?> clazz = Class.forName("jdk.internal.reflect.UnsafeQualifiedStaticLongFieldAccessorImpl");
        // Obtain the method getByte
        java.lang.reflect.Method getByteMethod = clazz.getDeclaredMethod("getByte", Object.class);
        // Instantiate the class with dummy parameters
        // Since constructor is package-private, use reflection
        Constructor<?> constructor = clazz.getDeclaredConstructor(Field.class, boolean.class);
        constructor.setAccessible(true);
        // Pass null for Field and false for isReadOnly
        Object instance = constructor.newInstance((Object) null, false);
        // Invoke getByte method, expecting it to throw IllegalArgumentException
        try {
            getByteMethod.invoke(instance, new Object());
            // If no exception, fail the test
            System.err.println("Expected IllegalArgumentException was not thrown.");
            System.exit(1);
        } catch (java.lang.reflect.InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof IllegalArgumentException) {
                // Test passes
                System.out.println("getByte() threw IllegalArgumentException as expected.");
            } else {
                System.err.println("Unexpected exception: " + cause);
                System.exit(1);
            }
        }
    }
}
