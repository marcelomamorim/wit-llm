/* @test    @summary Test getShort method of UnsafeQualifiedIntegerFieldAccessorImpl class    @run main UnsafeQualifiedIntegerFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeQualifiedIntegerFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetShortThrowsException();
    }

    private static void testGetShortThrowsException() {
        class Dummy {
        }
        Field dummyField = Dummy.class.getDeclaredFields().length > 0 ? Dummy.class.getDeclaredFields()[0] : null;
        // Since getShort always throws, we do not need an actual field instance
        jdk.internal.reflect.UnsafeQualifiedIntegerFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeQualifiedIntegerFieldAccessorImpl(dummyField, false);
        try {
            accessor.getShort(new Object());
            throw new AssertionError("Expected IllegalArgumentException was not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
