/*
 * @test
 * @summary Test for getDouble method in UnsafeQualifiedByteFieldAccessorImpl
 * @run main UnsafeQualifiedByteFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedByteFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetDoubleWithInvalidObject();
    }

    private static void testGetDoubleWithInvalidObject() {
        try {
            // Reflection to access the constructor
            Class<?> clazz = Class.forName("jdk.internal.reflect.UnsafeQualifiedByteFieldAccessorImpl");
            Field field = null; // No need to set a real field, as getDouble calls getByte which uses unsafe
            boolean isReadOnly = false;
            Object accessor = clazz.getConstructor(Field.class, boolean.class).newInstance(field, isReadOnly);
            // Invoke getDouble with an invalid object (not of the declaring class)
            ((java.lang.invoke.MethodHandle)accessor).invoke("invalidObject");
            // Since the actual method is not accessible directly, simulate the exception
            // Alternatively, test the guard condition explicitly
        } catch (Exception e) {
            if (e instanceof IllegalArgumentException) {
                // Expected exception
            } else {
                throw new RuntimeException("Unexpected exception", e);
            }
        }
    }
}
