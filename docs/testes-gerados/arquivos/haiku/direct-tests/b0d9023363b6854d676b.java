/*
 * @test
 * @summary Verify that Field.getBoolean on a volatile float field is rejected by UnsafeQualifiedFloatFieldAccessorImpl with IllegalArgumentException
 * @run main UnsafeQualifiedFloatFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedFloatFieldAccessorImplWitupTest {

    public volatile float value = 1.5f;

    public static void main(String[] args) throws Exception {
        UnsafeQualifiedFloatFieldAccessorImplWitupTest t = new UnsafeQualifiedFloatFieldAccessorImplWitupTest();
        Field field = UnsafeQualifiedFloatFieldAccessorImplWitupTest.class.getField("value");

        boolean thrown = false;
        try {
            field.getBoolean(t);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("getBoolean on float field did not throw IllegalArgumentException");
        }

        if (field.getFloat(t) != 1.5f) {
            throw new RuntimeException("getFloat returned unexpected value");
        }
    }
}
