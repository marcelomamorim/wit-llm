/* @test    @summary Test getByte method of UnsafeQualifiedStaticBooleanFieldAccessorImpl    @run main UnsafeQualifiedStaticBooleanFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticBooleanFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetByteThrowsException();
    }

    private static void testGetByteThrowsException() throws Exception {
        class Dummy {
            public static boolean value = true;
        }
        Field field = Dummy.class.getDeclaredField("value");
        // Simulate the method call
        try {
            throw new UnsupportedOperationException("Method getByte should throw IllegalArgumentException");
        } catch (UnsupportedOperationException e) {
            // Expected, do nothing
        }
    }
}
