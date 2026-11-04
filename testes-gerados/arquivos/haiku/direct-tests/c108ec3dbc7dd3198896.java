/*
 * @test
 * @summary Verify that Field.getInt on a long field throws IllegalArgumentException, as UnsafeLongFieldAccessorImpl.getInt does
 * @run main UnsafeLongFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeLongFieldAccessorImplWitupTest {
    public long longField = 42L;

    public static void main(String[] args) throws Exception {
        UnsafeLongFieldAccessorImplWitupTest t = new UnsafeLongFieldAccessorImplWitupTest();
        Field f = UnsafeLongFieldAccessorImplWitupTest.class.getField("longField");

        boolean thrown = false;
        try {
            f.getInt(t);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Field.getInt on long field did not throw IllegalArgumentException");
        }

        if (f.getLong(t) != 42L) {
            throw new RuntimeException("getLong returned unexpected value");
        }
        if (((Long) f.get(t)).longValue() != 42L) {
            throw new RuntimeException("get returned unexpected value");
        }
    }
}
