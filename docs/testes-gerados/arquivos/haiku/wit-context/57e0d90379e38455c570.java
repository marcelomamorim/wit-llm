/*
 * @test
 * @summary Verifies that Field.getDouble on a boolean instance field throws IllegalArgumentException, exercising UnsafeBooleanFieldAccessorImpl.getDouble
 * @run main UnsafeBooleanFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeBooleanFieldAccessorImplWitupTest {

    private boolean flag = true;

    public static void main(String[] args) throws Exception {
        UnsafeBooleanFieldAccessorImplWitupTest obj = new UnsafeBooleanFieldAccessorImplWitupTest();
        Field f = UnsafeBooleanFieldAccessorImplWitupTest.class.getDeclaredField("flag");

        boolean thrown = false;
        try {
            f.getDouble(obj);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected IllegalArgumentException from getDouble on boolean field (true)");
        }

        obj.flag = false;
        thrown = false;
        try {
            f.getDouble(obj);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected IllegalArgumentException from getDouble on boolean field (false)");
        }

        if (f.getBoolean(obj)) {
            throw new AssertionError("Boolean field value should remain false");
        }
    }
}
