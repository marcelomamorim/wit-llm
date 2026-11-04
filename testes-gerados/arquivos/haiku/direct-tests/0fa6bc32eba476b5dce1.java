/*
 * @test
 * @summary Verify that Field.getFloat on a boolean field throws IllegalArgumentException, while Field.getBoolean still works
 * @run main UnsafeBooleanFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeBooleanFieldAccessorImplWitupTest {
    public boolean flag = true;

    public static void main(String[] args) throws Exception {
        UnsafeBooleanFieldAccessorImplWitupTest target = new UnsafeBooleanFieldAccessorImplWitupTest();
        Field field = UnsafeBooleanFieldAccessorImplWitupTest.class.getDeclaredField("flag");

        if (!field.getBoolean(target)) {
            throw new RuntimeException("getBoolean should return the field value true");
        }

        boolean thrown = false;
        try {
            field.getFloat(target);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("getFloat on a boolean field should throw IllegalArgumentException");
        }
    }
}
