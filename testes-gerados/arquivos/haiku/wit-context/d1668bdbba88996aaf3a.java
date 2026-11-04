/*
 * @test
 * @summary Field.getDouble on a static final boolean field must throw IllegalArgumentException
 * @run main UnsafeQualifiedStaticBooleanFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticBooleanFieldAccessorImplWitupTest {

    public static final boolean FLAG = true;

    public static void main(String[] args) throws Exception {
        Field f = UnsafeQualifiedStaticBooleanFieldAccessorImplWitupTest.class.getDeclaredField("FLAG");

        if (!f.getBoolean(null)) {
            throw new RuntimeException("control: getBoolean should return true");
        }

        boolean thrown = false;
        try {
            f.getDouble(null);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("getDouble on boolean static final field did not throw IllegalArgumentException");
        }

        thrown = false;
        try {
            f.getDouble(new Object());
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("getDouble with non-null obj did not throw IllegalArgumentException");
        }
    }
}
