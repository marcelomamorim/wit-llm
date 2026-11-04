/* @test    @summary Test getLong method of UnsafeQualifiedObjectFieldAccessorImpl class    @run main UnsafeQualifiedObjectFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeQualifiedObjectFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetLongReturnsException();
    }

    private static void testGetLongReturnsException() {
        class Dummy {
        }
        Field dummyField = Dummy.class.getDeclaredFields()[0];
        // Since the source code shows getLong always throws, we test that calling getLong throws IllegalArgumentException
        jdk.internal.reflect.UnsafeQualifiedObjectFieldAccessorImpl accessor = 
            new jdk.internal.reflect.UnsafeQualifiedObjectFieldAccessorImpl(dummyField, false);
        try {
            accessor.getLong(new Object());
            throw new AssertionError("Expected IllegalArgumentException was not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
