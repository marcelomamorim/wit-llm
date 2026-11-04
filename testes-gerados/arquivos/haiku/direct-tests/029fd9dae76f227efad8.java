/*
 * @test
 * @summary Verify that getFloat on a volatile boolean field is rejected through the public Field API
 * @run main UnsafeQualifiedBooleanFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedBooleanFieldAccessorImplWitupTest {

    public volatile boolean flag = true;

    public static void main(String[] args) throws Exception {
        UnsafeQualifiedBooleanFieldAccessorImplWitupTest t = new UnsafeQualifiedBooleanFieldAccessorImplWitupTest();
        Field f = UnsafeQualifiedBooleanFieldAccessorImplWitupTest.class.getField("flag");

        boolean thrown = false;
        try {
            f.getFloat(t);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("expected IllegalArgumentException from getFloat on boolean field");
        }

        if (!f.getBoolean(t)) {
            throw new AssertionError("getBoolean should return the current value true");
        }
    }
}
