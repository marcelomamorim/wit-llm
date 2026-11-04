/*
 * @test
 * @summary Verify that Field.getLong on a float field throws IllegalArgumentException, the observable behavior of UnsafeQualifiedFloatFieldAccessorImpl.getLong
 * @run main UnsafeQualifiedFloatFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedFloatFieldAccessorImplWitupTest {

    volatile float f = 1.5f;

    public static void main(String[] args) throws Exception {
        UnsafeQualifiedFloatFieldAccessorImplWitupTest t = new UnsafeQualifiedFloatFieldAccessorImplWitupTest();
        Field field = UnsafeQualifiedFloatFieldAccessorImplWitupTest.class.getDeclaredField("f");

        float v = field.getFloat(t);
        if (v != 1.5f) {
            throw new RuntimeException("unexpected float value: " + v);
        }

        boolean thrown = false;
        try {
            field.getLong(t);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected IllegalArgumentException from getLong on float field");
        }
    }
}
