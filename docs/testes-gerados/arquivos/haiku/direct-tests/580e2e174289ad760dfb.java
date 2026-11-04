/*
 * @test
 * @summary Field.getInt on a volatile float field must throw IllegalArgumentException, reaching UnsafeQualifiedFloatFieldAccessorImpl.getInt
 * @run main UnsafeQualifiedFloatFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedFloatFieldAccessorImplWitupTest {

    public volatile float f = 1.5f;

    public static void main(String[] args) throws Exception {
        UnsafeQualifiedFloatFieldAccessorImplWitupTest t = new UnsafeQualifiedFloatFieldAccessorImplWitupTest();
        Field field = UnsafeQualifiedFloatFieldAccessorImplWitupTest.class.getDeclaredField("f");

        for (int i = 0; i < 3; i++) {
            boolean thrown = false;
            try {
                field.getInt(t);
            } catch (IllegalArgumentException expected) {
                thrown = true;
            }
            if (!thrown) {
                throw new RuntimeException("getInt on float field did not throw IllegalArgumentException");
            }
        }

        if (field.getFloat(t) != 1.5f) {
            throw new RuntimeException("getFloat returned unexpected value");
        }
    }
}
