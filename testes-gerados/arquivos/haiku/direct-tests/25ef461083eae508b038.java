/*
 * @test
 * @summary Field.getBoolean on a short field must throw IllegalArgumentException, reaching UnsafeShortFieldAccessorImpl.getBoolean
 * @run main UnsafeShortFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeShortFieldAccessorImplWitupTest {
    public short s = 7;

    public static void main(String[] args) throws Exception {
        UnsafeShortFieldAccessorImplWitupTest holder = new UnsafeShortFieldAccessorImplWitupTest();
        Field f = UnsafeShortFieldAccessorImplWitupTest.class.getField("s");

        if (f.getShort(holder) != 7) {
            throw new AssertionError("getShort should return 7");
        }

        try {
            f.getBoolean(holder);
            throw new AssertionError("expected IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            // expected: short fields do not support getBoolean
        }
    }
}
