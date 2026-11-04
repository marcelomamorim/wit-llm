/*
 * @test
 * @summary Checks short field reads through the public java.lang.reflect.Field API, which dispatches to UnsafeShortFieldAccessorImpl.getShort
 * @run main UnsafeShortFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeShortFieldAccessorImplWitupTest {
    short value = 1234;

    static void fail(String msg) {
        throw new RuntimeException(msg);
    }

    public static void main(String[] args) throws Exception {
        UnsafeShortFieldAccessorImplWitupTest t = new UnsafeShortFieldAccessorImplWitupTest();
        Field f = UnsafeShortFieldAccessorImplWitupTest.class.getDeclaredField("value");

        if (f.getShort(t) != 1234) {
            fail("getShort initial value mismatch");
        }

        f.setShort(t, (short) -7);
        if (f.getShort(t) != -7) {
            fail("getShort after setShort mismatch");
        }

        if (f.getInt(t) != -7) {
            fail("getInt widening of short field mismatch");
        }

        f.set(t, Short.valueOf((short) 42));
        if (f.getShort(t) != 42) {
            fail("getShort after set(Short) mismatch");
        }

        try {
            f.getShort(new Object());
            fail("expected IllegalArgumentException for object of unrelated class");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
