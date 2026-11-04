/*
 * @test
 * @summary Test for UnsafeQualifiedObjectFieldAccessorImpl.get method exception path
 * @run main UnsafeQualifiedObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedObjectFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetThrowsIllegalArgumentExceptionWhenObjectIsIncompatible();
    }

    private static void testGetThrowsIllegalArgumentExceptionWhenObjectIsIncompatible() throws Exception {
        // Setup: create a dummy field and accessor
        Field dummyField = null;
        try {
            dummyField = String.class.getDeclaredField("value");
        } catch (NoSuchFieldException e) {
            // Should not happen, as String has 'value' field
        }
        // Instantiate UnsafeQualifiedObjectFieldAccessorImpl with dummy field
        jdk.internal.reflect.UnsafeQualifiedObjectFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeQualifiedObjectFieldAccessorImpl(dummyField, false);
        Object incompatibleObject = new Object();
        try {
            accessor.get(incompatibleObject);
            // If no exception, test fails
            System.err.println("Expected IllegalArgumentException was not thrown.");
            System.exit(1);
        } catch (IllegalArgumentException e) {
            // Expected exception
            System.out.println("Caught expected IllegalArgumentException.");
        } catch (Throwable t) {
            System.err.println("Unexpected exception: " + t);
            System.exit(1);
        }
    }
}
