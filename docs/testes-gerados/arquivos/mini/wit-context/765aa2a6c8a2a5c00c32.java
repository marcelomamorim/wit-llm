/*
 * @test
 * @summary Test UnsafeBooleanFieldAccessorImpl.getShort throws IllegalArgumentException
 * @run main UnsafeBooleanFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeBooleanFieldAccessorImpl;

public class UnsafeBooleanFieldAccessorImplWitupTest {
    public static void main(String[] args) {
        try {
            // Create a dummy Field instance via reflection to pass to constructor
            Field dummyField = Dummy.class.getDeclaredField("flag");
            UnsafeBooleanFieldAccessorImpl accessor = new UnsafeBooleanFieldAccessorImpl(dummyField);

            // Call getShort and expect IllegalArgumentException
            try {
                accessor.getShort(new Dummy());
                throw new RuntimeException("Expected IllegalArgumentException not thrown");
            } catch (IllegalArgumentException expected) {
                // Expected exception
            }

            System.out.println("Test passed.");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Test failed due to unexpected exception");
        }
    }

    static class Dummy {
        boolean flag = true;
    }
}
