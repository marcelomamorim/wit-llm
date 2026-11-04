/*
 * @test
 * @summary Verify that Field.getBoolean on a static volatile byte field throws IllegalArgumentException via the qualified static byte accessor
 * @run main UnsafeQualifiedStaticByteFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticByteFieldAccessorImplWitupTest {

    static volatile byte b = 5;

    public static void main(String[] args) throws Exception {
        Field f = UnsafeQualifiedStaticByteFieldAccessorImplWitupTest.class.getDeclaredField("b");

        boolean thrown = false;
        try {
            f.getBoolean(null);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException from getBoolean");
        }

        if (f.getByte(null) != 5) {
            throw new RuntimeException("getByte should still return the field value");
        }
    }
}
