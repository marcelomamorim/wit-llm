/*
 * @test
 * @summary Test UnsafeLongFieldAccessorImpl.getBoolean throws expected IllegalArgumentException
 * @run main UnsafeLongFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeLongFieldAccessorImpl;

public class UnsafeLongFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetBooleanThrows();
    }

    private static void testGetBooleanThrows() {
        try {
            Field dummyField = Dummy.class.getDeclaredField("l");
            UnsafeLongFieldAccessorImpl accessor = new UnsafeLongFieldAccessorImpl(dummyField);
            accessor.getBoolean(new Dummy());
            throw new RuntimeException("Expected IllegalArgumentException was not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("Test setup failed: field not found", e);
        }
    }

    static class Dummy {
        public long l = 0L;
    }
}
