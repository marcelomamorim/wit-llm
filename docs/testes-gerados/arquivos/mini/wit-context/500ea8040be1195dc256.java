/*
 * @test
 * @summary Test UnsafeQualifiedDoubleFieldAccessorImpl.getByte throws IllegalArgumentException
 * @run main UnsafeQualifiedDoubleFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedDoubleFieldAccessorImpl;

public class UnsafeQualifiedDoubleFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetByteThrows();
    }

    private static void testGetByteThrows() {
        try {
            Field dummyField = Dummy.class.getDeclaredFields()[0];
            UnsafeQualifiedDoubleFieldAccessorImpl accessor = new UnsafeQualifiedDoubleFieldAccessorImpl(dummyField, false);
            accessor.getByte(new Dummy());
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception type", e);
        }
    }

    static class Dummy {
        public double value = 0.0;
    }
}
