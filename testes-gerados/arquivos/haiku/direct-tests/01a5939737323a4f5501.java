/*
 * @test
 * @summary Verify that Field.getInt on a static volatile boolean field is rejected by UnsafeQualifiedStaticBooleanFieldAccessorImpl.getInt with IllegalArgumentException
 * @run main UnsafeQualifiedStaticBooleanFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticBooleanFieldAccessorImplWitupTest {

    static volatile boolean flag = true;

    public static void main(String[] args) throws Exception {
        Field f = UnsafeQualifiedStaticBooleanFieldAccessorImplWitupTest.class.getDeclaredField("flag");

        if (!f.getBoolean(null)) {
            throw new RuntimeException("getBoolean returned false, expected true");
        }

        boolean thrown = false;
        try {
            int v = f.getInt(null);
            throw new RuntimeException("getInt did not throw, returned " + v);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("IllegalArgumentException not observed");
        }
    }
}
