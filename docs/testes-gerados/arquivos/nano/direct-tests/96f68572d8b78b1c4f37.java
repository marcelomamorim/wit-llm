/* @test    @summary Test getShort method of UnsafeLongFieldAccessorImpl class    @run main UnsafeLongFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeLongFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetShortThrowsException();
    }

    private static void testGetShortThrowsException() {
        try {
            // Create an instance of UnsafeLongFieldAccessorImpl with a dummy Field
            Field dummyField = String.class.getDeclaredField("value");
            jdk.internal.reflect.UnsafeLongFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeLongFieldAccessorImpl(dummyField);
            // Call getShort, expecting an IllegalArgumentException
            accessor.getShort(new Object());
            // If no exception, the test fails
            throw new AssertionError("Expected IllegalArgumentException was not thrown.");
        } catch (IllegalArgumentException e) {
            // Expected exception, test passes
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e, e);
        }
    }
}
