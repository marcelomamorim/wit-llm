/*
 * @test
 * @summary Verify that Field.getBoolean on a static short field throws IllegalArgumentException, covering the UnsafeStaticShortFieldAccessorImpl.getBoolean path
 * @run main UnsafeStaticShortFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeStaticShortFieldAccessorImplWitupTest {
    private static short value = 42;

    public static void main(String[] args) throws Exception {
        Field f = UnsafeStaticShortFieldAccessorImplWitupTest.class.getDeclaredField("value");
        for (int i = 0; i < 20; i++) {
            try {
                f.getBoolean(null);
                throw new RuntimeException("Expected IllegalArgumentException at iteration " + i);
            } catch (IllegalArgumentException expected) {
            }
        }
        if (f.getShort(null) != 42) {
            throw new RuntimeException("Static short value was modified by failed getBoolean calls");
        }
    }
}
