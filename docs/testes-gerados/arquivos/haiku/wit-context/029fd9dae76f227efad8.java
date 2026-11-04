/*
 * @test
 * @summary Verify that Field.getFloat on a volatile boolean field (served by UnsafeQualifiedBooleanFieldAccessorImpl) throws IllegalArgumentException
 * @run main UnsafeQualifiedBooleanFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedBooleanFieldAccessorImplWitupTest {

    public volatile boolean flag = true;

    public static void main(String[] args) throws Exception {
        UnsafeQualifiedBooleanFieldAccessorImplWitupTest t = new UnsafeQualifiedBooleanFieldAccessorImplWitupTest();
        Field f = UnsafeQualifiedBooleanFieldAccessorImplWitupTest.class.getField("flag");

        if (f.getBoolean(t) != true) {
            throw new RuntimeException("getBoolean returned unexpected value");
        }

        boolean thrown = false;
        try {
            f.getFloat(t);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("getFloat did not throw IllegalArgumentException");
        }
    }
}
