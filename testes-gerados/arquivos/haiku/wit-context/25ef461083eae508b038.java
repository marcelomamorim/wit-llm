/*
 * @test
 * @summary Verify that Field.getBoolean on a short field is rejected with IllegalArgumentException via UnsafeShortFieldAccessorImpl.getBoolean
 * @run main UnsafeShortFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeShortFieldAccessorImplWitupTest {
    public short value = 7;

    public static void main(String[] args) throws Exception {
        UnsafeShortFieldAccessorImplWitupTest target = new UnsafeShortFieldAccessorImplWitupTest();
        Field field = UnsafeShortFieldAccessorImplWitupTest.class.getField("value");

        if (field.getShort(target) != 7) {
            throw new RuntimeException("getShort returned unexpected value");
        }

        try {
            field.getBoolean(target);
            throw new RuntimeException("expected IllegalArgumentException from getBoolean on short field");
        } catch (IllegalArgumentException expected) {
        }
    }
}
