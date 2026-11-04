/*
 * @test
 * @summary Verify that Field.getShort on a long field is rejected with IllegalArgumentException via UnsafeLongFieldAccessorImpl.getShort
 * @run main UnsafeLongFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeLongFieldAccessorImplWitupTest {
    public long value = 42L;

    public static void main(String[] args) throws Exception {
        UnsafeLongFieldAccessorImplWitupTest target = new UnsafeLongFieldAccessorImplWitupTest();
        Field field = UnsafeLongFieldAccessorImplWitupTest.class.getField("value");

        try {
            short s = field.getShort(target);
            throw new RuntimeException("Expected IllegalArgumentException, got short " + s);
        } catch (IllegalArgumentException expected) {
            // expected
        }

        if (field.getLong(target) != 42L) {
            throw new RuntimeException("getLong returned unexpected value");
        }

        target.value = 7L;
        try {
            field.getShort(target);
            throw new RuntimeException("Expected IllegalArgumentException after update");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
