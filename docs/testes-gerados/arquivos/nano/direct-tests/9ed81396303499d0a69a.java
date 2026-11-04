/* @test    @summary Test getShort method of UnsafeQualifiedByteFieldAccessorImpl class    @run main UnsafeQualifiedByteFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeQualifiedByteFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetShortReturnsByteValue();
        testGetShortWithNullObjectThrows();
        testGetShortWithInvalidObjectThrows();
    }

    static class Dummy {
        public byte value = 42;
    }

    static void testGetShortReturnsByteValue() throws Exception {
        Field field = Dummy.class.getDeclaredField("value");
        field.setAccessible(true);
        Dummy dummy = new Dummy();
        jdk.internal.reflect.UnsafeQualifiedByteFieldAccessorImpl accessor =
            new jdk.internal.reflect.UnsafeQualifiedByteFieldAccessorImpl(field, false);
        short result = accessor.getShort(dummy);
        if (result != (short) 42) {
            throw new AssertionError("Expected 42, but got " + result);
        }
    }

    static void testGetShortWithNullObjectThrows() {
        try {
            Field field = Dummy.class.getDeclaredField("value");
            field.setAccessible(true);
            jdk.internal.reflect.UnsafeQualifiedByteFieldAccessorImpl accessor =
                new jdk.internal.reflect.UnsafeQualifiedByteFieldAccessorImpl(field, false);
            accessor.getShort(null);
            throw new AssertionError("Expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }

    static void testGetShortWithInvalidObjectThrows() {
        try {
            Field field = Dummy.class.getDeclaredField("value");
            field.setAccessible(true);
            jdk.internal.reflect.UnsafeQualifiedByteFieldAccessorImpl accessor =
                new jdk.internal.reflect.UnsafeQualifiedByteFieldAccessorImpl(field, false);
            accessor.getShort(new Object());
            throw new AssertionError("Expected IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            // expected
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }
}
