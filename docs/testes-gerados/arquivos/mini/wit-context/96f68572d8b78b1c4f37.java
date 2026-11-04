/*
 * @test
 * @summary Test UnsafeLongFieldAccessorImpl.getShort always throws IllegalArgumentException
 * @run main UnsafeLongFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeLongFieldAccessorImpl;

public class UnsafeLongFieldAccessorImplWitupTest {
    public static void main(String[] args) {
        try {
            Field dummyField = Dummy.class.getDeclaredField("l");
            UnsafeLongFieldAccessorImpl acc = new UnsafeLongFieldAccessorImpl(dummyField);
            try {
                acc.getShort(new Dummy());
                throw new RuntimeException("Expected IllegalArgumentException not thrown");
            } catch (IllegalArgumentException e) {
                // expected
            }
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("Test setup failed: field not found", e);
        }
    }

    static class Dummy {
        public long l = 0L;
    }
}
