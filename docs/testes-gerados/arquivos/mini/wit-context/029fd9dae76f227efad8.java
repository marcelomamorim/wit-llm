/*
 * @test
 * @summary Test that UnsafeQualifiedBooleanFieldAccessorImpl.getFloat always throws IllegalArgumentException
 * @run main UnsafeQualifiedBooleanFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedBooleanFieldAccessorImpl;

public class UnsafeQualifiedBooleanFieldAccessorImplWitupTest {

    static class Dummy {
        public boolean boolField = true;
    }

    public static void main(String[] args) {
        try {
            Field f = Dummy.class.getField("boolField");
            UnsafeQualifiedBooleanFieldAccessorImpl accessor = new UnsafeQualifiedBooleanFieldAccessorImpl(f, false);
            try {
                accessor.getFloat(new Dummy());
                throw new RuntimeException("Expected IllegalArgumentException not thrown by getFloat");
            } catch (IllegalArgumentException expected) {
                // Expected exception
            }
        } catch (Exception e) {
            throw new RuntimeException("Test failed with unexpected exception", e);
        }
    }
}
