/* @test    @summary Test getByte method of UnsafeQualifiedShortFieldAccessorImpl for exception throwing behavior    @run main UnsafeQualifiedShortFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeQualifiedShortFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetByteThrowsException();
    }

    private static void testGetByteThrowsException() throws Exception {
        class Dummy {
            public short s;
        }
        Dummy dummy = new Dummy();
        Field field = Dummy.class.getDeclaredField("s");
        // Simulate the behavior of getByte by directly calling the method and expecting an exception
        jdk.internal.reflect.UnsafeQualifiedShortFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeQualifiedShortFieldAccessorImpl(field, false);
        try {
            accessor.getByte(dummy);
            throw new AssertionError("Expected IllegalArgumentException was not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
