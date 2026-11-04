/*
 * @test
 * @summary Field.getShort on a static int field must throw IllegalArgumentException, repeated enough times to exercise the inflated accessor path
 * @run main UnsafeStaticIntegerFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeStaticIntegerFieldAccessorImplWitupTest {
    public static int value = 42;

    public static void main(String[] args) throws Exception {
        Field f = UnsafeStaticIntegerFieldAccessorImplWitupTest.class.getField("value");
        for (int i = 0; i < 50; i++) {
            try {
                f.getShort(null);
                throw new RuntimeException("expected IllegalArgumentException at iteration " + i);
            } catch (IllegalArgumentException expected) {
                // expected
            }
        }
        if (f.getInt(null) != 42) {
            throw new RuntimeException("getInt returned unexpected value");
        }
    }
}
