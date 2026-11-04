/*
 * @test
 * @summary Field.getBoolean on a static short field must throw IllegalArgumentException, repeatedly
 * @run main UnsafeStaticShortFieldAccessorImplWitupTest
 */
import java.lang.reflect.Field;

public class UnsafeStaticShortFieldAccessorImplWitupTest {
    public static short sf = 42;

    public static void main(String[] args) throws Exception {
        Field f = UnsafeStaticShortFieldAccessorImplWitupTest.class.getField("sf");
        for (int i = 0; i < 20; i++) {
            try {
                boolean b = f.getBoolean(null);
                throw new AssertionError("expected IllegalArgumentException, got " + b + " at iteration " + i);
            } catch (IllegalArgumentException expected) {
                // expected
            }
        }
        if (f.getShort(null) != 42) {
            throw new AssertionError("getShort mismatch");
        }
    }
}
