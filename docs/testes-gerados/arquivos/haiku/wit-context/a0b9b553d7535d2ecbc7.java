/*
 * @test
 * @summary Witup: Field.getByte on a volatile byte field reaches UnsafeQualifiedByteFieldAccessorImpl.getByte and rejects objects of unrelated types
 * @run main UnsafeQualifiedByteFieldAccessorImplWitupTest
 */
import java.lang.reflect.Field;

public class UnsafeQualifiedByteFieldAccessorImplWitupTest {
    private volatile byte value = 42;

    private static void fail(String message) {
        throw new RuntimeException(message);
    }

    public static void main(String[] args) throws Exception {
        UnsafeQualifiedByteFieldAccessorImplWitupTest target = new UnsafeQualifiedByteFieldAccessorImplWitupTest();
        Field field = UnsafeQualifiedByteFieldAccessorImplWitupTest.class.getDeclaredField("value");
        field.setAccessible(true);

        byte initial = field.getByte(target);
        if (initial != 42) {
            fail("Expected 42 but got " + initial);
        }

        field.setByte(target, (byte) 7);
        byte updated = field.getByte(target);
        if (updated != 7) {
            fail("Expected 7 after setByte but got " + updated);
        }

        try {
            field.getByte(new Object());
            fail("Expected IllegalArgumentException for object of unrelated type");
        } catch (IllegalArgumentException expected) {
            // expected: receiver is not an instance of the declaring class
        }
    }
}
