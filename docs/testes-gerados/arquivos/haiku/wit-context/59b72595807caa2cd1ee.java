/*
 * @test
 * @summary Field.getChar on a byte field must throw IllegalArgumentException, exercising UnsafeByteFieldAccessorImpl.getChar
 * @run main UnsafeByteFieldAccessorImplWitupTest
 */
import java.lang.reflect.Field;

public class UnsafeByteFieldAccessorImplWitupTest {
    public byte b;

    public static void main(String[] args) throws Exception {
        UnsafeByteFieldAccessorImplWitupTest t = new UnsafeByteFieldAccessorImplWitupTest();
        Field f = UnsafeByteFieldAccessorImplWitupTest.class.getField("b");

        t.b = 7;
        if (((Byte) f.get(t)).byteValue() != 7) {
            throw new RuntimeException("get(Object) of byte field returned unexpected value");
        }

        try {
            f.getChar(t);
            throw new RuntimeException("expected IllegalArgumentException from getChar on byte field");
        } catch (IllegalArgumentException expected) {
        }

        t.b = 0;
        try {
            f.getChar(t);
            throw new RuntimeException("expected IllegalArgumentException from getChar on byte field (value 0)");
        } catch (IllegalArgumentException expected) {
        }
    }
}
