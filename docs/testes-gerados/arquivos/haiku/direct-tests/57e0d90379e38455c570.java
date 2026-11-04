/*
 * @test
 * @summary Verifies that Field.getDouble on a boolean field throws IllegalArgumentException via UnsafeBooleanFieldAccessorImpl.getDouble
 * @run main UnsafeBooleanFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeBooleanFieldAccessorImplWitupTest {

    public boolean flag = true;

    public static void main(String[] args) throws Exception {
        UnsafeBooleanFieldAccessorImplWitupTest t = new UnsafeBooleanFieldAccessorImplWitupTest();
        Field f = UnsafeBooleanFieldAccessorImplWitupTest.class.getField("flag");

        if (f.getBoolean(t) != true) {
            throw new AssertionError("getBoolean should return true");
        }

        boolean thrown = false;
        try {
            f.getDouble(t);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("getDouble on boolean field must throw IllegalArgumentException");
        }

        thrown = false;
        try {
            f.getDouble(t);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("getDouble must be repeatable and keep throwing");
        }
    }
}
