/* @test    @summary Test getInt method of UnsafeShortFieldAccessorImpl class    @run main UnsafeShortFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeShortFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetIntReturnsCorrectValue();
        testGetIntWithNullObject();
    }

    static class Dummy {
        public short value = 12345;
    }

    static void testGetIntReturnsCorrectValue() throws Exception {
        Dummy dummy = new Dummy();
        Field field = Dummy.class.getField("value");
        jdk.internal.reflect.UnsafeShortFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeShortFieldAccessorImpl(field);
        int result = accessor.getInt(dummy);
        if (result != (int) dummy.value) {
            throw new AssertionError("Expected " + (int) dummy.value + " but got " + result);
        }
    }

    static void testGetIntWithNullObject() {
        Field field = null;
        try {
            field = Dummy.class.getField("value");
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
        jdk.internal.reflect.UnsafeShortFieldAccessorImpl accessor;
        try {
            accessor = new jdk.internal.reflect.UnsafeShortFieldAccessorImpl(field);
            accessor.getInt(null);
            throw new AssertionError("Expected NullPointerException for null object");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
