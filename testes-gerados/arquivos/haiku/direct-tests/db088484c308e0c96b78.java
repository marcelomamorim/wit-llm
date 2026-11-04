/*
 * @test
 * @summary Verify that reading a volatile char field as short through java.lang.reflect.Field is rejected with IllegalArgumentException
 * @run main UnsafeQualifiedCharacterFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedCharacterFieldAccessorImplWitupTest {

    public volatile char c = 'A';

    public static void main(String[] args) throws Exception {
        UnsafeQualifiedCharacterFieldAccessorImplWitupTest t = new UnsafeQualifiedCharacterFieldAccessorImplWitupTest();
        Field f = UnsafeQualifiedCharacterFieldAccessorImplWitupTest.class.getDeclaredField("c");

        if (f.getChar(t) != 'A') {
            throw new RuntimeException("getChar returned unexpected value");
        }

        boolean thrown = false;
        try {
            f.getShort(t);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("getShort on char field did not throw IllegalArgumentException");
        }
    }
}
