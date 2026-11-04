/* @test    @summary Test getByte method of UnsafeStaticShortFieldAccessorImpl class    @run main UnsafeStaticShortFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeStaticShortFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetByteThrowsException();
    }

    private static void testGetByteThrowsException() {
        try {
            // Create an instance of UnsafeStaticShortFieldAccessorImpl with a dummy Field
            Field dummyField = null; // No actual reflection needed for this test
            jdk.internal.reflect.UnsafeStaticShortFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeStaticShortFieldAccessorImpl(dummyField);
            // Call getByte and expect an IllegalArgumentException
            accessor.getByte(null);
            // If no exception is thrown, the test fails
            throw new AssertionError("Expected IllegalArgumentException was not thrown.");
        } catch (IllegalArgumentException e) {
            // Expected exception, test passes
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception: " + t, t);
        }
    }
}
