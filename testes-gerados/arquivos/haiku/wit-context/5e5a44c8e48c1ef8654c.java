/*
 * @test
 * @summary Verify Field.getChar on a volatile byte field reaches the qualified byte accessor and throws IllegalArgumentException
 * @run main UnsafeQualifiedByteFieldAccessorImplWitupTest
 */
import java.lang.reflect.Field;

public class UnsafeQualifiedByteFieldAccessorImplWitupTest {
    private volatile byte b = 42;

    public static void main(String[] args) throws Exception {
        UnsafeQualifiedByteFieldAccessorImplWitupTest t = new UnsafeQualifiedByteFieldAccessorImplWitupTest();
        Field f = UnsafeQualifiedByteFieldAccessorImplWitupTest.class.getDeclaredField("b");
        if (f.getByte(t) != 42) {
            throw new RuntimeException("getByte returned unexpected value");
        }
        boolean thrown = false;
        try {
            f.getChar(t);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected IllegalArgumentException from getChar");
        }
    }
}
