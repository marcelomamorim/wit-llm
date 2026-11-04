/*
 * @test
 * @summary Verify that reading a volatile boolean field as short through the public Field API throws IllegalArgumentException, and that getBoolean still works
 * @run main UnsafeQualifiedBooleanFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedBooleanFieldAccessorImplWitupTest {

    public static class Holder {
        public volatile boolean flag = true;
    }

    public static void main(String[] args) throws Exception {
        Holder h = new Holder();
        Field f = Holder.class.getField("flag");

        if (f.getBoolean(h) != true) {
            throw new AssertionError("getBoolean should return true");
        }

        try {
            short s = f.getShort(h);
            throw new AssertionError("getShort should have thrown IllegalArgumentException, returned " + s);
        } catch (IllegalArgumentException expected) {
            // expected
        }

        try {
            byte b = f.getByte(h);
            throw new AssertionError("getByte should have thrown IllegalArgumentException, returned " + b);
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
