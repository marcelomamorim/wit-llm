/*
 * @test
 * @summary Verifies that Field.getChar on a boolean instance field throws IllegalArgumentException via UnsafeBooleanFieldAccessorImpl.getChar
 * @run main UnsafeBooleanFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeBooleanFieldAccessorImplWitupTest {
    public boolean flag = true;

    public static void main(String[] args) throws Exception {
        UnsafeBooleanFieldAccessorImplWitupTest obj = new UnsafeBooleanFieldAccessorImplWitupTest();
        Field f = UnsafeBooleanFieldAccessorImplWitupTest.class.getField("flag");

        if (!f.getBoolean(obj)) {
            throw new AssertionError("getBoolean should return the current value");
        }

        boolean thrown = false;
        try {
            f.getChar(obj);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("expected IllegalArgumentException from getChar on boolean field");
        }
    }
}
