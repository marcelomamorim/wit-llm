/* @test    @summary Test getByte method of UnsafeStaticLongFieldAccessorImpl for exception throwing    @run main UnsafeStaticLongFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeStaticLongFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetByteThrowsException();
    }

    private static void testGetByteThrowsException() throws Exception {
        Field dummyField = null; // Placeholder, as getByte always throws
        jdk.internal.reflect.UnsafeStaticLongFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeStaticLongFieldAccessorImpl(dummyField);
        try {
            accessor.getByte(null);
            System.out.println("FAIL: Expected IllegalArgumentException was not thrown.");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: getByte threw IllegalArgumentException as expected.");
        }
    }
}
