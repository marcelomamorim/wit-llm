/*
 * @test
 * @summary Verify that Field.getLong on a static final float field is rejected with IllegalArgumentException
 * @run main UnsafeQualifiedStaticFloatFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticFloatFieldAccessorImplWitupTest {

    public static final float VALUE = 1.5f;

    public static void main(String[] args) throws Exception {
        Field field = UnsafeQualifiedStaticFloatFieldAccessorImplWitupTest.class.getField("VALUE");

        float f = field.getFloat(null);
        if (f != 1.5f) {
            throw new RuntimeException("Unexpected getFloat result: " + f);
        }

        boolean thrown = false;
        try {
            field.getLong(null);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("getLong on static final float field did not throw IllegalArgumentException");
        }
    }
}
