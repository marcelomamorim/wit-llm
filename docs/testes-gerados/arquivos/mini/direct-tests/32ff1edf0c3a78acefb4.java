/*
 * @test
 * @summary Test UnsafeIntegerFieldAccessorImpl.getChar throws IllegalArgumentException
 * @run main UnsafeIntegerFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeIntegerFieldAccessorImpl;

public class UnsafeIntegerFieldAccessorImplWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a dummy field to pass to constructor
        Field field = Dummy.class.getDeclaredField("dummyInt");
        UnsafeIntegerFieldAccessorImpl accessor = new UnsafeIntegerFieldAccessorImpl(field);

        try {
            accessor.getChar(new Dummy());
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    static class Dummy {
        int dummyInt = 42;
    }
}
