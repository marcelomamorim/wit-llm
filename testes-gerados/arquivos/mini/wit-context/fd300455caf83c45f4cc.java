/*
 * @test
 * @summary Test UnsafeByteFieldAccessorImpl.getBoolean throws expected IllegalArgumentException
 * @run main UnsafeByteFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeByteFieldAccessorImpl;

public class UnsafeByteFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetBooleanThrows();
    }

    private static void testGetBooleanThrows() {
        try {
            // Create a dummy Field instance via reflection to pass to constructor
            Field dummyField = Dummy.class.getDeclaredField("b");
            UnsafeByteFieldAccessorImpl accessor = new UnsafeByteFieldAccessorImpl(dummyField);
            accessor.getBoolean(new Dummy());
            throw new RuntimeException("Expected IllegalArgumentException was not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("Test setup failed: field not found", e);
        }
    }

    static class Dummy {
        public byte b = 1;
    }
}
