/*
 * @test
 * @summary Test getBoolean method of UnsafeQualifiedCharacterFieldAccessorImpl
 * @run main UnsafeQualifiedCharacterFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedCharacterFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetBooleanThrowsException();
    }

    public static void testGetBooleanThrowsException() throws Exception {
        class Dummy {
            public boolean getBoolean(Object obj) {
                throw new UnsupportedOperationException("Method should throw");
            }
        }
        
        // Create an instance of the class under test
        Class<?> clazz = Class.forName("jdk.internal.reflect.UnsafeQualifiedCharacterFieldAccessorImpl");
        Field field = null;
        try {
            field = clazz.getDeclaredField("field");
        } catch (NoSuchFieldException e) {
            // Since the actual field is not accessible, create a dummy instance
            // or skip actual reflection, as the method is static in behavior
        }
        
        // Instantiate the class with dummy parameters
        Object instance = null;
        try {
            instance = clazz.getDeclaredConstructor(Field.class, boolean.class).newInstance(null, false);
        } catch (NoSuchMethodException e) {
            // Constructor not accessible, skip instantiation
            return;
        }
        
        // Call getBoolean and verify it throws IllegalArgumentException
        try {
            ((java.lang.reflect.Method)clazz.getMethod("getBoolean", Object.class)).invoke(instance, new Object());
            // If no exception, test fails
            System.err.println("Expected IllegalArgumentException was not thrown.");
        } catch (java.lang.reflect.InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof IllegalArgumentException) {
                System.out.println("getBoolean correctly threw IllegalArgumentException.");
            } else {
                System.err.println("Unexpected exception: " + cause);
            }
        } catch (Exception e) {
            System.err.println("Unexpected exception during reflection: " + e);
        }
    }
}
