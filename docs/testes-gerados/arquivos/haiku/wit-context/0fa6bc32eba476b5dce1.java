/*
 * @test
 * @summary Verifies that reading a boolean field as float through the public Field API is rejected with IllegalArgumentException, while getBoolean still works.
 * @run main UnsafeBooleanFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeBooleanFieldAccessorImplWitupTest {
    private boolean flag = true;

    public static void main(String[] args) throws Exception {
        UnsafeBooleanFieldAccessorImplWitupTest t = new UnsafeBooleanFieldAccessorImplWitupTest();
        Field f = UnsafeBooleanFieldAccessorImplWitupTest.class.getDeclaredField("flag");

        boolean caught = false;
        try {
            f.getFloat(t);
        } catch (IllegalArgumentException expected) {
            caught = true;
        }
        if (!caught) {
            throw new RuntimeException("Expected IllegalArgumentException from getFloat on boolean field");
        }

        if (f.getBoolean(t) != true) {
            throw new RuntimeException("getBoolean returned unexpected value");
        }
    }
}
