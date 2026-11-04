/*
 * @test
 * @summary Test UnsafeStaticLongFieldAccessorImpl.getChar throws IllegalArgumentException
 * @run main UnsafeStaticLongFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeStaticLongFieldAccessorImpl;

public class UnsafeStaticLongFieldAccessorImplWitupTest {
    public static void main(String[] args) {
        try {
            Field f = Dummy.class.getDeclaredField("dummyLong");
            UnsafeStaticLongFieldAccessorImpl accessor = new UnsafeStaticLongFieldAccessorImpl(f);
            try {
                accessor.getChar(null);
                throw new RuntimeException("Expected IllegalArgumentException not thrown");
            } catch (IllegalArgumentException e) {
                // expected
            }
            System.out.println("Test passed");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Test failed", e);
        }
    }

    static class Dummy {
        static long dummyLong = 123L;
    }
}
