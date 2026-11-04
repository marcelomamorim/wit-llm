/*
 * @test
 * @summary Field.getInt on a public long field must throw IllegalArgumentException, reaching UnsafeLongFieldAccessorImpl.getInt
 * @run main UnsafeLongFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeLongFieldAccessorImplWitupTest {
    public long value = 42L;

    public static void main(String[] args) throws Exception {
        UnsafeLongFieldAccessorImplWitupTest t = new UnsafeLongFieldAccessorImplWitupTest();
        Field f = UnsafeLongFieldAccessorImplWitupTest.class.getField("value");

        if (f.getLong(t) != 42L) {
            throw new AssertionError("getLong should return the stored long value");
        }

        boolean thrown = false;
        try {
            f.getInt(t);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("getInt on a long field must throw IllegalArgumentException");
        }
    }
}
