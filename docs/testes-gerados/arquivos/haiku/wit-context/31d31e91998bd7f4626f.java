/*
 * @test
 * @summary Verify Field.getChar on a static volatile long field reaches the qualified static long accessor and throws IllegalArgumentException
 * @run main UnsafeQualifiedStaticLongFieldAccessorImplWitupTest
 */
import java.lang.reflect.Field;

public class UnsafeQualifiedStaticLongFieldAccessorImplWitupTest {
    public static volatile long value = 42L;

    public static void main(String[] args) throws Exception {
        Field f = UnsafeQualifiedStaticLongFieldAccessorImplWitupTest.class.getDeclaredField("value");
        expectIllegalArgument(f, null);
        expectIllegalArgument(f, new Object());
        if (f.getLong(null) != 42L) {
            throw new AssertionError("getLong mismatch");
        }
    }

    static void expectIllegalArgument(Field f, Object obj) {
        try {
            f.getChar(obj);
            throw new AssertionError("expected IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
        }
    }
}
