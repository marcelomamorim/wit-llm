/* @test    @summary Test for getLong method in UnsafeLongFieldAccessorImpl    @run main UnsafeLongFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeLongFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetLongReturnsCorrectValue();
        testGetLongWithNullObjectThrows();
    }

    static class Dummy {
        public long value = 123456789L;
    }

    static void testGetLongReturnsCorrectValue() throws Exception {
        Dummy dummy = new Dummy();
        Field field = Dummy.class.getField("value");
        jdk.internal.reflect.UnsafeLongFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeLongFieldAccessorImpl(field);
        long result = accessor.getLong(dummy);
        if (result != dummy.value) {
            throw new AssertionError("Expected " + dummy.value + " but got " + result);
        }
    }

    static void testGetLongWithNullObjectThrows() {
        try {
            Field field = Dummy.class.getField("value");
            jdk.internal.reflect.UnsafeLongFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeLongFieldAccessorImpl(field);
            accessor.getLong(null);
            throw new AssertionError("Expected IllegalArgumentException for null object");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }
}
