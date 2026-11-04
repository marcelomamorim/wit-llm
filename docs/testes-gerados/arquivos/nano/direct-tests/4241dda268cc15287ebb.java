/* @test    @summary Test getShort method of UnsafeQualifiedFloatFieldAccessorImpl class    @run main UnsafeQualifiedFloatFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeQualifiedFloatFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetShortThrowsException();
    }

    private static void testGetShortThrowsException() {
        class Dummy {
        }
        Field dummyField = Dummy.class.getDeclaredFields().length > 0 ? Dummy.class.getDeclaredFields()[0] : null;
        // Since getShort always throws, we do not need an actual field instance
        jdk.internal.reflect.UnsafeQualifiedFloatFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeQualifiedFloatFieldAccessorImpl(dummyField, false);
        try {
            accessor.getShort(new Object());
            throw new AssertionError("Expected IllegalArgumentException was not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
