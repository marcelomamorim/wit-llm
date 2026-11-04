/*
 * @test
 * @summary Verifies that Field.getByte on a volatile float field throws IllegalArgumentException, reaching UnsafeQualifiedFloatFieldAccessorImpl.getByte through the public reflection API
 * @run main UnsafeQualifiedFloatFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedFloatFieldAccessorImplWitupTest {

    public volatile float value = 1.5f;

    public static void main(String[] args) throws Exception {
        UnsafeQualifiedFloatFieldAccessorImplWitupTest holder = new UnsafeQualifiedFloatFieldAccessorImplWitupTest();
        Field f = UnsafeQualifiedFloatFieldAccessorImplWitupTest.class.getDeclaredField("value");

        boolean thrown = false;
        try {
            f.getByte(holder);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException from getByte on float field");
        }

        if (f.getFloat(holder) != 1.5f) {
            throw new RuntimeException("Float value changed unexpectedly");
        }
    }
}
