/*
 * @test
 * @summary Test UnsafeQualifiedDoubleFieldAccessorImpl.getBoolean throws expected IllegalArgumentException
 * @run main UnsafeQualifiedDoubleFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedDoubleFieldAccessorImpl;

public class UnsafeQualifiedDoubleFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetBooleanThrows();
    }

    private static void testGetBooleanThrows() {
        try {
            Field dummyField = Dummy.class.getDeclaredFields()[0];
            UnsafeQualifiedDoubleFieldAccessorImpl accessor = new UnsafeQualifiedDoubleFieldAccessorImpl(dummyField, false);
            accessor.getBoolean(new Dummy());
            throw new RuntimeException("Expected IllegalArgumentException was not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception type: " + e, e);
        }
    }

    static class Dummy {
        public double value = 0.0;
    }
}
