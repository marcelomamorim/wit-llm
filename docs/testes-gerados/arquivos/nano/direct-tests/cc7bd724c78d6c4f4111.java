/* @test    @summary Test getDouble method of UnsafeByteFieldAccessorImpl class    @run main UnsafeByteFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeByteFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetDoubleReturnsByteValue();
        testGetDoubleWithNullObject();
    }

    static class Dummy {
        public byte value = 42;
    }

    static class UnsafeByteFieldAccessorImpl {
        private final Field field;
        private final long fieldOffset;
        private final sun.misc.Unsafe unsafe;
        private final boolean isFinal;

        UnsafeByteFieldAccessorImpl(Field field) throws Exception {
            this.field = field;
            this.fieldOffset = sun.misc.Unsafe.class.getDeclaredMethod("objectFieldOffset", Field.class)
                .invoke(sun.misc.Unsafe.getUnsafe(), field);
            this.unsafe = sun.misc.Unsafe.getUnsafe();
            this.isFinal = java.lang.reflect.Modifier.isFinal(field.getModifiers());
        }

        public double getDouble(Object obj) throws IllegalArgumentException {
            return getByte(obj);
        }

        public byte getByte(Object obj) throws IllegalArgumentException {
            if (obj == null) {
                throw new NullPointerException();
            }
            return unsafe.getByte(obj, fieldOffset);
        }
    }

    static void testGetDoubleReturnsByteValue() throws Exception {
        Dummy dummy = new Dummy();
        Field field = Dummy.class.getField("value");
        UnsafeByteFieldAccessorImpl accessor = new UnsafeByteFieldAccessorImpl(field);
        double result = accessor.getDouble(dummy);
        assert result == (double) dummy.value : "Expected " + (double) dummy.value + " but got " + result;
    }

    static void testGetDoubleWithNullObject() throws Exception {
        Field field = Dummy.class.getField("value");
        UnsafeByteFieldAccessorImpl accessor = new UnsafeByteFieldAccessorImpl(field);
        try {
            accessor.getDouble(null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
