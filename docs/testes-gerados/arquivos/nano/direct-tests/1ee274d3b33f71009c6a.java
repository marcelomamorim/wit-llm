/* @test    @summary Test getShort method of UnsafeStaticIntegerFieldAccessorImpl class    @run main UnsafeStaticIntegerFieldAccessorImplWitupTest */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeStaticIntegerFieldAccessorImpl;

public class UnsafeStaticIntegerFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetShortReturnsExpectedException();
    }

    private static void testGetShortReturnsExpectedException() {
        try {
            Field dummyField = null; // No actual field needed for this test
            UnsafeStaticIntegerFieldAccessorImpl accessor = new UnsafeStaticIntegerFieldAccessorImpl(dummyField);
            accessor.getShort(null);
            throw new AssertionError("Expected IllegalArgumentException was not thrown");
        } catch (IllegalArgumentException e) {
            // Test passes if exception is thrown
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception type: " + t, t);
        }
    }
}
