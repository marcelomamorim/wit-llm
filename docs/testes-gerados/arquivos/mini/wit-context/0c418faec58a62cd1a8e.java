/*
 * @test
 * @summary Test UnsafeLongFieldAccessorImpl.getByte throws expected IllegalArgumentException
 * @run main UnsafeLongFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeLongFieldAccessorImpl;

public class UnsafeLongFieldAccessorImplWitupTest {

    static class Dummy {
        long value = 123L;
    }

    public static void main(String[] args) throws Exception {
        Field field = Dummy.class.getDeclaredField("value");
        UnsafeLongFieldAccessorImpl accessor = new UnsafeLongFieldAccessorImpl(field);

        try {
            accessor.getByte(new Dummy());
            throw new RuntimeException("Expected IllegalArgumentException not thrown by getByte");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
