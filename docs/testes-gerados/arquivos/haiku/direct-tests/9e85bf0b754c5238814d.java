/*
 * @test
 * @summary Verify Unsafe.staticFieldBase rejects null Field and returns a non-null base for a static field
 * @modules java.base/jdk.internal.misc
 * @run main UnsafeWitupTest
 */
import java.lang.reflect.Field;
import jdk.internal.misc.Unsafe;

public class UnsafeWitupTest {
    static int sfield;

    public static void main(String[] args) throws Exception {
        Unsafe unsafe = Unsafe.getUnsafe();

        boolean npeThrown = false;
        try {
            unsafe.staticFieldBase(null);
        } catch (NullPointerException expected) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new RuntimeException("expected NullPointerException for null Field");
        }

        Field f = UnsafeWitupTest.class.getDeclaredField("sfield");
        Object base = unsafe.staticFieldBase(f);
        if (base == null) {
            throw new RuntimeException("staticFieldBase returned null for a static field");
        }
    }
}
