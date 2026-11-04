/*
 * @test
 * @summary Verifies that reading a static boolean field as double through public reflection throws IllegalArgumentException
 * @run main UnsafeStaticBooleanFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeStaticBooleanFieldAccessorImplWitupTest {

    public static boolean flag = true;

    public static void main(String[] args) throws Exception {
        Field field = UnsafeStaticBooleanFieldAccessorImplWitupTest.class.getField("flag");
        boolean thrown = false;
        try {
            field.getDouble(null);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException from getDouble on boolean static field");
        }
    }
}
