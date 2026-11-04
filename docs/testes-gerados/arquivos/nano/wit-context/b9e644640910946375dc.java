/* @test    @summary Test for getLong method in UnsafeQualifiedShortFieldAccessorImpl
 * @run main UnsafeQualifiedShortFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedShortFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetLongWithInvalidObject();
    }

    public static void testGetLongWithInvalidObject() throws Exception {
        // Setup: create a dummy Field object (mocked or dummy, as actual creation is complex)
        // Since the actual constructor is package-private and complex, we simulate the behavior by
        // creating a subclass or using reflection to access a real Field. Here, we assume a valid Field.
        // For the purpose of this test, we focus on the exception path.
        Field dummyField = null;
        try {
            dummyField = String.class.getDeclaredField("value");
        } catch (NoSuchFieldException e) {
            // Should not happen, as String has a "value" field
        }
        // Create an instance of UnsafeQualifiedShortFieldAccessorImpl
        // Note: The constructor is package-private; in real tests, use reflection or a test-specific subclass.
        // Here, we assume access for simplicity.
        Class<?> clazz = Class.forName("jdk.internal.reflect.UnsafeQualifiedShortFieldAccessorImpl");
        java.lang.reflect.Constructor<?> ctor = clazz.getDeclaredConstructor(Field.class, boolean.class);
        ctor.setAccessible(true);
        Object accessor = ctor.newInstance(dummyField, false);
        // Test: invoke getLong with an object of incompatible type
        Object invalidObj = new Object();
        try {
            // The method getLong is package-private; use reflection to invoke
            java.lang.reflect.Method getLongMethod = clazz.getMethod("getLong", Object.class);
            getLongMethod.invoke(accessor, invalidObj);
            // If no exception, test fails
            throw new AssertionError("Expected IllegalArgumentException was not thrown");
        } catch (java.lang.reflect.InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (!(cause instanceof IllegalArgumentException)) {
                throw new AssertionError("Unexpected exception type: " + cause, cause);
            }
            // Test passes
        }
    }
}
