/* @test    @summary Test getChar method of UnsafeByteFieldAccessorImpl that throws IllegalArgumentException */
/* @run main UnsafeByteFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeByteFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetCharThrowsException();
    }

    public static void testGetCharThrowsException() throws Exception {
        class Dummy {
        }
        Field dummyField = Dummy.class.getDeclaredField("dummy");
        dummyField.setAccessible(true);
        jdk.internal.reflect.UnsafeByteFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeByteFieldAccessorImpl(dummyField);
        try {
            accessor.getChar(new Object());
            throw new AssertionError("Expected IllegalArgumentException was not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
