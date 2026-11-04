/*
 * @test
 * @summary Verifies that Field.getInt on a boolean field throws IllegalArgumentException
 * @run main UnsafeBooleanFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeBooleanFieldAccessorImplWitupTest {
    public boolean flag = true;

    public static void main(String[] args) throws Exception {
        Field f = UnsafeBooleanFieldAccessorImplWitupTest.class.getDeclaredField("flag");
        UnsafeBooleanFieldAccessorImplWitupTest target = new UnsafeBooleanFieldAccessorImplWitupTest();
        boolean thrown = false;
        try {
            f.getInt(target);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected IllegalArgumentException from getInt on boolean field");
        }
    }
}
