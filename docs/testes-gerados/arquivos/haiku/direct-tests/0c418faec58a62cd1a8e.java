/*
 * @test
 * @summary Field.getByte on a long field must throw IllegalArgumentException through UnsafeLongFieldAccessorImpl.getByte, while getLong still returns the stored value
 * @run main UnsafeLongFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeLongFieldAccessorImplWitupTest {

    long value = 42L;

    public static void main(String[] args) throws Exception {
        UnsafeLongFieldAccessorImplWitupTest t = new UnsafeLongFieldAccessorImplWitupTest();
        Field f = UnsafeLongFieldAccessorImplWitupTest.class.getDeclaredField("value");

        boolean thrown = false;
        try {
            f.getByte(t);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException from getByte on long field");
        }

        if (f.getLong(t) != 42L) {
            throw new RuntimeException("getLong returned unexpected value");
        }

        f.setLong(t, -7L);
        thrown = false;
        try {
            f.getByte(t);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException after setLong");
        }
        if (f.getLong(t) != -7L) {
            throw new RuntimeException("Value changed unexpectedly after failed getByte");
        }
    }
}
