/*
 * @test
 * @summary Verify that Field.getChar on byte fields throws IllegalArgumentException and that get still returns the byte value
 * @run main UnsafeQualifiedByteFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedByteFieldAccessorImplWitupTest {
    public byte b = 1;
    public final byte fb = 2;

    public static void main(String[] args) throws Exception {
        UnsafeQualifiedByteFieldAccessorImplWitupTest t = new UnsafeQualifiedByteFieldAccessorImplWitupTest();
        checkGetCharThrows(t, "b");
        checkGetCharThrows(t, "fb");
        checkGetReturnsByte(t, "b", (byte) 1);
        checkGetReturnsByte(t, "fb", (byte) 2);
    }

    static void checkGetCharThrows(Object obj, String name) throws Exception {
        Field f = UnsafeQualifiedByteFieldAccessorImplWitupTest.class.getDeclaredField(name);
        try {
            f.getChar(obj);
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new RuntimeException("Expected IllegalArgumentException from getChar on byte field " + name);
    }

    static void checkGetReturnsByte(Object obj, String name, byte expected) throws Exception {
        Field f = UnsafeQualifiedByteFieldAccessorImplWitupTest.class.getDeclaredField(name);
        Object value = f.get(obj);
        if (!(value instanceof Byte) || ((Byte) value).byteValue() != expected) {
            throw new RuntimeException("Unexpected value from get for field " + name + ": " + value);
        }
    }
}
