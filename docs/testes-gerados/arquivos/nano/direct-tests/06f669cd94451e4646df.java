/* @test    @summary Test getShort method of UnsafeStaticLongFieldAccessorImpl class    @run main UnsafeStaticLongFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeStaticLongFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetShortThrowsException();
    }

    private static void testGetShortThrowsException() {
        class Dummy {
        }
        try {
            Field dummyField = Dummy.class.getDeclaredField("dummy");
            dummyField.setAccessible(true);
            // Create an instance of UnsafeStaticLongFieldAccessorImpl with a dummy field
            jdk.internal.reflect.UnsafeStaticLongFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeStaticLongFieldAccessorImpl(dummyField);
            // Call getShort, expecting an IllegalArgumentException
            accessor.getShort(new Object());
            // If no exception, the test fails
            throw new AssertionError("Expected IllegalArgumentException was not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception, test passes
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception: " + t, t);
        }
    }
}
