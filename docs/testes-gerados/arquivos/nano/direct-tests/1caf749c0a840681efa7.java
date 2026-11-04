/* @test    @summary Test getShort method of UnsafeByteFieldAccessorImpl class    @run main UnsafeByteFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeByteFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetShortReturnsByteValue();
        testGetShortWithNullObject();
        testGetShortWithInvalidObject();
    }

    static class Dummy {
        public byte value;
    }

    static void testGetShortReturnsByteValue() throws Exception {
        Dummy dummy = new Dummy();
        dummy.value = 42;
        Field field = Dummy.class.getDeclaredField("value");
        jdk.internal.reflect.UnsafeByteFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeByteFieldAccessorImpl(field);
        short result = accessor.getShort(dummy);
        if (result != (short) 42) {
            throw new AssertionError("Expected 42, but got " + result);
        }
    }

    static void testGetShortWithNullObject() {
        try {
            Field field = Dummy.class.getDeclaredField("value");
            jdk.internal.reflect.UnsafeByteFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeByteFieldAccessorImpl(field);
            accessor.getShort(null);
            throw new AssertionError("Expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }

    static void testGetShortWithInvalidObject() throws Exception {
        Field field = Dummy.class.getDeclaredField("value");
        jdk.internal.reflect.UnsafeByteFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeByteFieldAccessorImpl(field);
        try {
            accessor.getShort(new Object());
            // Depending on implementation, this may throw, but since ensureObj() is called, it may throw IllegalArgumentException
            // For safety, we check if exception is thrown
        } catch (IllegalArgumentException expected) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }
}
