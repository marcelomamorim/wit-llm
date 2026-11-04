/* @test    @summary Test getBoolean method of UnsafeQualifiedLongFieldAccessorImpl class    @run main UnsafeQualifiedLongFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeQualifiedLongFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetBooleanThrowsException();
    }

    private static void testGetBooleanThrowsException() throws Exception {
        // Setup: create an instance of UnsafeQualifiedLongFieldAccessorImpl with a dummy Field
        Field dummyField = null; // placeholder, as actual field is not used in getBoolean
        boolean isReadOnly = false;
        jdk.internal.reflect.UnsafeQualifiedLongFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeQualifiedLongFieldAccessorImpl(dummyField, isReadOnly);
        try {
            accessor.getBoolean(new Object());
            // If no exception, test fails
            throw new AssertionError("Expected IllegalArgumentException was not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception, test passes
        }
    }
}
