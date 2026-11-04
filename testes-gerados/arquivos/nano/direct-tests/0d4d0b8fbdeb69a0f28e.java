/* @test    @summary Test getChar method of UnsafeQualifiedObjectFieldAccessorImpl class    @run main UnsafeQualifiedObjectFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeQualifiedObjectFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetCharReturnsException();
    }

    private static void testGetCharReturnsException() throws Exception {
        class Dummy {
            public char dummyChar;
        }
        Field field = Dummy.class.getDeclaredField("dummyChar");
        boolean isReadOnly = false;
        jdk.internal.reflect.UnsafeQualifiedObjectFieldAccessorImpl accessor =
            new jdk.internal.reflect.UnsafeQualifiedObjectFieldAccessorImpl(field, isReadOnly);
        try {
            accessor.getChar(new Dummy());
            // Expecting an exception, so if no exception, fail the test
            throw new AssertionError("Expected getChar to throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Test passes if exception is thrown
        }
    }
}
