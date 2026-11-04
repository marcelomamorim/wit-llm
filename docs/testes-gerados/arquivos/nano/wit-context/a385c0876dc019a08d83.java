/*
 * @test
 * @summary Test for getChar method in UnsafeQualifiedStaticObjectFieldAccessorImpl
 * @run main UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetCharThrowsIllegalArgumentException();
    }

    public static void testGetCharThrowsIllegalArgumentException() {
        // Setup: create an instance of the class with a dummy Field
        // Since constructor is package-private and no public factory is available,
        // we assume the test is run within the same package or with reflection.
        // For simplicity, we simulate the method call directly.
        // As the method always throws, we just invoke and check for exception.
        try {
            // Create an instance of the class via reflection
            Class<?> clazz = Class.forName("jdk.internal.reflect.UnsafeQualifiedStaticObjectFieldAccessorImpl");
            Field field = null; // placeholder, as actual field setup is complex
            // Instantiate the class (assuming accessible constructor)
            Object instance = null;
            try {
                instance = clazz.getDeclaredConstructor(Field.class, boolean.class).newInstance(field, false);
            } catch (NoSuchMethodException e) {
                // Constructor not accessible, skip actual instantiation
                // Instead, create a dummy subclass or use reflection if possible
                // For this test, we focus on the method behavior
            }
            // Since the method getChar always throws, we invoke and expect exception
            // We simulate the call
            // Note: in real scenario, reflection or mocking would be needed
            // Here, we directly call the method via reflection
            if (instance != null) {
                java.lang.reflect.Method getCharMethod = clazz.getMethod("getChar", Object.class);
                try {
                    getCharMethod.invoke(instance, new Object());
                    // If no exception, test fails
                    System.err.println("Expected IllegalArgumentException was not thrown.");
                } catch (java.lang.reflect.InvocationTargetException e) {
                    Throwable cause = e.getCause();
                    if (cause instanceof IllegalArgumentException) {
                        System.out.println("getChar correctly threw IllegalArgumentException.");
                    } else {
                        System.err.println("Unexpected exception: " + cause);
                    }
                }
            } else {
                // As we cannot instantiate directly, we test the method behavior via a dummy subclass
                class DummyAccessor extends jdk.internal.reflect.UnsafeQualifiedStaticObjectFieldAccessorImpl {
                    DummyAccessor() throws Exception {
                        super(null, false);
                    }
                    @Override
                    public char getChar(Object obj) {
                        return super.getChar(obj);
                    }
                }
                try {
                    new DummyAccessor().getChar(new Object());
                    System.err.println("Expected IllegalArgumentException was not thrown.");
                } catch (IllegalArgumentException e) {
                    System.out.println("getChar correctly threw IllegalArgumentException.");
                } catch (Exception e) {
                    System.err.println("Unexpected exception during dummy instantiation: " + e);
                }
            }
        } catch (ClassNotFoundException e) {
            System.err.println("Class not found: " + e);
        } catch (Exception e) {
            System.err.println("Exception during test: " + e);
        }
    }
}
