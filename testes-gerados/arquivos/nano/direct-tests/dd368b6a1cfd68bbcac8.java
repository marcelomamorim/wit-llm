/* @test    @summary Test getInt method of UnsafeObjectFieldAccessorImpl class    @run main UnsafeObjectFieldAccessorImplWitupTest */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeObjectFieldAccessorImpl;

public class UnsafeObjectFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetIntReturnsException();
    }

    private static void testGetIntReturnsException() {
        Field dummyField = null;
        try {
            dummyField = DummyClass.class.getDeclaredField("value");
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
        UnsafeObjectFieldAccessorImpl accessor = new UnsafeObjectFieldAccessorImpl(dummyField);
        try {
            accessor.getInt(new DummyClass());
            throw new AssertionError("Expected IllegalArgumentException was not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    static class DummyClass {
        public Object value;
    }
}
