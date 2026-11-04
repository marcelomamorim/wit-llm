/*
 * @test
 * @summary Field.getBoolean on a long field must reject the conversion with IllegalArgumentException while getLong still works
 * @run main UnsafeLongFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeLongFieldAccessorImplWitupTest {
    public long value = 42L;

    public static void main(String[] args) throws Exception {
        UnsafeLongFieldAccessorImplWitupTest holder = new UnsafeLongFieldAccessorImplWitupTest();
        Field field = UnsafeLongFieldAccessorImplWitupTest.class.getField("value");

        if (field.getLong(holder) != 42L) {
            throw new RuntimeException("getLong returned unexpected value");
        }

        boolean thrown = false;
        try {
            field.getBoolean(holder);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("getBoolean on long field did not throw IllegalArgumentException");
        }

        thrown = false;
        try {
            field.getBoolean(holder);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("repeated getBoolean on long field did not throw IllegalArgumentException");
        }
    }
}
