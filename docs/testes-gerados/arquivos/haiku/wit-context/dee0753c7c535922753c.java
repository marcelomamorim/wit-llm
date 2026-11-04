/*
 * @test
 * @summary Verify that Field.getBoolean on a static char field throws IllegalArgumentException via the public reflection API
 * @run main UnsafeQualifiedStaticCharacterFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticCharacterFieldAccessorImplWitupTest {

    public static char staticChar = 'x';

    public static void main(String[] args) throws Exception {
        Field f = UnsafeQualifiedStaticCharacterFieldAccessorImplWitupTest.class.getDeclaredField("staticChar");

        boolean thrown = false;
        try {
            f.getBoolean(null);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("getBoolean on char field did not throw IllegalArgumentException");
        }

        thrown = false;
        try {
            f.getBoolean(new UnsafeQualifiedStaticCharacterFieldAccessorImplWitupTest());
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("getBoolean with non-null obj did not throw IllegalArgumentException");
        }

        if (f.getChar(null) != 'x') {
            throw new RuntimeException("getChar returned unexpected value");
        }
    }
}
