/* @test    @summary Test getShort method of UnsafeQualifiedStaticLongFieldAccessorImpl class    @run main UnsafeQualifiedStaticLongFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticLongFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetShortReturnsException();
    }

    private static void testGetShortReturnsException() throws Exception {
        // Obtain the class object
        Class<?> clazz = Class.forName("jdk.internal.reflect.UnsafeQualifiedStaticLongFieldAccessorImpl");
        // Obtain the method getShort
        java.lang.reflect.Method getShortMethod = clazz.getDeclaredMethod("getShort", Object.class);
        // Instantiate the class with dummy parameters
        // Since constructor is package-private, use reflection to access it
        Constructor<?> constructor = clazz.getDeclaredConstructor(Field.class, boolean.class);
        constructor.setAccessible(true);
        // Create a dummy Field object (null, as it won't be used)
        Field dummyField = null;
        Object accessorInstance = constructor.newInstance(dummyField, false);
        // Call getShort and expect an IllegalArgumentException
        try {
            getShortMethod.setAccessible(true);
            getShortMethod.invoke(accessorInstance, new Object[]{null});
            // If no exception, test fails
            throw new AssertionError("Expected IllegalArgumentException was not thrown.");
        } catch (java.lang.reflect.InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (!(cause instanceof IllegalArgumentException)) {
                throw new AssertionError("Unexpected exception cause: " + cause, cause);
            }
            // Test passes
        }
    }
}
