/* @test    @summary Test getChar method throws IllegalArgumentException as expected    @run main UnsafeQualifiedStaticBooleanFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticBooleanFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetCharThrowsException();
    }

    private static void testGetCharThrowsException() {
        try {
            // Create an instance of the class under test
            Class<?> clazz = Class.forName("jdk.internal.reflect.UnsafeQualifiedStaticBooleanFieldAccessorImpl");
            // Obtain the getChar method
            java.lang.reflect.Method getCharMethod = clazz.getDeclaredMethod("getChar", Object.class);
            // Invoke the method with null (since it ignores the argument)
            getCharMethod.invoke(clazz.getDeclaredConstructor(Field.class, boolean.class).newInstance(null, false), (Object) null);
            // If no exception, the test fails
            throw new AssertionError("Expected IllegalArgumentException was not thrown.");
        } catch (java.lang.reflect.InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (!(cause instanceof IllegalArgumentException)) {
                throw new AssertionError("Unexpected exception type: " + cause, cause);
            }
            // Expected exception caught, test passes
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception during test execution", e);
        }
    }
}
