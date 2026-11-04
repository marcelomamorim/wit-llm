/*
 * @test
 * @summary Field.getLong on a boolean field must throw IllegalArgumentException via the boolean accessor, while getBoolean still works
 * @run main UnsafeBooleanFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeBooleanFieldAccessorImplWitupTest {
    public boolean flag = true;

    public static void main(String[] args) throws Exception {
        UnsafeBooleanFieldAccessorImplWitupTest t = new UnsafeBooleanFieldAccessorImplWitupTest();
        Field f = UnsafeBooleanFieldAccessorImplWitupTest.class.getField("flag");

        boolean threw = false;
        try {
            f.getLong(t);
        } catch (IllegalArgumentException expected) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("Expected IllegalArgumentException from getLong on boolean field");
        }

        if (!f.getBoolean(t)) {
            throw new RuntimeException("getBoolean should return true for flag");
        }
    }
}
