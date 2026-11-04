/* @test    @summary Test getByte method of UnsafeStaticDoubleFieldAccessorImpl for exception throwing behavior    @run main UnsafeStaticDoubleFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeStaticDoubleFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetByteThrowsException();
    }

    private static void testGetByteThrowsException() throws Exception {
        class Dummy {
        }
        Field dummyField = Dummy.class.getDeclaredField("dummy");
        dummyField.setAccessible(true);
        jdk.internal.reflect.UnsafeStaticDoubleFieldAccessorImpl accessor = 
            new jdk.internal.reflect.UnsafeStaticDoubleFieldAccessorImpl(dummyField);
        try {
            accessor.getByte(new Object());
            throw new AssertionError("Expected IllegalArgumentException was not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
