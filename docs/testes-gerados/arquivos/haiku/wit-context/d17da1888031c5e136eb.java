/*
 * @test
 * @summary Verify that Field.getFloat on a static boolean field throws IllegalArgumentException through the public reflection API
 * @run main UnsafeStaticBooleanFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeStaticBooleanFieldAccessorImplWitupTest {
    static boolean flag = true;

    public static void main(String[] args) throws Exception {
        Field f = UnsafeStaticBooleanFieldAccessorImplWitupTest.class.getDeclaredField("flag");
        try {
            float v = f.getFloat(null);
            throw new RuntimeException("Expected IllegalArgumentException, got " + v);
        } catch (IllegalArgumentException expected) {
        }
    }
}
