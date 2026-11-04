/*
 * @test
 * @summary Test UnsafeShortFieldAccessorImpl.getChar throws IllegalArgumentException
 * @run main UnsafeShortFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeShortFieldAccessorImpl;

public class UnsafeShortFieldAccessorImplWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a dummy field to pass to constructor
        Field dummyField = Dummy.class.getDeclaredField("dummyShort");
        UnsafeShortFieldAccessorImpl accessor = new UnsafeShortFieldAccessorImpl(dummyField);

        try {
            accessor.getChar(new Dummy());
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    static class Dummy {
        short dummyShort = 1;
    }
}
