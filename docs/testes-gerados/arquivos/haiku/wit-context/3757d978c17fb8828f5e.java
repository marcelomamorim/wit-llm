/*
 * @test
 * @summary Verify that reading a boolean field as int through the public Field API throws IllegalArgumentException
 * @run main UnsafeQualifiedBooleanFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedBooleanFieldAccessorImplWitupTest {

    public boolean flag = true;

    public static void main(String[] args) throws Exception {
        UnsafeQualifiedBooleanFieldAccessorImplWitupTest obj = new UnsafeQualifiedBooleanFieldAccessorImplWitupTest();
        Field f = UnsafeQualifiedBooleanFieldAccessorImplWitupTest.class.getField("flag");

        boolean control = f.getBoolean(obj);
        if (!control) {
            throw new AssertionError("getBoolean control read returned false");
        }

        boolean thrown = false;
        try {
            f.getInt(obj);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("getInt on boolean field did not throw IllegalArgumentException");
        }
    }
}
