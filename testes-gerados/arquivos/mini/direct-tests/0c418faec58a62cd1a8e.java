/*
 * @test
 * @summary Test UnsafeLongFieldAccessorImpl.getByte throws IllegalArgumentException
 * @run main UnsafeLongFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeLongFieldAccessorImpl;

public class UnsafeLongFieldAccessorImplWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a dummy field to pass to constructor
        Field dummyField = Dummy.class.getDeclaredField("value");
        UnsafeLongFieldAccessorImpl accessor = new UnsafeLongFieldAccessorImpl(dummyField);

        try {
            accessor.getByte(new Dummy());
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    static class Dummy {
        public long value = 123L;
    }
}
