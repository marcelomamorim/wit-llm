/* @test    @summary Test getByte method of UnsafeStaticObjectFieldAccessorImpl class for exception throwing behavior    @run main UnsafeStaticObjectFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeStaticObjectFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetByteThrowsIllegalArgumentException();
    }

    private static void testGetByteThrowsIllegalArgumentException() {
        try {
            // Create an instance of the class under test
            Field dummyField = null; // placeholder, as actual field is not used in getByte
            jdk.internal.reflect.UnsafeStaticObjectFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeStaticObjectFieldAccessorImpl(dummyField);
            // Call getByte method, expecting it to throw IllegalArgumentException
            accessor.getByte(null);
            // If no exception is thrown, the test fails
            throw new AssertionError("Expected IllegalArgumentException was not thrown.");
        } catch (IllegalArgumentException e) {
            // Expected exception caught, test passes
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception type: " + t, t);
        }
    }
}
