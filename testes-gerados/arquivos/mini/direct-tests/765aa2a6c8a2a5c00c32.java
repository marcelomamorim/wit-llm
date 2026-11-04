/*
 * @test
 * @summary Test UnsafeBooleanFieldAccessorImpl.getShort throws IllegalArgumentException
 * @run main UnsafeBooleanFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeBooleanFieldAccessorImpl;

public class UnsafeBooleanFieldAccessorImplWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a dummy boolean field to pass to the constructor
        Field field = Dummy.class.getDeclaredField("flag");
        UnsafeBooleanFieldAccessorImpl accessor = new UnsafeBooleanFieldAccessorImpl(field);

        try {
            accessor.getShort(new Dummy());
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    static class Dummy {
        boolean flag = true;
    }
}
