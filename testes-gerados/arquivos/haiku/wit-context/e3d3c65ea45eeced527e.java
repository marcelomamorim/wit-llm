/*
 * @test
 * @summary Verify that Field.getByte on a static long field throws IllegalArgumentException through UnsafeStaticLongFieldAccessorImpl.getByte
 * @run main UnsafeStaticLongFieldAccessorImplWitupTest
 */
import java.lang.reflect.Field;

public class UnsafeStaticLongFieldAccessorImplWitupTest {
    static long value = 42L;

    public static void main(String[] args) throws Exception {
        Field f = UnsafeStaticLongFieldAccessorImplWitupTest.class.getDeclaredField("value");

        if (f.getLong(null) != 42L) {
            throw new RuntimeException("getLong returned unexpected value");
        }

        try {
            f.getByte(null);
            throw new RuntimeException("expected IllegalArgumentException from getByte(null)");
        } catch (IllegalArgumentException expected) {
        }

        try {
            f.getByte(new Object());
            throw new RuntimeException("expected IllegalArgumentException from getByte(Object)");
        } catch (IllegalArgumentException expected) {
        }
    }
}
