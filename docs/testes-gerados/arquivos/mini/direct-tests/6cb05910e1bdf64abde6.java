/*
 * @test
 * @summary Test getByte method throws IllegalArgumentException as expected
 * @run main UnsafeQualifiedStaticShortFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedStaticShortFieldAccessorImpl;

public class UnsafeQualifiedStaticShortFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        // Create a dummy Field instance for constructor (using reflection)
        Field field = Dummy.class.getDeclaredField("dummyShort");

        // Create instance with isReadOnly = false
        UnsafeQualifiedStaticShortFieldAccessorImpl accessor = new UnsafeQualifiedStaticShortFieldAccessorImpl(field, false);

        try {
            accessor.getByte(null);
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    static class Dummy {
        static short dummyShort = 0;
    }
}
